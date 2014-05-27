package game.objects.entities.base;

import org.jrabbit.base.graphics.skins.DynamicSkinned;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;

/*****************************************************************************
 * StaticMatrixEntity provides a general, non-abstract implementation of Entity that
 * uses a static Skin for rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class StaticMatrixEntity extends MatrixEntity implements DynamicSkinned
{
	/**
	 * The Skin that represents the MatrixEntity.
	 **/
	protected Skin skin;

	/*************************************************************************
	 * Creates a StaticMatrixEntity that uses the indicated Image as a Skin.
	 * 
	 * @param imageReference
	 * 			  The reference of the Image to use.
	 *************************************************************************/
	public StaticMatrixEntity(String imageReference)
	{
		this(new ImageSkin(imageReference));
	}

	/*************************************************************************
	 * Creates a StaticMatrixEntity that uses the indicated Skin.
	 * 
	 * @param skin
	 * 			  The skin to use for rendering.
	 *************************************************************************/
	public StaticMatrixEntity(Skin skin)
	{
		setSkin(skin);
	}

	/*************************************************************************
	 * Accesses the active Skin.
	 * 
	 * @return The object that handles rendering and keeps track of the base
	 *         dimensions of the StaticMatrixEntity.
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
	 * Accesses the StaticMatrixEntity's base dimensions.
	 * 
	 * @return The unscaled width of the StaticMatrixEntity.
	 ***************************************************************/ @Override
	public float width() { return skin.width(); }

	/*************************************************************************
	 * Accesses the StaticMatrixEntity's base dimensions.
	 * 
	 * @return The unscaled height of the StaticMatrixEntity.
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