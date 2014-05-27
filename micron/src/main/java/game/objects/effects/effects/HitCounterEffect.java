package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A HitCounterEffect is used to limit the number of "hits" that a MatrixEffect
 * can perform. Once it performs more than the maximum limit, it is killed.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HitCounterEffect implements Effect
{
	/**
	 * The remaining number of allowed hits.
	 **/
	protected int hitsLeft;

	/*************************************************************************
	 * Creates a HitCounterEffect.
	 * 
	 * @param maxHits
	 * 			  The maximum number of hits allowed.
	 *************************************************************************/
	public HitCounterEffect(int maxHits)
	{
		hitsLeft = maxHits;
	}

	/*************************************************************************
	 * Kills the source MatrixEffect if this affect causes the hit counter to 
	 * overflow.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		if(hitsLeft-- <= 0)
			source.kill();
	}
}