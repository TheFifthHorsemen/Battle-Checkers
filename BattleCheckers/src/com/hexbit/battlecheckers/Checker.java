package com.hexbit.battlecheckers;

import java.util.ArrayList;

public class Checker {

	private int color;
	private int x, y;
	private int role;
	private boolean isKing;
	private int powerUp;
	
	public static ArrayList<int[][]> jumpList = new ArrayList<int[][]>();
	
	public Checker(int Color, int X, int Y, int Role, boolean IsKing, int PowerUp) {
		this.color = Color;
		this.x = X;
		this.y = Y;
		this.role = Role;
		this.isKing = IsKing;
		this.powerUp = PowerUp;
	}
	
	public Checker(int Color, int X, int Y, int Role){
		this(Color, X, Y, Role, false, PowerUp.NONE);
	}
	
	public void jump(){
		
		
		if (powerUp != PowerUp.EXTRALIFE){
			BattleCheckers.setValAtBoard(x, y, 0);
			
			if (color == BattleCheckers.WHITE)
				BattleCheckers.blackScore++;
			else 
				BattleCheckers.whiteScore++;
			
			x = -1;
			y = -1;
			
								
		} else {
			powerUp = PowerUp.NONE;
		}
		
		
	}
	
	public void setPos(int newX, int newY)
	{
		int oldX = x;
		int oldY = y;
		int jumpedCheckerID, index;
		
		int checkerID = BattleCheckers.getValAtBoard(oldX, oldY);
		
		BattleCheckers.setValAtBoard(oldX, oldY, 0);
		
		if(Math.sqrt(Math.pow((oldX-newX), 2)+ Math.pow((oldY-newY), 2)) > Math.sqrt(2) )
		{		
			jumpedCheckerID = BattleCheckers.getValAtBoard((oldX + newX)/2, (oldY+newY)/2);
			if (jumpedCheckerID>13){ 
				BattleCheckers.blackCheckers[jumpedCheckerID-14].jump();
				this.getPossibleJumps(false, true);
			} else if (jumpedCheckerID>0) {
				BattleCheckers.whiteCheckers[jumpedCheckerID-1].jump();
				this.getPossibleJumps(false, true);
			}
			
		
		}
		
		if(BattleCheckers.getValAtBoard(newX, newY)==-1) {
			index = PowerUp.getPowerUpIndex(newX, newY);
			if (index>=0){
				powerUp = (PowerUp.powerUpList.get(index).getType());
			}
			
		}
		
		BattleCheckers.setValAtBoard(newX, newY, checkerID);
		
	}
	
	public void getPossibleJumps(boolean calledFromJump, boolean isDoubleJump){
		
		if (!calledFromJump)
			jumpList.clear();
		
		if (x>0 && y>0)
			addJump(x-1, y-1, calledFromJump, isDoubleJump);
		
		if (x>0 && y<BattleCheckers.BOARDHEIGHT-1)
			addJump(x-1, y+1, calledFromJump, isDoubleJump);
		
		if (x<BattleCheckers.BOARDWIDTH-1 && y<BattleCheckers.BOARDHEIGHT-1)
			addJump(x+1, y+1, calledFromJump, isDoubleJump);
		
		if (x<BattleCheckers.BOARDWIDTH-1 && y>0)
			addJump(x+1, y-1, calledFromJump, isDoubleJump);
		
	}
	
	public void getPossibleJumps(){
		getPossibleJumps(false, false);
	}
	
	private void addJump(int X, int Y, boolean calledFromJump, boolean isDoubleJump)
	{
		int checkerID = BattleCheckers.getValAtBoard(X, Y);
		if (checkerID == 0 && !isDoubleJump) {
			jumpList.add(new int [Y][X]);
		}
		else if (!calledFromJump) 
		{
			if (checkerID < 14 && color == BattleCheckers.BLACK){
				getPossibleJumps(true, false);
			} else if (checkerID > 13 && color == BattleCheckers.WHITE){
				getPossibleJumps(true, false);
			}
		}
			
			
	}

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
