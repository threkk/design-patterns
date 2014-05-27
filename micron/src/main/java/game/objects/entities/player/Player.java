package game.objects.entities.player;

import org.jrabbit.base.graphics.transforms.Color;

import static org.jrabbit.base.managers.Resources.*;

import game.objects.controllers.rotation.InputRotationController;
import game.objects.death.DeathBurstEffect;
import game.objects.entities.base.StaticKeyPointEntity;
import game.objects.entities.controllers.color.HealthColorController;
import game.objects.entities.player.controllers.*;
import game.objects.entities.player.death.PlayerDeathController;

/*****************************************************************************
 * A Player is, rather self-evidently, the Entity that is player-controlled.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Player extends StaticKeyPointEntity
{
	/**
	 * The Weapon manager of the Player.
	 **/
	protected WeaponController weapons;
	
	/*************************************************************************
	 * Creates the Player.
	 * 
	 * NOTE: The Player has an initial velocity and rotation to make his entry 
	 * into the Matrix more naturalized.
	 *************************************************************************/
	public Player()
	{
		super("Player");
		setLayer("Player");
		defineHealth(250);
		setMass(25);
		setMaxSpeed(0.166f);
		setFaction(2);
		setFactionRatings(-1, -1, 1);
		HealthController regen = new HealthController(this);
		armor = regen;
		addControllers(	new CameraController(this), 
						new KeyMovementController(this),
						new InputRotationController(this, 0.045f),
						new ScoreIncreaser(this),
						weapons = new WeaponController(this),
						regen,
						new HealthColorController(this){
							protected void setColor(float percent) {
								target.color().set(1f, percent, percent * percent);
							}});
		addDeathEffects(new DeathBurstEffect(Color.RED, 150, 0.025f),
						new PlayerDeathController());
		setGeometry(new float[][] {	{-2, -12},
									{16, 0}, 
									{-2, 12}, 
									{-16, 0}});
		defineKeyPoints(new float[][] {	{16, 0},
										{9, -6},
										{9, 6}});
		velocity.setPolar(random().nextFloat() * 6.283f, 0.01f + 
				random().nextFloat() * 0.025f);
		rotation.set(random().nextFloat() * 360);
	}

	/*************************************************************************
	 * Accesses the Player's WeaponController.
	 * 
	 * @return The device that manages the player's weapons.
	 *************************************************************************/
	public WeaponController weapons() { return weapons; }
}