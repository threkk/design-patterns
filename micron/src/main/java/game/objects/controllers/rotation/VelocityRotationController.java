package game.objects.controllers.rotation;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A VelocityRotationController makes the rotation of a PhysSprite match the
 * angle of its velocity;
 * 
 * @author Chris Molini
 *****************************************************************************/
public class VelocityRotationController extends TargetedController<PhysSprite>
{
	/*************************************************************************
	 * Creates a VelocityRotationController.
	 * 
	 * @param target
	 * 			  The PhysSprite whose rotation will match its velocity.
	 *************************************************************************/
	public VelocityRotationController(PhysSprite target)
	{
		super(target);
	}

	/*************************************************************************
	 * Updates the VelocityRotationController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		target.rotation().set(target.velocity().angle());
	}
}