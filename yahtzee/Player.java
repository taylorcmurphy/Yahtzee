package yahtzee;
/**
 * A player has a list of scores and a name
 * 
 * @author Taylor
 * @version	4.24.14
 */
public class Player {
	String name;
	int gameNum, roundNum = 0, finalScore, rollNum = 1;
	int[][] scores = new int[20][3];

	public Player(String name) {
		for (int r = 0; r < 3; r ++) {
			for (int i = 0; i < 20; i ++ ) {
				scores[i][r] = 0;
			}
		}
	}

	/**
	 * adds the score to the player's list of scores, and recalculates all of the other scores
	 * @param scoreType the category of score to be added
	 * @param scoreValue the value of score to be added
	 */
	public void addScore(int scoreType, int scoreValue) {
		if (scoreType > -1) {
			scores[scoreType][roundNum] = scoreValue;
			int upperSum = 0;
			for (int i = ScorePanel.ACES; i <= ScorePanel.SIXES; i ++) {
				upperSum += scores[i][roundNum];
			}
			scores[ScorePanel.UPPER_SUM][roundNum] = upperSum;
			
			if (upperSum > 63) { 
				scores[ScorePanel.UPPER_BONUS][roundNum] = 35; 																					// Adds bonus points if the total upper score is over 65
			}
			scores[ScorePanel.UPPER_TOTAL][roundNum] = upperSum + scores[ScorePanel.UPPER_BONUS][roundNum]; 									// Total upper score with bonus points
			scores[ScorePanel.UPPER_TOTAL_2][roundNum] = scores[ScorePanel.UPPER_TOTAL][roundNum]; 						
			
			for (int i = ScorePanel.THREE_OF_A_KIND; i < ScorePanel.CHANCE; i ++) {
				scores[ScorePanel.LOWER_TOTAL][roundNum] += scores[i][roundNum];					
			}
			scores[ScorePanel.UPPER_LOWER_SUM][roundNum] = scores[ScorePanel.LOWER_TOTAL][roundNum] + scores[ScorePanel.UPPER_TOTAL][roundNum];	// Sum score for this game
			scores[ScorePanel.ROUND_TOTAL][roundNum] = scores[ScorePanel.UPPER_LOWER_SUM][roundNum] * (roundNum + 1); 							// Total score for this game (the value of each game increases linearly)
		}
	}

	/**
	 * gives the score of the specific category
	 * @param scoreNum the category of the score
	 * @param roundNum the round number (0, 1, 2)
	 * @return the score of that category
	 */
	public int getScore(int scoreNum, int roundNum) {
		return scores[scoreNum][roundNum];
	}

	/**
	 * increases the round number
	 */
	public void addRound() {
		roundNum++;
	}
}
