package game.objects.entities.hostile;

import static org.jrabbit.base.managers.Resources.*;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.math.vector.BaseVector2f;

import game.objects.Actor;
import game.objects.Weapon;
import game.objects.controllers.Controller;
import game.objects.controllers.action.TimedActorController;
import game.objects.controllers.movement.HomingController;
import game.objects.controllers.movement.WanderingController;
import game.objects.controllers.rotation.RotateTowardsController;
import game.objects.controllers.rotation.VelocityRotationController;
import game.objects.death.DeathBurstEffect;
import game.objects.debris.DebrisSprite;
import game.objects.effects.projectiles.Bullet;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.base.StaticKeyPointEntity;
import game.objects.entities.base.StaticParentEntity;
import game.objects.entities.controllers.color.HealthColorController;
import game.objects.entities.controllers.health.HealingController;

/*****************************************************************************
 * A WormSegment is a section a Worm - a gigantic linked string of literally
 * dozens of segments.
 * 
 * Each segment has a turret that constantly fires bullets at the player. 
 * Additionally, every time a middle segment is destroyed, the Worm splits into
 * two.
 * 
 * A full Worm is probably the most dangerous enemy in the game.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class WormSegment extends StaticParentEntity implements Weapon
{
	/**
	 * The MatrixEntity being attacked.
	 **/
	protected MatrixEntity target;
	
	/**
	 * The WormSegment this Segment is following (if any).
	 **/
	protected WormSegment head;
	
	/**
	 * The WormSegment following this one (if any).
	 **/
	protected WormSegment tail;
	
	/**
	 * The SegmentLockController that is making this WormSegment follow another
	 * (if one is active).
	 **/
	protected SegmentLockController lock;
	
	/*************************************************************************
	 * Creates a WormSegment.
	 * 
	 * @param target
	 * 			  The MatrixEntity to attack.
	 * @param location
	 * 			  The Vector2f at which to spawn the WormSegment.
	 * @param tail
	 * 			  The WormSegment that is this one's tail (if any).
	 *************************************************************************/
	public WormSegment(MatrixEntity target, Vector2f location, WormSegment tail)
	{
		super("Worm Segment");
		this.location.set(location);
		this.target = target;
		this.tail = tail;
		setFactionRatings(1, 0, -1);
		setLayer("Enemies B");
		setMass(30 + random().nextFloat() * 12);
		complexity = 2;
		scalar.setScale(3f);
		setPoints(500);
		setMaxSpeed(0.07f + 0.03f * random().nextFloat());
		defineHealth(150 + random().nextFloat() * 100);
		defineKeyPoints(new float[][] { {-11, 0}, {-26, 0} });
		setGeometry(new float[][] {{-32, -22}, {16, -16}, {32, 0}, {16, 16}, {-32, 22}});
		WormGun gun = new WormGun();
		gun.addController(new HealthColorController(this, gun){
			protected void setColor(float percent) {
				target.color().set(1f, 
						0.1f + (percent * percent) * 0.9f,
						0.5f + percent / 2f);
			}});
		add(gun, 0, true);
		addControllers(	new HealthColorController(this){
							protected void setColor(float percent) {
								target.color().set(1f, 
										0.1f + (percent * percent) * 0.9f,
										0.5f + percent / 2f);
							}},
						new HealingController(this, 0.003f, Integer.MAX_VALUE));
		addDeathEffect(new DeathBurstEffect(Color.RED, 100, 0.1f));
	}

	/*************************************************************************
	 * Fires a bullet at the indicated position.
	 * 
	 * @param location
	 * 			  The Vector2f at which to fire the bullet.
	 * @param rotation
	 * 			  The Rotation that describes the angle of the bullet to fire.
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		matrix.add(new Bullet(this, location,  rotation,  new Color(1f, 0.1f, 0),
				10, 15, 0.2f, 8000, 8, 4, false, false));
	}
	
	/*************************************************************************
	 * Tells this WormSegment that it is a Head and should actively chase down
	 * the target.
	 *************************************************************************/
	public void setAsHead()
	{
		head = null;
		addControllers(	new VelocityRotationController(this),
				new HomingController(this, target.location(), 0.03f, 0.5f),
				new WanderingController(this, 0.15f, 25000, 50000, 0.5f));
		if(lock != null)
			lock.kill();
	}
	
	/*************************************************************************
	 * Tells this WormSegment that it should act as the Tail of another.
	 * 
	 * @param head
	 * 			  The WormSegment that will act as the Head of this one.
	 *************************************************************************/
	public void setAsTailTo(WormSegment head)
	{
		this.head = head;
		addController(lock = new SegmentLockController());
	}
	
	/*************************************************************************
	 * When a WormSegment is killed, it both leaves debris and alerts any other
	 * WormSegments in its chain that it has died.
	 *************************************************************************/
	protected void onDeath()
	{
		super.onDeath();
		if(head != null)
			head.tail = null;
		if(tail != null)
		{
			tail.setAsHead();
			tail.velocity.setPolar(tail.rotation.theta(), velocity().magnitude());
		}
		if(matrix != null)
		{
			DebrisSprite debris = new DebrisSprite("Worm Debris");
			debris.scalar().setScale(3f);
			debris.location().set(location);
			debris.rotation().set(rotation);
			debris.applyDrift(velocity.x(), velocity.y(), 
					(random().nextFloat() - 0.5f) * 0.1f);
			debris.velocity().addPolar(	random().nextFloat() * 0.0085f, 
										random().nextFloat() * 6.283f);
			matrix.add("Debris", debris);
		}
	}
	
	/*************************************************************************
	 * A WormGun is the Turret that is placed atop the back of each WormSegment.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class WormGun extends StaticKeyPointEntity implements Actor
	{
		/*********************************************************************
		 * Creates a WormGun.
		 *********************************************************************/
		public WormGun()
		{
			super("Worm Gun");
			scalar.setScale(3f);
			defineKeyPoints(new float[][] {{8, 0}});
			addControllers(	new RotateTowardsController(this, target.location(), 0.015f, 0.5f),
							new TimedActorController(this, 10000, 0.35f));
		}

		/*********************************************************************
		 * Fires a bullet at the target.
		 ***********************************************************/ @Override
		public void act()
		{
			fireAt(pointAt(0), rotation);
		}
	}
	
	/*************************************************************************
	 * A SegmentLockController ensures that a WormSegment that follows another
	 * is positioned appropriately to follow it.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class SegmentLockController extends Controller
	{
		/*********************************************************************
		 * Updates the SegmentLockController.
		 * 
		 * @param delta
		 * 			  The amount of time that has passed since the last update.
		 ***********************************************************/ @Override
		public void update(int delta)
		{
			rotation.set(location.angleTowards(head.pointAt(1)));
			location.set(head.pointAt(1));
			location.addPolar(-60, rotation.theta());
			velocity.decrease(delta * 0.00001f);
		}
	}
}