package game.objects.death;

import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * A DeathEffect handles an action that will occur when a MatrixSprite is 
 * killed.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface DeathEffect
{
	/*************************************************************************
	 * Performs the action that should occur at death.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is dying.
	 *************************************************************************/
	public void onDeath(MatrixSprite source);
}