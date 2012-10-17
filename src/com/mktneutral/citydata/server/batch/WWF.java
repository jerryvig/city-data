package com.mktneutral.citydata.server.batch;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class WWF {
	
	private static final ArrayList<String> letterDeck = new ArrayList<String>();
	private static final HashMap<String,Integer> frequencies = new HashMap<String,Integer>();
	
	public static void setFrequencies() {
		frequencies.put("A",9);
		frequencies.put("B",2);
		frequencies.put("C",2);
		frequencies.put("D",5);
		frequencies.put("E",13);
		frequencies.put("F",2);
		frequencies.put("G",3);
		frequencies.put("H",4);
		frequencies.put("I",8);
		frequencies.put("J",1);
		frequencies.put("K",1);
		frequencies.put("L",4);
		frequencies.put("M",2);
		frequencies.put("N",5);
		frequencies.put("O",8);
		frequencies.put("P",2);
		frequencies.put("Q",1);
		frequencies.put("R",6);
		frequencies.put("S",5);
		frequencies.put("T",7);
		frequencies.put("U",4);
		frequencies.put("V",2);
		frequencies.put("W",2);
		frequencies.put("X",1);
		frequencies.put("Y",2);
		frequencies.put("Z",1);
		frequencies.put("",2);
	}
	
	public static void fillLetterDeck() {
		Iterator iter = frequencies.entrySet().iterator();
		
		while( iter.hasNext() ) {
			Map.Entry pair = (Map.Entry) iter.next();
			String letter = (String) pair.getKey();
			Integer count = (Integer) pair.getValue();
			
			for ( int i=0; i<count; i++ ) {
				letterDeck.add( letter );
			}
		}
	}
	
	public static void shuffleLetterDeck() {
		Collections.shuffle( letterDeck );
	}
	
	public static void main(String[] args) {
		setFrequencies();
		
		fillLetterDeck();
		
		shuffleLetterDeck();
		
		for ( String letter : letterDeck ) {
			System.out.println( letter );
		}
	}
	
	
}