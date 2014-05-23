package game.objects.effects.base;

import game.objects.entities.base.MatrixEntity;

/*****************************************************************************
 * An Effect is an object used to delegate the functionality of a MatrixEffect.
 * It decides what happens when a MatrixEffect is affecting a MatrixEntity.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Effect
{
	/*************************************************************************
	 * Performs the appropriate effects.
	 * 
	 * @param source
	 * 			  The MatrixEffect targeting the MatrixEntity.
	 * @param target
	 * 			  The MatrixEntity that is receiving the source's effects.
	 *************************************************************************/
	public void affect(MatrixEffect source, MatrixEntity target);
}