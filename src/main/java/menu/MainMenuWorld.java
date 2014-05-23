package menu;

import loading.MicronCursors;

import org.jrabbit.base.sound.Music;
import org.jrabbit.standard.game.managers.GameManager;

import game.world.Matrix;
import grid.GridWorld;

/*****************************************************************************
 * MainMenuWorld provides the standard implementation of a GridWorld that 
 * contains the main menu.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MainMenuWorld extends GridWorld
{
	/**
	 * Indicates that gameplay should begin once fadeout finishes.
	 **/
	protected static final int COMMAND_PLAY = 1;

	/**
	 * Indicates that Micron should exit once fadeout finishes.
	 **/
	protected static final int COMMAND_EXIT = 2;
	
	/**
	 * The value that indicates what the MainMenuWorld should do once it has
	 * completely faded out.
	 **/
	protected int command;

	/*************************************************************************
	 * Creates a MainMenuWorld with a wandering viewpoint (see {@link 
	 * CameraWander}) and a MicronMenu that is used to display a menu and 
	 * process input.
	 *************************************************************************/
	public MainMenuWorld()
	{
		add(new CameraWander(camera));
		add(new MicronMenu());
	}

	/*************************************************************************
	 * Signals the MainMenuWorld should begin its fadeout, and that when it 
	 * finishes fading out it should begin playing a game.
	 *************************************************************************/
	public void play()
	{
		command = COMMAND_PLAY;
		fade.fadeOut();
	}

	/*************************************************************************
	 * Signals the MainMenuWorld should begin its fadeout, and that when it 
	 * finishes fading out Micron should exit.
	 *************************************************************************/
	public void exit()
	{
		command = COMMAND_EXIT;
		fade.fadeOut();
	}

	/*************************************************************************
	 * Whenever the MainMenuWorld is set as the active world, the cursor is
	 * set to the menu cursor.
	 ***************************************************************/ @Override
	public void onActivation()
	{
		MicronCursors.useMenuCursor();
		fade.fadeIn();
	}

	/*************************************************************************
	 * While fading in or out, if the command variable is set to {@link 
	 * #COMMAND_EXIT}, the background music will fade in or out appropriately.
	 * 
	 * @param alpha
	 * 			  The current level of fading.
	 ***************************************************************/ @Override
	protected void fading(float alpha)
	{
		if(command == COMMAND_EXIT)
			Music.setVolume(1f - alpha);
	}
	
	/*************************************************************************
	 * Causes the MainMenuWorld to either begin playing the game or exiting
	 * Micron once a fadeout cycle has finished.
	 ***************************************************************/ @Override
	protected void onCompleteFadeOut()
	{
		switch(command)
		{
			case COMMAND_PLAY: 
				switchTo(new Matrix()); 
				Music.setVolume(1f);
					break;
			case COMMAND_EXIT: 
				GameManager.currentLoop().exit(); 
					break;
		}
	}
}