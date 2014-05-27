package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * SingleHitEffect causes a MatrixEffect to die as soon as it affects a
 * MatrixEntity.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SingleHitEffect implements Effect
{
	/*************************************************************************
	 * Causes the source MatrixEffect to die.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		source.kill();
	}
}