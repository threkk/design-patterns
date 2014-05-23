package game.objects.entities.player.weapons;

import game.objects.controllers.Controller;
import game.objects.controllers.life.LifetimeController;
import game.objects.controllers.rotation.RotatingController;
import game.objects.death.DeathBurstEffect;
import game.objects.effects.base.AnimatedMatrixEffect;
import game.objects.effects.effects.DoTEffect;
import game.objects.effects.effects.HitCounterEffect;
import game.objects.effects.effects.PushEffect;
import game.objects.entities.base.KeyPointEntity;

import org.jrabbit.base.graphics.skins.animation.AnimationFactory;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.lwjgl.input.Keyboard;

/*****************************************************************************
 * A ChargeBlaster is a slow, chargeable weapon that fires very damaging blasts
 * that deal damage in a line of effect.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ChargeBlaster implements PlayerWeapon
{
	/**
	 * The base scale of a ChargeBlast.
	 **/
	protected static final float BASE_SCALE = 3f;
	
	/**
	 * The additional scale of a ChargeBlast at full charge.
	 **/
	protected static final float ADD_SCALE = 5f;
	
	/**
	 * The base damage rate of a ChargeBlast.
	 **/
	protected static final float BASE_DAMAGE = 0.005f;
	
	/**
	 * The additional damage rate of a ChargeBlast at full charge.
	 **/
	protected static final float ADD_DAMAGE = 0.4f;
	
	/**
	 * The base force of a ChargeBlast.
	 **/
	protected static final float BASE_FORCE = 0.3f;
	
	/**
	 * The additional force of a ChargeBlast at full charge.
	 **/
	protected static final float ADD_FORCE = 100f;
	
	/**
	 * The base velocity of a ChargeBlast.
	 **/
	protected static final float BASE_SPEED = 0.1f;
	
	/**
	 * The additional velocity of a ChargeBlast at full charge.
	 **/
	protected static final float ADD_SPEED = 0.04f;
	
	/**
	 * The base lifetime of a ChargeBlast.
	 **/
	protected static final int BASE_LIFETIME = 7000;
	
	/**
	 * The additional lifetime of a ChargeBlast at full charge.
	 **/
	protected static final int ADD_LIFETIME = 3000;
	
	/**
	 * The base number of hits a ChargeBlast can deal.
	 **/
	protected static final int BASE_HITS = 1;
	
	/**
	 * The additional number of hits that can be dealt at full charge.
	 **/
	protected static final int ADD_HITS = 9;
	
	/**
	 * The base number of particles to spawn.
	 **/
	protected static final int BASE_PARTICLES = 20;
	
	/**
	 * The additional number of particles to spawn at full charge.
	 **/
	protected static final int ADD_PARTICLES = 180;
	
	/**
	 * The base speed of spawned particles.
	 **/
	protected static final float BASE_PARTICLE_SPEED = 0.015f;
	
	/**
	 * The additional speed of spawned particles when at full charge.
	 **/
	protected static final float ADD_PARTICLE_SPEED = 0.03f;
	
	/**
	 * The number of milliseconds that it takes for a ChargeBlast to become
	 * fully charged.
	 **/
	protected static final int CHARGE_TIME = 22500;
	
	/**
	 * The KeyPointEntity that will be the source of all ChargeBlasts.
	 **/
	protected KeyPointEntity owner;
	
	/**
	 * The ChargeBlast that is currently charging.
	 **/
	protected ChargeBlast blast;

	/*************************************************************************
	 * Creates a ChargeBlaster.
	 *************************************************************************/
	public ChargeBlaster(KeyPointEntity owner)
	{
		this.owner = owner;
	}

	/*************************************************************************
	 * Tells the MissileLauncher that it is now the active weapon.
	 * 
	 * Note: This doesn't need to do anything, so it doesn't.
	 ***************************************************************/ @Override
	public void activate() { }

	/*************************************************************************
	 * Tells the ChargeBlaster that it is not longer the active weapon, and that
	 * it should release its active charge if necessary.
	 ***************************************************************/ @Override
	public void deactivate()
	{
		if(blast != null)
		{
			blast.releaseCharge();
			blast = null;
		}
	}

	/*************************************************************************
	 * Updates the ChargeBlaster.
	 * 
	 * @param delta
	 * 			  The amount of time since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(MouseHandler.isButtonDown(0) || KeyboardHandler.isKeyDown(Keyboard.KEY_SPACE))
		{
			if(blast == null)
				fireAt(owner.pointAt(0), owner.rotation());
		}
		else if(blast != null)
		{
			blast.releaseCharge();
			blast = null;
		}
	}
	 
	/*************************************************************************
	 * Creates a ChargeBlast and adds it to the matrix. The ChargeBlast takes
	 * over its own updating.
	 * 
	 * NOTE: If the current blast is not null, then it is automatically 
	 * released.
	 * 
	 * @param location
	 * 			  The Vector2f at which to spawn the ChargeBlast.
	 * @param rotation
	 * 			  The angle at which to spawn the ChargeBlast.
	 ***************************************************************/ @Override
	public void fireAt(BaseVector2f location, Rotation rotation)
	{
		if(blast != null)
			blast.releaseCharge();
		if(owner.matrix() != null)
			owner.matrix().add(blast = new ChargeBlast());
	}

	/*************************************************************************
	 * A ChargeBlast is the chargeable projectile that can be used to deal 
	 * massive damage to targets in a straight line. Its damage, lifetime,
	 * speed, etc. are all variable based upon its charge level.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class ChargeBlast extends AnimatedMatrixEffect
	{
		/**
		 * Whether or not this ChargeBlast has been released.
		 **/
		protected boolean released;
		
		/**
		 * The amount of time that the ChargeBlast has been charging.
		 **/
		protected int timer;
		
		/*********************************************************************
		 * Creates a ChargeBlast.
		 *********************************************************************/
		public ChargeBlast()
		{
			super(AnimationFactory.createLoop(	"Charge 1", "Charge 2", 
												"Charge 3", "Charge 4" ));
			location.set(owner.pointAt(0));
			color.set(Color.MAGENTA);
			color.setAlpha(0.5f);
			scalar.setScale(BASE_SCALE);
			setLayer("Projectiles");
			setFaction(owner.faction());
			setMaxSpeed(30);
			setFactionRatings(owner.factionRatings());
			addControllers(	new RotatingController(this, 0.05f),
							new ChargeBlastController());
			setGeometry(new float[][] { { 5.33f, 0 }, 	{ 4, -4 },
										{ 0, -5.33f }, { 4, -4 },
										{ -5.33f, 0 }, { -4, 4 },
										{ 0, 5.33f },	{ 4, 4 }});
		}

		/*********************************************************************
		 * "Releases" this ChargeBlast, causing it to detach from its owner and
		 * shoot forward, damaging enemies in its path.
		 *********************************************************************/
		public void releaseCharge()
		{
			if(!released)
			{
				color.setAlpha(1f);
				released = true;
				float chargePercent = (float) timer / CHARGE_TIME;
				velocity.set(owner.velocity());
				velocity.addPolar(BASE_SPEED + (chargePercent * ADD_SPEED), 
						owner.rotation().theta());
				DoTEffect doT = new DoTEffect(BASE_DAMAGE + (chargePercent * 
						chargePercent * chargePercent * ADD_DAMAGE), true);
				addEffects(new PushEffect(BASE_FORCE + (chargePercent * 
						chargePercent * chargePercent * ADD_FORCE)), doT, 
						new HitCounterEffect(BASE_HITS + (int) (chargePercent * ADD_HITS)));
				addControllers(new LifetimeController(this, BASE_LIFETIME + 
						(int) (chargePercent * ADD_LIFETIME)), doT);
				addDeathEffect(new DeathBurstEffect(color.copy(), 
						BASE_PARTICLES + (int) (chargePercent * ADD_PARTICLES),
						BASE_PARTICLE_SPEED + (chargePercent * ADD_PARTICLE_SPEED)));
			}
		}

		/*********************************************************************
		 * A ChargeBlastController handles the more specific functionality of a
		 * ChareBlast.
		 * 
		 * @author Chris Molini
		 *********************************************************************/
		protected class ChargeBlastController extends Controller
		{
			/*****************************************************************
			 * Updates the ChargeBlastController.
			 * 
			 * @param delta
			 * 			  The amount of time that has elapsed since the last
			 * 			  update.
			 *******************************************************/ @Override
			public void update(int delta)
			{
				if(!released)
					if(owner.health() <= 0)
						releaseCharge();
					else
					{
						location.set(owner.pointAt(0));
						scalar.setScale(BASE_SCALE + (timer * ADD_SCALE / CHARGE_TIME));
						color.setAlpha((float) timer / CHARGE_TIME);
						timer = Math.min(CHARGE_TIME, timer + delta);
					}
				else kill();
			}
		}
	}
}