package game.objects;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.standard.game.objects.base.BaseSprite;

/*****************************************************************************
 * PhysSprite extends BaseSprite to provide some simplistic "physics" 
 * functionality.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class PhysSprite extends BaseSprite implements Updateable, 
		Cullable
{
	/**
	 * The current velocity of the PhysSprite.
	 **/
	protected Vector2f velocity;

	/**
	 * The maximum speed the PhysSprite is allowed to move at.
	 **/
	protected float maxSpeed;

	/**
	 * How much force to apply to the PhysSprite on the next update.
	 **/
	protected Vector2f force;

	/**
	 * The mass of the PhysSprite.
	 **/
	protected float mass;

	/**
	 * The rotation speed of the PhysSprite.
	 **/
	protected float rotationSpeed;
	
	/**
	 * The amount of torque to apply when the PhysSprite is drifing.
	 **/
	protected float torque;
	
	/*************************************************************************
	 * Initializes the default settings in PhysSprite - it has a velocity and 
	 * force of [0, 0], it's maximum speed is 2000 pixels per second, and it
	 * has a mass of 1.0.
	 *************************************************************************/
	public PhysSprite()
	{
		velocity = new Vector2f();
		force = new Vector2f();
		setMaxSpeed(0.2f);
		setMass(1f);
	}

	/*************************************************************************
	 * Applies the physics settings (force, velocity, etc)
	 * 
	 * @param delta
	 * 			  The amount of time elapsed since the last call.
	 *************************************************************************/
	protected void drift(int delta)
	{
		velocity.add(force, delta * 0.0001f / mass);
		force.reset();
		velocity.cap(maxSpeed);
		location.add(velocity, delta);
		rotationSpeed += (torque / Math.sqrt(mass)) * delta;
		torque = 0;
		rotation.rotate(rotationSpeed * delta);
	}
	
	/*************************************************************************
	 * Accesses the PhySprite's velocity.
	 * 
	 * @return The current velocity of the PhysSprite. 
	 *************************************************************************/
	public Vector2f velocity() { return velocity; }

	/*************************************************************************
	 * Accesses the force vector that will be applied to the PhysSprite.
	 * 
	 * @return The force to be applied.
	 *************************************************************************/
	public Vector2f force() { return force; }

	/*************************************************************************
	 * Learns the maximum speed of the PhysSprite.
	 * 
	 * @return
	 *************************************************************************/
	public float maxSpeed() { return maxSpeed; }

	/*************************************************************************
	 * Redefines the PhysSprite's max speed.
	 * 
	 * @param mass
	 * 			  The new maximum speed for the PhysSprite to have.
	 *************************************************************************/
	public void setMaxSpeed(float maxSpeed)
	{
		this.maxSpeed = maxSpeed;
	}

	/*************************************************************************
	 * Learns the mass of the PhysSprite.
	 * 
	 * @return The variable that dampens force applied to the PhysSprite.
	 *************************************************************************/
	public float mass() { return mass; }
	
	/*************************************************************************
	 * Redefines the PhysSprite's mass.
	 * 
	 * @param mass
	 * 			  The new mass for the PhysSprite to have.
	 *************************************************************************/
	public void setMass(float mass)
	{
		this.mass = mass;
	}
	
	/*************************************************************************
	 * Applies the indicated amount of torque. Rotation speed will be finalized
	 * on the next update.
	 * 
	 * @param torque
	 * 			  The amount of torque to apply.
	 *************************************************************************/
	public void applyTorque(float torque)
	{
		this.torque += torque;
	}

	/*************************************************************************
	 * Updates the PhysSprite.
	 * 
	 * @param delta
	 * 			  The amount of time elapsed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		drift(delta);
	}
}