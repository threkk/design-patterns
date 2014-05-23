package game.objects.entities.player.weapons;

import game.objects.Weapon;

/*****************************************************************************
 * A PlayerWeapon extends Weapon to add capability for activation and 
 * deactivation. Since PlayerWeapons will be managed in a list, and switched
 * around, they need to know when they are turned off and turned on again.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface PlayerWeapon extends Weapon
{
	/*************************************************************************
	 * Tells the PlayerWeapon that it is now the active weapon.
	 *************************************************************************/
	public void activate();

	/*************************************************************************
	 * Tells the PlayerWeapon that it is no longer the active weapon.
	 *************************************************************************/
	public void deactivate();
}
