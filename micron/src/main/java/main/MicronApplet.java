package main;

import org.jrabbit.standard.game.main.StandardApplet;

import settings.MicronGameSettings;

/*****************************************************************************
 * This class is used to play Micron in an Applet, instead of from a desktop.
 * This allows for an online distribution.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronApplet extends StandardApplet
{
	/**
	 * The serialization ID.
	 **/
	private static final long serialVersionUID = 5204628414216804422L;
	
	protected Micron micron;

	/*************************************************************************
	 * Runs the game after the Applet has been initialized.
	 ***************************************************************/ @Override
	protected void startGame()
	{
		 MicronGameSettings.setAppletMode(true);
		 (micron = new Micron()).run();
	}

	/*************************************************************************
	 * Does nothing.
	 ***************************************************************/ @Override
	protected void pauseGame() { }

	/*************************************************************************
	 * Does nothing.
	 ***************************************************************/ @Override
	protected void resumeGame() { }

	/*************************************************************************
	 * Signals Micron to exit.
	 ***************************************************************/ @Override
	protected void exitGame()
	{
		micron.exit();
	}
}