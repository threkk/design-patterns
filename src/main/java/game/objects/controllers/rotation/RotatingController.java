package game.objects.controllers.rotation;

import org.jrabbit.base.graphics.types.Rotated;
import static org.jrabbit.base.managers.Resources.*;

import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A RotatingController rotates a Rotated object over time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class RotatingController extends TargetedController<Rotated>
{
	/**
	 * The rate of rotation.
	 **/
	protected float rotationSpeed;

	/*************************************************************************
	 * Creates a RotatingController.
	 * 
	 * @param target
	 * 			  The Rotated object that will be rotated.
	 * @param rotationSpeed
	 * 			  The rate of rotation.
	 *************************************************************************/
	public RotatingController(Rotated target, float rotationSpeed)
	{
		this(target, rotationSpeed, 0, false);
	}

	/*************************************************************************
	 * Creates a RotatingController.
	 * 
	 * @param target
	 * 			  The Rotated object that will be rotated.
	 * @param rotationSpeed
	 * 			  The base rate of rotation.
	 * @param variance
	 * 			  The percentage of randomization that will be applied to the
	 * 			  final rotation speed.
	 *************************************************************************/
	public RotatingController(Rotated target, float rotationSpeed, float variance)
	{
		this(target, rotationSpeed, variance, false);
	}

	/*************************************************************************
	 * Creates a RotatingController.
	 * 
	 * @param target
	 * 			  The Rotated object that will be rotated.
	 * @param rotationSpeed
	 * 			  The base rate of rotation.
	 * @param variance
	 * 			  The percentage of randomization that will be applied to the
	 * 			  final rotation speed.
	 * @param allowReverse
	 * 			  The base rate of rotation.
	 *************************************************************************/
	public RotatingController(Rotated target, float rotationSpeed, float variance, 
			boolean allowReverse)
	{
		super(target);
		this.rotationSpeed = rotationSpeed * (1f + (random().nextFloat() - 0.5f)
				* variance);
		if(allowReverse && random().nextBoolean())
			this.rotationSpeed *= -1f;
	}

	/*************************************************************************
	 * Updates the RotatingController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		target.rotation().rotate(rotationSpeed * delta);
	}
}