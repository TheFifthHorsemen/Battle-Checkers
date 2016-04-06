package com.hexbit.battlecheckers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public static final int ROW_AMT = BattleCheckers.BOARDHEIGHT; //Is this bad?
	public static final int COL_AMT = BattleCheckers.BOARDWIDTH;
	
	GamePanel() {
		setPreferredSize(new Dimension(800,800)); //For now
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //Important for resizing.
		Graphics2D g2 = (Graphics2D) g;
		paintBoard(g2, ROW_AMT, COL_AMT);
	}
	
	private void paintBoard(Graphics2D g2, int RowsNum, int ColumnNum) {
		int tileWidth = getWidth()/ColumnNum, tileHeight = getHeight()/RowsNum;
		for(int i = 0; i < RowsNum; i++) {
			for(int j = 0; j < ColumnNum; j++) {
				if((i+j)%2==0)  //Alternating checkered tiles.
					g2.setColor(Color.BLACK);
				else
					g2.setColor(Color.WHITE);
				
				g2.fillRect(j*tileWidth, i*tileHeight, tileWidth, tileHeight);
			}
		}
	}
}
