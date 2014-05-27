package game.objects.entities.player.controllers;

import game.objects.controllers.TargetedController;
import game.objects.entities.base.MatrixEntity;
import game.objects.entities.base.armor.Armor;

/*****************************************************************************
 * A HealthController doubles as armor and a regeneration controller. As armor,
 * it is useless, but being used as Armor allows it to sync with damage.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class HealthController extends TargetedController<MatrixEntity> implements Armor
{
	/**
	 * The base regeneration rate.
	 **/
	private static final float BASE_REGEN = 0.0001f;
	
	/**
	 * The point at which regeneration will begin.
	 **/
	private static final int REGEN_BEGIN = 10000;
	
	/**
	 * The amount of time that has passed since the MatrixEntity was last 
	 * damaged.
	 **/
	private int timeSinceDamaged;
	
	/**
	 * This value is used to smooth out the rate of regeneration.
	 **/
	private float regenValue;

	/*************************************************************************
	 * Creates a HealthController that will heal the indicated MatrixEntity.
	 * 
	 * @param target
	 * 			  The MatrixEntity to heal.
	 *************************************************************************/
	public HealthController(MatrixEntity target) { super(target); }

	/*************************************************************************
	 * Updates regeneration.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since he last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(target.health() < target.maxHealth())
		{
			int healInterval = ((timeSinceDamaged += delta) - REGEN_BEGIN);
			if(healInterval >= 0)
			{
				timeSinceDamaged -= healInterval;
				regenValue += healInterval / 10000f;
				target.heal((float) Math.sqrt(regenValue * regenValue * 
						regenValue) * BASE_REGEN * delta);
			}
		}
	}

	/*************************************************************************
	 * Whenever this armor is used to reduce damage, regeneration is stopped
	 * and must begin the build-up process again.
	 ***************************************************************/ @Override
	public float modifyDamage(float amount)
	{
		timeSinceDamaged = 0;
		regenValue = 0;
		return amount;
	}
}