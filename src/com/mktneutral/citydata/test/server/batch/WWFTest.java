package com.mktneutral.citydata.test.server.batch;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	  
	  for ( String letter : lettersArray ) {
		  Assert.assertNotNull(alphabet.get(letter));
	  }
  }
}
