package game.objects.controllers.movement;

import static org.jrabbit.base.managers.Resources.random;
import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A PropellingController pushes a PhysSprite in the direction that it is 
 * rotated, acting like a "propeller" that moves it according to the direction
 * that it faces.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class PropellingController extends TargetedController<PhysSprite>
{
	/**
	 * The amount of thrust to apply to the PhysSprite.
	 **/
	protected float thrust;

	/*************************************************************************
	 * Creates a PropellingController.
	 * 
	 * @param target
	 * 			  The PhysSprite to affect.
	 * @param thrust
	 * 			  The force to apply.
	 *************************************************************************/
	public PropellingController(PhysSprite target, float thrust)
	{
		super(target);
		this.thrust = thrust;
	}

	/*************************************************************************
	 * Creates a PropellingController.
	 * 
	 * @param target
	 * 			  The PhysSprite to affect.
	 * @param thrust
	 * 			  The force to apply.
	 * @param variance
	 * 			  The percentage of randomization to use on the final thrust.
	 *************************************************************************/
	public PropellingController(PhysSprite owner, float thrust, float variance)
	{
		super(owner);
		this.thrust = thrust * (1f + (random().nextFloat() - 0.5f) * variance);
	}

	/*************************************************************************
	 * Updates the PropellingController, pushing the PhysSprite.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		target.force().addPolar(delta * thrust, target.rotation().theta());
	}
}