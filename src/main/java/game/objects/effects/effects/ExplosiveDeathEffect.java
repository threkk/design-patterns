package game.objects.effects.effects;

import org.jrabbit.base.graphics.transforms.Color;

import game.objects.death.DeathEffect;
import game.objects.effects.projectiles.Explosion;
import game.objects.sprite.MatrixSprite;
import game.world.Matrix;

/*****************************************************************************
 * An ExplosiveEffect causes an Explosion to be generated each time a 
 * MatrixEffect collides with a MatrixEntity.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ExplosiveDeathEffect implements DeathEffect
{
	/**
	 * The scale of the Explosion to create.
	 **/
	protected float scale;
	
	/**
	 * The Color to make the Explosion.
	 **/
	protected Color color;
	
	/**
	 * The damage rate of the Explosion to create.
	 **/
	protected float damageRate;
	
	/**
	 * The force with which the created Explosion will knock back surrounding
	 * targets.
	 **/
	protected float force;
	
	/**
	 * Whether or not the Explosion should reward points to the player when it
	 * successfully kills a target.
	 **/
	protected boolean addDamage;

	/*************************************************************************
	 * Creates an ExplosiveEffect.
	 * 
	 * @param scale
	 * 			  The scale of the Explosion to create.
	 * @param damageRate
	 * 			  The damage rate of the Explosion to create.
	 * @param force
	 * 			  The force with which the created Explosion will knock back 
	 * 			  surrounding targets.
	 *************************************************************************/
	public ExplosiveDeathEffect(float scale, float damageRate, float force)
	{
		this(scale, damageRate, force, false);
	}

	/*************************************************************************
	 * Creates an ExplosiveEffect.
	 * 
	 * @param scale
	 * 			  The scale of the Explosion to create.
	 * @param damageRate
	 * 			  The damage rate of the Explosion to create.
	 * @param force
	 * 			  The force with which the created Explosion will knock back 
	 * 			  surrounding targets.
	 * @param addDamage
	 * 			  Whether or not the Explosion should reward points to the 
	 * 			  player when it successfully kills a target.
	 *************************************************************************/
	public ExplosiveDeathEffect(float scale, float damageRate, float force, 
			boolean addDamage)
	{
		this(scale, null, damageRate, force, addDamage);
	}

	/*************************************************************************
	 * Creates an ExplosiveEffect.
	 * 
	 * @param scale
	 * 			  The scale of the Explosion to create.
	 * @param color
	 * 			  The Color to make the explosion.
	 * @param damageRate
	 * 			  The damage rate of the Explosion to create.
	 * @param force
	 * 			  The force with which the created Explosion will knock back 
	 * 			  surrounding targets.
	 * @param addDamage
	 * 			  Whether or not the Explosion should reward points to the 
	 * 			  player when it successfully kills a target.
	 *************************************************************************/
	public ExplosiveDeathEffect(float scale, Color color, float damageRate, 
			float force, boolean addDamage)
	{
		this.scale = scale;
		this.color = color;
		this.damageRate = damageRate;
		this.force = force;
		this.addDamage = addDamage;
	}

	/*************************************************************************
	 * Creates an Explosion at the source MatrixSprite's location.
	 * 
	 * @param source
	 * 			  The MatrixSprite that is dying.
	 ***************************************************************/ @Override
	public void onDeath(MatrixSprite source)
	{
		Matrix matrix = source.matrix();
		if(matrix != null)
			matrix.add(new Explosion(	source, 
										color == null ? source.color() : color, 
										source.location(), 
										scale, 
										damageRate, 
										force, 
										addDamage));
	}
}