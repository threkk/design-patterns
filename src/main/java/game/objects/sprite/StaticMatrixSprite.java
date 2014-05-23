package game.objects.sprite;

import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;

/*****************************************************************************
 * StaticMatrixSprite provides a general, non-abstract implementation of 
 * MatrixSprite that uses a static Skin for rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class StaticMatrixSprite extends MatrixSprite
{
	/**
	 * The Skin that represents the MatrixSprite.
	 **/
	protected Skin skin;

	/*************************************************************************
	 * Creates a StaticMatrixSprite that uses the indicated Image as a Skin.
	 * 
	 * @param imageReference
	 * 			  The reference of the Image to use.
	 *************************************************************************/
	public StaticMatrixSprite(String imageReference)
	{
		this(new ImageSkin(imageReference));
	}

	/*************************************************************************
	 * Creates a StaticMatrixSprite that uses the indicated Skin.
	 * 
	 * @param skin
	 * 			  The skin to use for rendering.
	 *************************************************************************/
	public StaticMatrixSprite(Skin skin)
	{
		setSkin(skin);
	}

	/*************************************************************************
	 * Accesses the active Skin.
	 * 
	 * @return The object that handles rendering and keeps track of the base
	 *         dimensions of the StaticMatrixSprite.
	 *************************************************************************/
	public Skin skin() { return skin; }

	/*************************************************************************
	 * Redefines the active Skin.
	 * 
	 * @param skin
	 * 			  The skin to use for rendering.
	 *************************************************************************/
	public void setSkin(Skin skin)
	{
		this.skin = skin;
	}

	/*************************************************************************
	 * Accesses the StaticMatrixSprite's base dimensions.
	 * 
	 * @return The unscaled width of the StaticMatrixSprite.
	 ***************************************************************/ @Override
	public float width() { return skin.width(); }

	/*************************************************************************
	 * Accesses the StaticMatrixSprite's base dimensions.
	 * 
	 * @return The unscaled height of the StaticMatrixSprite.
	 ***************************************************************/ @Override
	public float height() { return skin.height(); }

	/*************************************************************************
	 * Renders the skin.
	 ***************************************************************/ @Override
	public void draw()
	{
		skin.render();
	}
}