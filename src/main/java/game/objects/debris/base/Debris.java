package game.objects.debris.base;

import game.objects.Cullable;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.Located;

/*****************************************************************************
 * Debris is a basic interface for any object in the Matrix that floats around
 * and can be consumed by a Vacuum.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Debris extends Cullable, Located
{
	/*************************************************************************
	 * Returns the force vector of this Debris.
	 * 
	 * @return The
	 *************************************************************************/
	public Vector2f force();

	/*************************************************************************
	 * Returns the mass of this Debris.
	 * 
	 * @return The value that dampens the acceleration applied from force.
	 *************************************************************************/
	public float mass();
}