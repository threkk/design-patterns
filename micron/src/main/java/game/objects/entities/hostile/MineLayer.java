package game.objects.entities.hostile;

import static org.jrabbit.base.managers.Resources.random;

import org.jrabbit.base.graphics.skins.animation.AnimationFactory;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.math.vector.BaseVector2f;

import game.objects.Actor;
import game.objects.Weapon;
import game.objects.controllers.action.TimedActorController;
import game.objects.controllers.life.LifetimeController;
import game.objects.controllers.movement.FollowController;
import game.objects.controllers.movement.HomingController;
import game.objects.controllers.movement.StrafeController;
import game.objects.controllers.rotation.RotatingController;
import game.objects.death.DeathBurstEffect;
import game.objects.effects.base.AnimatedMatrixEffect;
import game.objects.effects.effects.ExplosiveDeathEffect;
import game.objects.effects.effects.SingleHitEffect;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.base.StaticMatrixEntity;
import game.objects.entities.base.StaticParentEntity;
import game.objects.entities.controllers.color.HealthColorController;

/*****************************************************************************
 * A MineLayer is a somewhat durable enemy that drifts around the Matrix, laying
 * Mines that will be pulled towards the player and explode in a very damaging
 * attack.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MineLayer extends StaticParentEntity implements Weapon
{
	/**
	 * The MatrixEntity that is the victim of this MineLayer.
	 **/
	protected MatrixEntity target;
	
	/*************************************************************************
	 * Creates a MineLayer.
	 * 
	 * @param target
	 * 			  The MatrixEntity to attack with Mines.
	 * @param location
	 * 			  The location at which to spawn.
	 *************************************************************************/
	public MineLayer(MatrixEntity target, Vector2f location)
	{
		super("Mine Layer");
		setLayer("Enemies A");
		setMass(10f);
		scalar.setScale(3);
		setChildrenOnTop(false);
		complexity = 15;
		setPoints(1250);
		setFaction(0);
		setFactionRatings(1, 0, -1);
		setMaxSpeed(0.045f + random().nextFloat() * 0.015f);
		defineHealth(350 + random().nextFloat() * 275);
		setGeometry(new float[][] { {-12, -12}, {12, -12}, {12, 12}, {-12, 12} });
		this.location.set(location);
		defineKeyPoints(new float[][] { {-12, -12}, {12, -12}, {12, 12}, {-12, 12} });
		this.target = target;
		addControllers(	new RotatingController(this, 0.03f, 0.5f, true),
				new FollowController(this, target.location(), 400, 700, 1.3f, 0.5f),
				new StrafeController(this, target.location(), 0.5f, random().nextBoolean()),
				new HealthColorController(this){
					protected void setColor(float percent) {
						target.color().set(percent, 1f, percent);
					}});
		addDeathEffects(new DeathBurstEffect(Color.MAGENTA, 70, 0.04f));
		for(int i = 0; i < 4; i++)
		{
			MinePort port = new MinePort();
			port.addController(new HealthColorController(this, port){
					protected void setColor(float percent) {
						target.color().set(0.5f + percent / 2, percent, 1f);
					}});
			add(port, i, false);
		}
	}

	/*************************************************************************
	 * Places a Mine at the indicated location.
	 * 
	 * @param location
	 * 			  The Vector2f at which to spawn a Mine.
	 * @param rotation
	 * 			  The rotation of the MinePort triggering the call (unused).
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		Vector2f velocity = new Vector2f();
		velocity.add(velocity.unitVector(), -0.05f);
		matrix().add(new Mine(location, velocity));
	}

	/*************************************************************************
	 * A MinePort is a child MatrixEntity that will instruct the MineLayer when
	 * and where it should place Mines.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class MinePort extends StaticMatrixEntity implements Actor
	{
		/*********************************************************************
		 * Creates a MinePort.
		 *********************************************************************/
		public MinePort()
		{
			super("Mine Layer Component");
			setGeometry(new float[][] { {-4, -4}, {4, -4}, {4, 4}, {-4, 4} });
			setFaction(0);
			setFactionRatings(1, 0, -1);
			scalar.setScale(4);
			addControllers(	new RotatingController(this, 0.05f, 0.5f, true),
							new TimedActorController(this, 30000, 0.5f));
			addDeathEffect(new DeathBurstEffect(Color.RED, 15, 0.035f));
		}

		/*********************************************************************
		 * Instructs the MineLayer to place at Mine at this MinePort.
		 ***********************************************************/ @Override
		public void act()
		{
			fireAt(location, rotation);
		}
	}

	/*************************************************************************
	 * A Mine is placed into a Matrix and will attempt to seek out its target
	 * and explode at them.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class Mine extends AnimatedMatrixEffect
	{
		/*********************************************************************
		 * Creates a Mine.
		 * 
		 * @param location
		 * 			  The location at which to be placed.
		 * @param velocity
		 * 			  The velocity of the Mine.
		 *********************************************************************/
		public Mine(BaseVector2f location, BaseVector2f velocity)
		{
			super(AnimationFactory.createLoop(	"Mine 1",
												"Mine 2",
												"Mine 3",
												"Mine 4",
												"Mine 5",
												"Mine 6",
												"Mine 7",
												"Mine 8",
												"Mine 9",
												"Mine 10"));
			setGeometry(new float[][] { {-4, -4}, {4, -4}, {4, 4}, {-4, 4} });
			setLayer("Mines");
			setFaction(0);
			setFactionRatings(1, 0, -1);
			scalar.setScale(6f);
			this.location.set(location);
			setMaxSpeed(0.065f + random().nextFloat() * 0.035f);
			addEffect(new SingleHitEffect());
			addDeathEffects(new DeathBurstEffect(Color.MAGENTA, 15, 0.035f),
							new ExplosiveDeathEffect(5f, Color.RED, 0.04f, 1, false));
			addControllers(	new RotatingController(this, 0.015f, 0.5f, true),
							new LifetimeController(this, 50000, 0.35f),
							new HomingController(this, target.location(), 0.0003f));
			this.velocity().set(velocity);
		}
	}
}