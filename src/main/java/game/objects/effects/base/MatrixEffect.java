package game.objects.effects.base;

import org.jrabbit.base.data.structures.LockingList;

import game.objects.entities.base.MatrixEntity;
import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * A MatrixEffect is the counterpart of a MatrixEntity. MatrixEntities are game 
 * "characters," but cannot directly act upon one another. Instead, they use 
 * MatrixEffects as intermediaries. 
 * 
 * An example of this is one entity shooting at another. It creates a Projectile
 * effect, which is aimed at its target; only when the projectile collides with
 * its target will the other MatrixEntity be damaged.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class MatrixEffect extends MatrixSprite
{
	/**
	 * The List of effects that will be performed when this MatrixEffect acts
	 * upon a MatrixEntity.
	 **/
	protected LockingList<Effect> effects;
	
	/*************************************************************************
	 * Creates a MatrixEffect with an empty list of effects to apply.
	 *************************************************************************/
	public MatrixEffect()
	{
		effects = new LockingList<Effect>();
	}

	/*************************************************************************
	 * Adds the indicated Effect to this MatrixEffect.
	 * 
	 * @param effect
	 * 			  The Effect to add.
	 *************************************************************************/
	public void addEffect(Effect effect)
	{
		effects.add(effect);
	}

	/*************************************************************************
	 * Adds the indicated series of Effects to this MatrixEffect.
	 * 
	 * @param effects
	 * 			  The Effects to add.
	 *************************************************************************/
	public void addEffects(Effect... effects)
	{
		for(Effect effect : effects)
			addEffect(effect);
	}

	/*************************************************************************
	 * Removes the indicated Effect from this MatrixEffect.
	 * 
	 * @param effect
	 * 			  The Effect to remove.
	 *************************************************************************/
	public void removeEffect(Effect effect)
	{
		effects.remove(effect);
	}

	/*************************************************************************
	 * Attempts to affect the indicated Entity.
	 * 
	 * @param entity
	 * 			  The Entity to affect.
	 *************************************************************************/
	public void checkAgainst(MatrixEntity entity)
	{
		if(shouldAffect(entity))
			affect(entity);
	}

	/*************************************************************************
	 * Returns whether or not the Entity should be affected by this Effect.
	 * 
	 * @param entity
	 * 			  The Entity to check against.
	 * 
	 * @return True if the entity should be affected, false if not.
	 *************************************************************************/
	protected boolean shouldAffect(MatrixEntity entity)
	{
		return (isHostileTo(entity) || entity.isHostileTo(this)) && 
				collidesWith(entity);
	}

	/*************************************************************************
	 * Affects the indicated MatrixEntity.
	 * 
	 * @param entity
	 * 			  The MatrixEntity to affect.
	 *************************************************************************/
	protected void affect(MatrixEntity entity)
	{
		for(Effect effect : effects)
			effect.affect(this, entity);
		effects.unlock();
	}
}