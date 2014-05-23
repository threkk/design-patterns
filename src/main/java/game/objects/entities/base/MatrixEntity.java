package game.objects.entities.base;

import game.objects.ComplexObject;
import game.objects.entities.base.armor.*;
import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * A MatrixEntity is a character in the game world. It has health, armor, and an 
 * amount of "points" that it is worth.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class MatrixEntity extends MatrixSprite implements ComplexObject
{
	/**
	 * The current health of the MatrixEntity.
	 **/
	protected float health;

	/**
	 * The maximum health the MatrixEntity can have.
	 **/
	protected float maxHealth;

	/**
	 * The Armor the MatrixEntity has.
	 **/
	protected Armor armor;

	/**
	 * The amount of points the MatrixEntity is worth if the player kills it.
	 **/
	protected int points;
	
	/**
	 * How "complex" a MatrixEntity is.
	 **/
	protected int complexity;

	/*************************************************************************
	 * Creates the default MatrixEntity. It has a scale of 2, a base health of 
	 * 100, and armor that doesn't impare damage at all.
	 *************************************************************************/
	public MatrixEntity()
	{
		complexity = 1;
		scalar.setScale(2);
		defineHealth(100);
		armor = new BaseArmor(0, 0);
	}
	
	protected MatrixEntity parent() { return this; }
	
	/*************************************************************************
	 * Learns the complexity of this MatrixEntity. This represents how much 
	 * processing and rendering power this MatrixEntity will take up.
	 * 
	 * @return The "weight" of the MatrixEntity when in a Matrix.
	 ***************************************************************/ @Override
	public int complexity() { return complexity; }

	/*************************************************************************
	 * Learns how many points the MatrixEntity is worth.
	 * 
	 * @return The value of this MatrixEntity.
	 *************************************************************************/
	public int points() { return points; }

	/*************************************************************************
	 * Redefines the value of this MatrixEntity.
	 * 
	 * @param points
	 * 			  The new amount of points this MatrixEntity is worth.
	 *************************************************************************/
	public void setPoints(int points)
	{
		this.points = points;
	}

	/*************************************************************************
	 * Changes the maximum health of this MatrixEntity. Also sets the 
	 * MatrixEntity's current health to full.
	 * 
	 * @param amount
	 * 			  The new amount of health.
	 *************************************************************************/
	protected void defineHealth(float amount)
	{
		health = maxHealth = amount;
	}

	/*************************************************************************
	 * Learns the MatrixEntity's health.
	 * 
	 * @return The current health of the MatrixEntity.
	 *************************************************************************/
	public float health() { return health; }

	/*************************************************************************
	 * Learns the proportion of the MatrixEntity's health.
	 * 
	 * @return The percentage of the MatrixEntity's total health that remains.
	 *************************************************************************/
	public float healthPercentage() { return health / maxHealth; }

	/*************************************************************************
	 * Learns the MatrixEntity's max health.
	 * 
	 * @return The current maximum health of the MatrixEntity.
	 *************************************************************************/
	public float maxHealth() { return maxHealth; }

	/*************************************************************************
	 * Heals the MatrixEntity by the indicated amount.
	 * 
	 * @param amount
	 * 			  The amount to heal the MatrixEntity by.
	 *************************************************************************/
	public void heal(float amount)
	{
		health += amount;
		validateHealth();
	}

	/*************************************************************************
	 * Damages the MatrixEntity by the indicated amount (as modified by its 
	 * armor).
	 * 
	 * @param amount
	 * 			  The amount of damage that is being used against the 
	 * 			  MatrixEntity.
	 * 
	 * @return Whether or not the MatrixEntity was killed by the attack. 
	 *************************************************************************/
	public boolean damage(float amount)
	{
		if(health <= 0)
			return false;
		health -= armor.modifyDamage(amount);
		validateHealth();
		return health <= 0;
	}

	/*************************************************************************
	 * Ensures that the MatrixEntity's health is within the proper bounds. If it
	 * is greater than the maximum health, it is capped. If it is less than or 
	 * equal to 0, then the MatrixEntity is killed.
	 *************************************************************************/
	protected void validateHealth()
	{
		if(health > maxHealth)
			health = maxHealth;
		else if(health <= 0)
			kill();
	}
}