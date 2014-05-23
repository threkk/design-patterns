package grid;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.types.Colored;
import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * A GridColorManager manages the shifting hue of a Grid. It has a limited 
 * palette of main colors that it continuously blends between.
 * 
 * NOTE: A GridColorManager maintains two colors: it's primary one, which is at
 * full brightness and saturation, and a (much) darker one, which has the same
 * hue but a much darker value. This secondary hue is used for the background
 * of the world containing the Grid. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GridColorManager implements Updateable, Colored
{
	/**
	 * The list of Colors that the GridColorManager can choose from.
	 **/
	private static final Color[] palette = { Color.RED, 
											new Color(1f, 0.6f, 0),
											new Color(0.5f, 0, 1f),
											Color.BLUE,
											Color.GREEN,
											Color.MAGENTA,
											Color.WHITE,
											Color.CYAN};
	
	/**
	 * The index of the Color being blended "from."
	 **/
	protected int current;
	
	/**
	 * The index of the Color being blended "towards."
	 **/
	protected int next;
	
	/**
	 * The amount of time the GridColorManager has spent on its current blending
	 * phase.
	 **/
	protected int timer;
	
	/**
	 * The total duration of each blending phase.
	 **/
	protected int interval;
	
	/**
	 * The main color.
	 **/
	protected Color color;
	
	/**
	 * The secondary color.
	 **/
	protected Color backgroundColor;
	
	/*************************************************************************
	 * Creates a default GridColorManager. The initial colors are random.
	 *************************************************************************/
	public GridColorManager()
	{
		current = Resources.random().nextInt(palette.length);
		next = Resources.random().nextInt(palette.length);
		interval = 60000;
		color = palette[current].copy();
		backgroundColor = color.copy();
		backgroundColor.darken(0.2f);
	}
	
	/*************************************************************************
	 * Accesses the primary Color.
	 * 
	 * @return The main Color that determines the hue of the Grid.
	 ***************************************************************/ @Override
	public Color color() { return color; }

	/*************************************************************************
	 * Accesses the secondary color.
	 * 
	 * @return The darker version of the primary color used as a background.
	 *************************************************************************/
	public Color backgroundColor() { return backgroundColor; }
	
	/*************************************************************************
	 * Updates the GridColorManager, allowing its hue to shift over time.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timer += delta;
		if(timer >= interval)
		{
			current = next;
			next = Resources.random().nextInt(palette.length);
			timer %= interval;
		}
		color.set(Color.blend(palette[current], palette[next], (float) timer / 
				interval));
		backgroundColor.set(color);
		backgroundColor.darken(0.12f);
	}
}