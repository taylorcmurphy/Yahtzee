package yahtzee;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The score panel displays the score card and the scores
 * @author Taylor
 * @version 4.24.14
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel implements MouseListener {	
	private static final int STROKE_SIZE = 5;
	public static final int HEIGHT = 848;
	public static final int WIDTH = 504;

	// Upper scores: 
	public static final int ACES = 0;				// Dice value * number of Dice (of that value)
	public static final int TWOS = 1;					
	public static final int THREES = 2;
	public static final int FOURS = 3;
	public static final int FIVES = 4;
	public static final int SIXES = 5;

	public static final int UPPER_SUM = 6;			// The sum of the upper scores
	public static final int UPPER_BONUS = 7;		// The bonus (35 points if upper sum is over 63)
	public static final int UPPER_TOTAL = 8;		// Upper score w/ bonus

	// Lower scores:
	public static final int THREE_OF_A_KIND = 9;	// Total of all five dice	
	public static final int FOUR_OF_A_KIND = 10;	// Total of all five dice 
	public static final int FULL_HOUSE = 11;		// 25 Points (three of one number and two of another)
	public static final int SMALL_STRAIGHT = 12;	// 30 Points (sequence of four numbers)
	public static final int LARGE_STRAIGHT = 13;	// 40 Points (sequence of five numbers)	
	public static final int YAHTZEE = 14;			// 50 Points (Five of a kind)
	public static final int CHANCE = 15;			// Total of all five dice

	public static final int LOWER_TOTAL = 16;		// Total of all lower scores
	public static final int UPPER_TOTAL_2 = 17;		// Total of all upper scores (again)
	public static final int UPPER_LOWER_SUM = 18;	// Sum of upper and lower scores
	public static final int ROUND_TOTAL = 19;		// Total round score

	//Colors:
	public static final Color BACKGROUND_BEIGE = new Color(237, 207, 181);
	public static final Color FONT_BLUE = new Color(41, 47, 105);
	public static final Color SCORE_BLUE = new Color(151, 149, 162);
	public static final Color SCORE_RED = new Color(214, 124, 134);

	private int width;
	private int height;
	private Color backgroundColor = Color.black;
	private BufferedImage buffImage;
	
	Yahtzee yahtzee;
	int gameNum, selectedScore;
	BufferedImage background;						// The score card in the background

	public ScorePanel(Yahtzee y) {
		super(true); // creates a double buffered panel
		yahtzee = y;
		selectedScore = -1;
		try {
			background = ImageIO.read(new File("src/yahtzee/Images/yahtzeeScoreCard.jpg"));
		} catch (IOException e) {
			System.out.println("Card File not Found");
		}
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
	}

	/**
	 * draws the card and all the scores on it
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//fills the parent
        int panelWidth = getParent().getWidth();
        int panelHeight = getParent().getHeight();
        setSize(panelWidth, panelHeight);

        super.paintComponent(g);    //calls the normal JPanel.paintComponent(g)
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
			
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int x = 0, y = 0 ; 	// the x and y coordinate of the score to be drawn
		int rawScore; 
		String score; 		// the score to be drawn
		g2d.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
		g2d.setColor(FONT_BLUE);
		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 30));
		for (int r = 0; r < 3; r ++) {
			switch(r) {
			case 0: x = 274;
			case 1: x = 350;
			case 2: x = 424;
			}
			for (int i = 0; i < 20; i++) {
				rawScore = yahtzee.getScore(i, r);
				if (rawScore != 0) {
					score = "" + rawScore;
					switch (i) {
					case ACES:  					y = 35;		break;
					case TWOS:  					y = 68; 	break;
					case THREES: 					y = 101; 	break;	
					case FOURS:  					y = 136; 	break;
					case FIVES: 					y = 168;	break;
					case SIXES: 					y = 204; 	break;
					case THREE_OF_A_KIND:	 		y = 384; 	break;
					case FOUR_OF_A_KIND: 			y = 418; 	break;	
					case FULL_HOUSE: 				y = 452; 	break;
					case SMALL_STRAIGHT:	 		y = 486; 	break;
					case LARGE_STRAIGHT:	 		y = 520;	break;
					case YAHTZEE: 					y = 554; 	break;
					case CHANCE: 					y = 589; 	break;
					case UPPER_SUM: 				y = 246;	break;
					case UPPER_BONUS: 				y = 279;	break;
					case UPPER_TOTAL: 				y = 314;	break;
					case LOWER_TOTAL: 				y = 625;	break;
					case UPPER_TOTAL_2: 			y = 660;	break;
					case UPPER_LOWER_SUM:			y = 695;	break;
					case ScorePanel.ROUND_TOTAL:	y = 770; 	break;		
					}
					g2d.drawString(score, x - 135, y + 30);
				}
			}
		}
		
		//draws a blue box around the selected score
		g2d.setStroke(new BasicStroke(STROKE_SIZE));
		g2d.setColor(SCORE_RED);
		switch(selectedScore) {
		case ACES:  			g2d.drawRect( 7, 35 , 193 - 7 - STROKE_SIZE, 68  - 35	); 	break;
		case TWOS:  			g2d.drawRect( 7, 68 , 193 - 7 - STROKE_SIZE, 101 - 68	); 	break;
		case THREES:  			g2d.drawRect( 7, 101, 193 - 7 - STROKE_SIZE, 136 - 101	); 	break;  
		case FOURS:  			g2d.drawRect( 7, 136, 193 - 7 - STROKE_SIZE, 168 - 136	);	break;  
		case FIVES: 			g2d.drawRect( 7, 168, 193 - 7 - STROKE_SIZE, 204 - 168	); 	break;
		case SIXES: 			g2d.drawRect( 7, 204, 193 - 7 - STROKE_SIZE, 237 - 204	); 	break;
		case THREE_OF_A_KIND: 	g2d.drawRect( 6, 384, 192 - 6 - STROKE_SIZE, 418 - 384	); 	break;
		case FOUR_OF_A_KIND: 	g2d.drawRect( 6, 418, 192 - 6 - STROKE_SIZE, 452 - 418	); 	break;
		case FULL_HOUSE: 		g2d.drawRect( 6, 452, 192 - 6 - STROKE_SIZE, 486 - 452	); 	break;
		case SMALL_STRAIGHT: 	g2d.drawRect( 6, 486, 192 - 6 - STROKE_SIZE, 520 - 486	); 	break;
		case LARGE_STRAIGHT: 	g2d.drawRect( 6, 520, 192 - 6 - STROKE_SIZE, 554 - 520	); 	break;
		case YAHTZEE:			g2d.drawRect( 6, 554, 192 - 6 - STROKE_SIZE, 589 - 554	); 	break;
		case CHANCE: 			g2d.drawRect( 6, 589, 192 - 6 - STROKE_SIZE, 624 - 589	); 	break;
		}
		
		//calculates the size of every part of the drawing
        double scale = Math.min((double)panelWidth / width, (double)panelHeight / height);
        int drawWidth = (int)Math.round(width * scale);
        int drawHeight = (int)Math.round(height * scale);
        int edgeWidth = (panelWidth - drawWidth) / 2;
        int edgeHeight = (panelHeight - drawHeight) / 2;

        //copies the image to the screen, scaled to fit and centered
        g.drawImage(buffImage, edgeWidth, edgeHeight, drawWidth, drawHeight, null);
	}

	/**
	 * selects the score based upon where the mouse is clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//upper scores
		if (x >= 7 && x <= 193) {
			if (y >= 35 && y <= 68) 		{	if ( selectedScore == ACES   ) 			{ selectedScore = -1; } else { selectedScore = ACES; 			}	}
			else if (y >= 68 && y <= 101) 	{	if ( selectedScore == TWOS   ) 			{ selectedScore = -1; } else { selectedScore = TWOS;			}	}
			else if (y >= 101 && y <= 136)	{	if ( selectedScore == THREES )			{ selectedScore = -1; } else { selectedScore = THREES;			}	}
			else if (y >= 136 && y <= 168)	{	if ( selectedScore == FOURS  ) 			{ selectedScore = -1; } else { selectedScore = FOURS;			}	}
			else if (y >= 168 && y <= 204) 	{	if ( selectedScore == FIVES  ) 			{ selectedScore = -1; } else { selectedScore = FIVES;			}	}
			else if (y >= 204 && y <= 237)	{	if ( selectedScore == SIXES  ) 			{ selectedScore = -1; } else { selectedScore = SIXES;			}	}	
		}	

		//lower scores
		if (x >= 6 && x <= 192) {
			if (y >= 384 && y <= 418)		{	if ( selectedScore == THREE_OF_A_KIND ) { selectedScore = -1; } else { selectedScore = THREE_OF_A_KIND;	}	}
			else if (y >= 418 && y <= 452)  {	if ( selectedScore == FOUR_OF_A_KIND  ) { selectedScore = -1; } else { selectedScore = FOUR_OF_A_KIND;	}	}
			else if (y >= 452 && y <= 486)  {	if ( selectedScore == FULL_HOUSE 	  ) { selectedScore = -1; }	else { selectedScore = FULL_HOUSE;		} 	}
			else if (y >= 486 && y <= 520)  {	if ( selectedScore == SMALL_STRAIGHT  )	{ selectedScore = -1; } else { selectedScore = SMALL_STRAIGHT;	} 	}
			else if (y >= 520 && y <= 554)  {	if ( selectedScore == LARGE_STRAIGHT  ) { selectedScore = -1; }	else { selectedScore = LARGE_STRAIGHT; 	}	}
			else if (y >= 554 && y <= 589)  {	if ( selectedScore == YAHTZEE 		  )	{ selectedScore = -1; }	else { selectedScore = YAHTZEE;			}	}
			else if (y >= 589 && y <= 624)  {	if ( selectedScore == CHANCE 		  )	{ selectedScore = -1; }	else { selectedScore = CHANCE;		 	}	}
		}
		yahtzee.updateSelectedScore(selectedScore);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
