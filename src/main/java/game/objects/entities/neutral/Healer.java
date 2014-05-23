package game.objects.entities.neutral;

import static org.jrabbit.base.managers.Resources.random;

import org.jrabbit.base.graphics.transforms.BlendOp;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Vector2f;

import game.objects.Actor;
import game.objects.controllers.TargetedController;
import game.objects.controllers.action.TimedActorController;
import game.objects.controllers.movement.AnchoredController;
import game.objects.controllers.movement.WanderingController;
import game.objects.controllers.rotation.CrazySpinController;
import game.objects.controllers.rotation.RotatingController;
import game.objects.death.DeathBurstEffect;
import game.objects.death.DeathKillEffect;
import game.objects.effects.base.MatrixEffect;
import game.objects.effects.base.StaticMatrixEffect;
import game.objects.effects.effects.DistancedHoTEffect;
import game.objects.entities.base.StaticMatrixEntity;
import game.objects.entities.controllers.color.HealthColorController;
import game.objects.sprite.MatrixSprite;
import game.objects.sprite.StaticMatrixSprite;
import game.world.Matrix;

/*****************************************************************************
 * A Healer is a neutral MatrixEntity that emits an aura that washes damage from
 * all MatrixEntities that enter into its radius.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Healer extends StaticMatrixEntity implements Actor
{
	/*************************************************************************
	 * Creates a Healer.
	 * 
	 * @param location
	 * 			  The location for the Healer to spawn at.
	 *************************************************************************/
	public Healer(Vector2f location)
	{
		super("Healer");
		setLayer("Enemies A");
		defineHealth(100 + random().nextFloat() * 50);
		setFaction(1);
		setFactionRatings(1, 1, 1);
		setPoints(300);
		complexity = 3;
		setMass(100);
		scalar.setScale(3f);
		this.location.set(location);
		setGeometry(new float[][] {{18, 0}, {0, -18}, {-18, 0}, {0, 18}});
		setMaxSpeed(0.025f + random().nextFloat() * 0.015f);
		addControllers(	new RotatingController(this, 0.0095f, 0.5f, true),
						new WanderingController(this, 0.74f, 30000, 60000, 0.5f),
						new WanderingController(this, 0.05f, 2500, 5000, 0.5f),
						new HealthColorController(this){
							protected void setColor(float percent) {
								target.color().set(1f, percent, 0);
							}},
						new TimedActorController(this, 10000, 0.5f));
		addDeathEffects(new DeathBurstEffect(new Color(0.66f, 0.33f, 0), 20, 0.04f),
						new DeathBurstEffect(Color.YELLOW, 10, 0.02f));
	}
	
	/*************************************************************************
	 * When added to a matrix, a Healer also adds its aura.
	 * 
	 * @param matrix
	 * 			  The Matrix to which this Healer is being added.
	 ***************************************************************/ @Override
	public void addedToMatrix(Matrix matrix)
	{
		super.addedToMatrix(matrix);
		MatrixEffect aura = new StaticMatrixEffect("Aura");
		aura.setGeometry(new float[][] {{ 64, 0 },	{ 48, -48 },
										{ 0, -64 }, { -48, -48 },
										{ -64, 0 }, { -48, 48 },
										{ 0, 64 }, 	{ 48, 48 }});
		aura.setLayer("Auras A");
		aura.scalar().setScale(6.5f);
		DistancedHoTEffect hoT = new DistancedHoTEffect(0.0066f, aura.scaledWidth() / 2);
		aura.addEffects(hoT);
		aura.addControllers(new CrazySpinController(aura), hoT,
							new HealthColorController(this, aura){
								protected void setColor(float percent) {
									target.color().set(1f - percent, percent, 0f, percent / 3f);
								}},
							new AnchoredController(aura, this.location));
		aura.location().add(location);
		aura.setFaction(1);
		aura.setFactionRatings(-1, -1, -1);
		addDeathEffect(new DeathKillEffect(aura));
		matrix.add(aura);
	}
		
	/*************************************************************************
	 * Emits a pulse. This is a purely decorative effect.
	 ***************************************************************/ @Override
	public void act()
	{
		if(matrix != null)
		{
			StaticMatrixSprite pulse = new StaticMatrixSprite("Pulse");
			pulse.setLayer("Auras A");
			pulse.transforms().add(new BlendOp.Additive());
			pulse.color().set(0, 1f, 0, 0);
			pulse.location().set(location);
			pulse.addControllers(	new PulseController(pulse), 
									new AnchoredController(pulse, location),
									new CrazySpinController(pulse));
			matrix.add(pulse);
		}
	}
	
	/*************************************************************************
	 * A PulseController handles a Pulse's scale and lifetime to ensure that it
	 * behaves properly.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class PulseController extends TargetedController<MatrixSprite>
	{
		/**
		 * Keeps track of the time the Pulse has been alive.
		 **/
		protected int timer;
		
		/**
		 * The duration of a single fade phase.
		 **/
		protected int fadeLength;
		
		/*********************************************************************
		 * Creates a PulseController.
		 * 
		 * @param target
		 * 			  The object to manage for a fade in / fade out effect.
		 *********************************************************************/
		public PulseController(MatrixSprite target)
		{
			super(target);
			fadeLength = 15000;
		}

		/*********************************************************************
		 * Updates the PulseController.
		 * 
		 * @param delta
		 * 			  The amount of time that has passed since the last update.
		 ***********************************************************/ @Override
		public void update(int delta)
		{
			timer += delta;
			if(timer >= fadeLength * 2)
				target.kill();
			else
			{
				target.color().setAlpha(Math.abs(1f - (float) (timer - fadeLength) 
						/ fadeLength) / 2f);
				target.scalar().setScale(timer * 0.00035f);
			}
		}
	}
}