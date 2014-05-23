package game.objects.entities.controllers.health;

import static org.jrabbit.base.managers.Resources.random;

import game.objects.controllers.TargetedController;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A DamagingController damages a MatrixEntity over time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DamagingController extends TargetedController<MatrixEntity>
{
	/**
	 * The damage dealt per microsecond.
	 **/
	protected float damageRate;
	
	/**
	 * The amount of time left for this DamagingController.
	 **/
	protected int timeLeft;

	/*************************************************************************
	 * Creates a DamagingController.
	 * 
	 * @param target
	 * 			  The MatrixEntity to damage.
	 * @param damageRate
	 * 			  The amount of damage to deal per microsecond.
	 * @param duration
	 * 			  The amount of time that this DamagingController should last.
	 *************************************************************************/
	public DamagingController(MatrixEntity target, float damageRate, int duration)
	{
		this(target, damageRate, duration, 0);
	}

	/*************************************************************************
	 * Creates a DamagingController.
	 * 
	 * @param target
	 * 			  The MatrixEntity to damage.
	 * @param damageRate
	 * 			  The amount of damage to deal per microsecond.
	 * @param duration
	 * 			  The amount of time that this DamagingController should last.
	 * @param variance
	 * 			  The percentage of randomization to apply to settings.
	 *************************************************************************/
	public DamagingController(MatrixEntity owner, float damageRate, int duration, 
			float variance)
	{
		super(owner);
		this.damageRate = damageRate * (1f + (random().nextFloat() - 0.5f) * variance);
		timeLeft = (int) (duration * (1f + (random().nextFloat() - 0.5f) * variance));
	}

	/*************************************************************************
	 * Damages the MatrixEntity.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timeLeft -= delta;
		if(timeLeft <= 0)
		{
			if(timeLeft > -delta);
				target.damage(damageRate * (delta + timeLeft));
			kill();
		}
		else
			target.damage(damageRate * delta);
	}
}