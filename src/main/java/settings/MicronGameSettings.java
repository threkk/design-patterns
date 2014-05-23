package settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jrabbit.base.directory.EngineDirectory;

import menu.MainMenuWorld;
import music.MicronSongList;

/*****************************************************************************
 * MicronGameSettings handles some general settings used by the game.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronGameSettings
{
	/**
	 * Whether or not the game should run in Applet mode (i.e., don't save 
	 * anything).
	 **/
	private static boolean isApplet = false;
	
	/**
	 * The name of the subdirectory to keep all Micron game data in.
	 **/
	private static String MICRON_DIR = "Micron";
	
	/**
	 * The name of the file that will store game data between replays.
	 **/
	private static final String FILE_NAME = "user_data_v1-1";
	
	/**
	 * To simplify saving and loading the same file, the path to the file that
	 * contains game data is cached for all uses.
	 **/
	private static String userDataFilepath;
	
	/**
	 * The world that handles Micron's main menu.
	 **/
	private static MainMenuWorld menuWorld;
	
	/**
	 * The active UserData.
	 **/
	private static UserData userData;
	
	/*************************************************************************
	 * Learns whether or not the game is running in applet mode.
	 * 
	 * @return True if the game is an applet, false otherwise.
	 *************************************************************************/
	public static boolean isApplet() { return isApplet; }
	
	/*************************************************************************
	 * Defines whether or not the game should run as an applet.
	 * 
	 * @param appletMode
	 * 			  Whether or not the game should run in Applet mode (i.e., don't
	 * 			  save or load anything).
	 *************************************************************************/
	public static void setAppletMode(boolean appletMode)
	{
		isApplet = appletMode;
	}
	
	/*************************************************************************
	 * Initializes and runs Micron.
	 *************************************************************************/
	public static void startGame()
	{
		if(isApplet)
		{
			userData = new UserData();
		}
		else
		{
			EngineDirectory.makeSubDirectory(MICRON_DIR);
			userDataFilepath = EngineDirectory.subDirectory(MICRON_DIR).
					getAbsolutePath() + '/' + FILE_NAME;
			File data = new File(userDataFilepath);
			if(data.exists())
				try
				{
					ObjectInputStream in = new ObjectInputStream(new 
							FileInputStream(data));
					userData = (UserData) in.readObject();
					in.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					userData = new UserData();
				}
			else
				userData = new UserData();
		}
		MicronSongList.play(userData.song());
		(menuWorld = new MainMenuWorld()).makeCurrent();
	}
	
	/*************************************************************************
	 * Saves and exits Micron.
	 *************************************************************************/
	public static void endGame()
	{
		if(!isApplet)
		{
			userData.setChangeListener(null);
			File data = new File(userDataFilepath);
			try {
				ObjectOutputStream out = new ObjectOutputStream(new 
						FileOutputStream(data));
				out.writeObject(userData);
				out.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	/*************************************************************************
	 * Accesses the world that manages the main menu.
	 * 
	 * @return The active MainMenuWorld.
	 *************************************************************************/
	public static MainMenuWorld menuWorld() { return menuWorld; }

	/*************************************************************************
	 * Accesses the object that manages the song preferences and the list of
	 * high scores.
	 * 
	 * @return The active UserData.
	 *************************************************************************/
	public static UserData userData() { return userData; }
}