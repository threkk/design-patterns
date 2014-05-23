package grid;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.base.graphics.transforms.BlendOp;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.types.Colored;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.managers.GameManager;

/*****************************************************************************
 * A Grid is the characteristic background effect of Micron worlds, showing
 * as scintillating, stylized background that changes color over time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Grid implements Updateable, Renderable, Colored
{
	/**
	 * The ImageSkins used to render the background effect.
	 **/
	private static final ImageSkin[] images = { new ImageSkin("Grid 1"),
			new ImageSkin("Grid 2"), new ImageSkin("Grid 3"),
			new ImageSkin("Grid 4"), new ImageSkin("Grid 5"),
			new ImageSkin("Grid 6"), new ImageSkin("Grid 7"),
			new ImageSkin("Grid 8") };

	/*************************************************************************
	 * Obtains a random ImageSkin.
	 * 
	 * @return One of the ImageSkins used to render the Grid.
	 *************************************************************************/
	public static ImageSkin randomImage()
	{
		return images[Resources.random().nextInt(images.length)];
	}

	/**
	 * The object that manages the Grid's shifting hue.
	 **/
	protected GridColorManager colors;
	
	/**
	 * The list of objects that render pieces of the Grid.
	 **/
	protected LockingList<GridUnit> units;

	/*************************************************************************
	 * Creates a Grid.
	 * 
	 * NOTE: The number of GridUnits that are used is based on screen size; that
	 * way, the overall effect is roughly the same, even if run by different 
	 * computers on different resolutions.
	 *************************************************************************/
	public Grid()
	{
		colors = new GridColorManager();
		units = new LockingList<GridUnit>();
		int num = (int) ((WindowManager.controller().width() * WindowManager
				.controller().height()) / 2000);
		for (int i = 0; i < num; i++)
			units.add(new GridUnit());
	}

	/*************************************************************************
	 * Accesses the current color of the Grid.
	 * 
	 * @return The base color used to render the GridUnits composing the effect.
	 ***************************************************************/ @Override
	public Color color()
	{
		return colors.color();
	}
	
	/*************************************************************************
	 * Accesses the color the Grid assigns to the background.
	 * 
	 * @return A much darker version of the main color.
	 *************************************************************************/
	public Color backgroundColor()
	{
		return colors.backgroundColor();
	}

	/*************************************************************************
	 * Updates the Grid.
	 * 
	 * @param delta
	 * 			  The number of clock ticks since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		colors.update(delta);
		for (GridUnit unit : units)
			unit.update(delta);
		units.unlock();
	}

	/*************************************************************************
	 * Renders the grid.
	 ***************************************************************/ @Override
	public void render()
	{
		float x = GameManager.camera().location().x();
		float y = GameManager.camera().location().y();
		float r = colors.color().red();
		float g = colors.color().green();
		float b = colors.color().blue();
		BlendOp.ADDITIVE.bind();
		for (GridUnit unit : units)
			unit.render(x, y, r, g, b);
		units.unlock();
		BlendOp.ADDITIVE.release();
	}
}