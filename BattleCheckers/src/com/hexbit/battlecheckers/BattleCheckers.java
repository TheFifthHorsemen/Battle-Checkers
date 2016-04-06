package com.hexbit.battlecheckers;


public class BattleCheckers {
	
	final static int BLACK = 1;
	final static int WHITE = 2;
	
	final static int SHORTRANGE = 1;
	final static int MEDIUMRANGE = 2;
	final static int LONGRANGE = 3;
	
	final static int BOARDHEIGHT = 9;
	final static int BOARDWIDTH = 11;
	
	public static int turn = 0;
	
	public static int blackScore = 0;
	public static int whiteScore = 0;
	
	public static Checker[] whiteCheckers = initCheckers(true);
	public static Checker[] blackCheckers = initCheckers(false);
	
	public static int [][] board = new int[BOARDHEIGHT][BOARDWIDTH];
	
	/**		
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
	
	public static void changeTurn() {
		int rand;
		
		if(turn == BLACK) {
			turn = WHITE;
		} else {
			turn = BLACK;
		}
		
		rand = (int)Math.random()*100+1;
		if(rand > 95){
			PowerUp.spawnPowerUp(3);
		} else if (rand > 80) {
			PowerUp.spawnPowerUp(2);
		} else if (rand > 60){
			PowerUp.spawnPowerUp(1);
		}
		
		
		
	}
	
	
	
	public static Checker[] initCheckers(boolean isWhite)
	{
		Checker[] checkers = new Checker[12];
		
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
			setValAtBoard(x, y, (i+1) + ((isWhite) ? 0:12));
			checkers[i] = new Checker((isWhite) ? WHITE:BLACK, x, y, (int)(Math.random()*3)+1);
			
		}
		
		return checkers;
		
	}
	
	public Checker getCheckerFromBoard(int x, int y){
		int checkerID = board[y][x];
		if (checkerID>12)
			return blackCheckers[checkerID-13];
		return whiteCheckers[checkerID-1];
		
	}
	
	public static int getValAtBoard(int x, int y){
		return board[y][x];
	}
	
	public static void setValAtBoard(int x, int y, int newVal){
		board[y][x] = newVal;
	}
	

}
