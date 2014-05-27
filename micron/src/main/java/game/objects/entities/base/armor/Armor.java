package game.objects.entities.base.armor;

/*****************************************************************************
 * Armor is a basic interface for reducing damage for Entities.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Armor
{
	/*************************************************************************
	 * Returns a reduced amount of damage.
	 * 
	 * @param amount
	 * 			  The initial damage that the parent Entity is being attacked 
	 * 			  with.
	 * 
	 * @return The reduced amount of damage that the Entity should use to reduce
	 *         its health. 
	 *************************************************************************/
	public float modifyDamage(float amount);
}
