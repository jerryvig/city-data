package com.mktneutral.citydata.test.server.batch;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mktneutral.citydata.server.batch.Player;

public class PlayerTest {

  @Test
  public void testGetName() {
	  Player player1 = new Player("FluviaLacerda");
	  Player player2 = new Player("JessicaKane");
	  
	  Assert.assertEquals(player1.getName(),"FluviaLacerda");
	  Assert.assertEquals(player2.getName(),"JessicaKane");
  }
}
