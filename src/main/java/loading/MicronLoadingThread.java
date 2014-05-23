package loading;

import org.jrabbit.base.data.thread.WatchableGLThread;
import org.jrabbit.base.graphics.font.Font;
import org.jrabbit.base.graphics.font.renderer.AngelCodeRenderer;
import org.jrabbit.base.graphics.image.Image;
import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * MicronLoadingThread attempts to load all required resources for Micron in a
 * different thread. If context sharing (required for resources loading in a
 * separate thread) fails, this class is still utilized to load all resources,
 * but it does so in the main thread of execution (causing an unfortunate pause
 * in the game).
 * 
 * @author Chris Molini
 *****************************************************************************/
public class MicronLoadingThread extends WatchableGLThread
{
	/**
	 * This list is used to load images and set their references.
	 **/
	private static String[][] imageMap = new String[][] {
		// Main Menu
			{ "Logo", "res/images/menu/Micron Logo.png" },
			{ "Random Song", "res/images/menu/RandomSong.png" },
			{ "Shift Song", "res/images/menu/ShiftSong.png" },
			{ "Song Title Bar", "res/images/menu/SongTitleBar.png" },

		// Grid
			{ "Grid 1", "res/images/grid/1.png" },
			{ "Grid 2", "res/images/grid/2.png" },
			{ "Grid 3", "res/images/grid/3.png" },
			{ "Grid 4", "res/images/grid/4.png" },
			{ "Grid 5", "res/images/grid/5.png" },
			{ "Grid 6", "res/images/grid/6.png" },
			{ "Grid 7", "res/images/grid/7.png" },
			{ "Grid 8", "res/images/grid/8.png" },

		// Particle effects
			{ "Particle 1", "res/images/game/effects/particles/1.png" },
			{ "Particle 2", "res/images/game/effects/particles/2.png" },
			{ "Particle 3", "res/images/game/effects/particles/3.png" },
			{ "Particle 4", "res/images/game/effects/particles/4.png" },
			{ "Particle 5", "res/images/game/effects/particles/5.png" },
			{ "Particle 6", "res/images/game/effects/particles/6.png" },
			{ "Particle 7", "res/images/game/effects/particles/7.png" },
			{ "Particle 8", "res/images/game/effects/particles/8.png" },

		// GUI
			// Health
			{ "Health Back", "res/images/game/gui/health/Health Back.png" },
			{ "Health Cap", "res/images/game/gui/health/Health Cap.png" },
			{ "Health Bar", "res/images/game/gui/health/Health Bar.png" },
			
			// Weapons
			{ "Weapon GUI 1", "res/images/game/gui/weapon/1.png" },
			{ "Weapon GUI 2", "res/images/game/gui/weapon/2.png" },
			{ "Weapon GUI 3", "res/images/game/gui/weapon/3.png" },

		// Entities
			// General
			{ "Aura", "res/images/game/entities/general/Aura.png" },
			{ "Pulse", "res/images/game/entities/general/Pulse.png" },

			// Player
			{ "Player", "res/images/game/entities/player/Player.png" },

			// Assaulter
			{ "Assaulter Body", "res/images/game/entities/assaulter/Assaulter Body.png" },
			{ "Assaulter Turret", "res/images/game/entities/assaulter/Assaulter Gun.png" },
			{ "Assaulter Debris 1", "res/images/game/entities/assaulter/Assaulter Debris 1.png" },
			{ "Assaulter Debris 2", "res/images/game/entities/assaulter/Assaulter Debris 2.png" },
			{ "Assaulter Debris 3", "res/images/game/entities/assaulter/Assaulter Debris 3.png" },
			{ "Assaulter Debris 4", "res/images/game/entities/assaulter/Assaulter Debris 4.png" },
			{ "Assaulter Debris 5", "res/images/game/entities/assaulter/Assaulter Debris 5.png" },

			// Void
			{ "Black Hole", "res/images/game/entities/black hole/Black Hole.png" },
			{ "Black Hole Aura", "res/images/game/entities/black hole/Black Hole Aura.png" },

			// Hive
			{ "Juggernaut Fins", "res/images/game/entities/juggernaut/Juggernaut Fins.png" },
			{ "Juggernaut Glow", "res/images/game/entities/juggernaut/Juggernaut Glow.png" },
			{ "Juggernaut Ring", "res/images/game/entities/juggernaut/Juggernaut Ring.png" },
			{ "Juggernaut Gun", "res/images/game/entities/juggernaut/Juggernaut Gun.png" },
			{ "Juggernaut Gun Debris", "res/images/game/entities/juggernaut/Juggernaut Gun Debris.png" },
			{ "Juggernaut Debris 1", "res/images/game/entities/juggernaut/Juggernaut Debris 1.png" },
			{ "Juggernaut Debris 2", "res/images/game/entities/juggernaut/Juggernaut Debris 2.png" },
			{ "Juggernaut Debris 3", "res/images/game/entities/juggernaut/Juggernaut Debris 3.png" },

			// Mine Layer
			{ "Mine Layer", "res/images/game/entities/mine layer/Mine Layer.png" },
			{ "Mine Layer Component", "res/images/game/entities/mine layer/Mine Layer Comp.png" },

			// Mine Layer
			{ "Worm Segment", "res/images/game/entities/worm/Worm Segment.png" },
			{ "Worm Debris", "res/images/game/entities/worm/Worm Debris.png" },
			{ "Worm Gun", "res/images/game/entities/worm/Worm Gun.png" },
			
			// Mine
			{ "Mine 1", "res/images/game/entities/mine/Mine 1.png" },
			{ "Mine 2", "res/images/game/entities/mine/Mine 2.png" },
			{ "Mine 3", "res/images/game/entities/mine/Mine 3.png" },
			{ "Mine 4", "res/images/game/entities/mine/Mine 4.png" },
			{ "Mine 5", "res/images/game/entities/mine/Mine 5.png" },
			{ "Mine 6", "res/images/game/entities/mine/Mine 6.png" },
			{ "Mine 7", "res/images/game/entities/mine/Mine 7.png" },
			{ "Mine 8", "res/images/game/entities/mine/Mine 8.png" },
			{ "Mine 9", "res/images/game/entities/mine/Mine 9.png" },
			{ "Mine 10", "res/images/game/entities/mine/Mine 10.png" },

			// Healer
			{ "Healer", "res/images/game/entities/healer/Healer.png" },

		// Effects
			// Bullets
			{ "Bullet", "res/images/game/effects/bullet/Bullet.png" },
			
			// Charge Blast
			{ "Charge 1", "res/images/game/effects/charge/Charge 1.png" },
			{ "Charge 2", "res/images/game/effects/charge/Charge 2.png" },
			{ "Charge 3", "res/images/game/effects/charge/Charge 3.png" },
			{ "Charge 4", "res/images/game/effects/charge/Charge 4.png" },
			
			// Missile
			{ "Missile 1", "res/images/game/effects/missile/Missile 1.png" },
			{ "Missile 2", "res/images/game/effects/missile/Missile 2.png" },
			{ "Missile 3", "res/images/game/effects/missile/Missile 3.png" },

			// Burst
			{ "Burst 1", "res/images/game/effects/burst/small/1.png" },
			{ "Burst 2", "res/images/game/effects/burst/small/2.png" },
			{ "Burst 3", "res/images/game/effects/burst/small/3.png" },
			{ "Burst 4", "res/images/game/effects/burst/small/4.png" },
			{ "Burst 5", "res/images/game/effects/burst/small/5.png" },
			
			// Explosion
			{ "Explosion 1", "res/images/game/effects/explosion/Explosion 1.png" },
			{ "Explosion 2", "res/images/game/effects/explosion/Explosion 2.png" },
			{ "Explosion 3", "res/images/game/effects/explosion/Explosion 3.png" },
			{ "Explosion 4", "res/images/game/effects/explosion/Explosion 4.png" },
			{ "Explosion 5", "res/images/game/effects/explosion/Explosion 5.png" },
			{ "Explosion 6", "res/images/game/effects/explosion/Explosion 6.png" },
			{ "Explosion 7", "res/images/game/effects/explosion/Explosion 7.png" },
			{ "Explosion 8", "res/images/game/effects/explosion/Explosion 8.png" },
			{ "Explosion 9", "res/images/game/effects/explosion/Explosion 9.png" },
			{ "Explosion 10", "res/images/game/effects/explosion/Explosion 10.png" } };

