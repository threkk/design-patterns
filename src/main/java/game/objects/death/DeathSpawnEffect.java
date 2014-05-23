package game.objects.death;

import org.jrabbit.standard.game.objects.base.BaseSprite;

import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * A DeathSpawnEffect is used to place a separate object into a MatrixSprite's
 * matrix when it is killed.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DeathSpawnEffect implements DeathEffect
{
	/**
	 * The object to spawn.
	 **/
	protected BaseSprite spawn;
	
	/**
	 * The layer into which to place the spawn.
	 **/
	protected String layer;

	/*************************************************************************
	 * Creates a DeathSpawnEffect.
	 * 
	 * @param spawn
	 * 			  The object to spawn on death.
	 * @param layer
	 * 			  The layer into which to spawn the object.
	 *************************************************************************/
	public DeathSpawnEffect(BaseSprite spawn, String layer)
	{
		this.spawn = spawn;
		this.layer = layer;
	}

	/*************************************************************************
	 * Sets the spawn's location and rotation to that of the source, and adds it
	 * to the source's Matrix.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is dying.
	 ***************************************************************/ @Override
	public void onDeath(MatrixSprite source)
	{
		if(source.matrix() != null)
		{
			spawn.location().set(source.location());
			spawn.rotation().set(source.rotation());
			source.matrix().add(layer, spawn);
		}
	}
}