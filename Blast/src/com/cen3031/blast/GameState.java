package com.cen3031.blast;

import java.io.Serializable;
import java.util.LinkedList;

import org.andengine.entity.sprite.Sprite;

import com.cen3031.blast.UnitAllocationActivity.Tank;

public class GameState implements Serializable  {

  public String user1;
  public String user2;
  public int gameID;
  LinkedList <Tank> p1Tanks;
  LinkedList <Tank> p2Tanks;
  LinkedList <Sprite> p1Mines;
  LinkedList <Sprite> p2Mines;
  Tank selTank;
  float targetX;
  float targetY;
  boolean p1turn;
  public GameState(String user1, String user2,int gameID){
	  this.user1 = user1;
	  this.user2 = user2;
	  this.gameID = gameID;
  }
  
  public GameState(LinkedList <Tank> p1Tanks, LinkedList <Tank> p2Tanks,LinkedList <Sprite> p1Mines,LinkedList <Sprite> p2Mines,
		  			Tank selTank,float targetX,float targetY, boolean p1turn,int gameID){
	  this.p1Tanks = p1Tanks;
	  this.p2Tanks = p2Tanks;
	  this.p1Mines = p1Mines;
	  this.p2Mines = p2Mines;
	  this.selTank = selTank;
	  this.targetX = targetX;
	  this.targetY = targetY;
	  this.p1turn = p1turn;
	  this.gameID = gameID;
  }
  

}
