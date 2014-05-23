package game.objects.entities.player.gui;

import game.objects.entities.player.Player;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.objects.Sprite;
import org.jrabbit.standard.game.objects.specialized.TextSprite;

/*****************************************************************************
 * 
 * @author Chris Molini
 *****************************************************************************/
public class PlayerGUI implements Renderable, Updateable
{
	/**
	 * The player whose general information is being rendered.
	 **/
	protected Player owner;

	/**
	 * Renders the back of the health display.
	 **/
	protected Sprite healthBarBack;
	
	/**
	 * Renders the health bar itself.
	 **/
	protected Sprite healthBar;
	
	/**
	 * Renders the left end of the health bar.
	 **/
	protected Sprite healthBarLCap;
	
	/**
	 * Renders the right end of the health bar.
	 **/
	protected Sprite healthBarRCap;
	
	/**
	 * Renders the amount of points that have been scored.
	 **/
	protected TextSprite scoreDisplay;

	/*************************************************************************
	 * Creates a PlayerGUI that will focus on the indicated Player.
	 * 
	 * @param owner
	 * 			  The Player whose health, score, and weapons to display 
	 * 			  information about.
	 *************************************************************************/
	public PlayerGUI(Player owner)
	{
		this.owner = owner;
		scoreDisplay = new TextSprite("0", "Score");
		scoreDisplay.color().setAlpha(0.5f);
		scoreDisplay.screenCoords().setEnabled(true);
		healthBarBack = new Sprite("Health Back");
		healthBarBack.screenCoords().setEnabled(true);
		healthBar = new Sprite("Health Bar");
		healthBar.screenCoords().setEnabled(true);
		healthBarLCap = new Sprite("Health Cap");
		healthBarLCap.screenCoords().setEnabled(true);
		healthBarLCap.scalar().setScale(4);
		healthBarRCap = new Sprite("Health Cap");
		healthBarRCap.screenCoords().setEnabled(true);
		healthBarRCap.scalar().setScale(4);
		healthBarRCap.scalar().flipHorizontally();
		
	}

	/*************************************************************************
	 * Positions and updates the GUI.
	 * 
	 * @param delta
	 * 			  the amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(owner!= null && owner.matrix() != null && owner.health() > 0)
		{
			float screenWidth = WindowManager.controller().width();
			float screenHeight = WindowManager.controller().height();
			float barWidth = screenWidth * 0.66f;
			float healthPercent = owner.healthPercentage();
			scoreDisplay.textSkin().setText("" + owner.matrix().score());
			scoreDisplay.textSkin().checkFormat();
			scoreDisplay.location().set(screenWidth - 15 - 
					scoreDisplay.textSkin().textWidth(), 15);
			healthBarBack.scalar().setXScale(barWidth / healthBarBack.width());
			healthBarBack.location().set(screenWidth / 2, screenHeight - 
					healthBarBack.scaledHeight() / 2);
			healthBarBack.color().set(1f - healthPercent, 0, healthPercent);
			healthBar.scalar().setXScale(healthPercent * barWidth / 
					healthBar.width());
			healthBar.location().set((screenWidth / 2) - (healthBarBack.scaledWidth()
					/ 2) + (healthBar.scaledWidth() / 2), screenHeight -
							healthBar.scaledHeight() / 2);
			healthBar.color().set(1f - healthPercent, healthPercent, 0);
			healthBarLCap.location().set((screenWidth / 2) - 
					(healthBarBack.scaledWidth() / 2) - healthBarLCap.width(), 
					screenHeight - healthBarLCap.height());
			healthBarRCap.location().set((screenWidth / 2) + 
					(healthBarBack.scaledWidth() / 2) + healthBarRCap.width(), 
					screenHeight - healthBarRCap.height());
		}
	}

	/*************************************************************************
	 * Renders the GUI if the player is still in the matrix and alive.
	 ***************************************************************/ @Override
	public void render()
	{
		if(owner != null && owner.matrix() != null && owner.health() > 0)
		{
			healthBarBack.render();
			healthBar.render();
			healthBarLCap.render();
			healthBarRCap.render();
			scoreDisplay.render();
			owner.weapons().render();
		}
	}
}