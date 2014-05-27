package menu.contents;

import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.input.MouseHandler;

/*****************************************************************************
 * An ImageButton extends BaseButton to use an image to render the button.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ImageButton extends BaseButton
{
	/**
	 * The ImageSkin that renders the button.
	 **/
	protected ImageSkin image;

	/*************************************************************************
	 * Creates an ImageButton with the indicated settings.
	 * 
	 * @param image
	 * 			  The String that indicates which Image to use for rendering.
	 * @param listener
	 * 			  The CommandListener to send alerts to.
	 * @param command
	 * 			  The command to issue.
	 * @param selected
	 * 			  The Color to use when the button is highlighted.
	 * @param deselected
	 * 			  The Color to use when the button is not highlighted.
	 * @param flipX
	 * 			  Whether or not to flip the button horizontally.
	 *************************************************************************/
	public ImageButton(String image, CommandListener listener, int command, 
			Color selected, Color deselected, boolean flipX)
	{
		super(listener, command, selected, deselected);
		this.image = new ImageSkin(image);
		scalar.setFlipHorizontally(flipX);
	}

	/*************************************************************************
	 * Learns the dimensions of the ImageButton.
	 * 
	 * @return The width of the button.
	 ***************************************************************/ @Override
	public float width() { return image.width(); }

	/*************************************************************************
	 * Learns the dimensions of the ImageButton.
	 * 
	 * @return The height of the button.
	 ***************************************************************/ @Override
	public float height() { return image.height(); }

	/*************************************************************************
	 * Applies the demanded location and spacing. 
	 * 
	 * NOTE: As the MicronMenu will only use ImageButtons on the lower right 
	 * corner of the screen (for the soundtrack controls), this location 
	 * formatting is designed with that in mind.
	 * 
	 * @return The amount to shift the coordinates for the next object.
	 ***************************************************************/ @Override
	public float appendToLocation(float x, float y, float spacing)
	{
		location.set(x - image.width() / 2, y - image.height() / 2);
		return image.width() + spacing;
	}

	/*************************************************************************
	 * Determines if the mouse is currently over the ImageButton.
	 * 
	 * @return True if the mouse if hovering over the button, false otherwise.
	 ***************************************************************/ @Override
	public boolean mouseOver()
	{
		Vector2f mouseLoc = MouseHandler.location(true);
		float x = mouseLoc.x() - location.x();
		float y = mouseLoc.y() - location.y();
		return Math.abs(x) < image.width() / 2 && Math.abs(y) < height() / 2;
	}

	/*************************************************************************
	 * Renders the button.
	 ***************************************************************/ @Override
	public void draw()
	{
		image.render();
	}
}