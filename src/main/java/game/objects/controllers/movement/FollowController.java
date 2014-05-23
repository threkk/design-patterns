package game.objects.controllers.movement;

import org.jrabbit.base.graphics.transforms.Vector2f;
import static org.jrabbit.base.managers.Resources.*;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A FollowController controls a PhysSprite and attempts to make it stay within
 * a certain radius of a Vector2f. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class FollowController extends TargetedController<PhysSprite>
{
	/**
	 * The Vector2f to follow.
	 **/
	protected Vector2f targetLoc;
	
	/**
	 * The minimum distance the FollowController attempts to stay within. Once
	 * this radius is passed, the FollowController does nothing.
	 **/
	protected float minDist;
	
	/**
	 * The maximum distance the FollowController will allow itself to be away
	 * from the target Vector2f; if this distance is exceeded it will move 
	 * towards the target with maximum possible force.
	 **/
	protected float maxDist;
	
	/**
	 * The maximum force the FollowController can use to move its target.
	 **/
	protected float maxForce;

	/*************************************************************************
	 * Creates a FollowController.
	 * 
	 * @param target
	 * 			  The PhysSprite to move.
	 * @param targetLoc
	 * 			  The Vector2f to follow.
	 * @param minDist
	 * 			  The minimum distance to follow in.
	 * @param maxDist
	 * 			  The maximum distance to follow in.
	 * @param maxForce
	 * 			  The maximum force for the FollowController to use.
	 *************************************************************************/
	public FollowController(PhysSprite target, Vector2f targetLoc, 
			float minDist, float maxDist, float maxForce)
	{
		this(target, targetLoc, minDist, maxDist, maxForce, 0);
	}

	/*************************************************************************
	 * Creates a FollowController.
	 * 
	 * @param target
	 * 			  The PhysSprite to move.
	 * @param targetLoc
	 * 			  The Vector2f to follow.
	 * @param minDist
	 * 			  The minimum distance to follow in.
	 * @param maxDist
	 * 			  The maximum distance to follow in.
	 * @param maxForce
	 * 			  The maximum force for the FollowController to use.
	 * @param variance
	 * 			  The percentage of randomization to apply to the final 
	 * 			  settings.
	 *************************************************************************/
	public FollowController(PhysSprite target, Vector2f targetLoc, 
			float minDist, float maxDist, float maxForce, float variance)
	{
		super(target);
		this.targetLoc = targetLoc;
		this.minDist = minDist * (1f + (random().nextFloat() - 0.5f) * variance);
		this.maxDist = maxDist * (1f + (random().nextFloat() - 0.5f) * variance);
		this.maxForce = maxForce * (1f + (random().nextFloat() - 0.5f) * variance);
	}

	/*************************************************************************
	 * Updates the FollowController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(targetLoc != null)
		{
			float dist = target.location().distanceTo(targetLoc) - minDist;
			if(dist > 0)
			{
				target.force().add(target.location().unitVectorTowards(targetLoc), 
						Math.min(1f, dist / maxDist) * maxForce);
			}
		}
	}
}