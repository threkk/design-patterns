package game.objects.controllers.action;

import org.jrabbit.base.managers.Resources;

import game.objects.Actor;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A TimedActorController extends TargetedController to command an Actor, 
 * making it perform its action in regular intervals.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TimedActorController extends TargetedController<Actor>
{
	/**
	 * The time passed since the last action.
	 **/
	protected int counter;
	
	/**
	 * The time between actions.
	 **/
	protected int interval;
	
	/*************************************************************************
	 * Creates a TimedActorController that controls the indicated Actor.
	 * 
	 * @param owner
	 * 			  The Actor to control.
	 * @param interval
	 * 			  The interval between each action.
	 *************************************************************************/
	public TimedActorController(Actor owner, int interval)
	{
		this(owner, interval, 0);
	}

	/*************************************************************************
	 * Creates a TimedActorController that controls the indicated Actor.
	 * 
	 * @param target
	 * 			  The Actor to control.
	 * @param interval
	 * 			  The interval between each action.
	 * @param variance
	 * 			  The amount of randomization that should be applied to the 
	 * 			  interval between actions.
	 *************************************************************************/
	public TimedActorController(Actor target, int interval, float variance)
	{
		super(target);
		this.interval = (int) (interval * (1f + (Resources.random().nextFloat()
				- 0.5f) * variance));
	}

	/*************************************************************************
	 * Updates the TimedActorController, causing actions whenever necessary.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last interval.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		counter += delta;
		while(counter >= interval)
		{
			counter -= interval;
			target.act();
		}
	}
}