package menu.contents;

/*****************************************************************************
 * A CommandListener is used to have objects signal commands at it.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface CommandListener
{
	/*************************************************************************
	 * Tells the CommandListener that it has a command to process.
	 * 
	 * @param command
	 * 			  The value that indicates the command being signaled.
	 *************************************************************************/
	public void processCommand(int command);
}