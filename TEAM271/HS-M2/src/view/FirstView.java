package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

public class FirstView extends JFrame{
	private JPanel start;
	public FirstView() {
		this.setBounds(300, 100, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		start = new JPanel();  
		start.setLayout(new FlowLayout());
		start.setPreferredSize(new Dimension(600, getHeight()));
		this.add(start);
		JButton a = new JButton("Start Game");
		start.add(a);
		a.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 SecondView Game = new SecondView();
				 dispose();
			   }
			 }
		);
		this.setLocationRelativeTo(null);
		this.revalidate();
		this.repaint();
		this.setVisible(true);

}
	public static void main (String[] args) {
		new FirstView();
	}
}
