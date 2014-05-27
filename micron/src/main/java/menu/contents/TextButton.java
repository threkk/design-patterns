package menu.contents;

import org.jrabbit.base.graphics.skins.text.TextSkin;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.input.MouseHandler;

import static menu.contents.MenuSettings.*;

/*****************************************************************************
 * A TextButton extends BaseButton to use a String of text to render the button.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TextButton extends BaseButton
{
	/**
	 * The TextSkin used to render the button.
	 **/
	protected TextSkin textSkin;

	/*************************************************************************
	 * Creates a TextButton with the indicated settings.
	 * 
	 * @param text
	 * 			  The String to display as the button.
	 * @param listener
	 * 			  The CommandListener to send alerts to.
	 * @param command
	 * 			  The command to issue.
	 * @param selected
	 * 			  The Color to use when the button is highlighted.
	 * @param deselected
	 * 			  The Color to use when the button is not highlighted.
	 *************************************************************************/
	public TextButton(String text, CommandListener listener, int command, 
			Color selected, Color deselected)
	{
		super(listener, command, selected, deselected);
		textSkin = new TextSkin(text, "Buttons");
	}

	/*************************************************************************
	 * Learns the dimensions of the TextButton.
	 * 
	 * @return The width of the button.
	 ***************************************************************/ @Override
	public float width() { return textSkin.width(); }

	/*************************************************************************
	 * Learns the dimensions of the TextButton.
	 * 
	 * @return The height of the button.
	 ***************************************************************/ @Override
	public float height() { return textSkin.height(); }

	/*************************************************************************
	 * Applies the demanded location and spacing. 
	 * 
	 * NOTE: As the MicronMenu will only use TextButtons for the "main" button 
	 * list, this location formatting is designed with that in mind.
	 * 
	 * @return The amount to shift the coordinates for the next object.
	 ***************************************************************/ @Override
	public float appendToLocation(float x, float y, float spacing)
	{
		location.set(x + (selected ? BUTTON_PHASE_SHIFT : 0), y - textSkin.textHeight());
		return textSkin.textHeight() + spacing;
	}

	/*************************************************************************
	 * Determines whether or not the mouse is currently over the TextButton.
	 * 
	 * @return True if the mouse if hovering over the button, false otherwise.
	 ***************************************************************/ @Override
	public boolean mouseOver()
	{
		Vector2f mouseLoc = MouseHandler.location(true);
		float x = mouseLoc.x() - location.x() + SELECTION_BUFFER;
		float y = mouseLoc.y() - location.y() + SELECTION_BUFFER;
		if(selected)
			x += BUTTON_PHASE_SHIFT;
		float width = textSkin.textWidth() + SELECTION_BUFFER * 2 + BUTTON_PHASE_SHIFT;
		float height = textSkin.textHeight() + SELECTION_BUFFER * 2;
		return x > 0 && x < width && y > 0 && y < height;
	}

	/*************************************************************************
	 * Sets the TextButton into its selected state.
	 ***************************************************************/ @Override
	protected void setSelected()
	{
		super.setSelected();
		location.addX(BUTTON_PHASE_SHIFT);
	}

	/*************************************************************************
	 * Sets the TextButton into its deselected state.
	 ***************************************************************/ @Override
	protected void setDeselected()
	{
		super.setDeselected();
		location.addX(-BUTTON_PHASE_SHIFT);
	}

	/*************************************************************************
	 * Renders the TextButton.
	 ***************************************************************/ @Override
	public void draw()
	{
		textSkin.render();
	}
}