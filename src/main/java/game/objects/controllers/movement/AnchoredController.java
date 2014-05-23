package game.objects.controllers.movement;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.Located;

import game.objects.controllers.TargetedController;

/*****************************************************************************
 * An AnchoredController ensures that a Located object is fixed at the location
 * of another Vector2f.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnchoredController extends TargetedController<Located>
{
	/**
	 * The Vector2f that acts as the anchor.
	 */
	protected Vector2f anchor;

	/*************************************************************************
	 * Creates an AnchoredController.
	 * 
	 * @param target
	 * 			  The Located object that will follow the anchor.
	 * @param anchor
	 * 			  The Vector2f to follow.
	 *************************************************************************/
	public AnchoredController(Located target, Vector2f anchor)
	{
		super(target);
		this.anchor = anchor;
	}

	/*************************************************************************
	 * Sets the target's location to that of the anchor.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		target.location().set(anchor);
	}
}