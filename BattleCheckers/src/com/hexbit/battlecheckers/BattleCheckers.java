package com.hexbit.battlecheckers;


public class BattleCheckers {
	
	//Game Constants
	
	final static int BLACK = 1;
	final static int WHITE = 2;
	
	final static int SHORTRANGE = 1;
	final static int MEDIUMRANGE = 2;
	final static int LONGRANGE = 3;
	
	final static int BOARDHEIGHT = 9;
	final static int BOARDWIDTH = 11;
	
	//Current turn (Uses color constants)
	public static int turn = 0;
	
	//Each color's score
	public static int blackScore = 0;
	public static int whiteScore = 0;
	
	//Array of checkers per color
	public static Checker[] whiteCheckers = initCheckers(true);
	public static Checker[] blackCheckers = initCheckers(false);
	
	//Int matrix for board. Defined as (Y, X) 
	public static int [][] board = new int[BOARDHEIGHT][BOARDWIDTH];
	
	/*		
	 * [y][x]
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 *	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 	0 0 0 0 0 0 0 0 0 0 0
	 * 
	 * 
	 */
	

	
	public static void main(String[] args) {
		
		PowerUp.spawnPowerUp(2);
		
	}
	
	/** 
	 * Changes the turn for the game
	 */
	public static void changeTurn() {
		int rand;
		
		//Flips turn
		if(turn == BLACK) {
			turn = WHITE;
		} else {
			turn = BLACK;
		}
		
		//Adds 0-3 PowerUps
		rand = (int)Math.random()*100+1;
		if(rand > 95){
			PowerUp.spawnPowerUp(3);
		} else if (rand > 80) {
			PowerUp.spawnPowerUp(2);
		} else if (rand > 60){
			PowerUp.spawnPowerUp(1);
		}
	
	}
	
	
	/**
	 * Creates array of checkers and moves to starting position
	 * @param isWhite
	 * @return
	 */
	public static Checker[] initCheckers(boolean isWhite)
	{
		Checker[] checkers = new Checker[12]; //Creates empty array
		
		//Sets Checker's starting position
		for(int i = 0; i<checkers.length; i++)
		{
			int x, y;
			if (i<5){
				y = i*2;
				x = ((isWhite) ? 0:BOARDWIDTH-1);
			} else if (i<9){
				y = (i%5)*2+1;
				x = ((isWhite) ? 1:BOARDWIDTH-2);
			} else {
				y = (i%9)*2+2;
				x = ((isWhite) ? 2:BOARDWIDTH-3);
			}
			
			//Adds checkers position to the board
			setValAtBoard(x, y, (i+1) + ((isWhite) ? 0:12));
			
			//Creates checker with random type
			checkers[i] = new Checker((isWhite) ? WHITE:BLACK, x, y, (int)(Math.random()*3)+1);
			
		}
		
		return checkers;
		
	}
	
	/**
	 * Returns true of the coordinate exists on the board
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean doesPositionExist(int x, int y){
		if ((x>-1 && x<BOARDWIDTH) && (y>-1 && y<BOARDHEIGHT))
			return true;
		return false;
	}
	
	
	
	/**
	 * Returns the checker that is at the given position
	 * @param x
	 * @param y
	 * @return
	 */
	public Checker getCheckerFromBoard(int x, int y){
		int checkerID = board[y][x];
		if (checkerID>12)
			return blackCheckers[checkerID-13];
		return whiteCheckers[checkerID-1];
		
	}
	
	/**
	 * Returns what is occupying given position (0 = empty, -1 = PowerUp, 1-12 = White Checker, 13-24 = Black Checker) 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getValAtBoard(int x, int y){
		return board[y][x];
	}
	
	/**
	 * Sets the value of the board at specified position
	 * @param x
	 * @param y
	 * @param newVal
	 */
	public static void setValAtBoard(int x, int y, int newVal){
		board[y][x] = newVal;
	}
	

}
