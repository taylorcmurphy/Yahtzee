package yahtzee;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.JPanel;

/**
 * The dice panel displays all five of the dice, and highlights them if they are selected to be kept
 * @author Taylor
 * @version 4.24.14
 */
@SuppressWarnings("serial")
public class DicePanel extends JPanel implements MouseListener{
	public final int dieSize = 150;
	public final int dotSize = 30;
	public final int WIDTH = (dieSize + 20 )* 5 + 20;
	public final int HEIGHT = ScorePanel.HEIGHT;
	public final String instructionsLink = "http://www.hasbro.com/common/instruct/yahtzee.pdf";
	Yahtzee yahtzee;
	BufferedImage side1, side2, side3, side4, side5, side6; //each approx. 159x159
	//Michael's resizing stuff
	private int width;
	private int height;
	private Color backgroundColor = Color.black;
	private BufferedImage buffImage;
	private Graphics2D g2d;
	private ScorePanel scores;
	private boolean startScreen = true;
	
	public DicePanel(Yahtzee y, ScorePanel p) {
		super(true);		// creates a double buffered panel
		yahtzee = y;
		scores = p;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		width = WIDTH;	
		height = HEIGHT;	
		buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) buffImage.getGraphics();
	}

	/**
	 * paints all of the dice and highlights them if they have been selected
	 */
	public void paintComponent(Graphics g) {
		FontMetrics fm;
		String buttonText;
		int totalWidth;
		//fills the parent
		int panelWidth = getParent().getWidth();
		int panelHeight = getParent().getHeight();
		setSize(panelWidth, panelHeight);

		super.paintComponent(g);    //calls the normal JPanel.paintComponent(g)

		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, width, height);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(ScorePanel.FONT_BLUE);			// background color
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		int dieValue = 0;
		int x, y, center, corner, farCorner;
		g2d.setStroke(new BasicStroke(10));

		if (startScreen) {
			g2d.setColor(ScorePanel.BACKGROUND_BEIGE);
			g2d.setStroke(new BasicStroke(20));
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 75));		// makes the font bigger
			g2d.drawString("Welcome to Yahtzee!", 30, 70 );

			//draws instruction button
			y = 120;
			x = 20;
			g2d.drawRoundRect(x , y, WIDTH - 50, 300, 30, 30);		
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 120));		// makes the font bigger
			fm = g2d.getFontMetrics();
			buttonText = "Instructions";
			totalWidth = (fm.stringWidth(buttonText));
			x = (WIDTH - totalWidth) / 2 ;
			y = y + 50 + fm.getAscent() + 20;
			g2d.drawString(buttonText, x, y);

			//draws start button
			x = 20;
			y = 120 + 50 + 300;
			g2d.drawRoundRect(x, y, WIDTH - 50, 300 , 30, 30);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 200));		// makes the font bigger
			fm = g2d.getFontMetrics();
			buttonText = "Start";
			totalWidth = (fm.stringWidth(buttonText));
			x = (WIDTH - totalWidth) / 2 ;
			y = (WIDTH - totalWidth) / 2 ;
			y = y + 275 + fm.getAscent() + 20;
			g2d.drawString(buttonText, x, y);
		}
		else {
			//draws all the dice and their dots
			for (int i = 0; i < 5; i ++){		
				dieValue = yahtzee.getDieValue(i);
				center = (dieSize - dotSize) / 2;
				corner = (dotSize / 2);
				farCorner = dieSize - (3 * dotSize / 2);
				x = i * (dieSize + 20) + 20;
				y = 353 - (dieSize - dotSize) / 2;
				g2d.setColor(ScorePanel.BACKGROUND_BEIGE);		// die background color
				g2d.fillRoundRect(x, y, dieSize, dieSize, 30, 30);
				g2d.setColor(ScorePanel.FONT_BLUE);
				if (dieValue % 2 == 1) {	// odd #'s have center dot
					g2d.fillOval(x + center, y + center, dotSize, dotSize);
				}
				if (dieValue > 1) {			// 2-6 have top left and bottom right corner dots
					g2d.fillOval(x + corner, y + corner, dotSize, dotSize);
					g2d.fillOval(x + farCorner, y + farCorner, dotSize, dotSize);
				}
				if (dieValue > 3) {			// 4-6 have top right and bottom left corner dots
					g2d.fillOval(x + farCorner, y + corner, dotSize, dotSize);
					g2d.fillOval(x + corner, y + farCorner, dotSize, dotSize);
				}
				if (dieValue == 6) {		// 6 has center left and center right dots
					g2d.fillOval(x + corner, y + center, dotSize, dotSize);
					g2d.fillOval(x + farCorner, y + center, dotSize, dotSize);
				}
				// outlines the die if it has been selected to be kept
				if (!yahtzee.getDieRoll(i)) {				 
					g2d.setColor(ScorePanel.SCORE_RED);	// die hightlight color		
					g2d.drawRoundRect(x, y, dieSize, dieSize, 30, 30);
				}
			}

			//draws the Roll button and the insert score button
			g2d.setStroke(new BasicStroke(20));
			g2d.setColor(ScorePanel.BACKGROUND_BEIGE);
			y = 353 - (dieSize - dotSize) / 2 + dieSize + 10 + dieSize / 2;
			x = 20;
			g2d.drawRoundRect(x , y, WIDTH - 40, HEIGHT - y - 20, 30, 30);

			//draws "Roll" in the roll button
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 250));		// makes the font bigger
			fm = g2d.getFontMetrics();
			buttonText = "Roll " + yahtzee.getRollNum();
			totalWidth = (fm.stringWidth(buttonText));
			x = (WIDTH - totalWidth) / 2 ;
			y = (HEIGHT - y - 20 - fm.getHeight()) / 2 + fm.getAscent() + y;
			g2d.drawString(buttonText, x, y);

			//draws the enter score button
			g2d.drawRoundRect(20, 20, WIDTH - 40, 353 - (dieSize - dotSize) / 2 - dieSize - 10 + dieSize / 2, 30, 30);

			//draws "Add Score" in the score button
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 100));		// makes the font bigger
			fm = g2d.getFontMetrics();
			buttonText = "Add Score";
			totalWidth = (fm.stringWidth(buttonText));
			x = (WIDTH - totalWidth) / 2 ;
			y = (353 - (dieSize - dotSize) / 2 - dieSize - 10 + dieSize / 2 - fm.getHeight()) / 2 + fm.getAscent() + 20;
			g2d.drawString(buttonText, x, y);
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
	 * toggles whether the clicked die should be rolled
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//dice selection
		if (startScreen) {
			// instructions button
			if (y > 120 && y < 120 + 300 && x > 20 && x < 20 + WIDTH - 50) {
				try {
			        Desktop.getDesktop().browse(new URL(instructionsLink).toURI());
			    } catch (Exception ex) {
			        System.out.println("Check your internet connection");
			    }
			}
			// start button
			else if (y > 120 + 50 + 300 && y < 120 + 50 + 300 + 300 && x > 20 && x < 20 + WIDTH - 50) {
				x = 20;
				y = 120 + 50 + 300;
				g2d.drawRoundRect(x, y, WIDTH - 50, 300 , 30, 30);
				startScreen = false;
			}
		}
		else {
			if (y > (353 - (dieSize - dotSize) / 2) && y < (353 - (dieSize - dotSize) / 2 + dieSize + 10)) {
				if (x < 160) {
					yahtzee.selectDie(0);
				} else if (x > 160 && x < 320) {
					yahtzee.selectDie(1);
				} else if (x > 320 && x < 480) {
					yahtzee.selectDie(2);
				} else if (x > 480 && x < 640) {
					yahtzee.selectDie(3);
				} else if (x > 640 && x < 800) {
					yahtzee.selectDie(4);
				}
			}
			//roll button
			else if (y > 353 - (dieSize - dotSize) / 2 + dieSize + 10 + dieSize / 2 && y < HEIGHT - 20) {
				if (x > 20 && x < WIDTH - 40) {
					yahtzee.roll();
				}
			}
			//record score button
			else if (y > 20 && y < 353 - (dieSize - dotSize) / 2 - dieSize - 10 + dieSize / 2) {
				if (x > 20 && x < WIDTH - 40) {
					yahtzee.recordScore();
					scores.repaint();
				}
			}
		}
		repaint();
	}	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}
