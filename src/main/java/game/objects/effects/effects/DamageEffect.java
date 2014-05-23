package game.objects.effects.effects;

import game.objects.effects.base.Effect;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * A DamageEffect causes damage to MatrixEntities.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DamageEffect implements Effect
{
	/**
	 * The amount of damage to deal on contact.
	 **/
	protected float damage;
	
	/**
	 * Whether or not to add points if the target is killed.
	 **/
	protected boolean addPoints;

	/*************************************************************************
	 * Creates a DamageEffect.
	 * 
	 * @param damage
	 * 			  The amount of damage to deal to MatrixEntities.
	 *************************************************************************/
	public DamageEffect(float damage)
	{
		this(damage, false);
	}

	/*************************************************************************
	 * Creates a DamageEffect.
	 * 
	 * @param damage
	 * 			  The amount of damage to deal to MatrixEntities.
	 * @param addPoints
	 * 			  Whether or not points should be awarded when a MatrixEntity is
	 * 			  killed by this DamageEffect.
	 *************************************************************************/
	public DamageEffect(float damage, boolean addPoints)
	{
		this.damage = damage;
		this.addPoints = addPoints;
	}

	/*************************************************************************
	 * Damages the MatrixEntity. If it is killed then points are awarded if
	 * necessary.
	 * 
	 * @param source
	 * 			  The MatrixEffect that is the source of this Effect.
	 * @param target
	 * 			  The MatrixEntity on the receiving end of this MatrixEffect's
	 * 			  Effects.
	 ***************************************************************/ @Override
	public void affect(MatrixEffect source, MatrixEntity target)
	{
		if(target.damage(damage) && addPoints && source.matrix() != null)
			source.matrix().addPoints(target.points());
	}
}