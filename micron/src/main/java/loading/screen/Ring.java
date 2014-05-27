package loading.screen;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.standard.game.objects.Sprite;

/*****************************************************************************
 * A Ring is a simple Sprite that is used to create a fancy display while 
 * resources load.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Ring extends Sprite implements Updateable
{
	/**
	 * The speed at which the ring rotates.
	 **/
	protected float rotationSpeed;

	/*************************************************************************
	 * Creates the indicated ring.
	 * 
	 * @param number
	 * 			  The number of the ring to use. Valid entries are 1-5.
	 *************************************************************************/
	public Ring(int number)
	{
		super("res/images/loading/Ring " + number + ".png");
		rotation.set(70 * number);
		rotationSpeed = ((Resources.random().nextFloat() * 0.04f) + 0.02f) /
			(float) Math.sqrt(number);
		if(Resources.random().nextBoolean())
			rotationSpeed *= -1;
		color.setAlpha(0.5f);
	}

	/*************************************************************************
	 * Rotates the Ring.
	 * 
	 * @param delta
	 * 			  The number of clock ticks that have passed since the last 
	 * 			  update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		rotation.rotate(delta * rotationSpeed);
	}
}