package com.hexbit.battlecheckers;

import java.util.ArrayList;

public class PowerUp {
	final static int NONE = 0;
	final static int JUMP = 1;
	final static int STEALTH = 2;
	final static int SPECIAL = 3;
	final static int EXTRALIFE = 4;
	final static int INSTEALTH = 5;
	
	final static ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();
	
	
	
	private int x, y, type;
	public PowerUp(int X, int Y, int Type)
	{
		this.x = X;
		this.y = Y;
		this.type = Type;
		
		BattleCheckers.setValAtBoard(X, Y, -1);
		powerUpList.add(this);
		
	}
	
	public PowerUp(int X, int Y){
		this(X, Y, (int)Math.random()*4+1);
	}
	
	public static int getPowerUpIndex(int x, int y){
		for(int i = 0; i < powerUpList.size(); i++){
			if ((powerUpList.get(i).x) == x && (powerUpList.get(i).y == y))
				return i;
		}
		return -1;
	}
	
	public static void spawnPowerUp(int quantity){
		for (int i = 0; i<quantity; i++)
		{
			int spawnX = (int) Math.random()*BattleCheckers.BOARDWIDTH;
			int spawnY = (int) Math.random()*BattleCheckers.BOARDHEIGHT;
			while (BattleCheckers.getValAtBoard(spawnX, spawnY) != 0){
				spawnX = (int) Math.random()*BattleCheckers.BOARDWIDTH;
				spawnY = (int) Math.random()*BattleCheckers.BOARDHEIGHT;
			}
			
			powerUpList.add(new PowerUp(spawnX, spawnY));
		
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getType(){
		return type;
	}
}
