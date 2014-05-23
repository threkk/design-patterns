package main;

import java.util.Scanner;

import loading.MicronLoader;

import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.managers.window.controllers.DesktopWindowController;
import org.jrabbit.standard.game.main.StandardGame;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import settings.MicronGameSettings;

/*****************************************************************************
 * This class handles running Micron.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Micron extends StandardGame
{
	/*************************************************************************
	 * Creates the game.
	 *************************************************************************/
	public Micron()
	{
		super("Micron", "res/images/Micron Icon.png");
	}

	/*************************************************************************
	 * Initializes Micron.
	 ***************************************************************/ @Override
	protected void setup()
	{
		profiler.setVisible(false);
		new MicronLoader().run();
		MicronGameSettings.startGame();
	}
	
	/*************************************************************************
	 * Determines if the game should fast escape (by holding left-shift and 
	 * escape at the same time), handles toggling whether or not the Profiler is
	 * displayed, manages toggling fullscreen mode from within an applet, and
	 * handles switching input modes from between laptop and desktop.
	 * 
	 * @param delta
	 * 			  The number of clock ticks that have passed since the game was
	 * 			  last updated.
	 ***************************************************************/ @Override
	public void advanceGame(int delta)
	{
		if(!MicronGameSettings.isApplet() && 
				KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE) && 
				KeyboardHandler.isKeyDown(Keyboard.KEY_LSHIFT))
			exit();
		else if(KeyboardHandler.isKeyDown(Keyboard.KEY_TAB) &&
				KeyboardHandler.isKeyDown(Keyboard.KEY_TAB))
		{
			if(Display.isFullscreen())
				WindowManager.controller().setWindowed(0, 0);
			else
				WindowManager.controller().setFullscreen();
		}
		if(KeyboardHandler.wasKeyPressed(Keyboard.KEY_F11))
			profiler.setVisible(!profiler.visible());
		if(KeyboardHandler.wasKeyPressed(Keyboard.KEY_EQUALS))
			MicronGameSettings.userData().toggleInputSetup();
	}
	
	/*************************************************************************
	 * Saves all necessary game data (high scores, currently playing song).
	 ***************************************************************/ @Override
	public void onExit()
	{
		MicronGameSettings.endGame();
	}
	
	/*************************************************************************
	 * Initializes jRabbit and runs Micron.
	 * 
	 * NOTE: The window settings can be determined by command-line parameters.
	 * Supplying a parameter of the form AxB (where A and B are integers) will
	 * cause the game to run in a windowed mode of those dimensions. Adding a
	 * '-f' as another parameter will cause the game to run in fullscreen mode,
	 * but with the indicated resolution.
	 * 
	 * @param args
	 * 			  Any command-line arguments.
	 *************************************************************************/
	public static void main(String[] args)
	{
		if(args.length == 0)
			init();
		else if(args.length == 1)
		{
			Scanner dimensionScanner = new Scanner(args[0]);
			dimensionScanner.useDelimiter("x");
			init(dimensionScanner.nextInt(), dimensionScanner.nextInt());
		}
		else if(args.length == 2)
		{
			Scanner dimensionScanner = new Scanner(args[0]);
			dimensionScanner.useDelimiter("x");
			init(new DesktopWindowController(dimensionScanner.nextInt(), 
					dimensionScanner.nextInt(), args[1].equals("-f")));
		}
		new Micron().run();
	}
}