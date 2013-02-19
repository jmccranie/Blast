package com.cen3031.blast;

import java.io.Serializable;

public class GameState implements Serializable  {

  public String user1;
  public String user2;
  public int gameID;
  
  public GameState(String user1, String user2){
	  this.user1 = user1;
	  this.user2 = user2;
  }

}
