package com.mktneutral.citydata.test.server.batch;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mktneutral.citydata.server.batch.WWF;
import com.mktneutral.citydata.server.batch.Word;

public class WordTest {
  @Test
  public void testGetWordString() {
	  WWF.setAlphabet();
	  
	  Word word = new Word("testing");
	  Assert.assertEquals(word.getWordString(), "testing");
	  
	  Word word2 = new Word("breathless");
	  Assert.assertEquals(word2.getWordString(), "breathless");
  }
  
  @Test
  public void testScoreWord() {
	  Word word = new Word("a");
	  Assert.assertEquals(word.getWordScore(), 1);
	  
	  Word word2 = new Word("adele");
	  Assert.assertEquals(word2.getWordScore(), 7);
	  
	  Word allLetters = new Word("abcdefghijklmnopqrstuvwxyz");
	  Assert.assertEquals(allLetters.getWordScore(), 96);
  }
}
