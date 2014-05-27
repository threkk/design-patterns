package game.objects.controllers.movement;

import org.jrabbit.base.graphics.transforms.Vector2f;
import static org.jrabbit.base.managers.Resources.*;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A HomingController is designed to push a PhysSprite towards a target 
 * Vector2f.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HomingController extends TargetedController<PhysSprite>
{
	/**
	 * The Vector2f to move towards.
	 **/
	protected Vector2f targetLoc;
	
	/**
	 * The amount of force to use when moving.
	 **/
	protected float force;

	/*************************************************************************
	 * Creates a HomingController.
	 * 
	 * @param target
	 * 			  The PhysSprite to control.
	 * @param targetLoc
	 * 			  The Vector2f to move towards.
	 * @param force
	 * 			  The amount of force to use when pushing the PhysSprite.
	 *************************************************************************/
	public HomingController(PhysSprite target, Vector2f targetLoc, float force)
	{
		this(target, targetLoc, force, 0);
	}

	/*************************************************************************
	 * Creates a HomingController.
	 * 
	 * @param target
	 * 			  The PhysSprite to control.
	 * @param targetLoc
	 * 			  The Vector2f to move towards.
	 * @param force
	 * 			  The amount of force to use when pushing the PhysSprite.
	 * @param variance
	 * 			  The percentage of randomization to use on the final force.
	 *************************************************************************/
	public HomingController(PhysSprite target, Vector2f targetLoc, float force,
			float variance)
	{
		super(target);
		this.targetLoc = targetLoc;
		this.force = force * (1f + (random().nextFloat() - 0.5f) * variance);
	}

	/*************************************************************************
	 * Updates the HomingController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(targetLoc != null)
			target.force().add(target.location().unitVectorTowards(targetLoc), 
					delta * force);
	}
}