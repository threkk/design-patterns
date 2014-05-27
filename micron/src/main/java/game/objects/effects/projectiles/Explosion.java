package game.objects.effects.projectiles;

import static org.jrabbit.base.managers.Resources.random;

import org.jrabbit.base.graphics.skins.animation.AnimationFactory;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.math.vector.BaseVector2f;

import game.objects.controllers.rotation.RotatingController;
import game.objects.effects.base.AnimatedMatrixEffect;
import game.objects.effects.effects.DistancedDoTEffect;
import game.objects.effects.effects.RepulsionEffect;
import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * An Explosion is a large, deadly area-impacting damaging effect. It will 
 * damage EVERY MatrixEntity in its radius, ignoring faction rankings.
 * 
 * NOTE: Yes, there is a truly abominable amount of parameters for these 
 * constructors, and I realize it's a bad design decision, but it works and I
 * wanted to finish this game up.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Explosion extends AnimatedMatrixEffect
{
	/*************************************************************************
	 * Creates an Explosion that uses the color of its source.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is the source of this Explosion.
	 * @param location
	 * 			  The location at which to spawn this Explosion.
	 * @param scale
	 * 			  The scale of the explosion.
	 * @param damageRate
	 * 			  The rate at which the explosion deals damage. Since this is
	 * 			  a very short-lived effect, it is best for this to be a high
	 * 			  value.
	 * @param force
	 * 			  The force with which the Explosion will push away 
	 * 			  MatrixEntities.
	 * @param addPoints
	 * 			  Whether or not the Explosion should add points when it kills
	 * 			  a MatrixEntity.
	 *************************************************************************/
	public Explosion(MatrixSprite source, BaseVector2f location, float scale, 
			float damageRate, float force, boolean addPoints)
	{
		this(source, source.color(), location, scale, damageRate, force, addPoints);
	}
	/*************************************************************************
	 * Creates an Explosion.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is the source of this Explosion.
	 * @param color
	 * 			  The Color of the Explosion.
	 * @param location
	 * 			  The location at which to spawn this Explosion.
	 * @param scale
	 * 			  The scale of the explosion.
	 * @param damageRate
	 * 			  The rate at which the explosion deals damage. Since this is
	 * 			  a very short-lived effect, it is best for this to be a high
	 * 			  value.
	 * @param force
	 * 			  The force with which the Explosion will push away 
	 * 			  MatrixEntities.
	 * @param addPoints
	 * 			  Whether or not the Explosion should add points when it kills
	 * 			  a MatrixEntity.
	 *************************************************************************/
	public Explosion(MatrixSprite source, Color color, BaseVector2f location, 
			float scale, float damageRate, float force, boolean addPoints)
	{
		super(AnimationFactory.createLoop(300, "Explosion 1", "Explosion 2",
				"Explosion 3", "Explosion 4", "Explosion 5", "Explosion 6",
				"Explosion 7", "Explosion 8", "Explosion 9", "Explosion 10"));
		setFaction(0);
		setFactionRatings(-1, -1, -1);
		setGeometry(new float[][] { { 32, 0 },	{ 24, -24 },
									{ 0, -32 }, { -24, -24 },
									{ -32, 0 }, { -24, 24 },
									{ 0, 32 },	{ 24, 24 } });
		setLayer("Projectiles");
		scalar.setScale(scale);
		this.color.set(color);
		rotation.set(random().nextFloat() * 360);
		this.location.set(location);
		DistancedDoTEffect doT;
		addEffects(new RepulsionEffect(force), doT = new DistancedDoTEffect(
				damageRate, scaledWidth() / 2f, addPoints));
		addControllers(new RotatingController(this, 0.009f), doT);
	}

	/*********************************************************************
	 * Kills the Explosion when the burst animation has finished.
	 * 
	 * @param cycle
	 * 			  The cycle of animation that has just finished.
	 ***********************************************************/ @Override
	public void onCycleEnd(int cycle) { kill(); }
}