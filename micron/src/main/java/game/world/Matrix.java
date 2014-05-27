package game.world;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.graphics.layers.types.BlendedLayer;
import org.jrabbit.base.graphics.transforms.BlendOp;
import org.jrabbit.base.input.KeyboardHandler;
import org.lwjgl.input.Keyboard;

import settings.MicronGameSettings;

import loading.MicronCursors;
import game.objects.ComplexObject;
import game.objects.Cullable;
import game.objects.debris.base.*;
import game.objects.effects.base.MatrixEffect;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.player.Player;
import game.objects.entities.player.gui.PlayerGUI;
import game.objects.sprite.MatrixSprite;
import game.spawning.SpawnManager;
import game.visual.particles.MatrixParticles;
import grid.GridWorld;

/*****************************************************************************
 * Matrix is the base game world of Micron.
 * 
 * Matrix is designed so that on creation, a new game is ready for play; to play
 * simply set it as the active game world. Everything in it is randomized so as
 * to provide a different game experience each time, while maintaining a 
 * consistent overall experience.
 *****************************************************************************/
public class Matrix extends GridWorld 
{
	/**
	 * The maximum possible complexity a Matrix can have.
	 **/
	private static final int MAX_COMPLEXITY = 500;
	
	/**
	 * The initial complexity a Matrix will have.
	 **/
	private static final int START_COMPLEXITY = 35;

	/**
	 * The rate at which a Matrix's max complexity will increase.
	 **/
	private static final float COMPLEXITY_INCR_RATE = 0.00005f;
	
	/**
	 * Whether or not Micron to exit to the main menu once the Matrix finishes 
	 **/
	protected boolean quitToMenu;
	
	/**
	 * This variable multiplies the amount by which the world is updated. By
	 * making it higher or lower, Micron gameplay can have a "time dilation"
	 * effect.
	 **/
	protected float timeRate;

	/**
	 * The current Debris in the world.
	 **/
	protected LockingList<Debris> debris;

	/**
	 * The current Vacuums that can destroy Debris.
	 **/
	protected LockingList<Vacuum> vacuums;

	/**
	 * The list of MatrixEntities that are considered "actors" in the gameworld.
	 **/
	protected LockingList<MatrixEntity> entities;

	/**
	 * The list of MatrixEffects that act upon the list of MatrixEntities.
	 **/
	protected LockingList<MatrixEffect> effects;
	
	/**
	 * The player.
	 **/
	protected Player player;
	
	/**
	 * The generic particle system that renders explosions and bursts.
	 **/
	protected MatrixParticles particles;
	
	/**
	 * The current score that has been gained by the player.
	 **/
	protected long score;
	
	/**
	 * The current maximum complexity allowed by this Matrix.
	 **/
	protected float maxComplexity;
	
	/**
	 * The sum of the complexity of all ComplexObjects in the Matrix.
	 **/
	protected int currentComplexity;
	
	/*************************************************************************
	 * Creates a Matrix. It is ready for play once created; simply make it the
	 * active world and it will handle everything else.
	 *************************************************************************/
	public Matrix()
	{
		timeRate = 1f;
		maxComplexity = START_COMPLEXITY;
		layers.add(	"Debris");
		layers.add(new BlendedLayer("Auras A", new BlendOp.Additive()));
		layers.add(	"Mines",
					"Enemies A",
					"Player",
					"Enemies B",
					"Burst");
		layers.add(	new BlendedLayer("Auras B", new BlendOp.Additive()),
					new BlendedLayer("Powerups", new BlendOp.Additive()),
					new BlendedLayer("Projectiles", new BlendOp.Additive()),
					new BlendedLayer("Black Hole Aura", new BlendOp.Additive()));
		layers.add(	"Black Hole",
					"GUI");
		debris = new LockingList<Debris>();
		vacuums = new LockingList<Vacuum>();
		entities = new LockingList<MatrixEntity>();
		effects = new LockingList<MatrixEffect>();
		add(new SpawnManager(this));
		add(player = new Player());
		add(new PlayerGUI(player), "GUI");
		add("Burst", particles = new MatrixParticles());
	}

