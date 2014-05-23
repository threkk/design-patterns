package game.objects.debris;

import game.objects.PhysSprite;
import game.objects.debris.base.Debris;

import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.base.managers.window.WindowManager;

/*****************************************************************************
 * DebrisSprite provides a generic implementation of Debris with a PhysSprite as
 * the base object.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DebrisSprite extends PhysSprite implements Debris
{
	/**
	 * The Skin used to render the DebrisSprite.
	 **/
	protected Skin skin;
	
	/**
	 * The distance away from the Camera at which the DebrisSprite will be 
	 * culled from the Matrix.
	 **/
	protected float cullDistance;
	
	/*************************************************************************
	 * Creates a DebrisSprite that uses the indicated Image to render itself.
	 * 
	 * @param reference
	 * 			  The reference of the Image to use.
	 *************************************************************************/
	public DebrisSprite(String reference)
	{
		this(new ImageSkin(reference));
	}

	/*************************************************************************
	 * Creates a DebrisSprite that uses the indicated Skin to render itself.
	 * 
	 * @param skin
	 * 			  The Skin to use.
	 *************************************************************************/
	public DebrisSprite(Skin skin)
	{
		this.skin = skin;
		cullDistance = (float) Math.hypot(WindowManager.screenWidth(), 
				WindowManager.screenHeight());
	}
	
	/*************************************************************************
	 * Applies the indicated velocity and rotation to the DebrisSprite, ignoring
	 * mass.
	 * 
	 * @param xV
	 * 			  The x-velocity to have.
	 * @param yV
	 * 			  The y-velocity to have.
	 * @param rotSpeed
	 * 			  The rotation speed to have.
	 *************************************************************************/
	public void applyDrift(float xV, float yV, float rotSpeed)
	{
		velocity.set(xV, yV);
		rotationSpeed = rotSpeed;
	}

	/*************************************************************************
	 * Learns the culling radius of the DebrisSprite.
	 * 
	 * @return The distance the DebrisSprite can be away from the camera before
	 * 		   being culled from the Matrix.
	 ***************************************************************/ @Override
	public float cullDistance() { return cullDistance; }

	/*************************************************************************
	 * Accesses the dimensions of the DebrisSprite.
	 * 
	 * @return The unscaled width of the Skin representing the Debris.
	 ***************************************************************/ @Override
	public float width() { return skin.width(); }

	/*************************************************************************
	 * Accesses the dimensions of the DebrisSprite.
	 * 
	 * @return The unscaled height of the Skin representing the Debris.
	 ***************************************************************/ @Override
	public float height() { return skin.height(); }

	/*************************************************************************
	 * Renders the DebrisSprite's Skin.
	 ***************************************************************/ @Override
	public void draw()
	{
		skin.render();
	}
}