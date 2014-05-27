package game.objects.controllers;

/*****************************************************************************
 * A ControllerParent manages a list of children controllers to delegate 
 * functionality.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface ControllerParent
{
	/*************************************************************************
	 * Adds the indicated controller to the parent.
	 * 
	 * @param controller
	 * 			  The Controller to add.
	 *************************************************************************/
	public void addController(Controller controller);

	/*************************************************************************
	 * Adds the indicated controllers to the parent.
	 * 
	 * @param controllers
	 * 			  The Controllers to add.
	 *************************************************************************/
	public void addControllers(Controller... controllers);

	/*************************************************************************
	 * Attempts to remove the indicated controller from the parent.
	 * 
	 * @param controller
	 * 			  The Controller to remove.
	 *************************************************************************/
	public void removeController(Controller controller);
}