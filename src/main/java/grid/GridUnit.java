package grid;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.base.managers.window.WindowManager;

import static org.lwjgl.opengl.GL11.*;

/*****************************************************************************
 * A GridUnit manages and renders one of the pieces of the Grid.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GridUnit implements Updateable
{
	/**
	 * This is the base scale for each GridUnit.
	 **/
	private static final float BASE_SCALE = 40f;
	
	/**
	 * Half of the diagonal of the screen, using in checks.
	 **/
	private static final float SCREEN_DELTA = (float) Math.sqrt((
			WindowManager.controller().width() * WindowManager.controller().
			width()) + (WindowManager.controller().height() * WindowManager.
			controller().height())) / 2;
	
	/**
	 * The current ImageSkin being used to render the GridUnit.
	 **/
	protected ImageSkin image;
	
	/**
	 * The GridUnit's x coordinate.
	 **/
	protected float x;
	
	/**
	 * The GridUnit's y coordinate.
	 **/
	protected float y;
	
	/**
	 * The horizontal scaling of the GridUnit.
	 **/
	protected float scaleX;
	
	/**
	 * The vertical scaling of the GridUnit.
	 **/
	protected float scaleY;
	
	/**
	 * The current transparency of the GridUnit.
	 **/
	protected float alpha;
	
	/**
	 * The "z-distance from the camera" the GridUnit will mimic.
	 **/
	protected float distance;
	
	/**
	 * The diagonal distance that the GridUnit needs to respect when checking
	 * its position.
	 **/
	protected float delta;
	
	/**
	 * The amount of time that has elapsed on the current transparency phase.
	 **/
	protected int timer;
	
	/**
	 * The duration of the current transparency phase.
	 **/
	protected int interval;
	
	/**
	 * The base duration of each transparency phase.
	 **/
	protected int intervalBase;
	
	/*************************************************************************
	 * Creates a random GridUnit.
	 *************************************************************************/
	public GridUnit()
	{
		distance = (Resources.random().nextFloat() * 8) + 2f;
		randomizeImage();
		randomizeAlpha();
		intervalBase = 8000;
		interval = (int) (intervalBase * (Resources.random().nextFloat() + 1));
		x = (Resources.random().nextFloat() - 0.5f) * 2 * delta;
		y = (Resources.random().nextFloat() - 0.5f) * 2 * delta;
	}
	
	/*************************************************************************
	 * Randomly chooses the image and flipping of this GridUnit.
	 *************************************************************************/
	public void randomizeImage()
	{
		image = Grid.randomImage();
		scaleX = (Resources.random().nextBoolean() ? BASE_SCALE : -BASE_SCALE) /
				distance;
		scaleY = Resources.random().nextBoolean() ? scaleX : -scaleX;
		delta = (float) (Math.hypot(scaleX * image.width(), scaleY * 
				image.height()) + SCREEN_DELTA) * distance;
	}
	
	/*************************************************************************
	 * Randomly sets a new transparency value.
	 *************************************************************************/
	public void randomizeAlpha()
	{
		alpha = (0.2f + Resources.random().nextFloat()) * ((10.5f - distance) * 
				(10.5f - distance)) / 500f;
	}
	
	/*************************************************************************
	 * Varies the GridUnit's transparency over time.
	 * 
	 * @param delta
	 * 			  The amount of time passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timer += delta;
		if(timer >= interval)
		{
			timer %= interval;
			randomizeAlpha();
		}
	}
	
	/*************************************************************************
	 * Ensures that the GridUnit follows the camera unobtrusively.
	 * 
	 * @param cX
	 * 			  The x coordinate of the camera.
	 * @param cY
	 * 			  The y coordinate of the camera.
	 *************************************************************************/
	public void check(float cX, float cY)
	{
		float screenX = (x - cX);
		float screenY = (y - cY);
		if(screenX > delta)
		{
			x -= delta * 2;
			y = ((Resources.random().nextFloat() - 0.5f) * 2 * delta) + cY;
			randomizeImage();
		}
		else if(screenX < -delta)
		{
			x += delta * 2;
			y = ((Resources.random().nextFloat() - 0.5f) * 2 * delta) + cY;
			randomizeImage();
		}
		if(screenY > delta)
		{
			y -= delta * 2;
			x = ((Resources.random().nextFloat() - 0.5f) * 2 * delta) + cX;
			randomizeImage();
		}
		else if(screenY < -delta)
		{
			y += delta * 2;
			x = ((Resources.random().nextFloat() - 0.5f) * 2 * delta) + cX;
			randomizeImage();
		}
	}
	
	/*************************************************************************
	 * Renders the GridUnit, applying the color and having a "depth" effect.
	 * 
	 * @param cX
	 * 			  The x coordinate of the camera.
	 * @param cY
	 * 			  The y coordinate of the camera.
	 * @param r
	 * 			  The red value to render at.
	 * @param g
	 * 			  The green value to render at.
	 * @param b
	 * 			  The blue value to render at.
	 *************************************************************************/
	public void render(float cX, float cY, float r, float g, float b)
	{
		check(cX, cY);
		float viewX = (cX + (x - cX) / distance);
		float viewY = (cY + (y - cY) / distance);
		glColor4f(r, g, b, alpha);
		glTranslatef(viewX, viewY, 0);
		glScalef(scaleX, scaleY, 1);
		image.render();
		glScalef(1f / scaleX, 1f / scaleY, 1);
		glTranslatef(-viewX, -viewY, 0);
	}
}