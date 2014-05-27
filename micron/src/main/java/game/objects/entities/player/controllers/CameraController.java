package game.objects.entities.player.controllers;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.managers.GameManager;
import org.lwjgl.input.Keyboard;

import settings.MicronGameSettings;

import game.objects.PhysSprite;
import game.objects.controllers.TargetedController;

/*****************************************************************************
 * A CameraController controls the camera to give the player an appropriate 
 * viewpoint.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CameraController extends TargetedController<PhysSprite>
{
	/**
	 * The base speed at which to move the Camera.
	 **/
	private static final float CAMERA_SPEED = 0.1f;

	/*************************************************************************
	 * Creates a CameraController that will manage the camera to accommodate the
	 * indicated PhysSprite.
	 * 
	 * @param target
	 * 			  The PhysSprite who should be treated as the focus of the main
	 * 			  viewpoint.
	 *************************************************************************/
	public CameraController(PhysSprite target) { super(target); }

	/*************************************************************************
	 * Manages the camera.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		Vector2f cameraLoc = GameManager.camera().location();
		Vector2f targetLoc = target.location().copy();
		if(MicronGameSettings.userData().useLaptopControls())
		{
			cameraLoc.add(target.velocity(), delta);
			if(KeyboardHandler.isKeyDown(Keyboard.KEY_LEFT))
				targetLoc.addX(-250);
			if(KeyboardHandler.isKeyDown(Keyboard.KEY_RIGHT))
				targetLoc.addX(250);
			if(KeyboardHandler.isKeyDown(Keyboard.KEY_UP))
				targetLoc.addY(-250);
			if(KeyboardHandler.isKeyDown(Keyboard.KEY_DOWN))
				targetLoc.addY(250);
			float distance = cameraLoc.distanceTo(targetLoc);
			if(distance > 0)
			{
				float shiftAmount = (distance * distance / 250000f) * CAMERA_SPEED * delta;
				cameraLoc.moveTowards(targetLoc, shiftAmount);
			}
		}
		else
		{
			cameraLoc.add(target.velocity(), delta * 0.66f);
			Vector2f mouseLoc = MouseHandler.location(true).copy();
			mouseLoc.add(-WindowManager.controller().width() / 2, 
					-WindowManager.controller().height() / 2);
			targetLoc.add(mouseLoc, 0.35f);
			float distance = cameraLoc.distanceTo(targetLoc);
			if(distance > 0)
			{
				float shiftAmount = (distance * distance / 40000f) * CAMERA_SPEED * delta;
				cameraLoc.moveTowards(targetLoc, shiftAmount);
			}
		}
	}
}