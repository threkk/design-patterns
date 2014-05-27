package game.objects.effects.projectiles;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.math.vector.BaseVector2f;

import game.objects.controllers.life.LifetimeController;
import game.objects.controllers.movement.PropellingController;
import game.objects.controllers.rotation.InputRotationController;
import game.objects.controllers.rotation.RotateTowardsController;
import game.objects.effects.base.AnimatedMatrixEffect;
import game.objects.effects.effects.DamageEffect;
import game.objects.effects.effects.PushEffect;
import game.objects.effects.effects.SingleHitEffect;
import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * A HomingBullet is similar to a Bullet, but it will attempt to "home in" on
 * a particular target, similarly to a heat-seeking rocket.
 * 
 * NOTE: Yes, there is a truly abominable amount of parameters for these 
 * constructors, and I realize it's a bad design decision, but it works and I
 * wanted to finish this game up.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HomingBullet extends AnimatedMatrixEffect
{
	/*************************************************************************
	 * Creates a HomingBullet.
	 * 
	 * @param skin
	 * 			  The AnimatedSkin to use to animated to HomingBullet.
	 * @param source
	 * 			  The MatrixSprite that is firing this HomingBullet.
	 * @param location
	 * 			  The Vector2f from which the HomingBullet is being fired.
	 * @param rotation
	 * 			  The angle at which the HomingBullet is being fired.
	 * @param color
	 * 			  The Color of the HomingBullet.
	 * @param target
	 * 			  The target Vector2f to track.
	 * @param turnSpeed
	 * 			  The rate at which the HomingBullet can turn towards its 
	 * 			  target. The higher the value, the better it will home.
	 * @param thrust
	 * 			  The force with which the HomingBullet will accelerate itself.
	 * @param maxSpeed
	 * 			  The maximum speed of the HomingBullet.
	 * @param lifetime
	 * 			  The duration, in microseconds, of the HomingBullet's lifetime.
	 * @param damage
	 * 			  The damage for the HomingBullet's to inflict.
	 * @param force
	 * 			  The force with which the HomingBullet's will push enemies.
	 * @param playerControlled
	 * 			  Whether or not the HomingBullet should be controlled by the 
	 * 			  player, and whether it should add points when it kills a 
	 * 			  target.
	 *************************************************************************/
	public HomingBullet(AnimatedSkin skin, MatrixSprite source, 
			BaseVector2f location, Rotation rotation, Color color, Vector2f target, 
			float turnSpeed, float thrust, float maxSpeed, int lifetime,
			float damage, float force, boolean playerControlled)
	{
		super(skin);
		setFaction(source.faction());
		setFactionRatings(source.factionRatings());
		setGeometry(new float[][] { { 8, 0 }, { -8, 4 }, { -8, -4 } });
		setLayer("Projectiles");
		this.location.set(location);
		this.rotation.set(rotation);
		scalar.setScale(4);
		this.location.addPolar(scaledWidth() / 2, rotation.theta());
		setMaxSpeed(maxSpeed);
		velocity.set(source.velocity());
		this.color.set(color);
		addEffects(	new PushEffect(force),
					new DamageEffect(damage, playerControlled),
					new SingleHitEffect());
		addControllers(	new LifetimeController(this, lifetime),
						playerControlled ?new InputRotationController(this, turnSpeed)
							: new RotateTowardsController(this, target, turnSpeed),
						new PropellingController(this, thrust));
	}
}