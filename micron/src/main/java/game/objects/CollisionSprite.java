package game.objects;

import org.jrabbit.base.math.geom.Geometry;

/*****************************************************************************
 * CollisionSprite extends PhysSprite to add Geometry that can be used to check
 * for collision.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class CollisionSprite extends PhysSprite
{
	/**
	 * The Geometry of the CollisionSprite.
	 **/
	protected Geometry geometry;

	/*************************************************************************
	 * Determines if this CollisionSprite collides with the other.
	 * 
	 * @param sprite
	 * 			  The CollisionSprite to check for a collision with.
	 * 
	 * @return True if the two CollisionSprites collide, false if not.
	 *************************************************************************/
	public boolean collidesWith(CollisionSprite sprite)
	{
		return sprite.geometry().intersects(geometry);
	}

	/*************************************************************************
	 * Accesses the Geometry object.
	 * 
	 * @return The object that defines the geometric shape of this 
	 * 		   CollisionSprite.
	 *************************************************************************/
	public Geometry geometry() { return geometry; }

	/*************************************************************************
	 * Defines the Geometry of this CollisionSprite to have the indicated base
	 * vertices.
	 * 
	 * @param vertices
	 * 			  The points that outline the Geometric shape.
	 *************************************************************************/
	public void setGeometry(float[][] vertices)
	{
		if(geometry == null)
			geometry = new Geometry(vertices);
		else
			geometry.set(vertices);
	}

	/*************************************************************************
	 * Updates the Geometry to reflect the current location, scaling and 
	 * rotation of the CollisionSprite.
	 *************************************************************************/
	protected void updateGeometry()
	{
		if(geometry != null)
			geometry.apply(location, rotation, scalar);
	}

	/*************************************************************************
	 * Updates the CollisionSprite.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		super.update(delta);
		updateGeometry();
	}
}