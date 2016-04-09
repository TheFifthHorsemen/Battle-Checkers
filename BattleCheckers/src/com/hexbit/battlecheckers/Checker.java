package com.hexbit.battlecheckers;

import java.util.ArrayList;

public class Checker {

	private int color;		//Checker's color (Black or White)
	private int x, y;		//Checker's grid coordinates
	private int role;		//Checker's class (Long range, Medium range, Short range)
	private boolean isKing;	//If the checker is a king
	private int powerUp;	//Checker's current power up
	
	// A list of all possible jumps for the currently selected checker. Modified by .getPossibleJumps
	public static ArrayList<int[]> jumpList = new ArrayList<int[]>(); 
	public static ArrayList<int[]> specialJumps = new ArrayList<int[]>();
	
	/**
	 * Class definition for Checker
	 * @param Color
	 * @param X
	 * @param Y
	 * @param Role
	 * @param IsKing
	 * @param PowerUp
	 */
	public Checker(int Color, int X, int Y, int Role, boolean IsKing, int PowerUp) {
		this.color = Color;
		this.x = X;
		this.y = Y;
		this.role = Role;
		this.isKing = IsKing;
		this.powerUp = PowerUp;
	}
	
	/**
	 * Used for creating checker, creates a checker that isn't a king and has no power up 
	 * @param Color
	 * @param X
	 * @param Y
	 * @param Role
	 * 
	 * @see Checker
	 */
	public Checker(int Color, int X, int Y, int Role){
		this(Color, X, Y, Role, false, PowerUp.NONE);
	}
	
	/**
	 * Function called when the checker has been jumped and needs to be checked if it will be removed
	 */
	public void jump(){
		
		
		if (powerUp != PowerUp.EXTRALIFE){	//Checks to see if checker has power up ExtraLife
			// If it doesn't have extra life it increase score of the appropriate team and moves the checker to off the screen
			
			BattleCheckers.setValAtBoard(x, y, 0);	//Changes the grid value of the checker to empty
			
			if (color == BattleCheckers.WHITE)
				BattleCheckers.blackScore++;
			else 
				BattleCheckers.whiteScore++;
			
			//Moves checker to (-1,-1) or off screen
			x = -1;
			y = -1;
							
		} else { 	// If it does it gets rid of the power up but doesn't remove the checker)
			powerUp = PowerUp.NONE;
		}		
	}
	
