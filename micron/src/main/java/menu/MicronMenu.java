package menu;

import loading.TextLoader;
import menu.contents.*;
import music.MicronSongList;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.objects.Sprite;

import settings.MicronGameSettings;
import static menu.contents.MenuSettings.*;

/*****************************************************************************
 * MicronMenu describes the standard Main Menu object for Micron.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronMenu extends Sprite implements Updateable, CommandListener
{
	/**
	 * The base color of all of the buttons.
	 **/
	private static final Color buttonColor = new Color(0, 0.3f, 0);

	/**
	 * Indicates that Micron should begin gameplay.
	 **/
	private static final int COMMAND_PLAY = 0;
	
	/**
	 * Indicates that Micron should begin its exit sequence.
	 **/
	private static final int COMMAND_EXIT = 1;

	/**
	 * Indicates that the next song should be chosen for playback.
	 **/
	private static final int COMMAND_SONG_NEXT = 10;
	
	/**
	 * Indicates that the previous song should be chosen for playback.
	 **/
	private static final int COMMAND_SONG_LAST = 11;
	
	/**
	 * Indicates that a random song should be chosen for playback.
	 **/
	private static final int COMMAND_SONG_RANDOM = 12;
	
	/**
	 * These are the MenuEntities that comprise the actual "menu."
	 **/
	protected MenuEntity[] menuButtons;
	
	/**
	 * These are the MenuEntities that comprise the soundtrack controls.
	 **/
	protected MenuEntity[] musicButtons;
	
	/*************************************************************************
	 * Creates the standard menu used by Micron.
	 *************************************************************************/
	public MicronMenu()
	{
		super("Logo");
		this.screenCoords.setEnabled(true);
		ExpandingTextButton howTo = new ExpandingTextButton("How To Play", 
				TextLoader.read(MicronGameSettings.isApplet() ? 
						"res/files/Micron How To Play (applet).txt" : 
						"res/files/Micron How To Play (desktop).txt"), 
				buttonColor, Color.GREEN, new Color(0.75f, 1f, 0));
		ExpandingTextButton scores = new ScoreDisplay(buttonColor, Color.ORANGE,
				new Color(1f, 1f, 0));
		ExpandingTextButton credits = new ExpandingTextButton("Credits", 
				TextLoader.read("res/files/Micron Credits.txt"), buttonColor, 
				new Color(0, 0.25f, 1f), Color.CYAN);
		howTo.addCompetitors(scores, credits);
		scores.addCompetitors(howTo, credits);
		credits.addCompetitors(howTo, scores);
		if(MicronGameSettings.isApplet())
			menuButtons = new MenuEntity[] {
				new TextButton("Play", this, COMMAND_PLAY, Color.WHITE, buttonColor),
				howTo, scores, credits
			};
		else
			menuButtons = new MenuEntity[] {
				new TextButton("Play", this, COMMAND_PLAY, Color.WHITE, buttonColor),
				howTo, scores, credits,
				new TextButton("Exit", this, COMMAND_EXIT, Color.RED, buttonColor)
			};
			
		musicButtons = new MenuEntity[] {
			new ImageButton("Shift Song", this, COMMAND_SONG_LAST, Color.GREEN, 
					buttonColor, true),
			new SongDisplay(Color.GREEN, buttonColor),
			new ImageButton("Shift Song", this, COMMAND_SONG_NEXT, Color.GREEN, 
					buttonColor, false),
			new ImageButton("Random Song", this, COMMAND_SONG_RANDOM, 
					Color.GREEN, buttonColor, false)
		};
		adjust();
	}
	
	/*************************************************************************
	 * Adjusts the locations of all components of the menu to keep them 
	 * organized. Also ensures that the Micron logo is appropriately resized.
	 *************************************************************************/
	public void adjust()
	{
		float x = WindowManager.controller().width();
		float y = WindowManager.controller().height();
		for(int i = musicButtons.length - 1; i >= 0; i--)
			x -= musicButtons[i].appendToLocation(x, y, 0);
		y -= Y_BUFFER;
		x = X_BUFFER + MENU_X_BUFFER;
		for(int i = menuButtons.length - 1; i >= 0; i--)
			y -= menuButtons[i].appendToLocation(x, y, BUTTON_SPACING);
		scalar.setScale(Math.max((int) (WindowManager.controller().width() / 300), 2));
		location.set(X_BUFFER + scaledWidth() / 2, y - BUTTON_SPACING - scaledHeight() / 2);
	}

	/*************************************************************************
	 * When certain buttons are clicked, they send a command to this 
	 * CommandListener indicating what should happen. This method takes care of
	 * making each command happen appropriately.
	 * 
	 * @param command
	 * 			  The integer that represents the command that should occur.
	 * 
	 * @see #COMMAND_PLAY
	 * @see #COMMAND_EXIT
	 * @see #COMMAND_SONG_NEXT
	 * @see #COMMAND_SONG_LAST
	 * @see #COMMAND_SONG_RANDOM
	 ***************************************************************/ @Override
	public void processCommand(int command)
	{
		switch(command)
		{
			case COMMAND_PLAY:
				MicronGameSettings.menuWorld().play();
					break;
			case COMMAND_EXIT:
				MicronGameSettings.menuWorld().exit();
					break;
			case COMMAND_SONG_NEXT:
				MicronSongList.next();
					break;
			case COMMAND_SONG_LAST:
				MicronSongList.back();
					break;
			case COMMAND_SONG_RANDOM:
				MicronSongList.random();
					break;
		}
	}

	/*************************************************************************
	 * Updates all of the menu buttons and re-organizes them.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		for(Updateable button : menuButtons)
			button.update(delta);
		for(Updateable button : musicButtons)
			button.update(delta);
		adjust();
	}

	/*************************************************************************
	 * Renders the menu and all of its buttons.
	 ***************************************************************/ @Override
	public void render()
	{
		super.render();
		for(Renderable button : menuButtons)
			button.render();
		for(Renderable button : musicButtons)
			button.render();
	}
}