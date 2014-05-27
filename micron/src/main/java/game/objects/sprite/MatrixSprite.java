package game.objects.sprite;

import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.managers.window.WindowManager;

import game.objects.FactionedSprite;
import game.objects.Killable;
import game.objects.controllers.Controller;
import game.objects.controllers.ControllerParent;
import game.objects.death.DeathEffect;
import game.world.Matrix;

/*****************************************************************************
 * A MatrixSprite is a Sprite with some specialized functionality for existing
 * in a Matrix.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class MatrixSprite extends FactionedSprite implements Killable,
		ControllerParent
{
	/**
	 * The MatrixSprite's culling radius.
	 **/
	protected float cullDistance = (float) Math.hypot(
									WindowManager.controller().width(),
									WindowManager.controller().height()) * 2f;

	/**
	 * The Matrix this MatrixSprite currently inhabits.
	 **/
	protected Matrix matrix;

	/**
	 * The reference of the Layer this MatrixSprite wants to be placed on.
	 **/
	protected String layer;

	/**
	 * The list of Controllers maintained by this MatrixSprite.
	 **/
	protected LockingList<Controller> controllers;

	/**
	 * The list of DeathEffects maintained by this MatrixSprite.
	 **/
	protected LockingList<DeathEffect> deathEffects;
	
	/*************************************************************************
	 * Creates a MatrixSprite.
	 *************************************************************************/
	public MatrixSprite()
	{
		controllers = new LockingList<Controller>();
		deathEffects = new LockingList<DeathEffect>();
	}

	/*************************************************************************
	 * Adds the indicated Controller to this MatrixSprite.
	 * 
	 * @param controller
	 * 			  The Controller to add.
	 *************************************************************************/
	public void addController(Controller controller)
	{
		controller.setParent(this);
		controllers.add(controller);
	}

	/*************************************************************************
	 * Adds the indicated series of Controllers to this MatrixSprite.
	 * 
	 * @param controllers
	 * 			  The Controllers to add.
	 *************************************************************************/
	public void addControllers(Controller... controllers)
	{
		for(Controller controller : controllers)
			addController(controller);
	}

	/*************************************************************************
	 * Removes the indicated Controller from this MatrixSprite.
	 * 
	 * @param controller
	 * 			  The Controller to remove.
	 *************************************************************************/
	public void removeController(Controller controller)
	{
		if(controllers.remove(controller))
			controller.setParent(null);
	}

	/*************************************************************************
	 * Adds the indicated DeathEffect to this MatrixSprite.
	 * 
	 * @param deathEffect
	 * 			  The DeathEffect to add.
	 *************************************************************************/
	public void addDeathEffect(DeathEffect deathEffect)
	{
		deathEffects.add(deathEffect);
	}

	/*************************************************************************
	 * Adds the indicated series of DeathEffects to this MatrixSprite.
	 * 
	 * @param deathEffects
	 * 			  The DeathEffects to add.
	 *************************************************************************/
	public void addDeathEffects(DeathEffect... deathEffects)
	{
		for(DeathEffect deathEffect : deathEffects)
			addDeathEffect(deathEffect);
	}

	/*************************************************************************
	 * Removes the indicated DeathEffect from this MatrixSprite.
	 * 
	 * @param deathEffect
	 * 			  The DeathEffect to remove.
	 *************************************************************************/
	public void removeDeathEffect(DeathEffect deathEffect)
	{
		deathEffects.remove(deathEffect);
	}

	/*************************************************************************
	 * Accesses the MatrixSprite's current Matrix.
	 * 
	 * @return The Matrix the MatrixSprite is inhabiting.
	 *************************************************************************/
	public Matrix matrix() { return matrix; }

	/*************************************************************************
	 * Redefines the MatrixSprite's Matrix reference.
	 * 
	 * @param matrix
	 * 			  The new parent Matrix of this MatrixSprite.
	 *************************************************************************/
	public void setMatrix(Matrix matrix)
	{
		this.matrix = matrix;
	}
	
	/*************************************************************************
	 * Tells a MatrixSprite that it is being added to a Matrix.
	 * 
	 * @param matrix
	 * 			  The Matrix this MatrixSprite is being added to.
	 *************************************************************************/
	public void addedToMatrix(Matrix matrix)
	{
		if(this.matrix != null)
			this.matrix.remove(this);
		setMatrix(matrix);
	}
	
	/*************************************************************************
	 * Tells a MatrixSprite that it is being removed from a Matrix.
	 * 
	 * @param matrix
	 * 			  The Matrix this MatrixSprite is being removed from.
	 *************************************************************************/
	public void removedFromMatrix(Matrix matrix)
	{
		this.matrix = null;
	}

	/*************************************************************************
	 * Learns which Layer this MatrixSprite desires to be added to.
	 * 
	 * @return The identifier of the target layer.
	 *************************************************************************/
	public String layer() { return layer; }

	/*************************************************************************
	 * Changes this MatrixSprite's target layer.
	 * 
	 * @param layer
	 * 			  The identifier of the new target Layer.
	 *************************************************************************/
	public void setLayer(String layer)
	{
		this.layer = layer;
	}

	/*************************************************************************
	 * Learns the culling radius of the MatrixSprite.
	 * 
	 * @return The distance the MatrixSprite can be away from the camera before
	 * 		   being removed from the Matrix.
	 ***************************************************************/ @Override
	public float cullDistance() { return cullDistance; }

	/*************************************************************************
	 * Destroys this MatrixSprite and removes it from the Matrix.
	 ***************************************************************/ @Override
	public void kill()
	{
		onDeath();
		if(matrix != null)
			matrix.remove(this);
	}

	/*************************************************************************
	 * Handles what should happen when the MatrixSprite is killed.
	 *************************************************************************/
	protected void onDeath()
	{
		for(DeathEffect deathEffect : deathEffects)
			deathEffect.onDeath(this);
		deathEffects.unlock();
	}
	
	/*************************************************************************
	 * Updates the MatrixSprite's controllers.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 *************************************************************************/
	protected void updateControllers(int delta)
	{
		for(Controller controller : controllers)
			controller.update(delta);
		controllers.unlock();
	}

	/*************************************************************************
	 * Updates MatrixSprite and its controllers.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		super.update(delta);
		updateControllers(delta);
	}
}