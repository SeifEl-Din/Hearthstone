package view;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import model.cards.Card;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameView extends JFrame{
	private JPanel game; 
	private JTextArea field;
	public GameView() {
		this.setTitle("Hearthstone");
		this.setBounds(300, 100, 800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		game = new JPanel();  
		game.setLayout(new GridLayout(0, 3));
		game.setPreferredSize(new Dimension(600, getHeight()));
		add(game, BorderLayout.EAST);
		
		field = new JTextArea();
		field.setPreferredSize(new Dimension(200, getHeight()));
		this.add(field);
		this.setLocationRelativeTo(null);
		this.revalidate();
		this.repaint();
		

	}
	
	public JPanel getGame() {
		return game;
	}

	public JTextArea getField() {
		return field;
	}

	public static void main(String[]args) {
	}

	
}


