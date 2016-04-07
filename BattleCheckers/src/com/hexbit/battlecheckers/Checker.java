package com.hexbit.battlecheckers;

import java.util.ArrayList;

public class Checker {

	private int color;		//Checker's color (Black or White)
	private int x, y;		//Checker's grid coordinates
	private int role;		//Checker's class (Long range, Medium range, Short range)
	private boolean isKing;	//If the checker is a king
	private int powerUp;	//Checker's current power up
	
	// A list of all possible jumps for the currently selected checker. Modified by .getPossibleJumps
	public static ArrayList<int[][]> jumpList = new ArrayList<int[][]>(); 
	
	//Class definition for Checker
	public Checker(int Color, int X, int Y, int Role, boolean IsKing, int PowerUp) {
		this.color = Color;
		this.x = X;
		this.y = Y;
		this.role = Role;
		this.isKing = IsKing;
		this.powerUp = PowerUp;
	}
	
	//Used for creating checker, creates a checker that isn't a king and has no power up 
	public Checker(int Color, int X, int Y, int Role){
		this(Color, X, Y, Role, false, PowerUp.NONE);
	}
	
	//Function called when the checker has been jumped and needs to be checked if it will be removed
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
	
	//Adds a possible jump position to jumpList. Called by getPossibleJumps
	private void addJump(int X, int Y, boolean calledFromJump, boolean isDoubleJump)
	{
		int tileValue = BattleCheckers.getValAtBoard(X, Y);	//Grabs the value of the tile at the prescribed position from the board
		
		//If the tile is empty and the function is not searching for possible double jumps it add the tile to list of possible jumps
		if (tileValue == 0 && !isDoubleJump) {	
			jumpList.add(new int [Y][X]);
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
	
	//Moves checker to a new position, checking if a jump was made in the process
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
	
	//Used to find if a checker can move to a tile
	public void getPossibleJumps(boolean calledFromJump, boolean isDoubleJump){
		
		//Clears list if not finding tiles from jumping checkers
		if (!calledFromJump)
			jumpList.clear();
		
		//Checks if positions exist
		
		if (x>0 && y>0 && (color == BattleCheckers.BLACK || isKing))
			addJump(x-1, y-1, calledFromJump, isDoubleJump);
		
		if (x>0 && y<BattleCheckers.BOARDHEIGHT-1 && (color == BattleCheckers.BLACK || isKing))
			addJump(x-1, y+1, calledFromJump, isDoubleJump);
		
		if (x<BattleCheckers.BOARDWIDTH-1 && y<BattleCheckers.BOARDHEIGHT-1 && (color == BattleCheckers.WHITE || isKing))
			addJump(x+1, y+1, calledFromJump, isDoubleJump);
		
		if (x<BattleCheckers.BOARDWIDTH-1 && y>0 && (color == BattleCheckers.WHITE || isKing))
			addJump(x+1, y-1, calledFromJump, isDoubleJump);
		
	}
	
	//Used when first checking for movements
	public void getPossibleJumps(){
		getPossibleJumps(false, false);
	}
	
	//Returns defined values of checker

	public boolean getIsKing() {
		return isKing;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getRole() {
		return role;
	}
	
	public int getPowerUp() {
		return powerUp;
	}
	
	
	
	

}
