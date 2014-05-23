package game.objects.debris.base;

/*****************************************************************************
 * A Vacuum is an object that can remove Debris from the Matrix.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Vacuum
{
	/*************************************************************************
	 * Determines whether or not the Vacuum should remove the indicated Debris.
	 * 
	 * @param debris
	 * 			  The Debris to check against.
	 * 
	 * @return True if the Debris should be destroyed, false if it should be
	 *         left alone.
	 *************************************************************************/
	public boolean shouldRemove(Debris debris);
}