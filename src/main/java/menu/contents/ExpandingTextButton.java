package menu.contents;

import java.util.LinkedList;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.skins.text.RenderedTextSkin;
import org.jrabbit.base.graphics.skins.text.TextSkin;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.Dimensioned;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.standard.game.objects.base.BaseSprite;
import org.lwjgl.opengl.GL11;

import static menu.contents.MenuSettings.*;

/*****************************************************************************
 * ExpandingTextButton provides some more complicated functionality than a 
 * simple button; it is intended to display information, not cause a separate
 * effect to occur.
 * 
 * Basically, an ExpandingTextButton has a hidden block of text that it
 * will reveal when clicked. By clicking it again, the paragraph is hidden.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ExpandingTextButton extends BaseSprite implements MenuEntity
{
	/**
	 * The TextSkin that renders the button.
	 **/
	protected TextSkin buttonSkin;
	
	/**
	 * The ExpandingText that contains the info to display.
	 **/
	protected ExpandingText content;

	/**
	 * The Color to display when the ExpandingText is collapsed and the mouse
	 * is not over the button.
	 **/
	protected Color collapsed;

	/**
	 * The Color to display when the ExpandingText is expanded XOR the mouse
	 * is over the button.
	 **/
	protected Color expanded;
	
	/**
	 * The Color to display when the ExpandingText is expanded and the mouse
	 * is over the button.
	 **/
	protected Color willCollapse;
	
	/**
	 * The offset for the ExpandingText from the button.
	 **/
	protected Vector2f contentOffset;
	
	/**
	 * Whether or not the mouse is over the button.
	 **/
	protected boolean selected;
	
	/**
	 * Whether or not the content has been expanded.
	 **/
	protected boolean extended;
	
	protected LinkedList<ExpandingTextButton> competition;

	/*************************************************************************
	 * Creates an ExpandingTextButton with the indicated settings.
	 * 
	 * @param buttonText
	 * 			  The text to display as the button.
	 * @param content
	 * 			  The content to display.
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
	public ExpandingTextButton(String buttonText, String content, Color collapsed, 
			Color expanded, Color willCollapse)
	{
		screenCoords.setEnabled(true);
		color.set(collapsed);
		buttonSkin = new TextSkin(buttonText, "Buttons");
		this.content = new ExpandingText(content);
		this.collapsed = collapsed;
		this.expanded = expanded;
		this.willCollapse = willCollapse;
		contentOffset = new Vector2f(CONTENT_OFFSET, 16);
		competition = new LinkedList<ExpandingTextButton>();
	}

	/*************************************************************************
	 * Adds the indicated ExpandingTextButton references as competitors.
	 * An ExpandingTextButton automatically collapses all competition whenever 
	 * it is expanded.
	 * 
	 * @param competitors
	 * 			  The ExpandingTextButtons to close upon expanding this one.
	 *************************************************************************/
	public void addCompetitors(ExpandingTextButton... competitors)
	{
		for(ExpandingTextButton competitor : competitors)
			competition.add(competitor);
	}

	/*************************************************************************
	 * Tells the ExpandingTextButton that it needs to expand its content.
	 *************************************************************************/
	public void showContent()
	{
		if(!extended)
		{
			for(ExpandingTextButton competitor : competition)
				competitor.hideContent();
			location.addX(BUTTON_PHASE_SHIFT);
			extended = true;
			content.show();
			color.set(mouseOver() ? willCollapse : expanded);
		}
	}

	/*************************************************************************
	 * Tells the ExpandingTextButton that it needs to collapse its content.
	 *************************************************************************/
	public void hideContent()
	{
		if(extended)
		{
			location.addX(-BUTTON_PHASE_SHIFT);
			extended = false;
			content.hide();
			color.set(mouseOver() ? expanded : collapsed);
		}
	}

	/*************************************************************************
	 * Learns the dimensions of the ExpandingTextButton.
	 * 
	 * @return The width of the button.
	 ***************************************************************/ @Override
	public float width()
	{
		return Math.max(buttonSkin.width(), content.width() + contentOffset.x());
	}

	/*************************************************************************
	 * Learns the dimensions of the ExpandingTextButton.
	 * 
	 * @return The height of the button.
	 ***************************************************************/ @Override
	public float height()
	{
		return buttonSkin.width() + content.height() + contentOffset.y();
	}

	/*************************************************************************
	 * Applies the demanded location and spacing. 
	 * 
	 * NOTE: As the MicronMenu will only use ExpandingTextButtons for the "main"
	 * button list, this location formatting is designed with that in mind.
	 * 
	 * @return The amount to shift the coordinates for the next object.
	 ***************************************************************/ @Override
	public float appendToLocation(float x, float y, float spacing)
	{
		float xOffset = (selected ? BUTTON_PHASE_SHIFT  + (extended ? BUTTON_PHASE_SHIFT : 0) : 0);
		location.set(x + xOffset, y - content.height() - buttonSkin.textHeight());
		contentOffset.set(CONTENT_OFFSET - xOffset, buttonSkin.textHeight() + spacing / 2);
		return buttonSkin.textHeight() + content.height() + spacing;
	}

	/*************************************************************************
	 * Determines whether or not the mouse is currently over the 
	 * ExpandingTextButton.
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
		float width = buttonSkin.textWidth() + SELECTION_BUFFER * 2 + BUTTON_PHASE_SHIFT;
		float height = buttonSkin.textHeight() + SELECTION_BUFFER * 2;
		return x > 0 && x < width && y > 0 && y < height;
	}

	/*************************************************************************
	 * Updates the ExpandingTextButton and its content.
	 * 
	 * @param delta
	 * 			  The amount of time that has elapsed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		boolean mouseOver = mouseOver();
		if(selected)
		{
			if(!mouseOver)
			{
				selected = false;
				location.addX(-BUTTON_PHASE_SHIFT);
				color.set(extended ? expanded : collapsed);
			}
		}
		else if(mouseOver)
		{
			selected = true;
			location.addX(BUTTON_PHASE_SHIFT);
			color.set(extended ? willCollapse : expanded);
		}
		if(selected && MouseHandler.wasButtonClicked(0))
		{
			if(extended)
				hideContent();
			else
				showContent();
		}
		content.update(delta);
	}

	/*************************************************************************
	 * Renders the ExpandingTextButton and its content.
	 ***************************************************************/ @Override
	public void draw()
	{
		buttonSkin.render();
		contentOffset.bind();
		content.render();
		contentOffset.release();
	}
	
	/*************************************************************************
	 * An ExpandingText shows the information displayed by an 
	 * ExpandingTextButton.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class ExpandingText implements Dimensioned, Updateable, Renderable
	{
		/**
		 * The state of neither expanding nor collapsing.
		 **/
		private static final int STATE_NORMAL = 0;

		/**
		 * The state of expanding.
		 **/
		private static final int STATE_EXPAND = 1;

		/**
		 * The state of collapsing.
		 **/
		private static final int STATE_COLLAPSE = 2;
		
		/**
		 * The RenderedTextSkin that renders the text to be displayed.
		 **/
		protected RenderedTextSkin textSkin;
		
		/**
		 * How much the ExpandingText is currently expanded.
		 **/
		protected float percentExpanded;
		
		/**
		 * The current state of the ExpandingText.
		 **/
		protected int state = 0;
		
		/*********************************************************************
		 * Creates an ExpandingText that renders the indicated text.
		 * 
		 * @param text
		 * 			  The information to display.
		 *********************************************************************/
		public ExpandingText(String text)
		{
			textSkin = new RenderedTextSkin(text, "Content");
			textSkin.setFormatWidth(512);
			textSkin.reformat();
		}

		/*********************************************************************
		 * Tells the ExpandingText that it needs to expand.
		 *********************************************************************/
		public void show()
		{
			if(percentExpanded < 1)
				state = STATE_EXPAND;
		}

		/*********************************************************************
		 * Tells the ExpandingText that it needs to collapse.
		 *********************************************************************/
		public void hide()
		{
			if(percentExpanded > 0)
				state = STATE_COLLAPSE;
		}

		/*********************************************************************
		 * Accesses the current dimensions of the ExpandingText.
		 * 
		 * @return The current width of the ExpandingText.
		 ***********************************************************/ @Override
		public float width() { return textSkin.textWidth(); }

		/*********************************************************************
		 * Accesses the current dimensions of the ExpandingText.
		 * 
		 * @return The current height of the ExpandingText.
		 ***********************************************************/ @Override
		public float height() { return textSkin.textHeight() * percentExpanded; }

		/*********************************************************************
		 * Renders the ExpandingText.
		 ***********************************************************/ @Override
		public void render()
		{
			expanded.bind(percentExpanded);
			textSkin.renderSurface().bind();
			float width = textSkin.renderSurface().width();
			float height = textSkin.renderSurface().height() * percentExpanded;
			float texWidth = textSkin.renderSurface().widthRatio();
			float texHeight = textSkin.renderSurface().heightRatio() * percentExpanded;
			
			GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(texWidth, 0);
				GL11.glVertex2f(width, 0);
				GL11.glTexCoord2f(0, texHeight);
				GL11.glVertex2f(0, height);
				GL11.glTexCoord2f(texWidth, texHeight);
				GL11.glVertex2f(width, height);
			GL11.glEnd();
		}

		/*********************************************************************
		 * Updates the ExpandingText.
		 * 
		 * @param delta
		 * 			  The amount of time that has passed since the last update.
		 ***********************************************************/ @Override
		public void update(int delta)
		{
			switch(state)
			{
				case STATE_EXPAND:
					percentExpanded += delta * CONTENT_EXPAND_RATE;
					if(percentExpanded >= 1)
					{
						percentExpanded = 1f;
						state = STATE_NORMAL;
					}
						break;
				case STATE_COLLAPSE:
					percentExpanded -= delta * CONTENT_EXPAND_RATE;
					if(percentExpanded <= 0)
					{
						percentExpanded = 0;
						state = STATE_NORMAL;
					}
						break;
			}
		}
	}
}