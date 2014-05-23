package music;

import java.util.LinkedList;

import org.jrabbit.base.data.ChangeListener;
import org.jrabbit.base.managers.Resources;

import settings.MicronGameSettings;

/*****************************************************************************
 * MicronSongList manages the entire soundtrack for Micron and provides controls
 * for navigating through the song list.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronSongList
{
	/**
	 * The index of the currently playing song.
	 **/
	private static int songChoice;

	/**
	 * The list of Songs available for playback.
	 **/
	private static Song[] songList = {
			new Song("Hypnothis", "Kevin MacLeod", "res/music/Hypnothis.ogg"),
			new Song("Lightless Dawn", "Kevin MacLeod",
					"res/music/Lightless Dawn.ogg"),
			new Song("Spacial Harvest", "Kevin MacLeod",
					"res/music/Spacial Harvest.ogg"),
			new Song("Blue Sizzle", "Kevin MacLeod",
					"res/music/Blue Sizzle.ogg"),
			new Song("Martian Cowboy", "Kevin MacLeod",
					"res/music/Martian Cowboy.ogg"),
			new Song("Redletter", "Kevin MacLeod", "res/music/Redletter.ogg"),
			new Song("The Hive", "Kevin MacLeod", "res/music/The Hive.ogg"),
			new Song("Rising", "Kevin MacLeod", "res/music/Rising.ogg"),
			new Song("Babylon", "Kevin MacLeod", "res/music/Babylon.ogg"),
			new Song("Soporific", "Kevin MacLeod", "res/music/Soporific.ogg"),
			new Song("With a Creation", "Kevin MacLeod",
					"res/music/With a Creation.ogg"),
			new Song("How it Begins", "Kevin MacLeod",
					"res/music/How it Begins.ogg") };
	
	/**
	 * All listeners that will receive notifications if a song is changed.
	 **/
	private static LinkedList<ChangeListener> listeners = 
			new LinkedList<ChangeListener>();
	
	/*************************************************************************
	 * Accesses all objects listening for changes.
	 * 
	 * @return The list of registered SongChangeListeners.
	 *************************************************************************/
	public static LinkedList<ChangeListener> listeners() { return listeners; }

	/*************************************************************************
	 * Plays the song at the desired index in the list.
	 * 
	 * @param song
	 * 			  The index of the song to play.
	 *************************************************************************/
	public static void play(int song)
	{
		while (song < 0)
			song += songList.length;
		songChoice = song % songList.length;
		songList[songChoice].beginPlay();
		for(ChangeListener listener : listeners)
			listener.alertChange();
		MicronGameSettings.userData().setSong(songChoice);
	}

	/*************************************************************************
	 * Plays the song after the currently playing one. If the current song is 
	 * at the end of the list, the first song in the list is played.
	 *************************************************************************/
	public static void next()
	{
		play(songChoice + 1);
	}

	/*************************************************************************
	 * Plays the song immediately before the currently playing one. If the
	 * current song is the first in the list, the last song in the list is 
	 * played.
	 *************************************************************************/
	public static void back()
	{
		play(songChoice - 1);
	}

	/*************************************************************************
	 * Plays a random song in the list.
	 *************************************************************************/
	public static void random()
	{
		play(Resources.random().nextInt(songList.length));
	}

	/*************************************************************************
	 * Learns the description of the currently playing song.
	 * 
	 * @return A String describing the name of the current song and the artist.
	 *************************************************************************/
	public static String songDescription()
	{
		return songList[songChoice].toString();
	}
}