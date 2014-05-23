package menu.contents;

import static menu.contents.MenuSettings.*;

import music.MicronSongList;

import org.jrabbit.base.data.ChangeListener;
import org.jrabbit.base.graphics.skins.text.RenderedTextSkin;
import org.jrabbit.base.graphics.transforms.Color;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A SongDisplay shows a scrolling marquee of text that describes the current
 * song being played (its name and the name of the artist).
 * 
 * While technically a ImageButton, nothing happens when the button is clicked.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SongDisplay extends ImageButton implements ChangeListener
{
	/**
	 * The RenderedTextSkin that creates the Image used in rendering the 
	 * marquee.
	 **/
	protected RenderedTextSkin textSkin;

	/**
	 * The allowed width for the marquee.
	 **/
	private float marqueeWidth;

	/**
	 * The amount the marquee has scrolled.
	 **/
	private float marqueeScroll;
	
	/*************************************************************************
	 * Creates a SongDisplay that toggles between the two indicated colors when
	 * highlighted or not.
	 * 
	 * @param highlighted
	 * 			  The color to use when the button is hovered over with the 
	 * 			  mouse.
	 * @param normal
	 * 			  The color to use when the button is not hovered over.
	 *************************************************************************/
	public SongDisplay(Color highlighted, Color normal)
	{
		super("Song Title Bar", null, 0, highlighted, normal, false);
		textSkin = new RenderedTextSkin(MicronSongList.songDescription(), 
				"Content");
		textSkin.checkFormat();
		MicronSongList.listeners().add(this);
		marqueeWidth = width();
		marqueeScroll = -50;
	}

	/*************************************************************************
	 * Alerts the SongDisplay that it needs to update the text it uses in the 
	 * marquee.
	 ***************************************************************/ @Override
	public void alertChange()
	{
		textSkin.setText(MicronSongList.songDescription());
		textSkin.checkFormat();
		marqueeScroll = -50;
	}

	/*************************************************************************
	 * Updates the marquee in addition to managing button settings.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		super.update(delta);
		marqueeScroll = (marqueeScroll + MARQUEE_SPEED * delta) % textSkin.textWidth();
	}
	
	/*************************************************************************
	 * This admittedly monstrous method renders the marquee text over the frame.
	 ***************************************************************/ @Override
	public void draw()
	{
		super.draw();
		textSkin.renderSurface().bind();
		float textWidthRatio = textSkin.renderSurface().widthRatio();
		float textHeightRatio = textSkin.renderSurface().heightRatio();
		float textWidth = textSkin.textWidth();
		float textHeight = textSkin.textHeight();
		GL11.glTranslatef(-marqueeWidth / 2, -textHeight / 2, 0);
		float marqueeAmountLeft = (textWidth - marqueeScroll);
		float marqueeTextureStart = (1 - (marqueeAmountLeft / textWidth)) * textWidthRatio;
		if(marqueeAmountLeft > marqueeWidth)
		{
			float extra = marqueeAmountLeft - marqueeWidth;
			float textureEnd = (textWidthRatio - (textWidthRatio * (extra / textWidth)));
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(marqueeTextureStart, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(textureEnd, 0);
				GL11.glVertex2f(marqueeWidth, 0);
				GL11.glTexCoord2f(textureEnd, textHeightRatio);
				GL11.glVertex2f(marqueeWidth, textHeight);
				GL11.glTexCoord2f(marqueeTextureStart, textHeightRatio);
				GL11.glVertex2f(0, textHeight);
			GL11.glEnd();
		}
		else
		{
			float amountToRender = marqueeWidth;
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(marqueeTextureStart, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(textWidthRatio, 0);
				GL11.glVertex2f(marqueeAmountLeft, 0);
				GL11.glTexCoord2f(textWidthRatio, textHeightRatio);
				GL11.glVertex2f(marqueeAmountLeft, textHeight);
				GL11.glTexCoord2f(marqueeTextureStart, textHeightRatio);
				GL11.glVertex2f(0, textHeight);
			GL11.glEnd();
			amountToRender -= marqueeAmountLeft;
			GL11.glTranslatef(marqueeAmountLeft, 0, 0);
			for(int i = 0; i < amountToRender / textWidth; i++)
			{
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0, 0);
					GL11.glVertex2f(0, 0);
					GL11.glTexCoord2f(textWidthRatio, 0);
					GL11.glVertex2f(textWidth, 0);
					GL11.glTexCoord2f(textWidthRatio, textHeightRatio);
					GL11.glVertex2f(textWidth, textHeight);
					GL11.glTexCoord2f(0, textHeightRatio);
					GL11.glVertex2f(0, textHeight);
				GL11.glEnd();
				GL11.glTranslatef(textWidth, 0, 0);
				amountToRender -= textWidth;
			}
			float remainder = (amountToRender / textWidth) % 1;
			if(remainder > 0)
			{
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0, 0);
					GL11.glVertex2f(0, 0);
					GL11.glTexCoord2f(textWidthRatio * remainder, 0);
					GL11.glVertex2f(textWidth * remainder, 0);
					GL11.glTexCoord2f(textWidthRatio * remainder, textHeightRatio);
					GL11.glVertex2f(textWidth * remainder, textHeight);
					GL11.glTexCoord2f(0, textHeightRatio);
					GL11.glVertex2f(0, textHeight);
				GL11.glEnd();
			}
		}
		GL11.glTranslatef(marqueeWidth / 2, textHeight / 2, 0);
	}
}