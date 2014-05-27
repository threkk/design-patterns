package game.objects.entities.base.armor;

/*****************************************************************************
 * InvlunArmor is an implementation of Armor that makes an Entity 
 * indestructible to "conventional" damage.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class InvulnArmor implements Armor
{
	/*************************************************************************
	 * Prevents the Entity from receiving any damage.
	 * 
	 * @param amount
	 * 			  The amount of damage that is intended to damage the Entity.
	 * 
	 * @return 0.
	 ***************************************************************/ @Override
	public float modifyDamage(float amount)
	{
		return 0;
	}
}