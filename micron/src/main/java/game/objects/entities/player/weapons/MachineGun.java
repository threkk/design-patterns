package game.objects.entities.player.weapons;

import game.objects.effects.projectiles.Bullet;
import game.objects.entities.base.KeyPointEntity;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.lwjgl.input.Keyboard;

import settings.MicronGameSettings;

import static org.jrabbit.base.managers.Resources.*;

/*****************************************************************************
 * A MachineGun rapidly fires bullets when the left mouse button is held down.
 * 
 * Additionally, the closer the mouse is to the owner, the more inaccurate the
 * bullets are. This allows more control over the spread of the bullets.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MachineGun implements PlayerWeapon
{
	/**
	 * The delay, in microseconds, between each bullet.
	 **/
	protected static final int SHOT_INTERVAL = 500;
	
	/**
	 * The minimum inaccuracy of each bullet, in degrees.
	 **/
	protected static final float BASE_INACCURACY = 3f;
	
	/**
	 * The additional inaccuracy that the bullets will gain as the mouse moves
	 * further away from the owner.
	 **/
	protected static final float ADD_INACCURACY = 77f;
	
	/**
	 * The minimum distance the mouse can be away and still affect inaccuracy.
	 **/
	protected static final float MIN_INACCURACY_RAD = 150f;
	
	/**
	 * The the distance past the minimum inaccuracy radius that will affect
	 * inaccuracy.
	 **/
	protected static final float ADD_INACCURACY_RAD = 500f;
	
	/**
	 * The velocity of all bullets.
	 **/
	protected static final float BULLET_VELOCITY = 0.22f;
	
	/**
	 * The minimum number of microseconds that a bullet can remain alive.
	 **/
	protected static final int BULLET_LIFE_BASE = 2500;
	
	/**
	 * The number of microseconds above the minimum that each bullet can be 
	 * alive.
	 **/
	protected static final int BULLET_LIFE_RAND = 2500;
	
	/**
	 * The damage each bullet deals.
	 **/
	protected static final float BULLET_DAMAGE = 12;
	
	/**
	 * Keeps track of the time since the last bullet was fired.
	 **/
	protected int timer;
	
	/**
	 * Keeps track of which keypoint the next bullet should come from.
	 **/
	protected boolean phase;
	
	/**
	 * The KeyPointEntity that will be the source of all bullets.
	 **/
	protected KeyPointEntity owner;

	/*************************************************************************
	 * Creates a machine gun.
	 * 
	 * @param owner
	 * 			  The KeyPointEntity that will be the source of all bullets.
	 *************************************************************************/
	public MachineGun(KeyPointEntity owner)
	{
		this.owner = owner;
	}

	/*************************************************************************
	 * Tells the MachineGun that it is now the active weapon.
	 * 
	 * Note: This doesn't need to do anything, so it doesn't.
	 ***************************************************************/ @Override
	public void activate() { }

	/*************************************************************************
	 * Tells the MachineGun that it is no longer the active weapon.
	 * 
	 * Note: This doesn't need to do anything, so it doesn't.
	 ***************************************************************/ @Override
	public void deactivate() { }

	/*************************************************************************
	 * Fires a bullet at the indicated location and rotation.
	 * 
	 * @param location
	 * 			  The Vector2f from which the bullet is being fired.
	 * @param rotation
	 * 			  The base angle at which the bullet is being fired.
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		float inaccuracy = BASE_INACCURACY + ADD_INACCURACY;
		if(MicronGameSettings.userData().useLaptopControls())
			inaccuracy -= ADD_INACCURACY * 0.5f;
		else
		{
			float dist = owner.location().distanceTo(MouseHandler.location(false));
			dist = Math.max(dist -= MIN_INACCURACY_RAD, 0);
			inaccuracy -= Math.min(1f, dist / ADD_INACCURACY_RAD) * ADD_INACCURACY;
		}
		rotation.rotate((random().nextFloat() - 0.5f) * inaccuracy);
		owner.matrix().add(new Bullet(owner, location,  rotation, Color.GREEN,
				19, 10,  BULLET_VELOCITY, BULLET_LIFE_BASE + 
				random().nextInt(BULLET_LIFE_RAND), 7f, 2f, true));
	}

	/*************************************************************************
	 * Updates the MachineGun.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timer = Math.min(timer + delta, SHOT_INTERVAL);
		if(MouseHandler.isButtonDown(0) || KeyboardHandler.isKeyDown(Keyboard.KEY_SPACE))
			while(timer >= SHOT_INTERVAL)
			{
				timer -= SHOT_INTERVAL;
				fireAt(owner.pointAt((phase = !phase) ? 1 : 2), 
						owner.rotation().copy());
			}
	}
}