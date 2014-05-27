package menu.contents;

/*****************************************************************************
 * MenuVariables contains some general settings for the main menu.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MenuSettings
{
	/**
	 * The buffer of empty space from the bottom of the screen.
	 **/
	public static final float Y_BUFFER = 30;
	
	/**
	 * The buffer of empty space from the left of the screen.
	 **/
	public static final float X_BUFFER = 100;
	
	/**
	 * The amount that the menu buttons should be shifted over in addition to
	 * regular buffer.
	 **/
	public static final float MENU_X_BUFFER = 50;
	
	/**
	 * The spacing between each menu button.
	 **/
	public static final float BUTTON_SPACING = 10;
	
	/**
	 * The amount for buttons to shift when they change phase. 
	 **/
	public static final float BUTTON_PHASE_SHIFT = 20;
	
	/**
	 * The selection buffer amount for text-based buttons to use.
	 **/
	public static final float SELECTION_BUFFER = 5;
	
	/**
	 * The x-offset for the content of ExpandingTextButtons.
	 **/
	public static final float CONTENT_OFFSET = 50;
	
	/**
	 * The rate at which the marquee progresses.
	 **/
	public static final float MARQUEE_SPEED = 0.005f;
	
	/**
	 * The rate at which the content of ExpandingTextButtons will be revealed.
	 **/
	public static final float CONTENT_EXPAND_RATE = 0.000125f;
}