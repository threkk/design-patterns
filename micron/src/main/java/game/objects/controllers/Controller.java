package game.objects.controllers;

import game.objects.Killable;

import org.jrabbit.base.core.types.Updateable;

/*****************************************************************************
 * A Controller is an object that is updated over time. It is intended to 
 * provide a means to delegate functionality.
 * 
 * For example, one Controller could control an Entity's movement, while another
 * could control its attacks. Both would operate independently of each other.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class Controller implements Updateable, Killable
{
	/**
	 * The object managing the Controller in a dynamic list.
	 **/
	private ControllerParent parent;

	/*************************************************************************
	 * Accesses the Controller's parent.
	 * 
	 * @return The object that manages this Controller.
	 *************************************************************************/
	public ControllerParent parent() { return parent; }

	/*************************************************************************
	 * Redefines the Controller's parent reference.
	 * 
	 * @param parent
	 * 			  The new parent.
	 *************************************************************************/
	public void setParent(ControllerParent parent)
	{
		this.parent = parent;
	}

	/*************************************************************************
	 * Causes the Controller to remove itself from its parent.
	 ***************************************************************/ @Override
	public void kill()
	{
		if(parent != null)
			parent.removeController(this);
		parent = null;
	}
}