package yahtzee;

/**
 * A Yahtzee game has a player, and the five dice
 * @author Taylor
 * @version 4.24.14
 */

public class Yahtzee {
	int [] dieValues = new int[5]; 			// Contains the face value of the die (1-6)
	boolean [] dieRolls = new boolean[5];	// Contains whether a die should be rolled (true = roll, false = do not roll)
	int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0, selectedScore, roundNum = 0;
	int insertedScoreCount = 0;	//number of scores a player has entered this round
	int numScores = 0; 			//number of score a player will have to enter this round
	Player player;

	public Yahtzee(Player p) {
		player = p;
		resetRoll();
		roll();
		player.rollNum = 1;
	}

	/**
	 * rolls the dice
	 */
	public void roll() {
		if (insertedScoreCount <= numScores && player.rollNum < 3) {
		
		for(int i = 0; i < 5; i ++) {
			if (dieRolls[i]) {
				dieValues[i] = Utility.randomInteger(1, 7);
			}
		}
		countFaces();
		player.rollNum ++;
		}
		else if (insertedScoreCount == numScores) {
				player.rollNum = 1;
				numScores ++;
			}
		}

	/**
	 * 
	 * @param dieNum the dice number 0-4 (left to right)
	 * @return whether that die should be rolled or not
	 */
	public boolean getDieRoll(int dieNum) {
		return dieRolls[dieNum];
	}

	/** 
	 *
	 * @return the player's current roll
	 */
	public int getRollNum() {
		return player.rollNum;
	}

	/**
	 * 
	 * @param dieNum the dice number  0-4 (left to right)
	 * @return the face value of that die
	 */
	public int getDieValue(int dieNum) {
		return dieValues[dieNum];
	}

	/**
	 * resets all the dice back to 1
	 */
	public void resetRoll() {
		for (int i = 0; i < 5; i ++) {
			dieValues[i] = Utility.randomInteger(1, 7);
			dieRolls[i] = true;
		}
		countFaces();
	}

	/**
	 * counts up all of the instances of each dice
	 */
	public void countFaces() {
		ones = 0; twos = 0; threes = 0;
		fours = 0; fives = 0; sixes = 0;
		int currentDie;
		for (int i = 0; i < 5; i ++) {
			currentDie = dieValues[i];
			switch (currentDie) {
			case 1: ones ++; 	break;
			case 2: twos ++; 	break;
			case 3: threes ++; 	break;
			case 4: fours ++; 	break;
			case 5: fives ++; 	break;
			case 6: sixes++;	break;
			}
		}
	}

	/**
	 * toggles between the die between selected and unselected
	 * @param dieNum dieNum the dice number  0-4 (left to right)
	 */
	public void selectDie(int dieNum) {
		if(dieRolls[dieNum]) {
			dieRolls[dieNum] = false;
		}
		else {
			dieRolls[dieNum] = true; 
		}
	}

	/**
	 * 
	 * @param scoreNum the category of the score
	 * @param roundNum the round selected
	 * @return the player's score in that category for the specified round
	 */
	public int getScore(int scoreNum, int roundNum) {
		return player.getScore(scoreNum, roundNum);
	}

	/**
	 * 
	 * @param scoreType the category of the score
	 * @return the value that the player could score in that category
	 */
	public int getScoreValue(int scoreType) {
		int score = 0;
		switch(scoreType) {																														
		case ScorePanel.ACES: 				score = ones;			break;
		case ScorePanel.TWOS: 				score = twos * 2;		break;
		case ScorePanel.THREES: 			score = threes * 3;		break;
		case ScorePanel.FOURS: 				score = fours * 4;		break;
		case ScorePanel.FIVES: 				score = fives * 5;		break;
		case ScorePanel.SIXES: 				score = sixes * 6;		break;
		case ScorePanel.THREE_OF_A_KIND:																										
			if (threeOfAKindCheck()) 	{	score = diceSum();	}	break;
		case ScorePanel.FOUR_OF_A_KIND:																											
			if (fourOfAKindCheck()) 	{ 	score = diceSum();	}	break;
		case ScorePanel.FULL_HOUSE:										
			if (fullHouseCheck()) 		{	score = 25;	}			break;
		case ScorePanel.SMALL_STRAIGHT:
			if (smallStraightCheck())	{	score = 30;	}			break;
		case ScorePanel.LARGE_STRAIGHT:
			if (largeStraightCheck()) 	{	score = 30; }			break; 
		case ScorePanel.YAHTZEE:
			if (yahtzeeCheck()) 		{	score = 50; }			break;
		case ScorePanel.CHANCE:				score = diceSum();		break;
		}
		return score;
	}

