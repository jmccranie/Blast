package com.cen3031.blast;

import java.io.Serializable;
import java.util.LinkedList;

import com.cen3031.blast.UnitAllocationActivity.Tank;

public class GameState implements Serializable  {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String user1ID;
  public String user2ID;
  public String user1name;
  public String user2name;
  public int gameID;
  LinkedList <Integer> p1TanksX;
  LinkedList <Integer> p1TanksY;
  LinkedList <Integer> p2TanksX;
  LinkedList <Integer> p2TanksY;
  LinkedList <Integer> p1MinesX;
  LinkedList <Integer> p1MinesY;
  LinkedList <Integer> p2MinesX;
  LinkedList <Integer> p2MinesY;
  float targetX;
  float targetY;
  public String pIDturn;
  int turn;
  public GameState(String user1, String user2){
	  this.user1name = user1;
	  this.user2name = user2;
  }
  
  public GameState(  LinkedList <Integer> p1TanksX,LinkedList <Integer> p1TanksY,LinkedList <Integer> p2TanksX,LinkedList <Integer> p2TanksY,
		  				LinkedList <Integer> p1MinesX,LinkedList <Integer> p1MinesY,LinkedList <Integer> p2MinesX,LinkedList <Integer> p2MinesY,
		  				float targetX,float targetY, String user1ID, String user2ID, String user1name, String user2name,String pIDturn){
	  this.p1TanksX = p1TanksX;
	  this.p1TanksY = p1TanksY;
	  this.p2TanksX = p2TanksX;
	  this.p2TanksY = p2TanksY;
	  this.p1MinesX = p1MinesX;
	  this.p1MinesY = p1MinesY;
	  this.p2MinesX = p2MinesX;
	  this.p2MinesY = p2MinesY;
	  this.targetX = targetX;
	  this.targetY = targetY;
	  this.user1ID = user1ID;
	  this.user2ID = user2ID;
	  this.user1name = user1name;
	  this.user2name = user2name;
	  this.pIDturn = pIDturn;
  }
  

}
