package game.objects.controllers.rotation;

import game.objects.controllers.TargetedController;

import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.standard.game.objects.base.BaseSprite;
import org.lwjgl.input.Keyboard;

import settings.MicronGameSettings;

/*****************************************************************************
 * InputRotationController handles rotating a BaseSprite based upon player 
 * input. This rotation is based upon the input mode in the active UserData; if
 * the game is set to Laptop mode then rotation is controlled by the directional
 * keys. Otherwise, the indicated BaseSprite will rotate to face the Mouse 
 * cursor.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class InputRotationController extends TargetedController<BaseSprite>
{
	/**
	 * The rate at which to rotate the target.
	 **/
	protected float rotationSpeed;

	/*************************************************************************
	 * Creates an InputRotationController.
	 * 
	 * @param target
	 * 			  The BaseSprite to rotate.
	 * @param rotationSpeed
	 * 			  The rate at which to rotate the target.
	 *************************************************************************/
	public InputRotationController(BaseSprite target, float rotationSpeed)
	{
		super(target);
		this.rotationSpeed = rotationSpeed;
	}

	/*************************************************************************
	 * Determines if the Player should aim left.
	 * 
	 * @return True if 'Left' is down and 'Right' is not.
	 *************************************************************************/
	public boolean aimLeft()
	{
		return KeyboardHandler.isKeyDown(Keyboard.KEY_LEFT) 
			&& !KeyboardHandler.isKeyDown(Keyboard.KEY_RIGHT);
	}

	/*************************************************************************
	 * Determines if the Player should aim right.
	 * 
	 * @return True if 'Right' is down and 'Left' is not.
	 *************************************************************************/
	public boolean aimRight()
	{
		return !KeyboardHandler.isKeyDown(Keyboard.KEY_LEFT) 
			&& KeyboardHandler.isKeyDown(Keyboard.KEY_RIGHT);
	}

	/*************************************************************************
	 * Determines if the Player should aim up.
	 * 
	 * @return True if 'Up' is down and 'Down' is not.
	 *************************************************************************/
	public boolean aimUp()
	{
		return KeyboardHandler.isKeyDown(Keyboard.KEY_UP) 
			&& !KeyboardHandler.isKeyDown(Keyboard.KEY_DOWN);
	}

	/*************************************************************************
	 * Determines if the Player should aim down.
	 * 
	 * @return True if 'Down' is down and 'Up' is not.
	 *************************************************************************/
	public boolean aimDown()
	{
		return !KeyboardHandler.isKeyDown(Keyboard.KEY_UP) 
			&& KeyboardHandler.isKeyDown(Keyboard.KEY_DOWN);
	}
	
	/************************************************************************
	 * Gets the target angle based upon Laptop input.
	 * 
	 * @return The angle of rotation for the target to rotate towards.
	 ************************************************************************/
	public float getLaptopTargetRotation()
	{
		if(aimLeft())
		{
			if(aimUp())
				return -135f;
			else if(aimDown())
				return 135f;
			else
				return 180;
		}
		else if(aimRight())
		{
			if(aimUp())
				return -45f;
			else if(aimDown())
				return 45f;
			else
				return 0;
		}
		else 
		{
			if(aimUp())
				return -90f;
			if(aimDown())
				return 90f;
		}
		return target.rotation().degrees();
	}
	
	/************************************************************************
	 * Gets the target angle based upon Desktop input.
	 * 
	 * @return The angle of rotation for the target to rotate towards.
	 ************************************************************************/
	public float getMouseTargetRotation()
	{
		return target.location().angleTowards(MouseHandler.location(false));
	}

	/*************************************************************************
	 * Updates the InputRotationController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(target != null)
			target.rotation().rotateTowards(
					MicronGameSettings.userData().useLaptopControls() ? 
					getLaptopTargetRotation() : getMouseTargetRotation(), 
					delta * rotationSpeed);
	}
}