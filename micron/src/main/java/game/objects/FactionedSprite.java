package game.objects;

/*****************************************************************************
 * FactionSprite extends CollisionSprite to give it a "faction." Basically, this
 * is a flag of allegiance that allows Micron AI to discriminate.
 * 
 * Additionally, a FactionedSprite has a list of relationships with all existing
 * factions. This list determines if the FactionedSprite is hostile, neutral, or
 * friendly to others.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class FactionedSprite extends CollisionSprite
{
	/**
	 * The faction that this FactionedSprite belongs to. 
	 * 
	 * Allowed factions are:
	 *				 0 -- Enemies.
	 *				 1 -- Bystanders.
	 *				 2 -- Allied.
	 **/
	private int faction;
	
	/**
	 * The list of relationships with the existing factions.
	 * 
	 * Allowed relationships are:
	 * 				 -1 -- Hostile.
	 * 				 0 -- Neutral.
	 * 				 1 -- Friendly.
	 **/
	protected int[] factionRatings;
	
	/*************************************************************************
	 * Sets this FactionedSprite's faction ratings all to neutral.
	 *************************************************************************/
	public FactionedSprite()
	{
		factionRatings = new int[] {0, 0, 0};
	}

	/*************************************************************************
	 * Learns the FactionedSprite's faction.
	 * 
	 * @return The flag that indicates which faction this FactionedSprite is a
	 *         part of.
	 *************************************************************************/
	public int faction() { return faction; }

	/*************************************************************************
	 * Redefines the FactionedSprite's faction.
	 * 
	 * @param faction
	 * 			  The new faction for the FactionSprite.
	 *************************************************************************/
	public void setFaction(int faction)
	{
		this.faction = faction;
	}

	/*************************************************************************
	 * Accesses this FactionedSprite's relationship data.
	 * 
	 * @return The array of bytes that determine how this FactionedSprite views
	 * 		   other FactionedSprites.
	 *************************************************************************/
	public int[] factionRatings() { return factionRatings; }

	/*************************************************************************
	 * Redefines this FactionSprite's faction ratings.
	 * 
	 * @param factionRatings
	 * 			  The list of values that determines faction relationships.
	 *************************************************************************/
	public void setFactionRatings(int... factionRatings)
	{
		this.factionRatings = factionRatings.clone();
	}

	/*************************************************************************
	 * Determines this FactionedSprite's relationship with the indicated target.
	 * 
	 * @return If this FactionedSprite is friendly with the other.
	 *************************************************************************/
	public boolean isFriendlyTo(FactionedSprite other)
	{
		return factionRatings[other.faction()] == 1;
	}

	/*************************************************************************
	 * Determines this FactionedSprite's relationship with the indicated target.
	 * 
	 * @return If this FactionedSprite is neutral towards the other.
	 *************************************************************************/
	public boolean isNeutralTo(FactionedSprite other)
	{
		return factionRatings[other.faction()] == 0;
	}

	/*************************************************************************
	 * Determines this FactionedSprite's relationship with the indicated target.
	 * 
	 * @return If this FactionedSprite is hostile to the other.
	 *************************************************************************/
	public boolean isHostileTo(FactionedSprite other)
	{
		return factionRatings[other.faction()] == -1;
	}
}