	/*************************************************************************
	 * Adds the object to the world. The object is added to all appropriate 
	 * lists, based on its type.
	 * 
	 * @param object
	 * 			  The object to add. 
	 ***************************************************************/ @Override
	public void add(Object object)
	{
		boolean cantAdd = false;
		if(object instanceof ComplexObject)
		{
			int comp = ((ComplexObject) object).complexity();
			if(currentComplexity + comp <= maxComplexity)
				currentComplexity += comp;
			else
				cantAdd = true;
		}
		if(cantAdd)
			return;
		
		if(object instanceof MatrixSprite)
		{
			((MatrixSprite) object).addedToMatrix(this);
			layers.add((Renderable) object, ((MatrixSprite) object).layer());
			updated.add(((MatrixSprite) object));
			if(object instanceof MatrixEntity)
				entities.add((MatrixEntity) object);
			else if(object instanceof MatrixEffect)
				effects.add((MatrixEffect) object);
		}
		else
		{
			if(object instanceof Renderable)
				layers.add((Renderable) object);
			if(object instanceof Updateable)
				updated.add((Updateable) object);
		}
		if(object instanceof Vacuum)
			vacuums.add((Vacuum) object);
		if(object instanceof Debris)
			debris.add((Debris) object);
	}

	/*************************************************************************
	 * Adds the object to the world. The object is added to all appropriate 
	 * lists, based on its type.
	 * 
	 * @param layer
	 * 			  The layer to add the object to, if it is Renderable. 
	 * @param object
	 * 			  The object to add. 
	 ***************************************************************/ @Override
	public void add(String layer, Object object)
	{
		boolean cantAdd = false;
		if(object instanceof ComplexObject)
		{
			int comp = ((ComplexObject) object).complexity();
			if(currentComplexity + comp <= maxComplexity)
				currentComplexity += comp;
			else
				cantAdd = true;
		}
		if(cantAdd)
			return;
		
		if(object instanceof MatrixSprite)
		{
			((MatrixSprite) object).addedToMatrix(this);
			layers.add((Renderable) object, layer);
			updated.add(((MatrixSprite) object));
			if(object instanceof MatrixEntity)
				entities.add((MatrixEntity) object);
			else if(object instanceof MatrixEffect)
				effects.add((MatrixEffect) object);
		}
		else
		{
			if(object instanceof Renderable)
				layers.add((Renderable) object, layer);
			if(object instanceof Updateable)
				updated.add((Updateable) object);
		}
		if(object instanceof Vacuum)
			vacuums.add((Vacuum) object);
		if(object instanceof Debris)
			debris.add((Debris) object);
	}

	/*************************************************************************
	 * Removes the object from the world. The object is removed from all 
	 * appropriate lists, based on its type.
	 * 
	 * @param object
	 * 			  The object to remove. 
	 ***************************************************************/ @Override
	public void remove(Object object)
	{
		if(object instanceof ComplexObject)
		{
			currentComplexity -= ((ComplexObject) object).complexity();
			currentComplexity = Math.max(currentComplexity, 0);
		}
		if(object instanceof Renderable)
			layers.remove((Renderable) object);
		if(object instanceof Updateable)
			updated.remove((Updateable) object);
		if(object instanceof MatrixSprite)
		{
			((MatrixSprite) object).removedFromMatrix(this);
			if(object instanceof MatrixEntity)
				entities.remove((MatrixEntity) object);
			if(object instanceof MatrixEffect)
				effects.remove((MatrixEffect) object);
		}
		if(object instanceof Vacuum)
			vacuums.remove((Vacuum) object);
		if(object instanceof Debris)
			debris.remove((Debris) object);
	}
	
	/*************************************************************************
	 * Causes the Matrix to begin fading out. Once it completely fades out, it
	 * will either spawn a new play session (i.e., create a new Matrix and 
	 * switch to it) or it will quit to the main menu.
	 *************************************************************************/
	public void exit()
	{
		fade.fadeOut();
	}

