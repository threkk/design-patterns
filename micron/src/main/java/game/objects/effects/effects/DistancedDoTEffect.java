package game.objects.effects.effects;

import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * DistancedDoTEffect extends DoTEffect to make the damage inflicted diminish 
 * the further away the target is.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DistancedDoTEffect extends DoTEffect
{
	/**
	 * The distance at which 0 damage is dealt.
	 **/
	protected float radius;

	/*************************************************************************
	 * Creates a DistancedDoTEffect.
	 * 
	 * @param damageRate
	 * 			  The maximum damage per microsecond to inflict.
	 * @param radius
	 * 			  The distance at which 0 damage is dealt.
	 *************************************************************************/
	public DistancedDoTEffect(float damageRate, float radius)
	{
		this(damageRate, radius, false);
	}

	/*************************************************************************
	 * Creates a DistancedDoTEffect.
	 * 
	 * @param damageRate
	 * 			  The maximum damage per microsecond to inflict.
	 * @param radius
	 * 			  The distance at which 0 damage is dealt.
	 * @param addPoints
	 * 			  Whether or not points should be awarded when a MatrixEntity is
	 * 			  killed by this DamageEffect.
	 *************************************************************************/
	public DistancedDoTEffect(float damageRate, float radius, boolean addPoints)
	{
		super(damageRate, addPoints);
		this.radius = radius;
	}

	/*************************************************************************
	 * Damages the target based upon the dps and distance. If the target is 
	 * killed, this adds points if necessary.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		float dist = source.location().distanceTo(target.location());
		if((dist /= radius) < 1f)
			if(target.damage((1f - dist) * damageThisUpdate) && addPoints && 
					source.matrix() != null)
				source.matrix().addPoints(target.points());
	}
}