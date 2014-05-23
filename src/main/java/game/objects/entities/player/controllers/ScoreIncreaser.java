package game.objects.entities.player.controllers;

import game.objects.controllers.TargetedController;
import game.objects.sprite.MatrixSprite;

/*****************************************************************************
 * ScoreIncreaser increases a Matrix's score at a constant rate, awarding 
 * points for continued survival.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ScoreIncreaser extends TargetedController<MatrixSprite>
{
	/**
	 * The value that keeps track of the time elapsed since the last score 
	 * increment.
	 **/
	private int scoreIncreaseTimer;
	
	/**
	 * The duration between each score increase.
	 **/
	private int scoreIncreaseInterval;
	
	/**
	 * The amount to increase the score by for each score increase.
	 **/
	private int scoreIncreaseAmount;

	/*************************************************************************
	 * Creates a ScoreIncreaser.
	 * 
	 * @param target
	 * 			  The MatrixSprite whose Matrix will have its score increased.
	 *************************************************************************/
	public ScoreIncreaser(MatrixSprite target)
	{
		super(target);
		scoreIncreaseInterval = 3333;
		scoreIncreaseAmount = 10;
	}

	/*************************************************************************
	 * Updates the ScoreIncreaser.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		scoreIncreaseTimer += delta;
		while(scoreIncreaseTimer >= scoreIncreaseInterval)
		{
			scoreIncreaseTimer -= scoreIncreaseInterval;
			target.matrix().addPoints(scoreIncreaseAmount);
		}
	}
}