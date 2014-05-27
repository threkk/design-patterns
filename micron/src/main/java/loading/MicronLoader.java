package loading;

import loading.screen.Ring;

import org.jrabbit.base.core.loop.Loop;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.objects.Sprite;
import org.jrabbit.standard.game.world.World;

/*****************************************************************************
 * MicronLoader extends Loop to provide a means to load all required resources
 * at startup, as quickly and unobtrusively as possible.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronLoader extends Loop
{
	/**
	 * The World that renders the display during loading.
	 **/
	protected World world;
	
	/**
	 * The Thread that attempts to load all required resources.
	 **/
	protected MicronLoadingThread thread;
	
	/*************************************************************************
	 * Creates a MicronLoader that attempts to load resources in a separate
	 * Thread while an animated loading screen is displayed.
	 *************************************************************************/
	public MicronLoader()
	{
		Resources.images().setImageSmooth(false);
		world = new World();
		world.add(new Ring(1), new Ring(2), new Ring(3), new Ring(4), new Ring(5));
		Sprite loading = new Sprite("res/images/loading/Loading.png");
		loading.scalar().setScale(2);
		world.add(loading);
		(thread = new MicronLoadingThread()).start();
	}

	/*************************************************************************
	 * Begins the MicronLoader's loop.
	 ***************************************************************/ @Override
	public void start()
	{
		MouseHandler.hideMouse(true);
	}

	/*************************************************************************
	 * Ends the MicronLoader's loop.
	 ***************************************************************/ @Override
	public void end()
	{
		MicronCursors.useMenuCursor();
		MouseHandler.hideMouse(false);
	}

	/*************************************************************************
	 * Updates the loop that manages resource loading.
	 * 
	 * @param delta
	 * 			  The number of clock ticks to update by.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		MouseHandler.update();
		KeyboardHandler.update();
		if(thread.contextSharingFailed())
		{
			render();
			thread.act();
			exit();
		}
		else if(thread.complete())
			exit();
		else
			world.update(delta);
	}
	
	/*************************************************************************
	 * Renders the display shown while loading occurs.
	 ***************************************************************/ @Override
	public void render()
	{
		WindowManager.controller().beginRender();
		world.render();
		WindowManager.controller().endRender();
	}
}