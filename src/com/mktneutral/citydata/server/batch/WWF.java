package com.mktneutral.citydata.server.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class WWF {
	private static final ArrayList<String> letterDeck = new ArrayList<String>();
	private static final HashMap<String,Letter> alphabet = new HashMap<String,Letter>();
	private static final ArrayList<Word> dictionary = new ArrayList<Word>();
	
	private static int lastDealtIndex = 0;
	
	public static void setAlphabet() {
		alphabet.put("a",new Letter("A",9,1));				
		alphabet.put("b",new Letter("B",2,4));
		alphabet.put("c",new Letter("C",2,4));
		alphabet.put("d",new Letter("D",5,2));
		alphabet.put("e",new Letter("E",13,1));
		alphabet.put("f",new Letter("F",2,4));
		alphabet.put("g",new Letter("G",3,3));
		alphabet.put("h",new Letter("H",4,3));
		alphabet.put("i",new Letter("I",8,1));
		alphabet.put("j",new Letter("J",1,10));
		alphabet.put("k",new Letter("K",1,5));
		alphabet.put("l",new Letter("L",4,2));
		alphabet.put("m",new Letter("M",2,4));
		alphabet.put("n",new Letter("N",5,2));
		alphabet.put("o",new Letter("O",8,1));
		alphabet.put("p",new Letter("P",2,4));
		alphabet.put("q",new Letter("Q",1,10));
		alphabet.put("r",new Letter("R",6,1));
		alphabet.put("s",new Letter("S",5,1));
		alphabet.put("t",new Letter("T",7,1));
		alphabet.put("u",new Letter("U",4,2));
		alphabet.put("v",new Letter("V",2,5));
		alphabet.put("w",new Letter("W",2,4));
		alphabet.put("x",new Letter("X",1,8));
		alphabet.put("y",new Letter("Y",2,3));
		alphabet.put("z",new Letter("Z",1,10));
		alphabet.put("",new Letter("",2,0));
	}
	
	public static void fillLetterDeck() {
		Iterator iter = alphabet.entrySet().iterator();
		
		while ( iter.hasNext() ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Letter letter = (Letter) pair.getValue();
			letterDeck.add( letter.getLetter() );
		}
	}
	
	public static void shuffleLetterDeck() {
		Collections.shuffle( letterDeck );
	}
	
	public static void printLetterDeck() {
		for ( String letter : letterDeck ) {
			System.out.println( letter );
		}
	}
	
	public static void loadDictionary() throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader("enable1.txt") );
		
		String line = "";
		while ( (line = reader.readLine()) != null ) {
			int wordScore = scoreWord(line.trim()); 
			dictionary.add( new Word( line.trim(), wordScore ) );
		}
		
		reader.close();
	}
	
	public static void sortDictionary() {
		//Code here to sort the dictionary in descending order by the score of the words.
		Collections.sort( dictionary );
		Collections.reverse( dictionary );
	}
	
	public static int scoreWord( String word ) {
		//implement word scoring scheme
		int wordScore = 0;
		for ( int i=0; i<word.length(); i++ ) {
			Letter letter = alphabet.get( word.substring(i,i+1) );
			wordScore += letter.getScoreValue();
		}
		
		return wordScore;
	}
	
	public static void printDictionary() {
		for ( Word word : dictionary ) {
			System.out.println( word.getWordString() + ", " + word.getWordScore() );
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setAlphabet();
		
		fillLetterDeck();
		
		shuffleLetterDeck();
		
		printLetterDeck();
		
		try {
			loadDictionary();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
		sortDictionary();
		// printDictionary();
		
		Player p1 = new Player("Tess");
		p1.dealLetters();
		// p1.printLetters();
		
		Player p2 = new Player("Fluvia");
		p2.dealLetters();
		// p2.printLetters();
	}
	
	static private class Player {
		private String[] dealtLetters = new String[7];
		private int score = 0;
		private String name = "";
		
		public Player( String name ) {
			this.name = name;
		}
		
		public void dealLetters() {
			for ( int i=0; i<7; i++ ) {
				if ( dealtLetters[i] == null ) {
					dealtLetters[i] = letterDeck.get(lastDealtIndex);
					lastDealtIndex++;
				}
			}
		}
		
		public void printLetters() {
			for ( String letter : dealtLetters) 
				System.out.println( letter );
		}
		
		public void findBestWord() {
			
			for ( Word word : dictionary ) {
				
			}
		}
	}
	
	static private class Letter {
		private String letter;
		private int frequency;
		private int scoreValue;
		
		public Letter( String letter, int frequency, int scoreValue ) {
			this.letter = letter;
			this.frequency = frequency;
			this.scoreValue = scoreValue;
		}
		
		public String getLetter() {
			return this.letter;
		}
		
		public int getFrequency() {
			return this.frequency;
		}
		
		public int getScoreValue() {
			return this.scoreValue;
		}
	}
	
	static private class Word implements Comparable<Word> {
		private String word;
		private int wordScore;
		
		public Word( String word, int wordScore ) {
			this.word = word;
			this.wordScore = wordScore;
		}

		public String getWordString() {
			return this.word;
		}
		
		public int getWordScore() {
			return this.wordScore;
		}
		
		@Override
		public int compareTo(Word word) {
			if ( this.getWordScore() > word.getWordScore() ) {
				return 1;
			} 
			else if ( this.getWordScore() < word.getWordScore() ) {
				return -1;
			}
			else {
				return 0;
			}
		}
	}
	
}