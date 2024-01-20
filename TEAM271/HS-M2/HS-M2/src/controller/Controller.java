package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.minions.MinionListener;
import model.cards.spells.AOESpell;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.DivineSpirit;
import model.cards.spells.FieldSpell;
import model.cards.spells.Flamestrike;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.HolyNova;
import model.cards.spells.KillCommand;
import model.cards.spells.LeechingSpell;
import model.cards.spells.LevelUp;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.MultiShot;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.SiphonSoul;
import model.cards.spells.Spell;
import model.cards.spells.TwistingNether;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class Controller extends JFrame implements GameListener , ActionListener{
	private JPanel game; 
	private JPanel game1;
	private JPanel game2;
	private JPanel field;
	private ArrayList<JButton> f1= new ArrayList<JButton>();
	private ArrayList<JButton> f2= new ArrayList<JButton>();
	private Game hearthstone;
	private Hero player1;
	private Hero player2;
	private ArrayList<JButton> hand = new ArrayList<JButton>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Minion SelectedMinion = null;
	private Minion SelectedTarget = null;
	private Spell SelectedSpell = null;

	public Controller(Hero p1,  Hero p2) {		
		try {
			hearthstone = new Game (p1 , p2);
			hearthstone.setListener(this);

		} catch (FullHandException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
		player1 = hearthstone.getCurrentHero();
		player2 = hearthstone.getOpponent();
		this.setTitle("Hearthstone");
		this.setBounds(300, 100, 800, 600);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);

		game= new JPanel(new GridBagLayout());
		this.add(game,BorderLayout.SOUTH);

		game1 = new JPanel(new GridBagLayout());
		this.add(game1,BorderLayout.EAST);

		field = new JPanel(new GridBagLayout());
		this.add(field,BorderLayout.CENTER);

		JTextArea field2 = new JTextArea("Player 2 field");
		field2.setVisible(true);
		field.add(field2);

		game2 = new JPanel(new GridBagLayout());
		this.add(game2,BorderLayout.WEST);

		JTextArea field1 = new JTextArea("Player 1 field");
		field1.setVisible(true);

		c.gridx=0;
		c.gridy=0;
		game2.add(field1,c);
		//field.setText("Current Hero:" + hearthstone.getCurrentHero().getName() );
		//field.setPreferredSize(new Dimension(200,game.getHeight()));
		//field.setVisible(true);
		//game.add(field);

		JButton b1=new JButton("CurrentHero details");
		c.gridx=1;
		c.gridy=0;
		game1.add(b1,c);


		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, information(hearthstone.getCurrentHero()) );
			}
		}
				);

		JButton b2=new JButton("Opponent details");
		c.gridx=1;
		c.gridy=1;
		game1.add(b2,c);
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent z) {

				JOptionPane.showMessageDialog(null, information(hearthstone.getOpponent())+"\n" +"Number of cards in hand:" +hearthstone.getOpponent().getHand().size());

			}

		}
				);
		JButton b3=new JButton("End Turn");
		c.gridx=1;
		c.gridy=10;
		game1.add(b3,c);
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent z) {
				SelectedMinion = null;
				SelectedSpell = null;
				SelectedTarget = null;
				try {
					hearthstone.endTurn();
				} catch (FullHandException | CloneNotSupportedException e) {
					if (hearthstone.getCurrentHero().getHand().size() == 10) {
						JOptionPane.showMessageDialog(null, "Your Hand is too full!!!");
					}
				}
				for (int i =0;i<hand.size();i++){
					hand.get(i).setVisible(false);
				}
				adder(hearthstone.getCurrentHero());
			}
		}


				);	
		JButton b4=new JButton("Use Hero Power");
		c.gridx=1;
		c.gridy=2;
		game1.add(b4,c);
		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent z) {
				if (hearthstone.getCurrentHero() instanceof Mage) {
					JPanel panel = new JPanel(new GridBagLayout());
					GridBagConstraints d = new GridBagConstraints();
					d.insets = new Insets(10,10,10,10);

					d.gridx = 0;
					d.gridy = 0;
					JButton b1 = new JButton("Hero");
					panel.add(b1,d);

					d.gridx = 1;
					d.gridy = 0;
					JButton b2 = new JButton("Minion");
					panel.add(b2,d);

					panel.setVisible(true);

					b1.setVisible(true);
					b2.setVisible(true);
					b1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								((Mage)hearthstone.getCurrentHero()).useHeroPower(hearthstone.getOpponent());
							} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
									| FullHandException | FullFieldException | CloneNotSupportedException e1) {
								if (hearthstone.getCurrentHero().isHeroPowerUsed()) {
									JOptionPane.showMessageDialog(null, "You have already used your power");

								}
								else if (hearthstone.getCurrentHero().getCurrentManaCrystals() < 2) {
									JOptionPane.showMessageDialog(null, "No enough mana!");

								}
							}
						}
						
					}
							);
					b2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (SelectedTarget == null) {
								JOptionPane.showMessageDialog(null, "You have to choose a target");
								return;
							}
							else if (SelectedTarget.getCurrentHP() <= 1 && !(SelectedTarget.isDivine())) {
								if (hearthstone.getCurrentHero() == player1) {
									for (int i =0;i<f2.size();i++) {
										if (SelectedTarget.getName() == f2.get(i).getText()) {
											f2.get(i).setVisible(false);
										}
									}
								}
								else {
									for (int i =0;i<f1.size();i++) {
										if (SelectedTarget.getName() == f1.get(i).getText()) {
											f1.get(i).setVisible(false);
										}
									}
								
							try {
								((Mage)hearthstone.getCurrentHero()).useHeroPower(SelectedTarget);
							} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
									| FullHandException | FullFieldException | CloneNotSupportedException e1) {
								if (hearthstone.getCurrentHero().isHeroPowerUsed()) {
									JOptionPane.showMessageDialog(null, "You have already used your power");

								}
								else if (hearthstone.getCurrentHero().getCurrentManaCrystals() < 2) {
									JOptionPane.showMessageDialog(null, "No enough mana!");

								}
							}
						}
							}
						}
					}
							);
					JOptionPane.showMessageDialog(null, panel , "Please choose how to act" , JOptionPane.PLAIN_MESSAGE);
				}
				else if (hearthstone.getCurrentHero() instanceof Hunter) {
					try {
						((Hunter)hearthstone.getCurrentHero()).useHeroPower();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e) {
						if (hearthstone.getCurrentHero().isHeroPowerUsed()) {
							JOptionPane.showMessageDialog(null, "You have already used your power");

						}
						else if (hearthstone.getCurrentHero().getCurrentManaCrystals() < 2) {
							JOptionPane.showMessageDialog(null, "No enough mana!");

						}
					}
				}
				else if (hearthstone.getCurrentHero() instanceof Priest) {
					JPanel panel = new JPanel(new GridBagLayout());
					GridBagConstraints d = new GridBagConstraints();
					d.insets = new Insets(10,10,10,10);

					d.gridx = 0;
					d.gridy = 0;
					JButton b1 = new JButton("Hero");
					panel.add(b1,d);

					d.gridx = 1;
					d.gridy = 0;
					JButton b2 = new JButton("Minion");
					panel.add(b2,d);

					panel.setVisible(true);

					b1.setVisible(true);
					b2.setVisible(true);
					b1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								((Priest)hearthstone.getCurrentHero()).useHeroPower(hearthstone.getOpponent());
							} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
									| FullHandException | FullFieldException | CloneNotSupportedException e1) {
								if (hearthstone.getCurrentHero().isHeroPowerUsed()) {
									JOptionPane.showMessageDialog(null, "You have already used your power");

								}
								else if (hearthstone.getCurrentHero().getCurrentManaCrystals() < 2) {
									JOptionPane.showMessageDialog(null, "No enough mana!");

								}
							}
						}
						
					}
							);
					b2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (SelectedMinion == null) {
								JOptionPane.showMessageDialog(null, "You have to choose a friendly minion");
								return;
							}
							else {
							try {
								((Priest)hearthstone.getCurrentHero()).useHeroPower(SelectedMinion);
							} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
									| FullHandException | FullFieldException | CloneNotSupportedException e1) {
								if (hearthstone.getCurrentHero().isHeroPowerUsed()) {
									JOptionPane.showMessageDialog(null, "You have already used your power");

								}
								else if (hearthstone.getCurrentHero().getCurrentManaCrystals() < 2) {
									JOptionPane.showMessageDialog(null, "No enough mana!");

								}
							}
						}
							}
						}
					
							);
					JOptionPane.showMessageDialog(null, panel , "Please choose how to act" , JOptionPane.PLAIN_MESSAGE);
				}
				else if (hearthstone.getCurrentHero() instanceof Warlock) {
					try {
						((Warlock)hearthstone.getCurrentHero()).useHeroPower();
						erase();
						adder(hearthstone.getCurrentHero());
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e) {
					}
					
				}
				else if (hearthstone.getCurrentHero() instanceof Paladin) {
					try {
						((Paladin)hearthstone.getCurrentHero()).useHeroPower();
						adder(hearthstone.getCurrentHero().getField().get(0));
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

				);	
		JButton b5=new JButton("Minion info");
		c.gridx=1;
		c.gridy=5;
		game1.add(b5,c);
		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent z) {
				if (SelectedMinion != null) {

					JOptionPane.showMessageDialog(null, information(SelectedMinion));
				}
				
				else {
					JOptionPane.showMessageDialog(null,"Please select a minion or a spell first");

				}

			}
		}

				);	
		JButton b6=new JButton("Play Minion");
		c.gridx=1;
		c.gridy=3;
		game1.add(b6,c);
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SelectedMinion == null) {
					JOptionPane.showMessageDialog(null, "Please select a minion to play");
				}
				else {

				try {
					hearthstone.getCurrentHero().playMinion(SelectedMinion);
					delete(SelectedMinion);
				} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e1) {
					if (hearthstone.getCurrentHero().getCurrentManaCrystals() < SelectedMinion.getManaCost()) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
					else if (hearthstone.getCurrentHero() == hearthstone.getOpponent()) {
						JOptionPane.showMessageDialog(null,"Not your turn!");
					}
					else if (hearthstone.getCurrentHero().getField().size() == 7) {
						JOptionPane.showMessageDialog(null, "Your field is full!");
					}
				}	
				SelectedMinion = null;
			}
			}
		}
				);
		JButton b7=new JButton("Attack Minion");
		c.gridx=1;
		c.gridy=8;
		game1.add(b7,c);
		b7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SelectedMinion == null) {
					JOptionPane.showMessageDialog(null, "Please select a minion to attack");
				}
				else if (SelectedTarget == null) {
					JOptionPane.showMessageDialog(null, "Please select a target from you opponent's field");

				}
				
				else {
					try {
						hearthstone.getCurrentHero().attackWithMinion(SelectedMinion, SelectedTarget);
					} catch (CannotAttackException | TauntBypassException | InvalidTargetException
							| NotSummonedException | NotYourTurnException e1) {
						for (int i =0;i<hearthstone.getOpponent().getField().size();i++) {
							if(hearthstone.getOpponent().getField().get(i).isTaunt() && hearthstone.getOpponent().getField().get(i) != SelectedTarget) {
								JOptionPane.showMessageDialog(null, "A taunt minion is in the way");
							}
						}
						if (hearthstone.getCurrentHero().getField().contains(SelectedTarget)) {
							JOptionPane.showMessageDialog(null, "You can't attack a friendly minion");
						}
					}
					if (SelectedMinion.getCurrentHP() == 0) {
						if (hearthstone.getCurrentHero() == player1) {
							for(int i=0;i<f1.size();i++) {
								if (f1.get(i).getText() == SelectedMinion.getName()) {
									f1.get(i).setVisible(false);
								}
							}
							player1.onMinionDeath(SelectedMinion);
						}
						else {
							for(int i=0;i<f2.size();i++) {
								if (f2.get(i).getText() == SelectedMinion.getName()) {
									f2.get(i).setVisible(false);
								}
							}
							player2.onMinionDeath(SelectedMinion);
						}
					}
					if (SelectedTarget.getCurrentHP() == 0) {
						if (hearthstone.getCurrentHero() == player1) {
							for(int i=0;i<f2.size();i++) {
								if (f2.get(i).getText() == SelectedTarget.getName()) {
									f2.get(i).setVisible(false);
								}
							}
							player2.onMinionDeath(SelectedTarget);
						}
						else {
							for(int i=0;i<f1.size();i++) {
								if (f1.get(i).getText() == SelectedTarget.getName()) {
									f1.get(i).setVisible(false);
								}
							}
							player1.onMinionDeath(SelectedTarget);
						}
					}

				}
			}
		}
				);

		JButton b8=new JButton("Attack Hero");
		c.gridx=1;
		c.gridy=9;
		game1.add(b8,c);
		b8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SelectedMinion == null) {
					JOptionPane.showMessageDialog(null, "Please select a minion to attack");
				}
				else {
				try {
					hearthstone.getCurrentHero().attackWithMinion(SelectedMinion, hearthstone.getOpponent());
				} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
						| InvalidTargetException e1) {
					for (int i =0;i<hearthstone.getOpponent().getField().size();i++) {
						if (hearthstone.getOpponent().getField().get(i).isTaunt() &&(hearthstone.getOpponent().getField().get(i) != SelectedTarget) ) {
							JOptionPane.showMessageDialog(null, "A taunt minion is in the way");
						}
					}
				}
				}
			}
		}
				);

		JButton b9 = new JButton ("Cast Spell");
		c.gridx=1;
		c.gridy=4;
		game1.add(b9,c);
		b9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SelectedSpell == null) {
					JOptionPane.showMessageDialog(null, "Please select a Spell to cast");
				}
				else if (SelectedSpell.getName() == "Curse of Weakness") {
					try {
						hearthstone.getCurrentHero().castSpell((AOESpell)SelectedSpell, hearthstone.getOpponent().getField());
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
				}
				else if (SelectedSpell.getName() == "Divine Spirit") {
					if (SelectedMinion == null) {
						JOptionPane.showMessageDialog(null, "Please choose a minion to perform action");
					}
					else {
						try {
							hearthstone.getCurrentHero().castSpell((MinionTargetSpell)SelectedSpell, SelectedMinion);
							delete(SelectedSpell);
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							JOptionPane.showMessageDialog(null, "No enough mana!");
						}
					}
				}
				else if (SelectedSpell.getName() == "Flamestrike") {
					if (hearthstone.getCurrentHero() == player1) {
						for (int i=0;i<player2.getField().size();i++) {
							if ((player2.getField().get(i).getCurrentHP() <= 4) && !(player2.getField().get(i).isDivine())) {
								for (int y=0;y<f2.size();y++) {
									if (f2.get(y).getText() == player2.getField().get(i).getName()) {
										f2.get(y).setVisible(false);
									}
								}
							}
						}
					}
					else {
						for (int i=0;i<player1.getField().size();i++) {
							if ((player1.getField().get(i).getCurrentHP() <= 4) && !(player1.getField().get(i).isDivine())) {
								for (int y=0;y<f1.size();y++) {
									if (f1.get(y).getText() == player1.getField().get(i).getName()) {
										f1.get(y).setVisible(false);
									}
								}
							}
						}
					}
					try {
						hearthstone.getCurrentHero().castSpell((AOESpell) SelectedSpell , hearthstone.getOpponent().getField());
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
				}
				else if (SelectedSpell.getName() =="Holy Nova") {
					if (hearthstone.getCurrentHero() == player1) {
						for (int i=0;i<player2.getField().size();i++) {
							if ((player2.getField().get(i).getCurrentHP() <= 2) && !(player2.getField().get(i).isDivine())) {
								for (int y=0;y<f2.size();y++) {
									if (f2.get(y).getText() == player2.getField().get(i).getName()) {
										f2.get(y).setVisible(false);
									}
								}
							}
						}
					}
					else {
						for (int i=0;i<player1.getField().size();i++) {
							if ((player1.getField().get(i).getCurrentHP() <= 2) && !(player1.getField().get(i).isDivine())) {
								for (int y=0;y<f1.size();y++) {
									if (f1.get(y).getText() == player1.getField().get(i).getName()) {
										f1.get(y).setVisible(false);
									}
								}
							}
						}
					}
					try {
						hearthstone.getCurrentHero().castSpell((AOESpell) SelectedSpell , hearthstone.getOpponent().getField());
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
				}
				else if (SelectedSpell.getName() == "Kill Command") {
					JPanel panel = new JPanel(new GridBagLayout());
					GridBagConstraints d = new GridBagConstraints();
					d.insets = new Insets(10,10,10,10);

					d.gridx = 0;
					d.gridy = 0;
					JButton b1 = new JButton("Hero");
					panel.add(b1,d);

					d.gridx = 1;
					d.gridy = 0;
					JButton b2 = new JButton("Minion");
					panel.add(b2,d);

					panel.setVisible(true);

					b1.setVisible(true);
					b2.setVisible(true);
					b1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								hearthstone.getCurrentHero().castSpell((HeroTargetSpell) SelectedSpell, hearthstone.getOpponent());
								delete(SelectedSpell);
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								JOptionPane.showMessageDialog(null, "No enough mana!");

							}
						}

					}
							);
					b2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (SelectedTarget.getCurrentHP() <= 5 && !(SelectedTarget.isDivine())) {
								if (hearthstone.getCurrentHero() == player1) {
									for (int i=0;i<player2.getField().size();i++) {
										if (player2.getField().get(i)== SelectedTarget){
											for (int y=0;y<f2.size();y++) {
												if (f2.get(y).getText() == player2.getField().get(i).getName()) {
													f2.get(y).setVisible(false);
												}
											}
										}
									}
								}
								else {
									for (int i=0;i<player1.getField().size();i++) {
										if (player1.getField().get(i) == SelectedTarget) {
											for (int y=0;y<f1.size();y++) {
												if (f1.get(y).getText() == player1.getField().get(i).getName()) {
													f1.get(y).setVisible(false);
												}
											}
										}
									}
								}
							}
							try {
								hearthstone.getCurrentHero().castSpell((MinionTargetSpell) SelectedSpell,SelectedTarget);
								delete(SelectedSpell);
							} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
								JOptionPane.showMessageDialog(null, "No enough mana!");
							}

						}

					}
							);
					JOptionPane.showMessageDialog(null, panel , "Please choose how to act" , JOptionPane.PLAIN_MESSAGE);
				}
				else if (SelectedSpell.getName() == "Level Up!") {

					try {
						hearthstone.getCurrentHero().castSpell((FieldSpell) SelectedSpell);
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (SelectedSpell.getName() == "Multi-Shot") {
					if (hearthstone.getCurrentHero() == player1) {
						if ((player2.getField().size() == 1) && (player2.getField().get(0).getCurrentHP() <= 3)) {
							f2.get(0).setVisible(false);
						}
					}
					else {
						if ((player1.getField().size() == 1) && (player1.getField().get(0).getCurrentHP() <= 3)) {
							f1.get(0).setVisible(false);
						}

					}
					try {
						hearthstone.getCurrentHero().castSpell((AOESpell)SelectedSpell , hearthstone.getOpponent().getField());
						delete(SelectedSpell);

					} catch (NotYourTurnException | NotEnoughManaException e1) {
					}
					if (hearthstone.getCurrentHero() == player1) {
						for (int i=0;i<f2.size();i++) {
							boolean found = false;
							int y =0;
							while (y < player2.getField().size()) {
								if (player2.getField().get(y).getName() != f2.get(i).getText()) {
									y++;
								}
								else {
									found = true;
								}
							}
							if (found == false) {
								f2.get(i).setVisible(false);
							}
						}
					}
					else {
						for (int i=0;i<f1.size();i++) {
							boolean found = false;
							int y =0;
							while (y < player1.getField().size()) {
								if (player1.getField().get(y).getName() != f1.get(i).getText()) {
									y++;
								}
								else {
									found = true;
								}
							}
							if (found == false) {
								f1.get(i).setVisible(false);
							}
						}
					}
				}
				else if (SelectedSpell.getName() == "Polymorph") {
					if (SelectedTarget == null) {
						JOptionPane.showMessageDialog(null, "Please select a target");
					}
					else {
						try {
							hearthstone.getCurrentHero().castSpell((MinionTargetSpell)SelectedSpell, SelectedTarget);
							delete(SelectedSpell);
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							JOptionPane.showMessageDialog(null, "No enough mana!");

						}
					}
				}
				else if (SelectedSpell.getName() == "Pyroblast") {
					JPanel panel = new JPanel(new GridBagLayout());
					GridBagConstraints d = new GridBagConstraints();
					d.insets = new Insets(10,10,10,10);

					d.gridx = 0;
					d.gridy = 0;
					JButton b1 = new JButton("Hero");
					panel.add(b1,d);

					d.gridx = 1;
					d.gridy = 0;
					JButton b2 = new JButton("Minion");
					panel.add(b2,d);

					panel.setVisible(true);

					b1.setVisible(true);
					b2.setVisible(true);
					b1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								hearthstone.getCurrentHero().castSpell((HeroTargetSpell) SelectedSpell, hearthstone.getOpponent());
								delete(SelectedSpell);
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								JOptionPane.showMessageDialog(null, "No enough mana!");

							}
						}

					}
							);
					b2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (SelectedTarget == null) {
								JOptionPane.showMessageDialog(null, "Please select a target");
							}
							else{
								if (SelectedTarget.getCurrentHP() <= 10) {
									if (hearthstone.getCurrentHero() == player1) {
										for (int i =0;i<f2.size();i++) {
											if (f2.get(i).getText() == SelectedTarget.getName()) {
												f2.get(i).setVisible(false);
											}
										}
									}
									else {
										for (int i =0;i<f1.size();i++) {
											if (f1.get(i).getText() == SelectedTarget.getName()) {
												f1.get(i).setVisible(false);
											}
										}

									}
								}
								try {
									hearthstone.getCurrentHero().castSpell((MinionTargetSpell)SelectedSpell, SelectedTarget);
								} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
									JOptionPane.showMessageDialog(null, "No enough mana!");
								}
							}
						}
					}
							);
					JOptionPane.showMessageDialog(null, panel , "Please choose how to act" , JOptionPane.PLAIN_MESSAGE);
				}

				else if (SelectedSpell.getName() == "Seal of Champions") {
					if (SelectedMinion == null) {
						JOptionPane.showMessageDialog(null, "Please select a minion");
					}
					else {
					try {
						hearthstone.getCurrentHero().castSpell((MinionTargetSpell)SelectedSpell, SelectedMinion);
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
				}
				}
				else if (SelectedSpell.getName() == "Shadow Word: Death") {
					if (SelectedTarget == null) {
						JOptionPane.showMessageDialog(null, "Please select a target");
						return;
					}
					else if (SelectedTarget.getAttack() >= 5) {
						if (hearthstone.getCurrentHero() == player1) {
							for (int i =0;i<f2.size();i++) {
								if (SelectedTarget.getName() == f2.get(i).getText()) {
									f2.get(i).setVisible(false);
								}
							}
						}
						else {
							for (int i =0;i<f1.size();i++) {
								if (SelectedTarget.getName() == f1.get(i).getText()) {
									f1.get(i).setVisible(false);
								}
							}
						}
					}
					try {
						hearthstone.getCurrentHero().castSpell((MinionTargetSpell)SelectedSpell, SelectedTarget);
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");

					}
				}
				else if (SelectedSpell.getName() == "Siphon Soul") {
					if (SelectedTarget == null) {
						JOptionPane.showMessageDialog(null, "Please select a target");
						return;
					}
					else if (hearthstone.getCurrentHero() == player1) {
						for (int i =0;i<f2.size();i++) {
							if (SelectedTarget.getName() == f2.get(i).getText()) {
								f2.get(i).setVisible(false);
							}
						}
					}
					else {
						for (int i =0;i<f1.size();i++) {
							if (SelectedTarget.getName() == f1.get(i).getText()) {
								f1.get(i).setVisible(false);
							}
						}
					}
					try {
						hearthstone.getCurrentHero().castSpell((LeechingSpell)SelectedSpell, SelectedTarget);
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
				}
				else if (SelectedSpell.getName() == "Twisting Nether") {
					
					for(int i=0;i<f1.size();i++) {
						f1.get(i).setVisible(false);
					}
					for(int i=0;i<f2.size();i++) {
						f2.get(i).setVisible(false);
					}
					try {
						hearthstone.getCurrentHero().castSpell((AOESpell)SelectedSpell, hearthstone.getOpponent().getField());
						delete(SelectedSpell);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, "No enough mana!");
					}
				}
			}

		}
				);
		JButton b10=new JButton("Target info");
		c.gridx=1;
		c.gridy=7;
		game1.add(b10,c);
		b10.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (SelectedTarget == null) {
					JOptionPane.showMessageDialog(null,"Please select a target");

				}
				else {
					JOptionPane.showMessageDialog(null,information(SelectedTarget));
				}
			}
		}
				);
	
		JButton b12=new JButton("Spell Info");
		c.gridx=1;
		c.gridy=6;
		game1.add(b12,c);
		b12.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent z) {
				if (SelectedSpell != null) {

					JOptionPane.showMessageDialog(null, information(SelectedSpell));
				}
				else {
					JOptionPane.showMessageDialog(null,"Please select a spell");

				}

			}
		}

				);	
		adder(hearthstone.getCurrentHero());
		this.setLocationRelativeTo(null);
		this.revalidate();
		this.repaint();
	}

	public JPanel getGame() {
		return game;
	}

	public JPanel getField() {
		return field;
	}
	public String writer(Hero p) {
		if (p instanceof Mage) {
			return "Name: Jaina Proudmoore";
		}
		else if (p instanceof Hunter) {
			return "Name: Rexxar";
		}
		else if (p instanceof Paladin) {
			return "Name: Uther Lightbringer";
		}
		else if (p instanceof Priest) {
			return "Name: Anduin Wrynn";
		}
		else if (p instanceof Warlock)
			return "Name: Gul'dan";
		else 
			return ("Unknown");
	}
	public void erase(Minion m) {
		if (player1.getField().contains(m)) {
			for (int i =0;i<f1.size();i++) {
				if (f1.get(i).getActionCommand() == m.getName()) {
					f1.get(i).setVisible(false);
					f1.remove(i);
					return;
				}
			}
		}
		else {
			for (int i =0;i<f2.size();i++) {
				if (f2.get(i).getActionCommand() == m.getName()) {
					f2.get(i).setVisible(false);
					f2.remove(i);
					return;
				}
			}
		}
	}
	public boolean containsTaunt() {
		for (int i =0;i<hearthstone.getOpponent().getField().size();i++) {
			if (hearthstone.getOpponent().getField().get(i).isTaunt() &&(hearthstone.getOpponent().getField().get(i) != SelectedTarget) ) {
				return true;
			}
		}
		return false;
	}
	public String information(Hero p) {
		String s = "";
		String name = p.getName();
		int c = p.getCurrentManaCrystals();
		int t = p.getTotalManaCrystals();
		int d = p.getDeck().size();
		int e = p.getCurrentHP();
		s = "Name:"+ name + "\n" + "CurrentHP:" + e + "\n" + "CurrentMana:"+ c + "\n" + "TotalMana:"+ t + "\n" +"Size of deck:"+ d;
		return s;
	}
	public String information (Spell s) {
		String z = "";
		String name = s.getName();
		String rarity = s.getRarity().toString();
		int ManaCost = s.getManaCost();
		z ="Name:"+ name+"\n"+ "Rarity:"+ rarity +"\n" + "ManaCost:"+ ManaCost;
		return z;
	}
	public String information (Minion m) {
		String s = "";
		String name = m.getName();
		int c = m.getManaCost();
		String d = m.getRarity().toString();
		int e = m.getAttack();
		int f = m.getCurrentHP();
		s = "Name:"+ name + "\n" + "ManaCost:"+ c + "\n" + "Rarity:"+ d + "\n" +"Attack:"+ e +"\n"+ "CurrentHP:"+ f;

		if (m.isTaunt()) {
			String g = "Taunt";
			s = s + "\n" + g;
		}
		if (m.isDivine()) {
			String h = "Divine";
			s = s + "\n" + h;

		}
		if(!m.isSleeping()) {
			String i = "Charge";
			s = s + "\n" + i;
		}

		return s;
	}
	
	public void copy (Hero l) {
		cards.clear();
		for (int i = 0 ; i<l.getHand().size() ; i++) {
			cards.add(l.getHand().get(i));
		}
	}
	public void erase(){
		for(int i =0;i<hand.size();i++) {
			hand.get(i).setVisible(false);
		}
	}
	public void adder(Minion m) {
		JButton b = new JButton (m.getName());
		b.setPreferredSize(new Dimension(140,50));
		b.setVisible(false);
		if (hearthstone.getCurrentHero() == player1) {
			game2.add(b);
			f1.add(b);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (hearthstone.getCurrentHero() == player1) {
						SelectedMinion = m;
					}
					else {
						SelectedTarget = m;
					}
				}

			}
					);
		}
		else {
			f2.add(b);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (hearthstone.getCurrentHero() == player2) {
						SelectedMinion = m;
					}
					else {
						SelectedTarget = m;
					}
				}

			}
					);
			field.add(b);
		}
		b.setVisible(true);
	}
	public void adder(Hero p) {
		copy(p);
		for(int i =0; i<cards.size();i++) {
			JButton jb = new JButton (hearthstone.getCurrentHero().getHand().get(i).getName());
			jb.setPreferredSize(new Dimension(140,50));
			jb.addActionListener(this);
			hand.add(jb);
		}
		for (int y =0;y<hand.size();y++) {
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			c.gridx=1 + y;
			c.gridy=1;
			game.add(hand.get(y),c);
		}
	}

	public void delete (Minion m) {
		JButton b = null;
		for (int i = 0;i<hand.size();i++) {
			if (m.getName() == hand.get(i).getText()) {
				hand.get(i).setVisible(false);
				b = hand.remove(i);
			}
		}
		if (hearthstone.getCurrentHero() == player1) {
			f1.add(b);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (hearthstone.getCurrentHero() == player1) {
						SelectedMinion = m;
					}
					else {
						SelectedTarget = m;
					}
				}

			}
					);
			game2.add(b);
		}
		else {
			f2.add(b);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (hearthstone.getCurrentHero() == player2) {
						SelectedMinion = m;
					}
					else {
						SelectedTarget = m;
					}
				}

			}
					);
			field.add(b);
		}
		b.setVisible(true);
	}
	public void delete(Spell s) {
		for (int i = 0;i<hand.size();i++) {
			if (s.getName() == hand.get(i).getText()) {
				hand.get(i).setVisible(false);
				hand.remove(i);
			}
		}
	}

	public static void main(String[]args) throws IOException, CloneNotSupportedException {
		
	}
	@Override
	public void onGameOver() {
		if (player1.getCurrentHP() <= 0) {
			JOptionPane.showMessageDialog(null,"Player2 is the winner");
		}
		else {
			JOptionPane.showMessageDialog(null,"Player1 is the winner");
		}
		this.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) (e.getSource());
		for(int i=0; i<cards.size(); i++) {
			if (b.getActionCommand().equals(cards.get(i).getName())){
				if (cards.get(i) instanceof Minion) {
					SelectedSpell = null;
					SelectedMinion = (Minion) cards.get(i);
				}
				else {
					SelectedMinion = null;
					SelectedSpell = (Spell) cards.get(i);
				}
			}
		}


	}
}






