package game.objects.entities.controllers.color;

import org.jrabbit.base.graphics.types.Colored;

import game.objects.controllers.TargetedController;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A HealthColorController provides a simple controller that will continuously
 * reset an Object's color based on the health of a MatrixEntity.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class HealthColorController extends TargetedController<Colored>
{
	/**
	 * The object whose health levels define the color.
	 **/
	protected MatrixEntity source;
	
	/*************************************************************************
	 * Creates a HealthColorController.
	 * 
	 * @param target
	 * 			  The MatrixEntity whose color should be linked with its health.
	 *************************************************************************/
	public HealthColorController(MatrixEntity target)
	{
		this(target, target);
	}

	/*************************************************************************
	 * Creates a HealthColorController.
	 * 
	 * @param source
	 * 			  The MatrixEntity whose health levels should determine the 
	 * 			  resulting color.
	 * @param target
	 * 			  The Colored object whose color should managed.
	 *************************************************************************/
	public HealthColorController(MatrixEntity source, Colored target)
	{
		super(target);
		this.source = source;
	}
	
	/*************************************************************************
	 * Sets the Color of the target based upon the indicated health level.
	 * 
	 * @param percent
	 * 			  The percentage of its total health that the base MatrixEntity
	 * 			  has.
	 *************************************************************************/
	protected abstract void setColor(float percent);
	
	/*************************************************************************
	 * Updates the HealthColorController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		setColor(source.healthPercentage());
	}
}
