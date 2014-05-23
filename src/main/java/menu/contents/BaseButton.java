package menu.contents;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.standard.game.objects.base.BaseSprite;

/*****************************************************************************
 * A BaseButton extends BaseSprite to implement MenuEntity. It provides general
 * functionality for a 2-phase button (hovered over and not hovered over) that
 * will send an instruction to a CommandListener when clicked while active.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class BaseButton extends BaseSprite implements MenuEntity
{
	/**
	 * Whether or not the mouse is over the button.
	 **/
	protected boolean selected;
	
	/**
	 * The color the button should use when it is selected.
	 **/
	protected Color selectedColor;
	
	/**
	 * The color the button should use when it is not selected.
	 **/
	protected Color deselectedColor;
	
	/**
	 * The command the button should send to its CommandListener when it is
	 * clicked.
	 **/
	protected int command;
	
	/**
	 * The CommandListener that is notified when the button is clicked.
	 **/
	protected CommandListener listener;
	
	/*************************************************************************
	 * Creates a BaseButton with the indicated settings.
	 * 
	 * @param listener
	 * 			  The CommandListener to send alerts to.
	 * @param command
	 * 			  The command to issue.
	 * @param selected
	 * 			  The Color to use when the button is highlighted.
	 * @param deselected
	 * 			  The Color to use when the button is not highlighted.
	 *************************************************************************/
	public BaseButton(CommandListener listener, int command, Color selected, 
			Color deselected)
	{
		screenCoords.setEnabled(true);
		this.command = command;
		this.listener = listener;
		selectedColor = selected;
		deselectedColor = deselected;
		color.set(deselected);
	}
	
	/*************************************************************************
	 * Handles the mouse location, determining if the button should be 
	 * highlighted or not.
	 *************************************************************************/
	public void handleSelection()
	{
		if(selected)
		{
			if(!mouseOver())
			{
				selected = false;
				setDeselected();
			}
		}
		else if(mouseOver())
		{
			selected = true;
			setSelected();
		}
	}

	/*************************************************************************
	 * Modifies the button into its "selected" state.
	 *************************************************************************/
	protected void setSelected()
	{
		color.set(selectedColor);
	}

	/*************************************************************************
	 * Modifies the button into its "deselected" state.
	 *************************************************************************/
	protected void setDeselected()
	{
		color.set(deselectedColor);
	}

	/*************************************************************************
	 * Determines what happens when the button is clicked.
	 *************************************************************************/
	protected void onClick()
	{
		if(listener != null)
			listener.processCommand(command);
	}

	/*************************************************************************
	 * Updates the button, checking to see if the mouse is over it and 
	 * registering mouse clicks appropriately.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/@Override
	public void update(int delta)
	{
		handleSelection();
		if(selected && MouseHandler.wasButtonClicked(0))
			onClick();
	}
}