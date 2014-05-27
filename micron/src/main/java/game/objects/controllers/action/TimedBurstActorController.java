package game.objects.controllers.action;

import static org.jrabbit.base.managers.Resources.*;

import game.objects.Actor;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A TimedBurstActorController extends TargetedController to command an Actor 
 * to perform its action in "bursts."
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TimedBurstActorController extends TargetedController<Actor>
{
	/**
	 * Whether or not it is in the second phase.
	 **/
	protected boolean phase;
	
	/**
	 * The time that has passed in the current phase.
	 **/
	protected int counter;
	
	/**
	 * The duration of the first phase.
	 **/
	protected int intervalA;
	
	/**
	 * The duration between actions in the second phase.
	 **/
	protected int intervalB;
	
	/**
	 * The number of actions that have been taken in the second phase.
	 **/
	protected int phaseBCounter;
	
	/**
	 * The number of actions to take in the second phase.
	 **/
	protected int phaseBIterations;
	
	/*************************************************************************
	 * Creates a TimedBurstActorController with the indicated settings.
	 * 
	 * @param target
	 * 			  The Actor to control.
	 * @param intervalA
	 * 			  The duration of the first phase.
	 * @param intervalB
	 * 			  The interval between actions in the second phase.
	 * @param phaseBIterations
	 * 			  The number of actions to take in the second phase.
	 *************************************************************************/
	public TimedBurstActorController(Actor target, int intervalA, int intervalB,
			int phaseBIterations)
	{
		this(target, intervalA, intervalB, phaseBIterations, 0f);
	}

	/*************************************************************************
	 * Creates a TimedBurstActorController with the indicated settings.
	 * 
	 * @param target
	 * 			  The Actor to control.
	 * @param intervalA
	 * 			  The duration of the first phase.
	 * @param intervalB
	 * 			  The interval between actions in the second phase.
	 * @param phaseBIterations
	 * 			  The number of actions to take in the second phase.
	 * @param variance
	 * 			  The amount of randomization that should be applied to the 
	 * 			  phase settings.
	 *************************************************************************/
	public TimedBurstActorController(Actor owner, int intervalA, int intervalB,
			int phaseBIterations, float variance)
	{
		super(owner);
		this.intervalA = (int) (intervalA * (1f + (random().nextFloat() - 0.5f)
				* variance));
		this.intervalB = (int) (intervalB * (1f + (random().nextFloat() - 0.5f)
				* variance));
		this.phaseBIterations = Math.round(phaseBIterations * (1f + 
				(random().nextFloat() - 0.5f) * variance));
	}

	/*************************************************************************
	 * Updates the TimedBurstActorController, causing actions whenever 
	 * necessary.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last interval.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		counter += delta;
		if(phase)
			while(counter >= intervalB && phase)
			{
				counter -= intervalB;
				target.act();
				phaseBCounter++;
				if(phaseBCounter >= phaseBIterations - 1)
				{
					phaseBCounter = 0;
					phase = false;
				}
			}
		else
			while(counter >= intervalA)
			{
				counter -= intervalA;
				target.act();
				phase = true;
			}
	}
}