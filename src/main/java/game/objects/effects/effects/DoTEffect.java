package game.objects.effects.effects;

import game.objects.controllers.Controller;
import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A DoTEffect regulates its damage output to sync with the duration of each
 * frame. Effectively, it maintains a constant damage per second.
 * 
 * NOTE: A DoTEffect must also be added to a MatrixEffect as a Controller, not
 * simply as an Effect.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DoTEffect extends Controller implements Effect
{
	/**
	 * The damage per microsecond to apply.
	 **/
	protected float damageRate;
	
	/**
	 * The damage to apply on the current update cycle.
	 **/
	protected float damageThisUpdate;
	
	/**
	 * Whether or not to add points when a target is killed.
	 **/
	protected boolean addPoints;

	/*************************************************************************
	 * Creates a DoTEffect.
	 * 
	 * @param damageRate
	 * 			  The damage per microsecond to inflict.
	 *************************************************************************/
	public DoTEffect(float damageRate)
	{
		this(damageRate, false);
	}

	/*************************************************************************
	 * Creates a DoTEffect.
	 * 
	 * @param damageRate
	 * 			  The damage per microsecond to inflict.
	 * @param addPoints
	 * 			  Whether or not points should be awarded when a MatrixEntity is
	 * 			  killed by this DamageEffect.
	 *************************************************************************/
	public DoTEffect(float damageRate, boolean addPoints)
	{
		this.damageRate = damageRate;
		this.addPoints = addPoints;
	}

	/*************************************************************************
	 * Updates the damage for this update cycle.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		damageThisUpdate = damageRate * delta;
	}

	/*************************************************************************
	 * Damages the target based upon the dps. If the target is killed, this adds 
	 * points if necessary.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		if(target.damage(damageThisUpdate) && addPoints && source.matrix() != null)
			source.matrix().addPoints(target.points());
	}
}