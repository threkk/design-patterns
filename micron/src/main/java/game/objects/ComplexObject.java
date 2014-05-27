package game.objects;

/*****************************************************************************
 * A ComplexObject is an object whose presence in a Matrix has potentially 
 * significant costs in rendering and processing time. These costs do not have
 * to be directly caused by the object; if the ComplexObject spawns other 
 * objects, that should also be considered.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface ComplexObject
{
	/*************************************************************************
	 * Learns the complexity of this ComplexObject. This represents how much 
	 * processing and rendering power this ComplexObject will take up.
	 * 
	 * @return The "weight" of the ComplexObject when in a Matrix.
	 *************************************************************************/
	public int complexity();
}