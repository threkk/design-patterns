package game.objects.controllers.movement;

import static org.jrabbit.base.managers.Resources.*;

import org.jrabbit.base.graphics.transforms.Vector2f;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A StrafeController controls a PhysSprite and makes it move in a circular,
 * strafing pattern around a targeted Vector2f. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class StrafeController extends TargetedController<PhysSprite>
{
	/**
	 * The Vector2f to strafe.
	 **/
	protected Vector2f targetLoc;
	
	/**
	 * The amount of force to apply when strafing.
	 **/
	protected float force;
	
	/**
	 * True for clockwise movement, false for counter-clockwise.
	 **/
	protected boolean direction;
	
	/*************************************************************************
	 * Creates a StrafeController. It will be randomly set to either move 
	 * clockwise or counter-clockwise.
	 * 
	 * @param target
	 * 			  The PhysSprite to move.
	 * @param targetLoc
	 * 			  The Vector2f to strafe.
	 * @param force
	 * 			  The amount of force to apply when strafing.
	 *************************************************************************/
	public StrafeController(PhysSprite target, Vector2f targetLoc, float force)
	{
		this(target, targetLoc, force, random().nextBoolean());
	}

	/*************************************************************************
	 * Creates a StrafeController.
	 * 
	 * @param target
	 * 			  The PhysSprite to move.
	 * @param targetLoc
	 * 			  The Vector2f to strafe.
	 * @param force
	 * 			  The amount of force to apply when strafing.
	 * @param direction
	 * 			  True if this should strafe in a clockwise manner, false for
	 * 			  counter-clockwise.
	 *************************************************************************/
	public StrafeController(PhysSprite target, Vector2f targetLoc, float force,
			boolean direction)
	{
		super(target);
		this.targetLoc = targetLoc;
		this.force = force;
		this.direction = direction;
	}

	/*************************************************************************
	 * Updates the StrafeController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		target.force().addPolar(force, (float) Math.toRadians(
				target.location().angleTowards(targetLoc) + (direction ? -90 : 90)));
	}
}