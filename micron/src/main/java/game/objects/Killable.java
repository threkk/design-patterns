package game.objects;

/*****************************************************************************
 * An object that is killable can be destroyed with a single command.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Killable
{
	/*************************************************************************
	 * Destroys the Killable object, removing it from the gameworld. The object
	 * should also perform any necessary "on-death" actions.
	 *************************************************************************/
	public void kill();
}
