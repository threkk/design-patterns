package game.objects.effects.projectiles;

import org.jrabbit.base.graphics.skins.animation.AnimationFactory;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.jrabbit.standard.game.objects.base.BaseSprite;

import game.objects.controllers.life.LifetimeController;
import game.objects.death.DeathSpawnEffect;
import game.objects.effects.base.StaticMatrixEffect;
import game.objects.effects.effects.DamageEffect;
import game.objects.effects.effects.PushEffect;
import game.objects.effects.effects.SingleHitEffect;
import game.objects.sprite.MatrixSprite;
import game.visual.anim.SingleCycleMatrixAnimation;

/*****************************************************************************
 * Bullet provides a convenience class for simple damaging projectiles.
 * 
 * NOTE: Yes, there is a truly abominable amount of parameters for these 
 * constructors, and I realize it's a bad design decision, but it works and I
 * wanted to finish this game up.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Bullet extends StaticMatrixEffect
{
	/*************************************************************************
	 * Creates a Bullet.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is the source of this Bullet. Faction
	 * 			  information is copied from this sprite.
	 * @param location
	 * 			  The Vector2f from which the Bullet is being fired.
	 * @param rotation
	 * 			  The angle at which the Bullet is being fired.
	 * @param color
	 * 			  The Color of the Bullet.
	 * @param damage
	 * 			  The damage for the Bullet to inflict.
	 * @param force
	 * 			  The force with which the Bullet will push enemies.
	 * @param speed
	 * 			  The velocity of the Bullet.
	 * @param lifetime
	 * 			  The duration, in microseconds, of the Bullet's lifetime. 
	 *************************************************************************/
	public Bullet(MatrixSprite source, BaseVector2f location, Rotation rotation, 
			Color color, float damage, float force, float speed, int lifetime)
	{
		this(source, location, rotation, color, damage, force, speed, lifetime, 
				4, 2, false);
	}

	/*************************************************************************
	 * Creates a Bullet.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is the source of this Bullet. Faction
	 * 			  information is copied from this sprite.
	 * @param location
	 * 			  The Vector2f from which the Bullet is being fired.
	 * @param rotation
	 * 			  The angle at which the Bullet is being fired.
	 * @param color
	 * 			  The Color of the Bullet.
	 * @param damage
	 * 			  The damage for the Bullet to inflict.
	 * @param force
	 * 			  The force with which the Bullet will push enemies.
	 * @param speed
	 * 			  The velocity of the Bullet.
	 * @param lifetime
	 * 			  The duration, in microseconds, of the Bullet's lifetime. 
	 * @param addPoints
	 * 			  Whether or not the Bullet should add points when it kills a 
	 * 			  target. 
	 *************************************************************************/
	public Bullet(MatrixSprite source, BaseVector2f location, Rotation rotation, 
			Color color, float damage, float force, float speed, int lifetime, 
			boolean addPoints)
	{
		this(source, location, rotation, color, damage, force, speed, lifetime, 
				4, 2, addPoints);
	}

	/*************************************************************************
	 * Creates a Bullet.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is the source of this Bullet. Faction
	 * 			  information is copied from this sprite.
	 * @param location
	 * 			  The Vector2f from which the Bullet is being fired.
	 * @param rotation
	 * 			  The angle at which the Bullet is being fired.
	 * @param color
	 * 			  The Color of the Bullet.
	 * @param damage
	 * 			  The damage for the Bullet to inflict.
	 * @param force
	 * 			  The force with which the Bullet will push enemies.
	 * @param speed
	 * 			  The velocity of the Bullet.
	 * @param lifetime
	 * 			  The duration, in microseconds, of the Bullet's lifetime. 
	 * @param scaleX
	 * 			  The x scaling of the bullet. 
	 * @param scaleY
	 * 			  The y scaling of the bullet.
	 * @param addPoints
	 * 			  Whether or not the Bullet should add points when it kills a 
	 * 			  target. 
	 *************************************************************************/
	public Bullet(MatrixSprite source, BaseVector2f location, Rotation rotation, Color 
			color, float damage, float force, float speed, int lifetime, 
			float scaleX, float scaleY, boolean addPoints)
	{
		this(source, location, rotation, color, damage, force, speed, lifetime, 
				scaleX, scaleY, addPoints, true);
	}

	/*************************************************************************
	 * Creates a Bullet.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is the source of this Bullet. Faction
	 * 			  information is copied from this sprite.
	 * @param location
	 * 			  The Vector2f from which the Bullet is being fired.
	 * @param rotation
	 * 			  The angle at which the Bullet is being fired.
	 * @param color
	 * 			  The Color of the Bullet.
	 * @param damage
	 * 			  The damage for the Bullet to inflict.
	 * @param force
	 * 			  The force with which the Bullet will push enemies.
	 * @param speed
	 * 			  The velocity of the Bullet.
	 * @param lifetime
	 * 			  The duration, in microseconds, of the Bullet's lifetime. 
	 * @param scaleX
	 * 			  The x scaling of the bullet. 
	 * @param scaleY
	 * 			  The y scaling of the bullet.
	 * @param addPoints
	 * 			  Whether or not the Bullet should add points when it kills a 
	 * 			  target. 
	 * @param addVelocity
	 * 			  Whether or not the Bullet should add the velocity of the 
	 * 			  source to its own. 
	 *************************************************************************/
	public Bullet(MatrixSprite source, BaseVector2f location, Rotation rotation, Color 
			color, float damage, float force, float speed, int lifetime, 
			float scaleX, float scaleY, boolean addPoints, boolean addVelocity)
	{
		super("Bullet");
		setGeometry(new float[][] { { 8, 0 }, { -8, 4 }, { -8, -4 } });
		setLayer("Projectiles");
		this.location.set(location);
		this.rotation.set(rotation);
		scalar.setScale(scaleX, scaleY);
		this.location.addPolar(scaledWidth() / 2, rotation.theta());
		setMaxSpeed(1);
		if(addVelocity)
			velocity.set(source.velocity());
		velocity.addPolar(speed, rotation.theta());
		this.color.set(color);
		setFaction(source.faction());
		setFactionRatings(source.factionRatings());
		addEffects(	new PushEffect(force),
					new DamageEffect(damage, addPoints),
					new SingleHitEffect());
		addController(new LifetimeController(this, lifetime));
		BaseSprite burst = new SingleCycleMatrixAnimation(
				AnimationFactory.createLoop("Burst 1",
											"Burst 2",
											"Burst 3",
											"Burst 4",
											"Burst 5"), source.matrix());
		burst.scalar().setScale(4f);
		burst.color().set(color);
		addDeathEffects(new DeathSpawnEffect(burst, "Projectiles"));
	}
}