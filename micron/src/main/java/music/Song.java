package music;

import org.jrabbit.base.sound.Music;

/*****************************************************************************
 * A Song contains data used to play and display information about a particular
 * audio loop used by Micron.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Song
{
	/**
	 * The title of the Song.
	 **/
	protected String title;
	
	/**
	 * The name of the artist.
	 **/
	protected String artist;
	
	/**
	 * The location of the audio file.
	 **/
	protected String filepath;
	
	/*************************************************************************
	 * Initializes a Song with the indicated settings.
	 * 
	 * @param title
	 * 			  The title of the song.
	 * @param artist
	 * 			  The artist who created the piece. 
	 * @param filepath
	 * 			  The location of the audio file to play.
	 *************************************************************************/
	public Song(String title, String artist, String filepath)
	{
		this.title = title;
		this.artist = artist;
		this.filepath = filepath;
	}
	
	/*************************************************************************
	 * Begins looping the song.
	 *************************************************************************/
	public void beginPlay()
	{
		Music.loop(filepath);
	}
	
	/*************************************************************************
	 * Learns the Song's description.
	 * 
	 * @return A String representation of the Song that tells both the song's
	 * 		   name and that of the artist.
	 ***************************************************************/ @Override
	public String toString()
	{
		return title + " -- " + artist + " -- ";
	}
}