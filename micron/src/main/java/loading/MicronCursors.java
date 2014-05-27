package loading;

import java.awt.Point;

import org.jrabbit.base.graphics.misc.CursorLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

/*****************************************************************************
 * This class handles the different Cursors used by Micron.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronCursors
{
	/**
	 * The game cursor.
	 **/
	protected static Cursor gameCursor;
	
	/**
	 * The menu cursor.
	 **/
	protected static Cursor menuCursor;

	/*************************************************************************
	 * Loads both cursors and saves them for later use.
	 *************************************************************************/
	public static void loadCursors()
	{
		gameCursor = CursorLoader.load("res/images/game/gui/Reticule.png",
				new Point(14, 14));
		menuCursor = CursorLoader.load("res/images/menu/Cursor.png");
	}
	
	/*************************************************************************
	 * Makes the active cursor the one used in game.
	 *************************************************************************/
	public static void useGameCursor()
	{
		try
		{
			Mouse.setNativeCursor(gameCursor);
		} catch (LWJGLException e) { e.printStackTrace(); }
	}
	
	/*************************************************************************
	 * Makes the active cursor the one used for the main menu.
	 *************************************************************************/
	public static void useMenuCursor()
	{
		try
		{
			Mouse.setNativeCursor(menuCursor);
		} catch (LWJGLException e) { e.printStackTrace(); }
	}
}