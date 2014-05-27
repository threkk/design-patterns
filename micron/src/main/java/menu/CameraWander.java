package menu;

import java.util.Random;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.standard.game.world.camera.Camera;

/*****************************************************************************
 * A CameraWander controls the Camera of a world; it randomly moves and rotates
 * it.
 * 
 * The purpose of this class is to make the Grid effect of the main menu more 
 * visually interesting.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CameraWander implements Updateable
{
	/**
	 * The Camera to effect.
	 **/
	protected Camera camera;

	/**
	 * The camera's current velocity.
	 **/
	protected Vector2f velocity;

	/**
	 * The maximum speed that the camera can have.
	 **/
	protected float maxVelocity;

	/**
	 * The acceleration applied to the velocity.
	 **/
	protected Vector2f accel;

	/**
	 * The base acceleration strength.
	 **/
	protected float accelBase;

	/**
	 * The current speed of rotation, in clockwise degrees per microsecond.
	 **/
	protected float rotateSpeed;

	/**
	 * The current acceleration being put on rotation speed.
	 **/
	protected float rotateAcceleration;

	/**
	 * The maximum speed of rotation.
	 **/
	protected float maxRotSpeed;

	/**
	 * The base of the maximum rotation speed.
	 **/
	protected float maxRotSpeedBase;

	/**
	 * How long the current acceleration phase has lasted.
	 **/
	protected int accelTimer;

	/**
	 * How long the current acceleration phase should last.
	 **/
	protected int accelInterval;

	/**
	 * The base duration of all acceleration phases.
	 **/
	protected int accelIntervalBase;

	/**
	 * How long the current rotation phase has lasted.
	 **/
	protected int rotTimer;

	/**
	 * How long the current rotation phase should last.
	 **/
	protected int rotInterval;

	/**
	 * The base duration of all rotation phases.
	 **/
	protected int rotIntervalBase;

	/*************************************************************************
	 * Creates a new CameraWander that will cause the indicated Camera to 
	 * randomly have its viewpoint shifted and moved.
	 * 
	 * @param camera
	 * 			  The Camera to affect.
	 *************************************************************************/
	public CameraWander(Camera camera)
	{
		this.camera = camera;
		Random r = Resources.random();
		maxVelocity = 700;
		maxRotSpeedBase = 15;
		maxRotSpeed = (r.nextFloat() + 1) * maxRotSpeedBase;
		velocity = new Vector2f((r.nextFloat() - 0.5f) * 2 * maxVelocity,
				(r.nextFloat() - 0.5f) * 2 * maxVelocity);
		velocity.cap(maxVelocity);
		accel = new Vector2f();
		accelBase = 300;
		rotateSpeed = (r.nextFloat() - 0.5f) * 2 * maxRotSpeed;
		randomizeRot();
		containRotation();
		accelTimer = 0;
		accelIntervalBase = 25000;
		accelInterval = (int) ((r.nextFloat() + 1) * accelIntervalBase);
		rotTimer = 0;
		rotIntervalBase = 55000;
	}
	
	/*************************************************************************
	 * Ensures that the Camera is never rotated more quickly than desired.
	 *************************************************************************/
	protected void containRotation()
	{
		if(rotateSpeed < -maxRotSpeed)
			rotateSpeed = -maxRotSpeed;
		else if(rotateSpeed > maxRotSpeed)
			rotateSpeed = maxRotSpeed;
	}

	/*************************************************************************
	 * Randomizes the acceleration being put upon the Camera's velocity.
	 *************************************************************************/
	protected void randomizeAccel()
	{
		Random r = Resources.random();
		accel.setPolar((float) (r.nextFloat() * Math.PI * 2), r.nextFloat() * 
				accelBase);
	}

	/*************************************************************************
	 * Randomizes the rotation acceleration.
	 *************************************************************************/
	protected void randomizeRot()
	{
		Random r = Resources.random();
		rotateAcceleration = (r.nextFloat() - 0.5f) * 2 * maxRotSpeed;
		maxRotSpeed = (r.nextFloat() + 1) * maxRotSpeedBase;
	}
	
	/*************************************************************************
	 * This method affects the camera over time, randomly rotating and moving
	 * it.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		Random r = Resources.random();
		float time = delta / 10000.0f;
		accelTimer += delta;
		if(accelTimer > accelInterval)
		{
			randomizeAccel();
			accelTimer -= accelInterval;
			accelInterval = (int) ((r.nextFloat() + 1) * accelIntervalBase);
		}
		rotTimer += delta;
		if(rotTimer > rotInterval)
		{
			randomizeRot();
			rotTimer -= rotInterval;
			rotInterval = (int) ((r.nextFloat() + 1) * rotIntervalBase);
		}
		velocity.add(accel, time);
		velocity.cap(maxVelocity);
		rotateSpeed += rotateAcceleration * time;
		containRotation();
		camera.location().add(velocity, time);
		camera.rotation().rotate(rotateSpeed * time);
	}
}