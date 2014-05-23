package game.objects.entities.player.controllers;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.objects.Sprite;
import org.lwjgl.input.Keyboard;

import settings.MicronGameSettings;

import game.objects.controllers.Controller;
import game.objects.entities.base.KeyPointEntity;
import game.objects.entities.player.weapons.*;

/*****************************************************************************
 * A WeaponController acts as a manager of a group of weapons that can be 
 * switched between. The control of weapons is managed by user input; as such,
 * this class is intended to only be used by the Player.
 *****************************************************************************/
public class WeaponController extends Controller implements Renderable
{	
	/**
	 * The current weapons.
	 **/
	protected PlayerWeapon[] weapons;
	
	/**
	 * The Sprites that will be used to render the GUI of the weapons.
	 **/
	protected Sprite[] weaponGUI;
	
	/**
	 * The index of the weapon that is currently active.
	 **/
	protected int currentWeapon;

	/*************************************************************************
	 * Creates a WeaponController.
	 * 
	 * @param owner
	 * 			  The KeyPointEntity that will be treated as the wielder of all
	 * 			  managed weapons. 
	 *************************************************************************/
	public WeaponController(KeyPointEntity owner)
	{
		weapons = new PlayerWeapon[] {	new MachineGun(owner),
										new ChargeBlaster(owner),
										new MissileLauncher(owner) };
		weaponGUI = new Sprite[weapons.length];
		for(int i = 0; i < weaponGUI.length; i++)
		{
			weaponGUI[i] = new Sprite("Weapon GUI " + (i + 1));
			weaponGUI[i].color().set(0, 0.3f, 0, 0.5f);
			weaponGUI[i].screenCoords().setEnabled(true);
		}
		currentWeapon = MicronGameSettings.userData().preferredWeapon();
		weapons[currentWeapon].activate();
		weaponGUI[currentWeapon].color().set(0, 1f, 0, 1f);
	}

	/*************************************************************************
	 * Selects the indicated weapon, if it is not selected already.
	 * 
	 * @param choice
	 * 			  The index of the weapon to choose.
	 *************************************************************************/
	public void selectWeapon(int choice)
	{
		if(choice != currentWeapon)
		{
			weapons[currentWeapon].deactivate();
			weaponGUI[currentWeapon].color().set(0, 0.3f, 0, 0.5f);
			currentWeapon = choice;
			weaponGUI[choice].color().set(0, 1f, 0, 1f);
			weapons[choice].activate();
			MicronGameSettings.userData().setPreferredWeapon(choice);
		}
	}

	/*************************************************************************
	 * Updates the current weapon and checks to see if the user is attempting to
	 * select a different one.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(KeyboardHandler.wasKeyPressed(Keyboard.KEY_1))
			selectWeapon(0);
		else if(KeyboardHandler.wasKeyPressed(Keyboard.KEY_2))
			selectWeapon(1);
		else if(KeyboardHandler.wasKeyPressed(Keyboard.KEY_3))
			selectWeapon(2);
		weapons[currentWeapon].update(delta);
	}

	/*************************************************************************
	 * Renders the weapon selection GUI.
	 ***************************************************************/ @Override
	public void render()
	{
		float y = WindowManager.controller().height();
		float x = 0;
		for(int i = 0; i < weaponGUI.length; i++)
		{
			weaponGUI[i].location().set(x + weaponGUI[i].scaledWidth() / 2, 
					y - weaponGUI[i].scaledHeight() / 2);
			weaponGUI[i].render();
			x += weaponGUI[i].scaledWidth();
		}
	}
}