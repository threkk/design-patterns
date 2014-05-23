package settings;

import java.io.Serializable;

import org.jrabbit.base.data.ChangeListener;

/*****************************************************************************
 * UserData handles settings influenced by the user - high scores, song choice,
 * input and weapon preference.
 * 
 * UserData is Serializable; this way, it can be saved to file and read back in
 * as a complete object without needing to parse anything.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class UserData implements Serializable
{
	/**
	 * The serialization ID.
	 **/
	private static final long serialVersionUID = 3553469229227112085L;
	
	/**
	 * The current song choice of the user.
	 **/
	protected int song;
	
	/**
	 * The preferred weapon the player should use.
	 **/
	protected int preferredWeapon;
	
	/**
	 * All of the high scores of the user, in descending order.
	 **/
	protected long[] scores;
	
	/**
	 * Whether or not the input controls should be for laptops.
	 **/
	protected boolean useLaptopControls;
	
	/**
	 * This ChangeListener is updated whenever the list of high scores is 
	 * changed.
	 **/
	private ChangeListener listener;
	
	/*************************************************************************
	 * Creates a UserData that by default selects the first song in the list and
	 * has a set of 10 default high-scores.
	 *************************************************************************/
	public UserData()
	{
		preferredWeapon = 0;
		song = 0;
		// Initializes the default scores.
		scores = new long[] {	100000, 
								50000,
								30000, 
								25000, 
								10000, 
								5000, 
								3300, 
								2500, 
								1500, 
								1000};
		useLaptopControls = false;
	}
	
	/*************************************************************************
	 * Redefines the ChangeListener of the UserData.
	 * 
	 * @param listener
	 * 			  The ChangeListener that will be alerted whenever the list of 
	 * 			  high scores is modified.
	 *************************************************************************/
	public void setChangeListener(ChangeListener listener)
	{
		this.listener = listener;
	}
	
	/*************************************************************************
	 * Learns the current song choice of the player.
	 * 
	 * @return The index of the song the player has currently chosen to play
	 *         during Micron.
	 *************************************************************************/
	public int song() { return song; }
	
	/*************************************************************************
	 * Redefines the player's song choice.
	 * 
	 * @param song
	 *            The index of the song the player has currently chosen to play
	 *            during Micron.
	 *************************************************************************/
	public void setSong(int song)
	{
		this.song = song;
	}
	
	/*************************************************************************
	 * Learns the preferred weapon choice of the player.
	 * 
	 * @return The weapon the player wants to use when spawned.
	 *************************************************************************/
	public int preferredWeapon() { return preferredWeapon; }
	
	/*************************************************************************
	 * Redefines the player's preferred weapon choice.
	 * 
	 * @param preferredWeapon
	 *            The weapon the player wants to use when spawned.
	 *************************************************************************/
	public void setPreferredWeapon(int preferredWeapon)
	{
		this.preferredWeapon = preferredWeapon;
	}

	/*************************************************************************
	 * Switches between laptop mode and desktop mode.
	 *************************************************************************/
	public void toggleInputSetup()
	{
		useLaptopControls = !useLaptopControls;
	}
	
	/*************************************************************************
	 * Learns whether laptop controls should be used (keyboard only), or desktop
	 * controls (keyboard and mouse). 
	 * 
	 * @return True if the laptop controls are active, false if not.
	 *************************************************************************/
	public boolean useLaptopControls()
	{
		return useLaptopControls;
	}

	/*************************************************************************
	 * Accesses the current list of high scores.
	 * 
	 * @return The array of integers that lists the current records for the 
	 *         player.
	 *************************************************************************/
	public long[] scores() { return scores; }
	
	/*************************************************************************
	 * Offers the indicated score for processing as a high score. If it is not
	 * higher than any of the current scores on the list, nothing happens; 
	 * otherwise it is placed at the appropriate position and all lower scores 
	 * are pushed down one place.
	 * 
	 * @param score
	 * 			  The score to attempt to place in the list of high scores.
	 *************************************************************************/
	public void pushScore(long score)
	{
		if(score > scores[scores.length - 1])
		{
			int place = scores.length - 1;
			for(int i = scores.length - 2; i >= 0; i--)
				if(score > scores[i])
					place = i;
			for(int i = scores.length - 1; i > place; i--)
				scores[i] = scores[i - 1];
			scores[place] = score;
			listener.alertChange();
		}
	}

	/*************************************************************************
	 * Retrieves the String that will represent the list of high scores when
	 * rendered.
	 * 
	 * @return The list of high scores, in String form. This will be rendered by
	 *         a RenderedTextSkin in the main menu.
	 *************************************************************************/
	public String asString()
	{
		String results = " ";
		for(int i = 0; i < scores.length; i++)
			results += i + 1 + ": " + scores[i] + "\n ";
		return results;
	}
}