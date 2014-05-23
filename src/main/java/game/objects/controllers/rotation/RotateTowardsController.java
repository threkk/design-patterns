package game.objects.controllers.rotation;

import org.jrabbit.base.graphics.transforms.Vector2f;
import static org.jrabbit.base.managers.Resources.*;
import org.jrabbit.standard.game.objects.base.BaseSprite;

import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A RotateTowardsController will rotate a BaseSprite towards a targeted 
 * Vector2f. It can be used to make sprites always aim themselves at other 
 * sprites, etc.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class RotateTowardsController extends TargetedController<BaseSprite>
{
	/**
	 * The Vector2f to aim towards.
	 **/
	protected Vector2f targetLoc;
	
	/**
	 * The rate at which the RotateTowardsController will be rotated.
	 **/
	protected float rotateSpeed;

	/*************************************************************************
	 * Creates a RotateTowardsController.
	 * 
	 * @param target
	 * 			  The BaseSprite to rotate.
	 * @param targetLoc
	 * 			  The location Vector2f to rotate towards.
	 * @param rotationSpeed
	 * 			  The rate at which to rotate.
	 *************************************************************************/
	public RotateTowardsController(BaseSprite target, Vector2f targetLoc, 
			float rotationSpeed)
	{
		this(target, targetLoc, rotationSpeed, 0);
	}

	/*************************************************************************
	 * Creates a RotateTowardsController.
	 * 
	 * @param target
	 * 			  The BaseSprite to rotate.
	 * @param targetLoc
	 * 			  The location Vector2f to rotate towards.
	 * @param rotationSpeed
	 * 			  The rate at which to rotate.
	 * @param variance
	 * 			  The percentage of randomization to apply to the rotation 
	 * 			  speed.
	 *************************************************************************/
	public RotateTowardsController(BaseSprite target, Vector2f targetLoc, 
			float rotationSpeed, float variance)
	{
		super(target);
		this.targetLoc = targetLoc;
		this.rotateSpeed = rotationSpeed * (1f + (random().nextFloat() - 0.5f) 
				* variance);
	}

	/*************************************************************************
	 * Updates the RotateTowardsController.
	 * 
	 * @param delta
	 * 			  The amount of time that has elapsed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(targetLoc != null)
			target.rotation().rotateTowards(target.location(), targetLoc, 
					rotateSpeed * delta);
	}
}