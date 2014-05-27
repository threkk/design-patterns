package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A PushEffect causes a MatrixEffect to push MatrixEntities around. The result
 * is directed by their velocity.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class PushEffect implements Effect
{
	/**
	 * The amount of force to push with.
	 **/
	protected float force;

	/*************************************************************************
	 * Creates a PushEffect.
	 * 
	 * @param force
	 * 			  The amount of force that this PushEffect should push with.
	 *************************************************************************/
	public PushEffect(float force)
	{
		this.force = force;
	}

	/*************************************************************************
	 * Pushes the target in the direction of the source's velocity.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		target.force().add(source.velocity(), force / source.velocity().magnitude());
	}
}