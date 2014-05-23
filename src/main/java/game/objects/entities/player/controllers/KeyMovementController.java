package game.objects.entities.player.controllers;

import org.jrabbit.base.input.KeyboardHandler;
import org.lwjgl.input.Keyboard;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * KeyMovementController is used to enable a PhysSprite to move around the
 * game world with presses of the keys W, A, S, and D.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class KeyMovementController extends TargetedController<PhysSprite>
{
	/**
	 * The force with which to push the PhysSprite.
	 **/
	private static final float FORCE = 0.0375f;

	/*************************************************************************
	 * Creates a KeyMovementController.
	 * 
	 * @param target
	 * 			  The PhysSprite to move.
	 *************************************************************************/
	public KeyMovementController(PhysSprite target) { super(target); }
	
	/*************************************************************************
	 * Determines if the Player should move to the left.
	 * 
	 * @return True if 'A' is down and 'D' is not.
	 *************************************************************************/
	private boolean inputLeft()
	{
		return KeyboardHandler.isKeyDown(Keyboard.KEY_A) 
			&& !KeyboardHandler.isKeyDown(Keyboard.KEY_D);
	}

	/*************************************************************************
	 * Determines if the Player should move to the right.
	 * 
	 * @return True if 'D' is down and 'A' is not.
	 *************************************************************************/
	private boolean inputRight()
	{
		return !KeyboardHandler.isKeyDown(Keyboard.KEY_A) 
			&& KeyboardHandler.isKeyDown(Keyboard.KEY_D);
	}

	/*************************************************************************
	 * Determines if the Player should move up.
	 * 
	 * @return True if 'W' is down and 'S' is not.
	 *************************************************************************/
	private boolean inputUp()
	{
		return KeyboardHandler.isKeyDown(Keyboard.KEY_W) 
			&& !KeyboardHandler.isKeyDown(Keyboard.KEY_S);
	}

	/*************************************************************************
	 * Determines if the Player should move down.
	 * 
	 * @return True if 'S' is down and 'W' is not.
	 *************************************************************************/
	private boolean inputDown()
	{
		return !KeyboardHandler.isKeyDown(Keyboard.KEY_W) 
			&& KeyboardHandler.isKeyDown(Keyboard.KEY_S);
	}

	/*************************************************************************
	 * Controls pushing so that if the PhysSprite is moving in the opposite
	 * direction of the push, it gets slowed down twice as fast. This is done
	 * to increase user control by a significant amount.
	 * 
	 * @param xAccel
	 * 			  The amount to push on the x-axis.
	 * @param yAccel
	 * 			  The amount to push on the y-axis.
	 *************************************************************************/
	private void handlePush(float xAccel, float yAccel)
	{
		if(xAccel * target.velocity().x() < 0)
			xAccel *= 2;
		if(yAccel * target.velocity().y() < 0)
			yAccel *= 2;
		target.force().add(xAccel, yAccel);
	}

	/*************************************************************************
	 * Updates the KeyMovementController.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(inputLeft())
		{
			if(inputUp())
				handlePush(FORCE * delta * -0.707f, FORCE * delta * -0.707f);
			else if(inputDown())
				handlePush(FORCE * delta * -0.707f, FORCE * delta * 0.707f);
			else
				handlePush(-FORCE * delta, 0);
		}
		else if(inputRight())
		{
			if(inputUp())
				handlePush(FORCE * delta * 0.707f, FORCE * delta * -0.707f);
			else if(inputDown())
				handlePush(FORCE * delta * 0.707f, FORCE * delta * 0.707f);
			else
				handlePush(FORCE * delta, 0);
		}
		else 
		{
			if(inputUp())
				handlePush(0, -FORCE * delta);
			if(inputDown())
				handlePush(0, FORCE * delta);
		}
	}
}