	/**
	 * Function called when user wants to use the selected checkers powerUp
	 */
	public void activatePowerUp(){
		switch (powerUp) {
			/*PowerUp Jump - Adds jump as if it jumped over a checker when it didn't
			 * 
			 * 	X 0 0 0 X
			 *  0 0 0 0 0
			 *  0 0 @ 0 0
			 *  0 0 0 0 0
			 *  X 0 0 0 X
			 */
			case PowerUp.JUMP:	
				jumpList.clear();
								
				if (BattleCheckers.doesPositionExist(x-2, y-2) && (color == BattleCheckers.BLACK || isKing))
					addJump(x-2, y-2, true, false);
					
				if (BattleCheckers.doesPositionExist(x-2, y+2) && (color == BattleCheckers.BLACK || isKing))
					addJump(x-2, y+2, true, false);
								
				if (BattleCheckers.doesPositionExist(x+2, y+2)&& (color == BattleCheckers.WHITE || isKing))
					addJump(x+2, y+2, true, false);
						
				if (BattleCheckers.doesPositionExist(x+2, y-2) && (color == BattleCheckers.WHITE || isKing))
					addJump(x+2, y-2, true, false);
								
				break;
							
			/* PowerUp Special - Adds special jump according to the checker's Role
			 * 
			 */
			case PowerUp.SPECIAL:	
				switch (role) {
				
					/* Attacks all checkers in a 1 tile radius
					 * 	X X X
					 *  X @ X
					 *  X X X 
					 */
					case BattleCheckers.SHORTRANGE:
						
						
						jumpList.clear();
						
						/*	Adds attack spots
						 * 	X 0 X
						 *  X 0 X
						 *  X 0 X
						 */
						for (int i = -1; i<2; i+=2) {
							for (int j = -1; i<2; i++){
								
								
								if (BattleCheckers.doesPositionExist(x+i, y+j)){
									 
									 int[] pass = {x+i, y+j};
									specialJumps.add(pass);
								}
							}
						}
						
						//Adds the remaining two attack spots
						if (BattleCheckers.doesPositionExist(x, y+1))
						{
							int[] pass = {x, y+1};
							specialJumps.add(pass);
						}
						
						if (BattleCheckers.doesPositionExist(x, y-1))
						{
							int[] pass = {x, y-1};
							specialJumps.add(pass);
						}
							
						
						break;
						
					case BattleCheckers.MEDIUMRANGE:
						/*
						 * 	0 0 X 0 0
						 *  0 0 X 0 0
						 *  X X @ X X
						 *  0 0 X 0 0 
						 *  0 0 X 0 0
						 *  
						 *  Adds in order: South North West East
						 *  
						 *  DOES NOT CHECK IF 
						 */
						
						
						for (int i = -2; i<3; i++)
							if (i != 0)
								if (BattleCheckers.doesPositionExist(x+i, y))
								{
									int[] pass = {x+i, y};
									specialJumps.add(pass);
								} else {
									int[] pass = {-1, -1};
									specialJumps.add(pass);
								}
									
						
						for (int i = -2; i<3; i++)
							if (i != 0)
								if (BattleCheckers.doesPositionExist(x, y+i))
								{
									int[] pass = {x, y+i};
									specialJumps.add(pass);
								} else {
									int[] pass = {-1, -1};
									specialJumps.add(pass);
								}
									
						
						break;
						
					case BattleCheckers.LONGRANGE:	//Adds all enemy checkers to possible attacks
						boolean isBlack = (color == BattleCheckers.BLACK);
						//Loops through opposite color array of checkers to the current checker
						for (Checker C : (isBlack ? BattleCheckers.whiteCheckers : BattleCheckers.blackCheckers)){
							//Selects the checkers in line or in front of the checker
							if ((isBlack && C.getX()<=x) || (!isBlack && C.getX()>=x) || isKing)
							{
								int[] pass = {C.getX(), C.getY()};
								specialJumps.add(pass);
							}
									
						}
						break;
					}
						
				break;
									
			case PowerUp.STEALTH:  
								
				moveInOutStealth();
				
				break;
						
			case PowerUp.INSTEALTH:
						
				moveInOutStealth();
				
				break;
								
			}
		
	}
	
	/**
	 * Movements for getting in out of stealth
	 */
	private void moveInOutStealth(){
		
		/*
		 * 0 X 0
		 * X @ X
		 * 0 X 0
		 */ 
		jumpList.clear();
		
		if (BattleCheckers.doesPositionExist(x-1, y) && (color == BattleCheckers.BLACK || isKing))
			addJump(x-1, y, true, false);
		
		if (BattleCheckers.doesPositionExist(x+1, y)&& (color == BattleCheckers.WHITE || isKing))
			addJump(x+1, y, true, false);
		
		if (BattleCheckers.doesPositionExist(x, y+1))
			addJump(x, y+1, true, false);
						
		if (BattleCheckers.doesPositionExist(x, y-1))
			addJump(x, y-1, true, false);
	}
	
	/**
	 * Adds a possible jump position to jumpList. Called by getPossibleJumps
	 * @param X
	 * @param Y
	 * @param calledFromJump
	 * @param isDoubleJump
	 */
	private void addJump(int X, int Y, boolean calledFromJump, boolean isDoubleJump)
	{
		int tileValue = BattleCheckers.getValAtBoard(X, Y);	//Grabs the value of the tile at the prescribed position from the board
		
		//If the tile is empty and the function is not searching for possible double jumps it add the tile to list of possible jumps
		if (tileValue == 0 && !isDoubleJump) {
			int[] pass = {Y, X};
			jumpList.add(pass);
		}
		else if (!calledFromJump) //Used to see if its possible to jump a checker
		{
			if (tileValue < 14 && color == BattleCheckers.BLACK){
				getPossibleJumps(true, false);
			} else if (tileValue > 13 && color == BattleCheckers.WHITE){
				getPossibleJumps(true, false);
			}
		}
	}
	
