package game.objects.controllers.movement;

import org.jrabbit.base.graphics.transforms.Vector2f;
import static org.jrabbit.base.managers.Resources.*;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A WanderingController is designed to make a target PhysSprite be pushed 
 * randomly around the game world. It is useful for adding some randomization
 * to the movement of a MatrixSprite.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class WanderingController extends TargetedController<PhysSprite>
{
	/**
	 * The force Vector2f that is currently being applied to the PhysSprite.
	 **/
	protected Vector2f force;
	
	/**
	 * The magnitude of the force to apply.
	 **/
	protected float strength;
	
	/**
	 * This keeps track of the amount of time that has passed since the last
	 * randomization.
	 **/
	protected int counter;
	
	/**
	 * The minimum interval between force vector randomizations.
	 **/
	protected int baseInterval;
	
	/**
	 * The maximum interval that  can be randomly added to the base duration
	 * between force randomization.
	 **/
	protected int addInterval;

	/*************************************************************************
	 * Creates a WanderingController.
	 * 
	 * @param target
	 * 			  The PhysSprite to affect.
	 * @param strength
	 * 			  The magnitude of the force used to make the PhysSprite move
	 * 			  randomly.
	 * @param baseInterval
	 * 			  The minimum interval between force vector randomization.
	 * @param addInterval
	 * 			  The maximum interval that  can be randomly added to the base 
	 * 			  duration between force randomization.
	 *************************************************************************/
	public WanderingController(PhysSprite target, float strength, int baseInterval,
			int addInterval)
	{
		this(target, strength, baseInterval, addInterval, 0);
	}

	/*************************************************************************
	 * Creates a WanderingController.
	 * 
	 * @param target
	 * 			  The PhysSprite to affect.
	 * @param strength
	 * 			  The magnitude of the force used to make the PhysSprite move
	 * 			  randomly.
	 * @param baseInterval
	 * 			  The minimum interval between force vector randomization.
	 * @param addInterval
	 * 			  The maximum interval that  can be randomly added to the base 
	 * 			  duration between force randomization.
	 * @param variance
	 * 			  The randomization factor to apply to all settings.
	 *************************************************************************/
	public WanderingController(PhysSprite owner, float strength, int baseInterval,
			int addInterval, float variance)
	{
		super(owner);
		force = new Vector2f();
		this.strength = strength * (1f + (random().nextFloat() - 0.5f) * variance);
		force.setPolar(random().nextFloat() * 6.283f, strength);
		this.baseInterval = (int) (baseInterval * (1f + (random().nextFloat() - 
				0.5f) * variance));
		this.addInterval = (int) (addInterval * (1f + (random().nextFloat() -
				0.5f) * variance));
		counter = (int) (baseInterval + random().nextFloat() * addInterval);
	}

	/*************************************************************************
	 * Updates the WanderingController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		counter -= delta;
		if(counter <= 0)
		{
			counter  = (int) (baseInterval + random().nextFloat() * addInterval);
			force.setPolar(random().nextFloat() * 6.283f, strength);
		}
		target.force().add(force);
	}
}