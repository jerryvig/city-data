package com.mktneutral.citydata.test.server.batch;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.mktneutral.citydata.server.batch.Letter;
import com.mktneutral.citydata.server.batch.WWF;

public class WWFTest {
  @Test
  public void testSetAlphabet() {
	  WWF.setAlphabet();
	  
	  HashMap<String,Letter> alphabet = WWF.getAlphabet();
	  String[] lettersArray = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","_"};
	  int[] frequencyArray = {};
	  int[] scoresArray = {};
	  
	  for ( String letter : lettersArray ) {
		  Letter myLetter = alphabet.get(letter);
		  
		  Assert.assertNotNull(myLetter);
		  
		  Assert.assertEquals(myLetter.getLetter(),letter);
		  Assert.assertNotNull(myLetter.getFrequency());
		  Assert.assertNotNull(myLetter.getScoreValue());
	  }
	  
  }
  
  @Test
  public void testPrintAlphabet() {
	  WWF.setAlphabet();
	  
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	  System.setOut(new PrintStream(baos));
	  
	  WWF.printAlphabet();
	  
	  System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	  
	  String printedString = baos.toString();
	  Assert.assertNotNull(printedString);
	  
	  String[] lines = printedString.split("\n");
	  Assert.assertEquals(lines.length, 27);  
  }
  
  @Test
  public void testFillLetterDeck() {
	  WWF.setAlphabet();
	  
	  WWF.fillLetterDeck();
	  
	  ArrayList<Letter> letterDeck = WWF.getLetterDeck();
	  
	  Assert.assertEquals(letterDeck.size(),104);
	  
	  for ( Letter letter : letterDeck ) {
		  Assert.assertNotNull(letter);
	  }
  }
  
}