	/**
	 * records the players score for a specific score type
	 * @param p
	 * @param scoreType
	 */
	public void recordScore() { 
		player.addScore(selectedScore, getScoreValue(selectedScore));
		insertedScoreCount ++;
		if (insertedScoreCount == 13) {
			player.addRound();
			insertedScoreCount = 0;
			numScores = 0;
		}
		resetRoll();
	}

	/**
	 * 
	 * @return the sum of all the dice values
	 */
	public int diceSum() {
		return ones + twos * 2 + threes * 3 + fours * 4 + fives * 5 + sixes * 6;
	}

	/**
	 * checks to make sure that there is a three of a kind
	 * @return
	 */
	public boolean threeOfAKindCheck() { 
		if (ones >= 3 || twos >= 3 || threes >= 3 || fours >= 3 || fives >= 3 || sixes >= 3)
		{ return true; }
		return false;
	}

	/**
	 * checks to make sure that there is a four of a kind
	 * @return
	 */
	public boolean fourOfAKindCheck() {
		if (ones >= 4 || twos >= 4 || threes >= 4 || fours >= 4 || fives >= 4 || sixes >= 4) 
		{ return true; }
		return false;
	}

	/**
	 * checks to make sure that there is a full house
	 * @return
	 */
	public boolean fullHouseCheck() {
		if		((ones   == 3 && (twos == 2 || threes == 2 || fours  == 2 || fives == 2 || sixes == 2)) ||
				(twos 	 == 3 && (ones == 2 || threes == 2 || fours  == 2 || fives == 2 || sixes == 2)) ||
				(threes  == 3 && (ones == 2 || twos   == 2 || fours  == 2 || fives == 2 || sixes == 2)) ||
				(fours   == 3 && (ones == 2 || twos   == 2 || threes == 2 || fives == 2 || sixes == 2)) ||
				(fives   == 3 && (ones == 2 || twos   == 2 || threes == 2 || fours == 2 || sixes == 2)) ||
				(sixes   == 3 && (ones == 2 || twos   == 2 || threes == 2 || fours == 2 || fives == 2)))	{
			return true; }
		return false;
	}

	/**
	 * checks to make sure that there is a small straight
	 * @return
	 */
	public boolean smallStraightCheck() {
		if ((dieValues[0] == 1 && dieValues[1] == 2 && dieValues[2] == 3 && dieValues[3] == 4) 	||	// 1, 2, 3, 4, #
				(dieValues[1] == 1 && dieValues[2] == 2 && dieValues[3] == 3 && dieValues[4] == 4)	||		// #, 1, 2, 3, 4
				(dieValues[0] == 2 && dieValues[1] == 3 && dieValues[2] == 4 && dieValues[3] == 5)	||		// 2, 3, 4, 5, #
				(dieValues[1] == 2 && dieValues[2] == 3 && dieValues[3] == 4 && dieValues[4] == 5)	||		// #, 2, 3, 4, 5	
				(dieValues[0] == 3 && dieValues[1] == 4 && dieValues[2] == 5 && dieValues[3] == 6)	||		// 3, 4, 5, 6, #
				(dieValues[1] == 3 && dieValues[2] == 4 && dieValues[3] == 5 && dieValues[4] == 6)) {
			return true; }
		return false;
	}

	/**
	 * checks to make sure that there is a large straight
	 * @return
	 */
	public boolean largeStraightCheck() {
		if ((dieValues[0] == 1 && dieValues[1] == 2 && dieValues[2] == 3 && dieValues[3] == 4 && dieValues[4] == 5)	||	// 1, 2, 3, 4, 5		
			(dieValues[0] == 2 && dieValues[1] == 3 && dieValues[2] == 4 && dieValues[3] == 5 && dieValues[4] == 6)) {	// #, 3, 4, 5, 6
			return true;
		}
		return false;
	}

	/**
	 * checks for a YAHTZEE!
	 * @return
	 */
	public boolean yahtzeeCheck() {
		if (ones == 5 || twos == 5 || threes == 5 || fours == 5 || fives == 5 || sixes == 5)  { return true; }
		return false;
	}

	/**
	 * updates the selected score
	 * @param scoreType the new score type
	 */
	public void updateSelectedScore(int scoreType) {
		selectedScore = scoreType;
	}
}

