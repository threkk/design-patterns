package menu.contents;

import org.jrabbit.base.data.ChangeListener;
import org.jrabbit.base.graphics.transforms.Color;

import settings.MicronGameSettings;

/*****************************************************************************
 * ScoreDisplay extends ExpandingTextButton to show the list of high scores the 
 * user has gotten.
 * 
 * ScoreDisplay registers itself with the active UserData to automatically 
 * update its displayed text whenever the user makes a new high score.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ScoreDisplay extends ExpandingTextButton implements ChangeListener
{
	/*************************************************************************
	 * Gets the text used to display the high scores.
	 * 
	 * @return The String forming the list of high scores, as returned by the
	 *         active UserData.
	 *************************************************************************/
	public static String getScores()
	{
		return MicronGameSettings.userData().asString();
	}

	/*************************************************************************
	 * Creates a ScoreDisplay that uses the indicated Colors to display its
	 * button and its text.
	 * 
	 * @param collapsed
	 * 			  The color to display when the button is inactive.
	 * @param expanded
	 * 			  The color to display when the button is hovered over but the
	 *            text is not expanded, and when the text is expanded but the
	 *            button is not hovered over.
	 * @param willCollapse
	 *            The color to display when the button is hovered over and the
	 *            text is expanded.
	 *************************************************************************/
	public ScoreDisplay(Color collapsed, Color expanded, Color willCollapse)
	{
		super("High Scores", getScores(), collapsed, expanded, willCollapse);
		MicronGameSettings.userData().setChangeListener(this);
	}

	/*************************************************************************
	 * Alerts the ScoreDisplay that it needs to update its displayed text.
	 ***************************************************************/ @Override
	public void alertChange()
	{
		this.content.textSkin.setText(getScores());
		this.content.textSkin.checkFormat();
	}
}