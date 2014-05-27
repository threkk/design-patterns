package game.objects.entities.hostile;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Vector2f;
import static org.jrabbit.base.managers.Resources.*;
import org.jrabbit.base.math.vector.BaseVector2f;

import game.objects.Actor;
import game.objects.Weapon;
import game.objects.controllers.action.TimedBurstActorController;
import game.objects.controllers.movement.FollowController;
import game.objects.controllers.movement.WanderingController;
import game.objects.controllers.rotation.RotateTowardsController;
import game.objects.controllers.rotation.RotatingController;
import game.objects.death.DeathBurstEffect;
import game.objects.debris.DebrisSprite;
import game.objects.effects.projectiles.Bullet;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.base.StaticKeyPointEntity;
import game.objects.entities.base.StaticParentEntity;
import game.objects.entities.controllers.color.HealthColorController;

/*****************************************************************************
 * An Assaulter is a basic enemy that swarms around and fires bullets at the
 * Player. It is not very dangerous, but is designed to be found in massive
 * groups.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Assaulter extends StaticParentEntity implements Weapon 
{
	/*************************************************************************
	 * Creates an Assaulter.
	 * 
	 * @param target
	 * 		  The MatrixEntity to destroy.
	 * @param location.
	 * 		  The Vector2f to spawn at.
	 *************************************************************************/
	public Assaulter(MatrixEntity target, Vector2f location)
	{
		super("Assaulter Body");
		setLayer("Enemies A");
		setMass(10f);
		defineHealth(25 + random().nextFloat() * 10);
		setPoints(100);
		complexity = 3;
		setFactionRatings(1, 0, -1);
		setMaxSpeed(0.03f + random().nextFloat() * 0.01f);
		addControllers(	new WanderingController(this, 0.04f, 10000, 30000, 0.5f),
						new WanderingController(this, 0.1f, 2500, 5000, 0.5f),
						new RotatingController(this, 0.02f, 0.5f, true),
						new FollowController(this, target.location(), 300, 700, 1f),
						new HealthColorController(this){
							protected void setColor(float percent) {
								target.color().set(1f, percent, 0);
							}});
		addDeathEffect(new DeathBurstEffect(Color.RED, 30, 0.04f));
		setGeometry(new float[][] {	{18, 0},
									{0, -18},
									{-18, 0},
									{0, 18}});
		this.location.set(location);
		defineKeyPoints(new float[][] {	{0, 0}, {10, 0}, {0, 10}, {-10, 0}, {0, -10} });
		add(new AssaulterTurret(target), 0, false);
	}

	/*************************************************************************
	 * Fires a bullet at the indicated location and rotation.
	 * 
	 * @param location
	 * 			  The location from which the Bullet should be fired.
	 * @param rotation
	 * 			  The Rotation describing the angle at which the Bullet should
	 * 			  fire.
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		matrix.add(new Bullet(this, location,  rotation,  new Color(0, 0.7f, 1f), 
								6, 10,  0.1f, 8000));
	}

	/*************************************************************************
	 * In addition to normal death effects, an Assaulter spawns a mass of debris
	 * when killed.
	 ***************************************************************/ @Override
	public void onDeath()
	{
		super.onDeath();
		if(matrix != null)
			for(int i = 0; i < 5; i++)
			{
				DebrisSprite debris = new DebrisSprite("Assaulter Debris " + (i + 1));
				debris.scalar().setScale(1.5f + random().nextFloat());
				debris.location().set(pointAt(i));
				debris.applyDrift(velocity.x(), velocity.y(), 
						(random().nextFloat() - 0.5f) * 0.1f);
				debris.velocity().addPolar(	random().nextFloat() * 0.005f, 
											random().nextFloat() * 6.283f);
				matrix.add("Debris", debris);
			}
	}

	/*************************************************************************
	 * An AssaulterTurret is the main gun of an Assaulter.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class AssaulterTurret extends StaticKeyPointEntity implements Actor
	{
		/*********************************************************************
		 * Creates an AssaulterTurret.
		 * 
		 * @param target
		 * 			  The MatrixEntity to aim at.
		 *********************************************************************/
		public AssaulterTurret(MatrixEntity target)
		{
			super("Assaulter Turret");
			addControllers(	new RotateTowardsController(this, target.location(), 0.05f),
							new TimedBurstActorController(this, 15000, 5000, 4, 0.25f));
			addDeathEffect(new DeathBurstEffect(Color.ORANGE, 15, 0.02f));
			defineKeyPoints(new float[][] { {8, -6}, {8, 6} });
		}

		/*********************************************************************
		 * Fires a bullet from both guns.
		 ***********************************************************/ @Override
		public void act()
		{
			fireAt(pointAt(0), rotation);
			fireAt(pointAt(1), rotation);
		}
	}
}