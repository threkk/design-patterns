package game.objects.effects.effects;

import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * DistancedHoTEffect extends HoTEffect to make the healing performed diminish 
 * the further away the target is.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DistancedHoTEffect extends HoTEffect
{
	/**
	 * The distance at which 0 healing is performed.
	 **/
	protected float radius;

	/*************************************************************************
	 * Creates a DistancedHoTEffect.
	 * 
	 * @param healRate
	 * 			  The maximum healing per microsecond to perform.
	 * @param radius
	 * 			  The distance at which 0 healing is done.
	 *************************************************************************/
	public DistancedHoTEffect(float healRate, float radius)
	{
		super(healRate);
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
		if(dist < radius)
			target.heal((1f - (dist / radius)) * healingThisUpdate);
	}
}