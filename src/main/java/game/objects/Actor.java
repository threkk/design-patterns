package game.objects;

/*****************************************************************************
 * An Actor is an object that can perform an action.
 * 
 * This interface is intended to be used in collusion with Timers for repetitive
 * actions.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Actor
{
	/*************************************************************************
	 * Performs the action.
	 *************************************************************************/
	public void act();
}