	/*************************************************************************
	 * Creates all Images that are needed for the game.
	 * 
	 * @return An array of all loaded Images.
	 *************************************************************************/
	public static Image[] loadImages()
	{
		Image[] images = new Image[imageMap.length];
		for(int i = 0; i <images.length; i++)
			images[i] = new Image(imageMap[i][0], imageMap[i][1]);
		return images;
	}
	
	/*************************************************************************
	 * Creates all fonts that are needed for the game.
	 * 
	 * @return An array of all loaded fonts.
	 *************************************************************************/
	public static Font[] loadFonts()
	{
		Font[] fonts = new Font[3];
		
		/*fonts[0] = new Font("Content", new UnicodeRenderer("res/fonts/visitor1.ttf", 20));
		fonts[0].setSpacing(0.75f);
		fonts[0].setIndent(0);
		
		fonts[1] = new Font("Score", new UnicodeRenderer("res/fonts/ABSTRACT.TTF", 8));
		fonts[1].setIndent(0);
		
		UnicodeRenderer renderer = new UnicodeRenderer("res/fonts/ABSTRACT.TTF", 14);
		renderer.setGlyphPainter(new GlyphPainter() {
			public void drawGlyph(Graphics2D graphics, Shape glyph) { 
				graphics.setComposite(AlphaComposite.SrcOver);
				graphics.setColor(Color.WHITE);
				graphics.fill(glyph);
				graphics.setColor(Color.DARK_GRAY);
				graphics.draw(glyph);
			} } );
		fonts[2] = new Font("Buttons", renderer);
		fonts[2].setIndent(0);*/
		
		fonts[0] = new Font("Content", new AngelCodeRenderer("res/fonts/Micron Info.fnt",
				"res/fonts/Micron Info.png"));
		fonts[0].setSpacing(0.75f);
		fonts[0].setIndent(0);
		
		fonts[1] = new Font("Score", new AngelCodeRenderer("res/fonts/Micron Score.fnt",
				"res/fonts/Micron Score.png"));
		fonts[1].setIndent(0);
		
		fonts[2] = new Font("Buttons", new AngelCodeRenderer("res/fonts/Micron Menu.fnt",
				"res/fonts/Micron Menu.png"));
		fonts[2].setIndent(0);
		
		return fonts;
	}
	
	/*************************************************************************
	 * Loads all resources needed by Micron.
	 ***************************************************************/ @Override
	protected void act()
	{
		Resources.images().add(loadImages());
		Resources.fonts().add(loadFonts());
		MicronCursors.loadCursors();
	}
}