package game.visual.anim;

import game.world.Matrix;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;

/*****************************************************************************
 * A SingleCycleMatrixAnimation will kill itself as soon as it finishes a single
 * cycle of animation.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SingleCycleMatrixAnimation extends MatrixAnimation
{
	/*************************************************************************
	 * Creates a SingleCycleMatrixAnimation.
	 * 
	 * @param animation
	 * 			  The AnimatedSkin to use for rendering.
	 * @param matrix
	 * 			  The Matrix this will be placed in.
	 *************************************************************************/
	public SingleCycleMatrixAnimation(AnimatedSkin animation, Matrix matrix)
	{
		super(animation, matrix);
	}

	/*************************************************************************
	 * At the end of any cycle whatsoever, this MatrixAnimation is killed.
	 * 
	 * @param cycle
	 * 			  The cycle of animation that just ended.
	 ***************************************************************/ @Override
	public void onCycleEnd(int cycle)
	{
		kill();
	}
}