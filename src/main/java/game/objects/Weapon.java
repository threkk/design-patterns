package game.objects;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.math.vector.BaseVector2f;

/*****************************************************************************
 * A Weapon is a simple interface that can be updated and can fire.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Weapon extends Updateable
{
	/*************************************************************************
	 * Fires the weapon.
	 * 
	 * @param location
	 * 			  The location at which to fire.
	 * @param rotation
	 * 			  The angle of the bullet to fire.
	 *************************************************************************/
	public void fireAt(BaseVector2f location, Rotation rotation);
}