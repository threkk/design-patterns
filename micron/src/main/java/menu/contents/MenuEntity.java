package menu.contents;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.types.Located;

/*****************************************************************************
 * A MenuEntity is a basic component of Micron's menu. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface MenuEntity extends Located, Updateable, Renderable
{
	/*************************************************************************
	 * Handles organizing the MenuEntity.
	 * 
	 * @param x
	 * 			  The starting x coordinate of this menu entity.
	 * @param y
	 * 			  The starting y coordinate of this menu entity.
	 * @param spacing
	 * 			  The spacing setting to respect.
	 * 
	 * @return The distance to shift on the appropriate axis.
	 *************************************************************************/
	public float appendToLocation(float x, float y, float spacing);
	
	/*************************************************************************
	 * Determines if the mouse is hovering over this component or not.
	 * 
	 * @return True if the mouse is over this MenuEntity, false otherwise.
	 *************************************************************************/
	public boolean mouseOver();
}