	/*************************************************************************
	 * Updates the Matrix and its settings. Also provides general checking 
	 * between different game objects.
	 * 
	 * @param delta
	 * 			  The amount of time that has elapsed since the last call.
	 ***************************************************************/ @Override
	public void updateWorld(int delta)
	{
		if(KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			quitToMenu = true;
			exit();
		}
		maxComplexity = Math.min(maxComplexity + COMPLEXITY_INCR_RATE * delta, 
				MAX_COMPLEXITY);
		for(Vacuum vacuum : vacuums)
			for(Debris chunk : debris)
				if(vacuum.shouldRemove(chunk))
					remove(chunk);
		for(MatrixEffect effect : effects)
			for(MatrixEntity entity : entities)
				effect.checkAgainst(entity);
		cull(debris);
		cull(effects);
		cull(entities);
		vacuums.unlock();
		debris.unlock();
		effects.unlock();
		entities.unlock();
	}

	/*************************************************************************
	 * Determines if objects should be removed based on their distance from the
	 * viewpoint. This is important because it allows the gameworld to remain
	 * more free of "junk" that is offscreen.
	 * 
	 * @param toCull
	 * 			  The list of Cullable objects to check.
	 *************************************************************************/
	private void cull(Iterable<? extends Cullable> toCull)
	{
		for(Cullable cull : toCull)
			if(cull.location().distanceTo(camera.location()) >= 
					cull.cullDistance())
				remove(cull);
	}
	
	/*************************************************************************
	 * Learns the amount that the Matrix should update itself by.
	 * 
	 * NOTE: This method uses the current time rate to contract or expand the
	 * speed of the game world.
	 * 
	 * @return The amount to update by.
	 ***************************************************************/ @Override
	public int currentDelta()
	{
		return (int) (delta * timeRate);
	}

	/*************************************************************************
	 * Whenever the Matrix is set as the active world, the cursor is set to the
	 * aiming reticle.
	 ***************************************************************/ @Override
	public void onActivation()
	{
		MicronCursors.useGameCursor();
	}
	
	/*************************************************************************
	 * Causes the Matrix to either spawn a new session of play or exit to the
	 * main menu.
	 ***************************************************************/ @Override
	protected void onCompleteFadeOut()
	{
		MicronGameSettings.userData().pushScore(score);
		if(quitToMenu)
			switchTo(MicronGameSettings.menuWorld());
		else
			switchTo(new Matrix());
	}

	/*************************************************************************
	 * Accesses the Matrix's Player.
	 * 
	 * @return The Player that is active in this matrix.
	 *************************************************************************/
	public Player player() { return player; }
	
	/*************************************************************************
	 * Redefines the player reference.
	 * 
	 * @param player
	 * 			  The new player reference.
	 *************************************************************************/
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	/*************************************************************************
	 * Accesses the generic Matrix particle effect.
	 * 
	 * @return The particle system that handles death effects, general 
	 *         explosions, etc.
	 *************************************************************************/
	public MatrixParticles particles() { return particles; }
	
	/************************************************************************
	 * Learns the current score.
	 * 
	 * @return The current player score.
	 ************************************************************************/
	public long score() { return score; }
	
	/************************************************************************
	 * Adds the indicated number of points to the player's score.
	 * 
	 * @param points
	 * 			  The number of points to add.
	 ************************************************************************/
	public void addPoints(int points)
	{
		score += points;
	}

	/************************************************************************
	 * Learns how complex the Matrix can currently become.
	 * 
	 * @return The maximum complexity of all objects in the Matrix.
	 ************************************************************************/
	public int maxComplexity() { return (int) maxComplexity; }

	/************************************************************************
	 * Learns how complex the Matrix currently is.
	 * 
	 * @return The current complexity of all objects in the Matrix.
	 ************************************************************************/
	public int currentComplexity() { return currentComplexity; }

	/*************************************************************************
	 * Removes all objects from the Matrix.
	 ***************************************************************/ @Override
	public void clear()
	{
		super.clear();
		debris.clear();
		vacuums.clear();
		entities.clear();
		effects.clear();
	}
}