package game.objects.entities.hostile;

import static org.jrabbit.base.managers.Resources.random;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.base.graphics.skins.animation.AnimationFactory;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.math.vector.BaseVector2f;

import game.objects.Actor;
import game.objects.Weapon;
import game.objects.controllers.action.TimedBurstActorController;
import game.objects.controllers.movement.FollowController;
import game.objects.controllers.movement.StrafeController;
import game.objects.controllers.movement.WanderingController;
import game.objects.controllers.rotation.CrazySpinController;
import game.objects.controllers.rotation.RotateTowardsController;
import game.objects.controllers.rotation.RotatingController;
import game.objects.death.DeathBurstEffect;
import game.objects.debris.DebrisSprite;
import game.objects.effects.base.MatrixEffect;
import game.objects.effects.effects.ExplosiveDeathEffect;
import game.objects.effects.projectiles.HomingBullet;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.base.StaticKeyPointEntity;
import game.objects.entities.base.StaticMatrixEntity;
import game.objects.entities.base.StaticParentEntity;
import game.objects.entities.controllers.color.HealthColorController;

/*****************************************************************************
 * A Juggernaut is a large, incredibly durable enemy that fires salvos of 
 * homing missiles at the Player.
 * 
 * In both look and functionality, a Juggernaut is inspired as an "evolved" form
 * of an Assaulter.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Juggernaut extends StaticParentEntity implements Weapon
{
	/**
	 * The MatrixEntity to attack.
	 **/
	protected MatrixEntity target;
	
	/*************************************************************************
	 * Creates a Juggernaut.
	 * 
	 * @param target
	 * 			  The MatrixEntity to attack.
	 * @param location
	 * 			  The location at which to spawn.
	 *************************************************************************/
	public Juggernaut(MatrixEntity target, Vector2f location)
	{
		super("Juggernaut Fins");
		setFaction(0);
		setFactionRatings(1, 0, -1);
		setLayer("Enemies A");
		scalar.setScale(2f);
		setMass(120f);
		setMaxSpeed(0.025f + random().nextFloat() * 0.05f);
		defineHealth(750 + random().nextFloat() * 250);
		setPoints(2500);
		complexity = 30;
		setGeometry(new float[][] {{10, -60}, {60, 10}, {-10, 60}, {-60, -10}});
		this.location().set(location);
		defineKeyPoints(new float[][] {	{0, 0},
										{39, -19},
										{19, 39},
										{-39, 19},
										{-19, -39}});
		addDeathEffects(new DeathBurstEffect(Color.ORANGE, 200, 0.05f),
						new DeathBurstEffect(Color.RED, 100, 0.03f),
						new ExplosiveDeathEffect(10, Color.ORANGE, 0.15f, 5f, false));
		addControllers(	new RotatingController(this, 0.033f, 0.13f),
						new FollowController(this, target.location(), 400, 600, 30, 0.5f),
						new WanderingController(this, 10f, 2500, 5000, 0.5f),
						new StrafeController(this, target.location(), 20f + random().nextFloat() * 15),
						new HealthColorController(this){
							protected void setColor(float percent) {
								target.color().set(0.5f + percent / 2, 0.25f + percent * 0.75f, 1f);
							}});
		MatrixEntity glow = new StaticMatrixEntity("Juggernaut Glow");
		glow.scalar().setScale(6f);
		glow.color().set(0, 0, 0);
		glow.addController(new CrazySpinController(glow));
		add(glow, 0, false);
		MatrixEntity ring = new StaticMatrixEntity("Juggernaut Ring");
		ring.scalar().setScale(4f);
		ring.addControllers(new RotatingController(ring, 0.075f, 0.5f, true),
							new HealthColorController(this, ring){
								protected void setColor(float percent) {
									target.color().set(0.5f + percent / 2, 0.25f + percent * 0.75f, 1f);
								}});
		add(ring, 0, false);
		JuggernautGun gun = new JuggernautGun(target);
		gun.addController( new HealthColorController(this, gun){
								protected void setColor(float percent) {
									target.color().set(0.5f + percent / 2, 0.25f + percent * 0.75f, 1f);
								}});
		add(gun, 0, false);
		this.target = target;
	}

	/*************************************************************************
	 * Fires a missile at the indicated location and rotation.
	 * 
	 * @param location
	 *  		  The Vector2f at which to fire the missile.
	 * @param rotation
	 * 			  The angle at which to fire the missile.
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		if(matrix != null)
		{
			AnimatedSkin missileSkin = AnimationFactory.createLoop(	"Missile 1", 
																	"Missile 2", 
																	"Missile 3");
			MatrixEffect missile = new HomingBullet(missileSkin, this, location,
					rotation, new Color(0, 0.5f, 1f), target.location(), 0.036f,
					0.005f, 0.15f, 14000, 10, 30, false);
			missile.addDeathEffects(new ExplosiveDeathEffect(3, 0.05f, 3.5f, true),
					new DeathBurstEffect(Color.CYAN, 50, 0.075f));
			matrix.add(missile);
		}
		
	}

	/*************************************************************************
	 * In addition to normal death effects, when a Juggernaut dies, it leaves
	 * Debris in the Matrix.
	 ***************************************************************/ @Override
	public void onDeath()
	{
		super.onDeath();
		if(matrix != null)
		{
			for(int i = 1; i < 5; i++)
			{
				DebrisSprite debris = new DebrisSprite("Juggernaut Debris " + 
						(random().nextInt(3) + 1));
				debris.scalar().setScale(1.5f + random().nextFloat());
				debris.rotation().rotate(90 * i);
				debris.location().set(pointAt(i));
				debris.applyDrift(velocity.x(), velocity.y(), 
						(random().nextFloat() - 0.5f) * 0.1f);
				debris.velocity().addPolar(	random().nextFloat() * 0.0085f, 
											random().nextFloat() * 6.283f);
				matrix.add("Debris", debris);
			}
			DebrisSprite debris = new DebrisSprite("Juggernaut Gun Debris");
			debris.scalar().setScale(2f);
			debris.location().set(location);
			debris.rotation().set(location.angleTowards(target.location()));
			debris.applyDrift(velocity.x(), velocity.y(), 
					(random().nextFloat() - 0.5f) * 0.1f);
			debris.velocity().addPolar(	random().nextFloat() * 0.0085f, 
										random().nextFloat() * 6.283f);
			matrix.add("Debris", debris);
		}
	}

	/*************************************************************************
	 * A JuggernautGun is the central turret of a Juggernaut that fires
	 * missiles at the Player.
	 *************************************************************************/
	protected class JuggernautGun extends StaticKeyPointEntity implements Actor
	{
		/**
		 * Keeps track of which turret port is being fired from.
		 **/
		protected int phase;

		/*********************************************************************
		 * Creates a JuggernautGun.
		 * 
		 * @param target
		 * 			  The MatrixEntity to target with Missiles. 
		 *********************************************************************/
		public JuggernautGun(MatrixEntity target)
		{
			super("Juggernaut Gun");
			addControllers(	new RotateTowardsController(this, target.location(), 0.03f, 0.5f),
							new TimedBurstActorController(this, 25000, 3000, 3));
			defineKeyPoints(new float[][] { {14, -12}, {24, 0}, {14, 12} });
			setFaction(0);
			setFactionRatings(1, 0, -1);
		}

		/*********************************************************************
		 * Fires a missile.
		 ***********************************************************/ @Override
		public void act()
		{
			fireAt(pointAt(phase), rotation);
			phase++;
			phase %= 3;
		}
	}
}