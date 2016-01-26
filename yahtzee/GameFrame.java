package yahtzee;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameFrame extends JFrame implements ActionListener {
	Player player;
	DicePanel dice;
	ScorePanel score;
	Yahtzee game;
	JButton rollButton;
	JPanel scorePane, diePane;
	Container GUI;
	public GameFrame () {	
	player = new Player("Test Player - Make me a Name from a JOptionPane");
	game = new Yahtzee(player);
	
	score = new ScorePanel(game);
	scorePane = new JPanel();
	scorePane.add(score);
	
	dice = new DicePanel(game, score);
	diePane = new JPanel();
	diePane.add(dice);
		
	GUI = getContentPane();
	GUI.setLayout(new GridBagLayout());
	GUI.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	GridBagConstraints c = new GridBagConstraints();
	
	c.fill = GridBagConstraints.HORIZONTAL;		
	c.gridx = 0;
	c.gridy = 0;
	GUI.add(diePane, c);	
	
	c.gridx = 1;
	c.gridy = 0;
	GUI.add(scorePane, c);

	pack(); 
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationByPlatform(true);
	setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Roll") {
			game.roll();
			repaint();
		}	
	}
}
