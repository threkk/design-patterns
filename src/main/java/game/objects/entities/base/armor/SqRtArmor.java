package game.objects.entities.base.armor;

/*****************************************************************************
 * SqRtArmor provides an implementation of Armor that provides an Entity with
 * exponential protection - that is, it only receives the square root of the
 * initial damage.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SqRtArmor implements Armor
{
	/*************************************************************************
	 * Returns the square root of the indicated damage.
	 * 
	 * @param amount
	 * 			  The initial damage that the parent Entity is being attacked 
	 * 			  with.
	 * 
	 * @return The reduced amount of damage that the Entity should use to reduce
	 *         its health. 
	 ***************************************************************/ @Override
	public float modifyDamage(float amount)
	{
		return (float) Math.sqrt(amount);
	}
}