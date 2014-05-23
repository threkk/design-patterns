package game.objects.entities.base;

import org.jrabbit.base.graphics.transforms.Vector2f;

/*****************************************************************************
 * A KeyPointEntity extends MatrixEntity to add keypoint functionality. Basically,
 * a keypoint is a spot offset from the MatrixEntity that moves and rotates with
 * it.
 * 
 * This has a variety of useful features. For example, keypoints can be used as 
 * the "origin" of effects, "anchors" for other objects, etc.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class KeyPointEntity extends MatrixEntity
{
	/**
	 * The list of base points.
	 **/
	protected Vector2f[] basePoints;
	
	/**
	 * The calculated resulting points that take into account location, rotation
	 * and scaling.
	 **/
	private Vector2f[] transformedPoints;

	/*************************************************************************
	 * Creates a KeyPointEntity without any initial keypoints.
	 *************************************************************************/
	public KeyPointEntity()
	{
		basePoints = transformedPoints = new Vector2f[0];
	}

	/*************************************************************************
	 * Redefines the base keypoints.
	 * 
	 * @param coordinates
	 * 			  The "vertices" that describe the base offsets for all 
	 * 			  keypoints.
	 *************************************************************************/
	public void defineKeyPoints(float[][] coordinates)
	{
		basePoints = new Vector2f[coordinates.length];
		transformedPoints = new Vector2f[coordinates.length];
		for(int i = 0; i < coordinates.length; i++)
		{
			basePoints[i] = new Vector2f(coordinates[i][0], coordinates[i][1]);
			transformedPoints[i] = basePoints[i].copy();
		}
		updateKeyPoints();
	}

	/*************************************************************************
	 * Recalculates the transformed keypoints based on location, rotation and 
	 * scaling. 
	 *************************************************************************/
	protected void updateKeyPoints()
	{
		float theta = rotation.theta();
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		float xScale = scalar().xScale();
		float yScale = scalar.yScale();
		for(int i = 0; i < basePoints.length; i++)
		{
			float x = basePoints[i].x() * xScale;
			float y = basePoints[i].y() * yScale;
			transformedPoints[i].set(cos * x - sin * y, cos * y + sin * x);
			transformedPoints[i].add(location);
		}
	}

	/*************************************************************************
	 * Learns the location at the indicated point.
	 * 
	 * @param key
	 * 			  The index of the keypoint to use.
	 * 
	 * @return The transformed location (influenced by this Entity's location,
	 *         rotation, and scaling).
	 *************************************************************************/
	public Vector2f pointAt(int key) { return transformedPoints[key]; }

	/*************************************************************************
	 * Recalculates all of the KeyPointEntity's keypoints, and continues
	 * updating its controllers.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void updateControllers(int delta)
	{
		updateKeyPoints();
		super.updateControllers(delta);
	}
}