package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A RepulsionEffect pushes target MatrixEntities away from the source
 * MatrixEffect.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class RepulsionEffect implements Effect
{
	/**
	 * The magnitude of the force to apply.
	 **/
	protected float force;

	/*************************************************************************
	 * Creates a RepulsionEffect.
	 * 
	 * @param force
	 * 			  The magnitude of the force that will push objects away.
	 *************************************************************************/
	public RepulsionEffect(float force)
	{
		this.force = force;
	}

	/*************************************************************************
	 * Pushes the target away from the source.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		target.force().add(source.location().unitVectorTowards(target.location()),
				force);
	}
}