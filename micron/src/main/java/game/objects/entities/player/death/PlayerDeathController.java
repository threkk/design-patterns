package game.objects.entities.player.death;

import game.objects.death.DeathEffect;
import game.objects.sprite.MatrixSprite;
import game.world.Matrix;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.standard.game.managers.GameManager;

/*****************************************************************************
 * A PlayerDeathController is placed into the Matrix when the Player dies. It
 * continues moving the Camera in the direction of its velocity, so that the
 * Player's death doesn't cause a visual "jerk." 
 * 
 * Additionally, a PlayerDeathController causes a Matrix to automatically fade
 * out and finish gameplay.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class PlayerDeathController implements DeathEffect, Updateable
{
	/**
	 * The rate at which the camera movement is slowed down.
	 **/
	protected static final float CAMERA_DRAG = 0.00001f;

	/**
	 * The velocity vector.
	 **/
	protected Vector2f velocity;

	/*************************************************************************
	 * Moves the camera based on the remaining, diminishing velocity.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		GameManager.camera().location().add(velocity, delta);
		velocity.decrease(delta * CAMERA_DRAG);
	}

	/*************************************************************************
	 * Adds this PlayerDeathController to the gameworld, and instructs the 
	 * Matrix to exit.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is dying (in this case, the player).
	 ***************************************************************/ @Override
	public void onDeath(MatrixSprite source)
	{
		Matrix matrix = source.matrix();
		if(matrix != null)
		{
			velocity = source.velocity().copy();
			matrix.add(this);
			matrix.setPlayer(null);
			matrix.exit();
		}
	}
}