package game.objects.entities.controllers.health;

import static org.jrabbit.base.managers.Resources.*;

import game.objects.controllers.TargetedController;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A HealingController heals a MatrixEntity over time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HealingController extends TargetedController<MatrixEntity>
{
	/**
	 * The amount to heal per microsecond.
	 **/
	protected float healRate;
	
	/**
	 * The amount of time left for this HealingController.
	 **/
	protected int timeLeft;

	/*************************************************************************
	 * Creates a HealingController.
	 * 
	 * @param target
	 * 			  The MatrixEntity to heal.
	 * @param damageRate
	 * 			  The amount to heal per microsecond.
	 * @param duration
	 * 			  The amount of time that this HealingController should last.
	 *************************************************************************/
	public HealingController(MatrixEntity owner, float healRate, int duration)
	{
		this(owner, healRate, duration, 0);
	}

	/*************************************************************************
	 * Creates a HealingController.
	 * 
	 * @param target
	 * 			  The MatrixEntity to heal.
	 * @param damageRate
	 * 			  The amount to heal per microsecond.
	 * @param duration
	 * 			  The amount of time that this HealingController should last.
	 * @param variance
	 * 			  The percentage of randomization to apply to settings.
	 *************************************************************************/
	public HealingController(MatrixEntity owner, float healRate, int duration,
			float variance)
	{
		super(owner);
		this.healRate = healRate * (1f + (random().nextFloat() - 0.5f) * variance);
		timeLeft = (int) (duration * (1f + (random().nextFloat() - 0.5f) * variance));
	}

	/*************************************************************************
	 * Heals the MatrixEntity.
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
				target.heal(healRate * (delta + timeLeft));
			kill();
		}
		else
			target.heal(healRate * delta);
	}
}