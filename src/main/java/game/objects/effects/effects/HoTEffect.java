package game.objects.effects.effects;

import game.objects.controllers.Controller;
import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A HoTEffect regulates its healing output to sync with the duration of each
 * frame. Effectively, it maintains a constant heal rate per second.
 * 
 * NOTE: A DoTEffect must also be added to a MatrixEffect as a Controller, not
 * simply as an Effect.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HoTEffect extends Controller implements Effect
{
	/**
	 * The healing per microsecond to apply.
	 **/
	protected float healRate;
	
	/**
	 * The damage to apply on the current update cycle.
	 **/
	protected float healingThisUpdate;

	/*************************************************************************
	 * Creates a HoTEffect.
	 * 
	 * @param healRate
	 * 			  The healing per microsecond to perform.
	 *************************************************************************/
	public HoTEffect(float healRate)
	{
		this.healRate = healRate;
	}

	/*************************************************************************
	 * Updates the healing for this update cycle.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		healingThisUpdate = healRate * delta;
	}

	/*************************************************************************
	 * Heals the target based upon the heal rate.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		target.heal(healingThisUpdate);
	}
}