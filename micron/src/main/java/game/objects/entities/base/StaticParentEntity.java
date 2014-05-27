package game.objects.entities.base;

import org.jrabbit.base.graphics.skins.DynamicSkinned;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;

/*****************************************************************************
 * StaticParentEntity provides a general, non-abstract implementation of 
 * ParentEntity that uses a static Skin for rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class StaticParentEntity extends ParentEntity implements DynamicSkinned
{
	/**
	 * The Skin that represents the Entity.
	 **/
	protected Skin skin;

	/*************************************************************************
	 * Creates a StaticParentEntity that uses the indicated Image as a Skin.
	 * 
	 * @param imageReference
	 * 			  The reference of the Image to use.
	 *************************************************************************/
	public StaticParentEntity(String imageReference)
	{
		this(new ImageSkin(imageReference));
	}

	/*************************************************************************
	 * Creates a StaticParentEntity that uses the indicated Skin.
	 * 
	 * @param skin
	 * 			  The skin to use for rendering.
	 *************************************************************************/
	public StaticParentEntity(Skin skin)
	{
		setSkin(skin);
	}

	/*************************************************************************
	 * Accesses the active Skin.
	 * 
	 * @return The object that handles rendering and keeps tracks of the base
	 *         dimensions of the StaticParentEntity.
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
	 * Accesses the StaticParentEntity's base dimensions.
	 * 
	 * @return The unscaled width of the StaticParentEntity.
	 ***************************************************************/ @Override
	public float width() { return skin.width(); }

	/*************************************************************************
	 * Accesses the StaticParentEntity's base dimensions.
	 * 
	 * @return The unscaled height of the StaticParentEntity.
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