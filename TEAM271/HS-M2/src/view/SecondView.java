package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Controller;
import engine.Game;
import exceptions.FullHandException;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.GameView;


public class SecondView extends JFrame {

	private Hero FirstPlayer;
	private Hero SecondPlayer;
	private JPanel view;
	private JTextArea details;
	
	public SecondView(){
		this.setTitle("Hearthstone");
		this.setBounds(300, 100, 800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		view = new JPanel();
		view.setLayout(new GridBagLayout());
		view.setPreferredSize(new Dimension(600, getHeight()));
		this.add(view, BorderLayout.EAST);
		
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10,10,10,10);
		
		details = new JTextArea();
		details.setPreferredSize(new Dimension(200,view.getHeight()));
		details.setText("Please choose your heroes");
		details.setEditable(false);
		details.setVisible(true);
		
		this.setLocationRelativeTo(null);
		this.add(details);
	
		JButton a = new JButton("Mage");
		g.gridx=0;
		g.gridy=1;
		a.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 if (FirstPlayer == null) {
					 details.setText("First player is: Mage");
						try {
							FirstPlayer = new Mage();
							
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}

				 }
				  else if (SecondPlayer == null) {
					  details.setText(details.getText()+ "\n" + "Second player is: Mage");
					 
						try {
							SecondPlayer = new Mage();
							
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						
						Controller game = new Controller (FirstPlayer , SecondPlayer);
						dispose();
				 }
				 
			   }
			 }
		);
		view.add(a,g);
		JButton b = new JButton("Hunter");
		g.gridx=0;
		g.gridy=2;
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					 if (FirstPlayer == null) {
						 details.setText("First player is: Hunter");
							try {
								FirstPlayer = new Hunter();
								
							} catch (IOException | CloneNotSupportedException e1) {
								e1.printStackTrace();
							}

					 }
					 else if (SecondPlayer == null) {
						 details.setText(details.getText()+ "\n" + "Second player is: Hunter");
						
							try {
								SecondPlayer = new Hunter();
							} catch (IOException | CloneNotSupportedException e1) {
								e1.printStackTrace();
							}
							Controller game = new Controller (FirstPlayer , SecondPlayer);
							dispose();

					 }
				   }
				 }
			);
		view.add(b,g);
		JButton c = new JButton("Paladin");
		g.gridx=0;
		g.gridy=3;
		c.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 if (FirstPlayer == null) {
					 details.setText("First player is: Paladin");
						try {
							FirstPlayer = new Paladin();
							
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}

				 }
				 else if (SecondPlayer == null) {
					 details.setText(details.getText()+ "\n" + "Second player is: Paladin");
					
						
						try {
							SecondPlayer = new Paladin();
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						 Controller game = new Controller (FirstPlayer , SecondPlayer);
							dispose();

				 }
			   }
			 }
		);
		view.add(c,g);
		JButton d = new JButton("Priest");
		g.gridx=0;
		g.gridy=4;
			d.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 if (FirstPlayer == null) {
					 details.setText("First player is: Priest");
						try {
							FirstPlayer = new Priest();
							
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						

				 }
				 else if (SecondPlayer == null) {
					 details.setText(details.getText()+ "\n" + "Second player is: Priest");
						
						try {
							SecondPlayer = new Priest();
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						 Controller game = new Controller (FirstPlayer , SecondPlayer);
						 	dispose();
				 }
			   }
			 }
		);
		view.add(d,g);
		g.gridx=0;
		g.gridy=5;
		JButton e = new JButton("Warlock");
			e.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 if (FirstPlayer == null) {
					 details.setText("First player is: Warlock");
						
						try {
							FirstPlayer = new Warlock();
							
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}

				 }
				 else if (SecondPlayer == null) {
					 details.setText(details.getText()+ "\n" + "Second player is: Warlock");
						try {
							SecondPlayer = new Warlock();
						} catch (IOException | CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						 Controller game = new Controller (FirstPlayer , SecondPlayer);
							dispose();
				 }
			   }
	
			 }
		);

		view.add(e,g);
		view.revalidate();
		view.repaint();
	}
	
	public static void main(String[] args) {
	}
	

}




