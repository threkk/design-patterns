package game.objects.effects.base;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.base.graphics.skins.animation.AnimationListener;

/*****************************************************************************
 * StaticMatrixEffect provides a general, non-abstract implementation of 
 * MatrixEffect that uses a static AnimatedSkin for rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnimatedMatrixEffect extends MatrixEffect implements AnimationListener
{
	/**
	 * The AnimatedSkin that represents the Effect.
	 **/
	protected AnimatedSkin animation;

	/*************************************************************************
	 * Creates a AnimatedMatrixEffect that uses the indicated AnimatedSkin.
	 * 
	 * @param animation
	 * 			  The animation to use for rendering.
	 *************************************************************************/
	public AnimatedMatrixEffect(AnimatedSkin animation)
	{
		setAnimatedSkin(animation);
	}

	/*************************************************************************
	 * Handles any calls that should occur at the start of each frame of the
	 * animation.
	 * 
	 * @param cycle
	 * 			  The index of the cycle that is running.
	 * @param frame
	 * 			  The index of the frame that just began.
	 ***************************************************************/ @Override
	public void onFrame(int cycle, int frame) { }

	/*************************************************************************
	 * Handles any calls that should occur at the end of an animation cycle.
	 * 
	 * @param cycle
	 * 			  The index of the cycle that just ended.
	 ***************************************************************/ @Override
	public void onCycleEnd(int cycle) { }

	/*************************************************************************
	 * Accesses the active AnimatedSkin.
	 * 
	 * @return The object that handles rendering and keeps track of the base
	 *         dimensions of the AnimatedMatrixEffect.
	 *************************************************************************/
	public AnimatedSkin animation() { return animation; }

	/*************************************************************************
	 * Redefines the active AnimatedSkin.
	 * 
	 * @param animation
	 * 			  The animation to use for rendering.
	 *************************************************************************/
	public void setAnimatedSkin(AnimatedSkin animation)
	{
		if(this.animation != null)
			this.animation.removeListener(this);
		this.animation = animation;
		animation.addListener(this);
	}

	/*************************************************************************
	 * Accesses the AnimatedMatrixEffect's base dimensions.
	 * 
	 * @return The unscaled width of the AnimatedMatrixEffect.
	 ***************************************************************/ @Override
	public float width() { return animation.width(); }

	/*************************************************************************
	 * Accesses the AnimatedMatrixEffect's base dimensions.
	 * 
	 * @return The unscaled height of the AnimatedMatrixEffect.
	 ***************************************************************/ @Override
	public float height() { return animation.height(); }

	/*************************************************************************
	 * Renders the animation.
	 ***************************************************************/ @Override
	public void draw()
	{
		animation.render();
	}
	 
	/*************************************************************************
	 * Updates the animation along with the MatrixEffect.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		animation.update(delta);
		super.update(delta);
	}
}