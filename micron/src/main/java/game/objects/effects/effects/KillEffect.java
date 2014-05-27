package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * KillEffect automatically kills any MatrixEntity it affects.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class KillEffect implements Effect
{
	/*************************************************************************
	 * Kills the target.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this lethal effect.
	 * @param target
	 * 			  The MatrixEntity to affect.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		target.kill(); // Avada Kedavra!
	}
}
