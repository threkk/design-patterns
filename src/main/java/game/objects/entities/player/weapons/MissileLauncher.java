package game.objects.entities.player.weapons;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.base.graphics.skins.animation.AnimationFactory;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.lwjgl.input.Keyboard;

import game.objects.death.DeathBurstEffect;
import game.objects.effects.base.MatrixEffect;
import game.objects.effects.effects.ExplosiveDeathEffect;
import game.objects.effects.projectiles.HomingBullet;
import game.objects.entities.base.KeyPointEntity;

/*****************************************************************************
 * A MissileLauncher fires explosive missiles periodically while the left mouse
 * button is held down. These missiles can be steered with the mouse while they
 * fly.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MissileLauncher implements PlayerWeapon
{
	/**
	 * The duration between missile launches.
	 **/
	protected static final int SHOT_INTERVAL = 20000;
	
	/**
	 * The speed at which the missile can travel.
	 **/
	protected static final float MISSILE_VELOCITY = 0.1f;
	
	/**
	 * The number of milliseconds the missile can be active before it 
	 * automatically explodes.
	 **/
	protected static final int MISSILE_LIFETIME = 13000;
	
	/**
	 * The rate at which missiles will rotate towards the mouse cursor.
	 **/
	protected static final float MISSILE_TURN_RATE = 0.075f;
	
	/**
	 * Keeps track of the time since the last missile was fired.
	 **/
	protected int timer;
	
	/**
	 * The KeyPointEntity that will be the source of all bullets.
	 **/
	protected KeyPointEntity owner;

	/*************************************************************************
	 * Creates a missile launcher.
	 * 
	 * @param owner
	 * 			  The KeyPointEntity that will be the source of all bullets.
	 *************************************************************************/
	public MissileLauncher(KeyPointEntity owner)
	{
		this.owner = owner;
		timer = SHOT_INTERVAL;
	}

	/*************************************************************************
	 * Tells the MissileLauncher that it is now the active weapon.
	 * 
	 * Note: This doesn't need to do anything, so it doesn't.
	 ***************************************************************/ @Override
	public void activate() { }

	/*************************************************************************
	 * Tells the MissileLauncher that it is no longer the active weapon.
	 * 
	 * Note: This doesn't need to do anything, so it doesn't.
	 ***************************************************************/ @Override
	public void deactivate() { }

	/*************************************************************************
	 * Fires a missile at the indicated location and rotation.
	 * 
	 * @param location
	 * 			  The Vector2f from which the missile is being fired.
	 * @param rotation
	 * 			  The base angle at which the missile is being fired.
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		AnimatedSkin missileSkin = AnimationFactory.createLoop(	"Missile 1", 
																"Missile 2", 
																"Missile 3");
		MatrixEffect missile = new HomingBullet(missileSkin, owner,
				location, rotation, Color.ORANGE, MouseHandler.location(false),
				MISSILE_TURN_RATE, 1f, 0.2f, MISSILE_LIFETIME, 100, 10, true);
		missile.addDeathEffects(new ExplosiveDeathEffect(6, 0.1f, 3.5f, true),
								new DeathBurstEffect(Color.ORANGE, 150, 0.05f));
		owner.matrix().add(missile);
	}

	/*************************************************************************
	 * Updates the MissileLauncher.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timer = Math.min(timer + delta, SHOT_INTERVAL);
		while((MouseHandler.isButtonDown(0) || KeyboardHandler.isKeyDown(Keyboard.KEY_SPACE))
				&& timer >= SHOT_INTERVAL)
		{
			timer -= SHOT_INTERVAL;
			fireAt(owner.pointAt(0), owner.rotation());
		}
	}
}