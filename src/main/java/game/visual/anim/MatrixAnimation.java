package game.visual.anim;

import game.objects.Killable;
import game.world.Matrix;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.standard.game.objects.specialized.AnimatedSprite;

/*****************************************************************************
 * A MatrixAnimation is an AnimatedSprite that can remove itself from the
 * Matrix it is in at its own discretion. This allows the animation to be used
 * in a "fire and forget" manner.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MatrixAnimation extends AnimatedSprite implements Killable
{
	/**
	 * The Matrix that the MatrixAnimation will be placed in.
	 **/
	protected Matrix matrix;

	/*************************************************************************
	 * Creates a MatrixAnimation.
	 * 
	 * @param animation
	 * 			  The AnimatedSkin to use for rendering.
	 * @param matrix
	 * 			  The Matrix this will be placed in.
	 *************************************************************************/
	public MatrixAnimation(AnimatedSkin animation, Matrix matrix)
	{
		super(animation);
		this.matrix = matrix;
	}

	/*************************************************************************
	 * Attempts to remove this MatrixAnimation from its Matrix.
	 ***************************************************************/ @Override
	public void kill()
	{
		if(matrix != null)
			matrix.remove(this);
		matrix = null;
	}
}