package game.objects.effects.base;

import org.jrabbit.base.graphics.skins.DynamicSkinned;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;

/*****************************************************************************
 * StaticMatrixEffect provides a general, non-abstract implementation of 
 * MatrixEffect that uses a static Skin for rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class StaticMatrixEffect extends MatrixEffect implements DynamicSkinned
{
	/**
	 * The Skin that represents the MatrixEffect.
	 **/
	protected Skin skin;

	/*************************************************************************
	 * Creates a StaticMatrixEffect that uses the indicated Image as a Skin.
	 * 
	 * @param imageReference
	 * 			  The reference of the Image to use.
	 *************************************************************************/
	public StaticMatrixEffect(String imageReference)
	{
		this(new ImageSkin(imageReference));
	}

	/*************************************************************************
	 * Creates a StaticMatrixEffect that uses the indicated Skin.
	 * 
	 * @param skin
	 * 			  The skin to use for rendering.
	 *************************************************************************/
	public StaticMatrixEffect(Skin skin)
	{
		setSkin(skin);
	}

	/*************************************************************************
	 * Accesses the active Skin.
	 * 
	 * @return The object that handles rendering and keeps track of the base
	 *         dimensions of the StaticMatrixEffect.
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
	 * Accesses the StaticMatrixEffect's base dimensions.
	 * 
	 * @return The unscaled width of the StaticMatrixEffect.
	 ***************************************************************/ @Override
	public float width() { return skin.width(); }

	/*************************************************************************
	 * Accesses the StaticMatrixEffect's base dimensions.
	 * 
	 * @return The unscaled height of the StaticMatrixEffect.
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