package game.spawning;

import game.objects.entities.player.Player;
import game.world.Matrix;

import org.jrabbit.base.core.types.Updateable;
import static org.jrabbit.base.managers.Resources.*;
import static game.spawning.Spawner.*;

/*****************************************************************************
 * SpawnManager manages creating and positioning random enemies for the player
 * during gameplay.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SpawnManager implements Updateable
{
	/**
	 * THe initial time between spawning each set of enemies.
	 **/
	private static final int SPAWN_INTERVAL_INITIAL = 10000;
	
	/**
	 * The rate at which the delay between spawning enemies is decreasing.
	 **/
	private static final float SPAWN_INTERVAL_DECR_RATE = -0.001f;
	
	/**
	 * The minimum interval between each spawn.
	 **/
	private static final int SPAWN_MIN_INTERVAL = 1000;
	
	/**
	 * The time between "palette shifts" where the sets of enemies that can be
	 * spawned are changed.
	 **/
	private static final float RANDOMIZE_INTERVAL = 30000;
	
	/**
	 * The number of different spawners that can be active at any one time.
	 **/
	private static final int ACTIVE_SPAWNS = 3;

	/**
	 * Counts down to when the Spawn palette should be randomized.
	 **/
	protected int randomizeTimer;
	
	/**
	 * Counts down to when the SpawnManager should attempt to spawn another set
	 * of enemies.
	 **/
	protected int spawnTimer;
	
	/**
	 * The current interval between each spawn.
	 **/
	protected float spawnInterval;
	
	/**
	 * The Matrix to spawn entities in.
	 **/
	protected Matrix matrix;
	
	/**
	 * The list of all possible Spawners that can be used.
	 **/
	protected Spawner[] spawnPalette;
	
	/**
	 * The list of all Spawners that are currently active.
	 **/
	protected Spawner[] activeSpawners;
	
	/*************************************************************************
	 * Creates a SpawnManager.
	 * 
	 * @param matrix
	 * 			  The matrix into which to spawn.
	 *************************************************************************/
	public SpawnManager(Matrix matrix)
	{
		this.matrix = matrix;
		spawnInterval = SPAWN_INTERVAL_INITIAL;
		spawnPalette = new Spawner[] {	new AssaulterSpawner(), 
										new HealerSpawner(), 
										new BlackHoleSpawner(),
										new MineLayerSpawner(),
										new JuggernautSpawner(),
										new WormSpawner()};
		activeSpawners = new Spawner[ACTIVE_SPAWNS];
		for(int i = 0; i < activeSpawners.length; i++)
			activeSpawners[i] = spawnPalette[Math.min(random().nextInt((int) (
					spawnPalette.length * 1.5f)), random().nextInt(spawnPalette.length))];
	}

	/*************************************************************************
	 * Spawns a new set of entities for the game world. If the target is moving,
	 * the resulting entities may be positioned to be on an intercept course.
	 *************************************************************************/
	protected void spawn()
	{
		spawnTimer -= spawnInterval;
		Player player = matrix.player();
		if(player != null)
		{
			float theta = random().nextFloat() * 6.283f;
			float playerSpeedPercent = player.velocity().magnitude() / player.maxSpeed();
			if(random().nextFloat() >= 1f - playerSpeedPercent)
				theta = (float) Math.toRadians(player.velocity().angle());
			theta += (random().nextFloat() - 0.5f) * 3.1415f * (1.075f - playerSpeedPercent);
			activeSpawners[random().nextInt(activeSpawners.length)].spawn(
					matrix.player(), matrix, theta);
		}
	}

	/*************************************************************************
	 * Randomizes one active spawner from the spawn palette.
	 *************************************************************************/
	protected void randomize()
	{
		randomizeTimer -= RANDOMIZE_INTERVAL;
		activeSpawners[random().nextInt(activeSpawners.length)] =
				spawnPalette[Math.min(random().nextInt(spawnPalette.length * 2),
						random().nextInt(spawnPalette.length))];
	}
	
	/*************************************************************************
	 * Updates the SpawnManager.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		randomizeTimer += delta;
		if(randomizeTimer >= RANDOMIZE_INTERVAL)
			randomize();
		spawnTimer += delta;
		if(spawnTimer >= spawnInterval)
			spawn();
		spawnInterval += SPAWN_INTERVAL_DECR_RATE * delta;
		if(spawnInterval < SPAWN_MIN_INTERVAL)
			spawnInterval = SPAWN_MIN_INTERVAL;
	}
}