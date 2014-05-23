package game.objects.death;

import game.objects.Killable;
import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * A DeathKillEffect causes a group of Killable objects to also die when a
 * MatrixSprite is killed.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DeathKillEffect implements DeathEffect
{
	/**
	 * The objects to kill.
	 **/
	protected Killable[] toKill;
	
	/*************************************************************************
	 * Creates a DeathKillEffect.
	 * 
	 * @param toKill
	 * 			  The objects to kill when the MatrixSprite that owns this 
	 * 			  DeathEffect is killed.
	 *************************************************************************/
	public DeathKillEffect(Killable... toKill)
	{
		this.toKill = toKill;
	}
	
	/*************************************************************************
	 * Kills the Killable objects that have been registered with this 
	 * DeathKillEffect.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is dying.
	 ***************************************************************/ @Override
	public void onDeath(MatrixSprite source)
	{
		for(Killable killable : toKill)
			killable.kill();
	}
}
