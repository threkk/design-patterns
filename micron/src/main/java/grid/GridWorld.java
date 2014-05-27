package grid;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.GLGroupTransform;
import org.jrabbit.base.graphics.transforms.GLReset;
import org.jrabbit.base.graphics.types.Colored;
import org.jrabbit.base.graphics.types.GLGroupTransformed;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.world.World;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A GridWorld is the basic World class used by Micron. It doesn't add any 
 * particularly complex functionality, but has a more complex background effect
 * (the Grid), and has controls to handle the world fading in and fading out.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GridWorld extends World
{
	/**
	 * The Grid controls the background of the GridWorld.
	 **/
	protected Grid grid;
	
	/**
	 * The Fade controls how the GridWorld fades in and out.
	 **/
	protected Fade fade;
	
	/*************************************************************************
	 * Creates a GridWorld with a random Grid and a fully opaque Fade that is 
	 * told to fade in. The background is also made somewhat transparent, so
	 * that a slight motion blur effect will be active.
	 *************************************************************************/
	public GridWorld()
	{
		add(grid = new Grid());
		fade = new Fade();
		fade.fadeIn();
		background.color().setAlpha(0.6f);
	}

	/*************************************************************************
	 * Accesses this GridWorld's Grid.
	 * 
	 * @return The Grid that renders the background effect.
	 *************************************************************************/
	public Grid grid() { return grid; }

	/*************************************************************************
	 * Updates the GridWorld and its fade effect.
	 * 
	 * @param delta
	 * 			  The amount of time since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		fade.update(delta);
		super.update(delta);
	}

	/*************************************************************************
	 * Renders the GridWorld and its fade.
	 ***************************************************************/ @Override
	public void render()
	{
		background.color().set(grid.backgroundColor());
		super.render();
		fade.render();
	}

	/*************************************************************************
	 * This method is called when the GridWorld is made active.
	 *************************************************************************/
	public void onActivation() { }

	/*************************************************************************
	 * This method is called when the GridWorld is made inactive.
	 *************************************************************************/
	public void onDeactivation() { }

	/*************************************************************************
	 * This GridWorld will remove itself from being active, and it will call the
	 * appropriate methods on the indicated GridWorld to make it active. 
	 *************************************************************************/
	public void switchTo(GridWorld world)
	{
		onDeactivation();
		world.makeCurrent();
		world.onActivation();
	}

	/*************************************************************************
	 * This method is called when the GridWorld's Fade has completely faded out.
	 * By overriding this method, a GridWorld can have customized functionality
	 * when this occurs.
	 *************************************************************************/
	protected void onCompleteFadeOut() { }

	/*************************************************************************
	 * This method is called when the GridWorld's Fade has completely faded in.
	 * By overriding this method, a GridWorld can have customized functionality
	 * when this occurs.
	 *************************************************************************/
	protected void onCompleteFadeIn() { }
	
	/*************************************************************************
	 * This method is provided to allow the GridWorld to have customized logic
	 * while the Fade is shifting from one state to another.
	 * 
	 * @param alpha
	 * 			  The transparency level of the Fade.
	 *************************************************************************/
	protected void fading(float alpha) { }

	/*************************************************************************
	 * A Fade controls how the GridWorld fades in and out.
	 *************************************************************************/
	protected class Fade implements Renderable, Colored, GLGroupTransformed, 
			Updateable
	{
		/**
		 * The state of fading - fading in, fading out, or neither.
		 **/
		protected int fadeState;

		/**
		 * How quickly the Fade fades in or out.
		 **/
		protected float fadeRate;
		
		/**
		 * The color being faded in and out (by default, black).
		 **/
		protected Color color;
		
		/**
		 * The GLTransforms being applied when fading is being rendered.
		 **/
		protected GLGroupTransform transforms;
		
		/*********************************************************************
		 * Creates a new black, fully opaque Fade.
		 *********************************************************************/
		public Fade()
		{
			transforms = new GLGroupTransform(color = new Color(0, 0, 0),
												new GLReset());
			fadeRate = 0.0001f;
		}

		/*********************************************************************
		 * Notifies the Fade that it needs to start becoming more and more 
		 * transparent.
		 *********************************************************************/
		public void fadeIn()
		{
			fadeState = -1;
		}

		/*********************************************************************
		 * Notifies the Fade that it needs to start becoming more and more 
		 * opaque.
		 *********************************************************************/
		public void fadeOut()
		{
			fadeState = 1;
		}

		/*********************************************************************
		 * The current fade Color.
		 ***********************************************************/ @Override
		public Color color() { return color; }

		/*********************************************************************
		 * Accesses the dynamic list of transforms being applied for rendering.
		 * 
		 * @return The GLGroupTransform that is used for rendering.
		 ***********************************************************/ @Override
		public GLGroupTransform transforms() { return transforms; }

		/*********************************************************************
		 * Applies all transforms.
		 ***********************************************************/ @Override
		public void bind() { transforms.bind(); }

		/*********************************************************************
		 * Releases all transforms.
		 ***********************************************************/ @Override
		public void release() { transforms.bind(); }

		/*********************************************************************
		 * Updates the fade effect.
		 * @param delta
		 * 			  The amount of time since the last update.
		 ***********************************************************/ @Override
		public void update(int delta)
		{
			switch(fadeState)
			{
				case 1: // Fade out.
					color.setAlpha(color.alpha() + (fadeRate * delta));
					fading(color.alpha());
					if(color.alpha() >= 1f)
					{
						fadeState = 0;
						onCompleteFadeOut();
					}
						break;
				case -1: // Fade in.
					color.setAlpha(color.alpha() - fadeRate * delta);
					fading(color.alpha());
					if(color.alpha() <= 0f)
					{
						fadeState = 0;
						onCompleteFadeIn();
					}
						break;
			}
		}

		/*********************************************************************
		 * Renders the fade effect.
		 ***********************************************************/ @Override
		public void render()
		{
			if(color.alpha() > 0)
			{
				bind();
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
					float screenWidth = WindowManager.controller().width();
					float screenHeight = WindowManager.controller().height();
					GL11.glVertex2f(0, 0);
					GL11.glVertex2f(screenWidth, 0);
					GL11.glVertex2f(0, screenHeight);
					GL11.glVertex2f(screenWidth, screenHeight);
				GL11.glEnd();
				release();
			}
		}
	}
}