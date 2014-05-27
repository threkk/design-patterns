package game.objects.controllers;

/*****************************************************************************
 * A TargetedController is intended to perform actions upon a particular type of
 * object. It has a reference to one of these, which is considered its target.
 * 
 * @author Chris Molini
 *
 * @param <T>
 * 			  The type of object to affect.
 *****************************************************************************/
public abstract class TargetedController<T> extends Controller
{
	/**
	 * The object that operations should be performed upon.
	 **/
	protected T target;
	
	/*************************************************************************
	 * Creates a TargetedController with the indicated target.
	 * 
	 * @param target
	 * 			  The object to act upon.
	 *************************************************************************/
	public TargetedController(T target)
	{
		this.target = target;
	}
}