package game.objects.entities.base;

import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.base.graphics.skins.animation.AnimationListener;

/*****************************************************************************
 * AnimatedParentEntity provides a general, non-abstract implementation of 
 * ParentEntity that uses an AnimatedSkin for rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnimatedParentEntity extends ParentEntity implements AnimationListener
{
	/**
	 * The animation to represent the AnimatedParentEntity.
	 **/
	protected AnimatedSkin animation;

	/*************************************************************************
	 * Creates an AnimatedParentEntity that uses the indicated AnimatedSkin.
	 * 
	 * @param animation
	 * 			  The animation to use for rendering.
	 *************************************************************************/
	public AnimatedParentEntity(AnimatedSkin animation)
	{
		setAnimation(animation);
	}

	/*************************************************************************
	 * Accesses the active animation.
	 * 
	 * @return The AnimatedSkin that handles rendering and keeps track of the 
	 *         base dimensions of the AnimatedParentEntity.
	 *************************************************************************/
	public AnimatedSkin skin() { return animation; }

	/*************************************************************************
	 * Redefines the active animation.
	 * 
	 * @param animation
	 * 			  The AnimatedSkin to use for rendering.
	 *************************************************************************/
	public void setAnimation(AnimatedSkin animation)
	{
		if(this.animation != null)
			this.animation.removeListener(this);
		this.animation = animation;
		animation.addListener(this);
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
	 * Accesses the AnimatedParentEntity's base dimensions.
	 * 
	 * @return The unscaled width of the AnimatedParentEntity.
	 ***************************************************************/ @Override
	public float width() { return animation.width(); }

	/*************************************************************************
	 * Accesses the AnimatedParentEntity's base dimensions.
	 * 
	 * @return The unscaled height of the AnimatedParentEntity.
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
	 * Updates the animation along with the AnimatedParentEntity.
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