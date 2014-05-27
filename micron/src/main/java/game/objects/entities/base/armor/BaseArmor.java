package game.objects.entities.base.armor;

/*****************************************************************************
 * This provides the standard implementation of Armor.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class BaseArmor implements Armor
{
	/**
	 * The amount to reduce the initial damage by.
	 **/
	protected float straightReduction;
	
	/**
	 * The percentage of the remaining damage that will be ignored.
	 **/
	protected float percentReduction;

	/*************************************************************************
	 * Creates a BaseArmor.
	 * 
	 * @param reduction
	 * 			  The amount to reduce damage by.
	 * @param percentage
	 * 			  The percentage to reduce damage by.
	 *************************************************************************/
	public BaseArmor(float reduction, float percentage)
	{
		straightReduction = reduction;
		percentReduction = percentage;
	}

	/*************************************************************************
	 * Returns a reduced amount of damage.
	 * 
	 * @param amount
	 * 			  The initial damage that the parent Entity is being attacked 
	 * 			  with.
	 * 
	 * @return The reduced amount of damage that the Entity should use to reduce
	 *         its health. 
	 ***************************************************************/ @Override
	public float modifyDamage(float damage)
	{
		return Math.max((1f - percentReduction) *
				(damage - straightReduction), 0);
	}
}