package game.objects.death;

import org.jrabbit.base.graphics.transforms.Color;

import game.objects.sprite.MatrixSprite;
import game.world.Matrix;

/*****************************************************************************
 * A DeathEffect causes a spray of particles to explode out of a MatrixSprite
 * when it is killed. It is a purely visual effect, but it is an effective one.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DeathBurstEffect implements DeathEffect
{
	/**
	 * The Color of the particles to use.
	 **/
	protected Color color;
	
	/**
	 * The number of particles to spawn.
	 **/
	protected int number;
	
	/**
	 * The max velocity of the particles.
	 **/
	protected float maxSpeed;

	/*************************************************************************
	 * Creates a DeathBurstEffect.
	 * 
	 * @param color
	 * 			  The color of the burst.
	 * @param number
	 * 			  The number of particles in the burst.
	 * @param maxSpeed
	 * 			  The maximum velocity of the particles.
	 *************************************************************************/
	public DeathBurstEffect(Color color, int number, float maxSpeed)
	{
		this.color = color;
		this.number = number;
		this.maxSpeed = maxSpeed;
	}

	/*************************************************************************
	 * Spawns a burst of particles at the source's location.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is dying.
	 ***************************************************************/ @Override
	public void onDeath(MatrixSprite source)
	{
		Matrix matrix = source.matrix();
		if(matrix != null)
			matrix.particles().burstAt(source.location(), color, number, maxSpeed); 
	}
}