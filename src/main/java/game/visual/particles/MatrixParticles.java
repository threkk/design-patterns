package game.visual.particles;

import org.jrabbit.base.graphics.transforms.BlendOp;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Vector2f;
import static org.jrabbit.base.managers.Resources.*;
import org.jrabbit.standard.game.objects.particles.ParticleSprite;
import org.jrabbit.standard.game.objects.particles.generic.ExplosionParticle;

/*****************************************************************************
 * MatrixParticles defines the default particle effect system for a Matrix.
 * 
 * Micron is designed so that each game world only needs a single instance of
 * this class to handle its particles. When a new set of particles is needed, 
 * game entities can simply call the "burstAt" method to add a new effect. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MatrixParticles extends ParticleSprite.Images
{
	/*************************************************************************
	 * Creates a MatrixParticles that uses the predefined images for its 
	 * particles. 
	 *************************************************************************/
	public MatrixParticles()
	{
		super(	"Particle 1",
				"Particle 2",
				"Particle 3",
				"Particle 4",
				"Particle 5",
				"Particle 6",
				"Particle 7",
				"Particle 8");
		blending.set(BlendOp.ADDITIVE);
	}
	
	/*************************************************************************
	 * Creates an explosion of particles at the indicated location.
	 * 
	 * @param location
	 * 			  The location to spawn the particles at.
	 * @param color
	 * 			  The color of the particles to create.
	 * @param number
	 * 			  The number of particles to create.
	 * @param speed
	 * 			  How quickly the spawned particles should move away from the
	 * 			  center of the explosion.
	 *************************************************************************/
	public void burstAt(Vector2f location, Color color, int number, float speed)
	{
		this.color.set(color);
		this.location.set(location);
		
		for(int i = 0; i < number; i++)
		{
			ExplosionParticle particle = new ExplosionParticle(speed * 
					random().nextFloat(),  -0.0001f * (random().nextFloat() + 1f),
					0.018f * (random().nextFloat() + 1f), 1.6f);
			particle.setSkinID(random().nextInt(skins.length));
			particle.scalar().setScale(4f * (random().nextFloat() + 1f));
			add(particle);
		}
	}
}