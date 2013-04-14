package com.cen3031.blast;

import java.io.Serializable;
import java.util.LinkedList;

import org.andengine.entity.sprite.Sprite;

import com.cen3031.blast.UnitAllocationActivity.Tank;

public class GameState implements Serializable  {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public String user1;
  public String user2;
  public int gameID;
  LinkedList <Integer> p1TanksX;
  LinkedList <Integer> p1TanksY;
  LinkedList <Integer> p2TanksX;
  LinkedList <Integer> p2TanksY;
  LinkedList <Integer> p1MinesX;
  LinkedList <Integer> p1MinesY;
  LinkedList <Integer> p2MinesX;
  LinkedList <Integer> p2MinesY;
  Tank selTank;
  float targetX;
  float targetY;
  boolean p1turn;
  public GameState(String user1, String user2,int gameID){
	  this.user1 = user1;
	  this.user2 = user2;
	  this.gameID = gameID;
  }
  
  public GameState(  LinkedList <Integer> p1TanksX,LinkedList <Integer> p1TanksY,LinkedList <Integer> p2TanksX,LinkedList <Integer> p2TanksY,
		  				LinkedList <Integer> p1MinesX,LinkedList <Integer> p1MinesY,LinkedList <Integer> p2MinesX,LinkedList <Integer> p2MinesY,
		  				Tank selTank,float targetX,float targetY, boolean p1turn,int gameID){
	  this.p1TanksX = p1TanksX;
	  this.p1TanksY = p1TanksY;
	  this.p2TanksX = p2TanksX;
	  this.p2TanksY = p2TanksY;
	  this.p1MinesX = p1MinesX;
	  this.p1MinesY = p1MinesY;
	  this.p2MinesX = p2MinesX;
	  this.p2MinesY = p2MinesY;
	  this.selTank = selTank;
	  this.targetX = targetX;
	  this.targetY = targetY;
	  this.p1turn = p1turn;
	  this.gameID = gameID;
  }
  

}
