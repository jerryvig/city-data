package com.mktneutral.citydata.test.server.batch;

import org.testng.annotations.Test;
import org.testng.Assert;

import com.mktneutral.citydata.server.batch.Letter;

public class LetterTest {
	
  @Test
  public void testGetLetter() {
	  Letter letter = new Letter("a",0,0);
	  Assert.assertEquals(letter.getLetter(),"a");
	  
	  Letter letter2 = new Letter("b",0,0);
	  Assert.assertEquals(letter2.getLetter(),"b");
  }
  
  @Test
  public void testGetFrequency() {
	  Letter letter = new Letter("a",0,0);
	  Assert.assertEquals(letter.getFrequency(),0);
	  
	  Letter letter2 = new Letter("b",100,0);
	  Assert.assertEquals(letter2.getFrequency(),100);
  }
  
  @Test
  public void testGetScoreValue() {
	  Letter letter = new Letter("a",0,0);
	  Assert.assertEquals(letter.getScoreValue(),0);
	  
	  Letter letter2 = new Letter("b",100,1000);
	  Assert.assertEquals(letter2.getScoreValue(),1000);
  }
  
  @Test
  public void testCompareTo() {
	  Letter letter = new Letter("a",0,0);
	  Letter letter2 = new Letter("b",0,0);
	  Letter letter3 = new Letter("a",10,10);
	  
	  Assert.assertEquals(letter.compareTo(letter2), -1);
	  
	  Assert.assertEquals(letter.compareTo(letter3), 0);
	  
	  Assert.assertEquals(letter2.compareTo(letter), 1);
  }
}
