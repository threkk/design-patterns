package game.spawning;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.window.WindowManager;
import static org.jrabbit.base.managers.Resources.*;

import game.objects.entities.base.MatrixEntity;
import game.objects.entities.hostile.Assaulter;
import game.objects.entities.hostile.Juggernaut;
import game.objects.entities.hostile.MineLayer;
import game.objects.entities.hostile.WormSegment;
import game.objects.entities.neutral.BlackHole;
import game.objects.entities.neutral.Healer;
import game.world.Matrix;

/*****************************************************************************
 * A Spawner is used to create a single set of entities for the SpawnManager.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class Spawner
{
	/*************************************************************************
	 * A convenience method for setting the spawned entities the correct 
	 * distance from their target.
	 * 
	 * @return The diagonal dimension of the current screen.
	 *************************************************************************/
	protected static float screenDiag()
	{
		return (float) Math.hypot(	WindowManager.controller().width(), 
									WindowManager.controller().height());
	}
	
	/*************************************************************************
	 * Creates and adds a group of objects to the world.
	 * 
	 * @param target
	 * 			  The MatrixEntity that the created objects should "focus" on, 
	 * 			  if applicable.
	 * @param matrix
	 * 			  The matrix to spawn into.
	 * @param theta
	 * 			  The angle of the direction in which to place the created 
	 * 			  objects.
	 *************************************************************************/
	protected abstract void spawn(MatrixEntity target, Matrix matrix, float theta);
	
	/*************************************************************************
	 * An AssaulterSpawner creates Assaulters to fight against the player.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class AssaulterSpawner extends Spawner
	{
		/*************************************************************************
		 * Creates and adds a small squad of Assaulters to the world.
		 * 
		 * @param target
		 * 			  The MatrixEntity that the created Assaulters should 
		 * 			  attempt to kill.
		 * @param matrix
		 * 			  The matrix to spawn into.
		 * @param theta
		 * 			  The angle of the direction in which to place the created 
		 * 			  Assaulters.
		 ***********************************************************/ @Override
		protected void spawn(MatrixEntity target, Matrix matrix, float theta)
		{
			Vector2f location = matrix.camera().location().copy();
			location.addPolar(screenDiag(), theta);
			for(int i = 0; i < 5 + random().nextInt(5); i++)
			{
				Vector2f spawnPoint = location.copy();
				spawnPoint.addPolar(random().nextFloat() * 250f, 
						random().nextFloat() * 6.283f);
				matrix.add(new Assaulter(target, spawnPoint));
			}
		}
	}
	
	/*************************************************************************
	 * A HealerSpawner creates Healers that will heal all MatrixEntities in an
	 * area of effect.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class HealerSpawner extends Spawner
	{
		/*************************************************************************
		 * Creates and adds a Healer to the world.
		 * 
		 * @param target
		 * 			  The MatrixEntity that is the target of this spawn. This
		 * 			  is not applicable to a Healer.
		 * @param matrix
		 * 			  The matrix to spawn into.
		 * @param theta
		 * 			  The angle of the direction in which to place the created 
		 * 			  Healer.
		 ***********************************************************/ @Override
		protected void spawn(MatrixEntity target, Matrix matrix, float theta)
		{
			Vector2f location = matrix.camera().location().copy();
			location.addPolar(screenDiag(), theta);
			matrix.add(new Healer(location));
		}
	}
	
	/*************************************************************************
	 * A BlackHoleSpawner creates BlackHoles that will annihilate everything 
	 * they touch.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class BlackHoleSpawner extends Spawner
	{
		/*************************************************************************
		 * Creates and adds a BlackHole to the world.
		 * 
		 * @param target
		 * 			  The MatrixEntity that is the target of this spawn.
		 * @param matrix
		 * 			  The matrix to spawn into.
		 * @param theta
		 * 			  The angle of the direction in which to place the created 
		 * 			  BlackHole.
		 ***********************************************************/ @Override
		protected void spawn(MatrixEntity target, Matrix matrix, float theta)
		{
			Vector2f location = matrix.camera().location().copy();
			location.addPolar(screenDiag(), theta);
			matrix.add(new BlackHole(target, location));
		}
	}
	
	/*************************************************************************
	 * A JuggernautSpawner creates Juggernauts that will annihilate everything 
	 * they touch.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class JuggernautSpawner extends Spawner
	{
		/*************************************************************************
		 * Creates and adds a Juggernaut to the world.
		 * 
		 * @param target
		 * 			  The MatrixEntity that is the target of this spawn.
		 * @param matrix
		 * 			  The matrix to spawn into.
		 * @param theta
		 * 			  The angle of the direction in which to place the created 
		 * 			  Juggernaut.
		 ***********************************************************/ @Override
		protected void spawn(MatrixEntity target, Matrix matrix, float theta)
		{
			Vector2f location = matrix.camera().location().copy();
			location.addPolar(screenDiag(), theta);
			matrix.add(new Juggernaut(target, location));
		}
	}
	
	/*************************************************************************
	 * A MineLayerSpawner creates Mine Layers that will drop mines wherever they
	 * go.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class MineLayerSpawner extends Spawner
	{
		/*************************************************************************
		 * Creates and adds a Mine Layer to the world.
		 * 
		 * @param target
		 * 			  The MatrixEntity that is the target of this spawn.
		 * @param matrix
		 * 			  The matrix to spawn into.
		 * @param theta
		 * 			  The angle of the direction in which to place the created 
		 * 			  Mine Layer.
		 ***********************************************************/ @Override
		protected void spawn(MatrixEntity target, Matrix matrix, float theta)
		{
			Vector2f location = matrix.camera().location().copy();
			location.addPolar(screenDiag(), theta);
			matrix.add(new MineLayer(target, location));
		}
	}
	
	/*************************************************************************
	 * A WormSpawner creates snakelike Worms that are incredibly deadly.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class WormSpawner extends Spawner
	{
		/*************************************************************************
		 * Creates and adds a Worm to the world.
		 * 
		 * @param target
		 * 			  The MatrixEntity that is the target of this spawn.
		 * @param matrix
		 * 			  The matrix to spawn into.
		 * @param theta
		 * 			  The angle of the direction in which to place the created 
		 * 			  Worm.
		 ***********************************************************/ @Override
		protected void spawn(MatrixEntity target, Matrix matrix, float theta)
		{
			// Ensure that we will be able to add the number of Worm segments
			// that we want.
			int maxSegments = (matrix.maxComplexity() - matrix.currentComplexity()) / 2;
			int numSegments = Math.min(random().nextInt(100) + 15, maxSegments);
			if(numSegments >= 15)
			{
				Vector2f location = matrix.camera().location().copy();
				location.addPolar(screenDiag(), theta);
				WormSegment tail = null;
				WormSegment head = null;
				for(int i = 0; i < numSegments; i++)
				{
					head = new WormSegment(target, location, tail);
					if(tail != null)
						tail.setAsTailTo(head);
					matrix.add(head);
					tail = head;
				}
				head.setAsHead();
			}
		}
	}
}