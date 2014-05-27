package game.objects;

import org.jrabbit.base.graphics.types.Located;

/*****************************************************************************
 * An object that is Cullable will be removed from the Matrix when it moves more
 * than a set distance from the Camera's viewpoint. This is intended to keep the
 * gameworld free of clutter that is very far offscreen.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Cullable extends Located
{
	/*************************************************************************
	 * Learns the culling radius of the Cullable.
	 * 
	 * @return The distance the Cullable can be away from the camera before
	 * 		   being removed from the Matrix.
	 *************************************************************************/
	public float cullDistance();
}