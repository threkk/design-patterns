package game.objects.controllers.life;

import static org.jrabbit.base.managers.Resources.*;

import game.objects.Killable;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A LifetimeController forces a Killable object to only live for a certain 
 * period of time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class LifetimeController extends TargetedController<Killable> 
{
	/**
	 * The amount of time left that the object has to live.
	 **/
	protected int timeLeft;
	
	/*************************************************************************
	 * Creates a LifetimeController.
	 * 
	 * @param target
	 * 			  The Killable to to affect. 
	 * @param lifetime
	 * 			  The duration of the Killable's remaining life.
	 *************************************************************************/
	public LifetimeController(Killable target, int lifetime)
	{
		this(target, lifetime, 0);
	}
	
	/*************************************************************************
	 * Creates a LifetimeController.
	 * 
	 * @param target
	 * 			  The Killable to to affect. 
	 * @param lifetime
	 * 			  The base duration of the Killable's remaining life
	 * @param variance
	 * 			  The percentage of randomization that should be applied to the 
	 * 			  Killable's lifetime.
	 *************************************************************************/
	public LifetimeController(Killable target, int lifetime, float variance)
	{
		super(target);
		timeLeft = (int) (lifetime * (1f + (random().nextFloat() - 0.5f) * variance));
	}

	/*************************************************************************
	 * Counts down the Killable's life.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timeLeft -= delta;
		if(timeLeft <= 0)
			target.kill();
	}
}