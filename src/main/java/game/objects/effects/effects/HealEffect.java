package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A HealEffect heals MatrixEntities.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HealEffect implements Effect
{
	/**
	 * The amount of healing to perform on contact.
	 **/
	protected float healing;

	/*************************************************************************
	 * Creates a HealEffect.
	 * 
	 * @param healing
	 * 			  The amount of healing to perform on MatrixEntities.
	 *************************************************************************/
	public HealEffect(float healing)
	{
		this.healing = healing;
	}

	/*************************************************************************
	 * Heals the MatrixEntity.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		target.heal(healing);
	}
}