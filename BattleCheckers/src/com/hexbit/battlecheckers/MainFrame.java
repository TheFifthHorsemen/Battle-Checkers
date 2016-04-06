package com.hexbit.battlecheckers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();  			//Sets default window options
		mf.setTitle("Battle Checkers v1.0");
		mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mf.setSize(800,800);						//Eventually need to plan out the game and layout stuff.
		mf.setVisible(true);
		mf.initialize();
	}
	
	private void initialize() {
		JMenuBar jmb = new JMenuBar();    //The bar.
		JMenu myItem = new JMenu("Game"); //The dropdown menus
		jmb.add(myItem);
		myItem = new JMenu("About");
		jmb.add(myItem);
		setJMenuBar(jmb);
		GamePanel g = new GamePanel();
		setLayout(new BorderLayout());//Arranges components on the borders.
		add(g, BorderLayout.EAST);    //If you resize this, it will move to the right side.
		pack();
	}

}