	/**
	 * Moves checker to a new position, checking if a jump was made in the process
	 * @param newX
	 * @param newY
	 */
	public void setPos(int newX, int newY)
	{
		int oldX = x;				//Starting coordinates
		int oldY = y;
		int jumpedCheckerID, index;	//Values defined later on
		
		int checkerID = BattleCheckers.getValAtBoard(oldX, oldY);	//checkerID of the current checker
		
		BattleCheckers.setValAtBoard(oldX, oldY, 0);	//Changes the checkers old position on grid to empty
		
		//Checks if the diagonal moved is greater than sqrt of 2, meaning the checker has jumped another checker
		if(Math.sqrt(Math.pow((oldX-newX), 2)+ Math.pow((oldY-newY), 2)) > Math.sqrt(2) )	
		{		
			jumpedCheckerID = BattleCheckers.getValAtBoard((oldX + newX)/2, (oldY+newY)/2); //Determines the ID of the checker that was jumped
			
			if (jumpedCheckerID>13){  //If checker is black
				BattleCheckers.blackCheckers[jumpedCheckerID-14].jump(); //Removes checker
				this.getPossibleJumps(false, true); //Checks for double jump possibilities 
			} else if (jumpedCheckerID>0) { //If checker is white
				BattleCheckers.whiteCheckers[jumpedCheckerID-1].jump();
				this.getPossibleJumps(false, true);
			}
			
		
		}
		
		//Determines if there is a power up at checker's new coordinates
		if(BattleCheckers.getValAtBoard(newX, newY)==-1) {
			
			index = PowerUp.getPowerUpIndex(newX, newY);//Finds the index of the power up from power up array
			
			if (index>=0){ //If power up exists in list
				powerUp = (PowerUp.powerUpList.get(index).getType()); //Gives power up to checker
				PowerUp.removePowerUp(index); //Removes power up from list of power ups
			}
			
		}
		
		BattleCheckers.setValAtBoard(newX, newY, checkerID); //Sets the checkers new position on board to checker ID
		
	}
	
	/**
	 * Used to find if a checker can move to a tile
	 * @param calledFromJump
	 * @param isDoubleJump
	 */
	public void getPossibleJumps(boolean calledFromJump, boolean isDoubleJump){
		
		//Clears list if not finding tiles from jumping checkers
		if (!calledFromJump)
			jumpList.clear();
		
		//Checks if positions exist
		
		if (BattleCheckers.doesPositionExist(x-1, y-1) && (color == BattleCheckers.BLACK || isKing))
			addJump(x-1, y-1, calledFromJump, isDoubleJump);
		
		if (BattleCheckers.doesPositionExist(x-1, y+1) && (color == BattleCheckers.BLACK || isKing))
			addJump(x-1, y+1, calledFromJump, isDoubleJump);
		
		if (BattleCheckers.doesPositionExist(x+1, y+1)&& (color == BattleCheckers.WHITE || isKing))
			addJump(x+1, y+1, calledFromJump, isDoubleJump);
		
		if (BattleCheckers.doesPositionExist(x+1, y-1) && (color == BattleCheckers.WHITE || isKing))
			addJump(x+1, y-1, calledFromJump, isDoubleJump);
		
	}
	
	/**
	 * Used when first checking for movements
	 */
	public void getPossibleJumps(){
		getPossibleJumps(false, false);
	}
	
	/** 
	 * @returns If the checker is a king
	 */
	public boolean getIsKing() {
		return isKing;
	}
	
	/**
	 * @returns Checker's X coordinate
	 * 
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @returns Checker's Y coordinate
	 * 
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @returns Checker's color (WHITE or BLACK)
	 * 
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * @returns checker's role. Used for Special PowerUp
	 * 
	 */
	public int getRole() {
		return role;
	}
	
	/**
	 * @returns Checker's PowerUp. 
	 * @see PowerUp Constants
	 */
	public int getPowerUp() {
		return powerUp;
	}
	
	
	
	

}
