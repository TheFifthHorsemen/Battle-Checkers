package com.hexbit.battlecheckers;

import java.util.ArrayList;

public class PowerUp {
	
	// PowerUp Constants
	final static int NONE = 0;
	final static int JUMP = 1;
	final static int STEALTH = 2;
	final static int SPECIAL = 3;
	final static int EXTRALIFE = 4;
	final static int INSTEALTH = 5;
	
	// List of PowerUps on board
	final static ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();
	
	// Coordinates of PowerUp and the type (Defined by constants)
	private int x, y, type;
	
	/**
	 *  Class definition for PowerUp
	 * @param X
	 * @param Y
	 * @param Type
	 */
	public PowerUp(int X, int Y, int Type)
	{
		this.x = X;
		this.y = Y;
		this.type = Type;
		
		//Sets the value of board tile at PowerUp's coordinates to -1 (Meaning possess PowerUp)
		BattleCheckers.setValAtBoard(X, Y, -1);
		
		//Adds PowerUp to list of PowerUps
		powerUpList.add(this);
		
	}
	
	/** 
	 * Adds PowerUp with random type
	 * @param X
	 * @param Y
	 * 
	 * @see PowerUp
	 */
	public PowerUp(int X, int Y){
		this(X, Y, (int)Math.random()*4+1);
	}
	
	/**
	 * Removes PowerUp from list
	 * @param index
	 */
	public static void removePowerUp(int index){
		powerUpList.remove(index);
	}
	
	/**
	 * Adds a specified number of PowerUps to the board at random, unoccupied places
	 * @param quantity
	 */
	public static void spawnPowerUp(int quantity){
		for (int i = 0; i<quantity; i++)
		{
			int spawnX, spawnY;
			
			//Assigns random position on board and verifies it is empty
			do {
				spawnX = (int) Math.random()*BattleCheckers.BOARDWIDTH;
				spawnY = (int) Math.random()*BattleCheckers.BOARDHEIGHT;
			} while (BattleCheckers.getValAtBoard(spawnX, spawnY) != 0);
						
			powerUpList.add(new PowerUp(spawnX, spawnY));
		
		}
	}
	
	/**
	 * Goes through list of PowerUps and checks if one of them exists at the given coordinates
	 * @param x
	 * @param y
	 * @return PowerUp Index
	 */
	public static int getPowerUpIndex(int x, int y){
		for(int i = 0; i < powerUpList.size(); i++){
			if ((powerUpList.get(i).x) == x && (powerUpList.get(i).y == y))
				return i;
		}
		//Returns -1 if PowerUp does not exist
		return -1;
	}
	
	
	/**
	 * 
	 * @returns PowerUp X coordinate
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 
	 * @returns PowerUp Y coordinate
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @returns PowerUp Type
	 */
	public int getType(){
		return type;
	}
}
