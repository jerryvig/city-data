package com.mktneutral.citydata.test.server.batch;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mktneutral.citydata.server.batch.Player;

public class PlayerTest {

  @Test
  public void testGetName() {
	  Player player1 = new Player("FluviaLacerda");
	  Player player2 = new Player("JessicaKane");
	  Player player3 = new Player("TessMunster");
	  
	  Assert.assertEquals(player1.getName(),"FluviaLacerda");
	  Assert.assertEquals(player2.getName(),"JessicaKane");
	  Assert.assertEquals(player3.getName(), "TessMunster");
  }
  
  @Test
  public void testGetScore() {
	  Player player1 = new Player("RosieMercado");
	  Assert.assertEquals(player1.getScore(), 0);
	  
	  player1.incrementScore(20);
	  Assert.assertEquals(player1.getScore(),20);
	  
	  player1.incrementScore(50);
	  Assert.assertEquals(player1.getScore(), 70);
	  
	  player1.incrementScore(100);
	  Assert.assertEquals(player1.getScore(), 170);
  }
  
  @Test
  public void testDealLetters() {
	  
  }
}
