package game.objects.entities.neutral;

import static org.jrabbit.base.managers.Resources.random;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.Colored;

import game.objects.controllers.TargetedController;
import game.objects.controllers.movement.AnchoredController;
import game.objects.controllers.movement.HomingController;
import game.objects.controllers.movement.WanderingController;
import game.objects.controllers.rotation.CrazySpinController;
import game.objects.debris.base.Debris;
import game.objects.debris.base.Vacuum;
import game.objects.effects.base.MatrixEffect;
import game.objects.effects.base.StaticMatrixEffect;
import game.objects.effects.effects.DistancedDoTEffect;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.base.StaticMatrixEntity;
import game.objects.entities.base.armor.InvulnArmor;
import game.world.Matrix;

/*****************************************************************************
 * A Black Hole is an incredibly destructive effect that will annihilate 
 * everything it touches.
 * 
 * Black Holes also suck up any debris present in the Matrix, providing both a
 * cool visual effect but also making sure as few objects are active as is
 * sensible. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class BlackHole extends StaticMatrixEntity implements Vacuum
{
	/**
	 * The damaging aura of death surrounding this Black Hole.
	 **/
	protected MatrixEffect aura;
	
	/*************************************************************************
	 * Creates a Black Hole.
	 * 
	 * @param target
	 * 			  The MatrixEntity the Black Hole has a chance to chase.
	 * @param location
	 * 			  The location at which to spawn the Black Hole.
	 *************************************************************************/
	public BlackHole(MatrixEntity target, Vector2f location)
	{
		super("Black Hole");
		setLayer("Black Hole");
		complexity = 10;
		armor = new InvulnArmor();
		this.location.set(location);
		float magnitude = 3f + random().nextFloat() * 6f;
		setMass(20 * magnitude);
		scalar.setScale(magnitude);
		setFaction(1);
		setFactionRatings(-1, -1, -1);
		setMaxSpeed((1f + random().nextFloat()) * 0.1f / magnitude);
		setGeometry(new float[][] {	{ 20, 0 },	{ 15, -15 },
									{ 0, -20 }, { -15, -15 },
									{ -20, 0 }, { -15, 15 },
									{ 0, 20 }, 	{ 15, 15 }});
		addControllers( new CrazySpinController(this),
						new WanderingController(this, 7.4f, 30000, 60000, 0.5f),
						new WanderingController(this, 7.4f, 10000, 20000, 0.5f),
						new WanderingController(this, 0.5f, 2500, 5000, 0.5f),
						new TargetedController<Colored>(this) {
							public void update(int delta) {
								if(matrix() != null)
									target.color().set(Color.blend(Color.BLACK, 
											matrix.grid().backgroundColor(), 0.66f));
							}});
		
		// A BlackHole has a 10% chance to be filled with a burning hatred for
		// the target, and implacably chase them down.
		if(random().nextFloat() >= 0.9f)
			addController(new HomingController(this, target.location(), 15));
	}
	
	/*************************************************************************
	 * When added to a Matrix, a Black Hole also places its deathly aura into
	 * the Matrix it is being placed in.
	 * 
	 * @param matrix
	 * 		  The Matrix into which this Black Hole is being placed.
	 ***************************************************************/ @Override
	public void addedToMatrix(Matrix matrix)
	{
		super.addedToMatrix(matrix);
		aura = new StaticMatrixEffect("Black Hole Aura");
		aura.setGeometry(new float[][] {{ 32, 0 },	{ 24, -24 },
										{ 0, -32 }, { -24, -24 },
										{ -32, 0 }, { -24, 24 },
										{ 0, 32 }, 	{ 24, 24 }});
		
		aura.setLayer("Black Hole Aura");
		aura.color().setAlpha(0.35f);
		float magnitude = scalar().xScale();
		aura.scalar().setScale(magnitude * 1.5f);
		DistancedDoTEffect doT = new DistancedDoTEffect(0.03f * magnitude, scaledWidth() / 2);
		aura.addEffects(doT);
		aura.addControllers(doT, new CrazySpinController(aura),
							new AnchoredController(aura, this.location),
							new TargetedController<Colored>(aura) {
								public void update(int delta) {
									if(matrix() != null)
										target.color().set(matrix().grid().color());
								}});
		aura.setFaction(1);
		aura.setFactionRatings(-1, -1, -1);
		aura.location().set(location);
		matrix.add(aura);
	}
		
	/*************************************************************************
	 * When removed from a Matrix, a Black Hole also removes its aura.
	 * 
	 * @param matrix
	 * 		  The Matrix from which this Black Hole is being removed.
	 ***************************************************************/ @Override
	public void removedFromMatrix(Matrix matrix)
	{
		matrix.remove(aura);
		super.removedFromMatrix(matrix);
	}

	/*************************************************************************
	 * Checks to see if the indicated Debris has passed the Black Hole's event
	 * horizon.
	 * 
	 * Additionally, applies a "gravity" effect to the supplied Debris.
	 * 
	 * @param debris
	 * 			  The Debris to check against.
	 ***************************************************************/ @Override
	public boolean shouldRemove(Debris debris)
	{
		float dist = debris.location().distanceTo(location());
		debris.force().add(debris.location().unitVectorTowards(location()), 100f / dist);
		return dist <= scaledWidth() / 4f;
	}
}