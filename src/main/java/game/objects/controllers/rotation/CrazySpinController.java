package game.objects.controllers.rotation;

import org.jrabbit.base.graphics.types.Rotated;
import static org.jrabbit.base.managers.Resources.*;

import game.objects.controllers.TargetedController;

/*****************************************************************************
 * CrazySpinController causes a Rotated object to have a completely random 
 * rotation at every update.
 * 
 * This is mainly useful for visual effects. E.g: for roughly circular, 
 * pixelated objects, using this controller will, to the human eye, make them 
 * appear smooth, without allowing the eye the predictability of constant 
 * rotation.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CrazySpinController extends TargetedController<Rotated>
{
	/*************************************************************************
	 * Creates a CrazySpinController.
	 * 
	 * @param target
	 * 			  The Rotated object to affect.
	 *************************************************************************/
	public CrazySpinController(Rotated target) { super(target); }

	/*************************************************************************
	 * Randomly rotates the target.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		target.rotation().set(random().nextFloat() * 360f);
	}
}