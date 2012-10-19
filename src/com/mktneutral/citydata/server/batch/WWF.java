package com.mktneutral.citydata.server.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.Map;

public class WWF {
	private static final ArrayList<Letter> letterDeck = new ArrayList<Letter>();
	private static final HashMap<String,Letter> alphabet = new HashMap<String,Letter>();
	private static final ArrayList<Word> dictionary = new ArrayList<Word>();
	
	private static int lastDealtIndex = 0;
	
	public static void setAlphabet() {
		alphabet.put("a",new Letter("a",9,1));				
		alphabet.put("b",new Letter("b",2,4));
		alphabet.put("c",new Letter("c",2,4));
		alphabet.put("d",new Letter("d",5,2));
		alphabet.put("e",new Letter("e",13,1));
		alphabet.put("f",new Letter("f",2,4));
		alphabet.put("g",new Letter("g",3,3));
		alphabet.put("h",new Letter("h",4,3));
		alphabet.put("i",new Letter("i",8,1));
		alphabet.put("j",new Letter("j",1,10));
		alphabet.put("k",new Letter("k",1,5));
		alphabet.put("l",new Letter("l",4,2));
		alphabet.put("m",new Letter("m",2,4));
		alphabet.put("n",new Letter("n",5,2));
		alphabet.put("o",new Letter("o",8,1));
		alphabet.put("p",new Letter("p",2,4));
		alphabet.put("q",new Letter("q",1,10));
		alphabet.put("r",new Letter("r",6,1));
		alphabet.put("s",new Letter("s",5,1));
		alphabet.put("t",new Letter("t",7,1));
		alphabet.put("u",new Letter("u",4,2));
		alphabet.put("v",new Letter("v",2,5));
		alphabet.put("w",new Letter("w",2,4));
		alphabet.put("x",new Letter("x",1,8));
		alphabet.put("y",new Letter("y",2,3));
		alphabet.put("z",new Letter("z",1,10));
		alphabet.put("",new Letter("",2,0));
	}
	
	public static void printAlphabet() {
		Iterator iter = alphabet.entrySet().iterator();
		
		while ( iter.hasNext() ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Letter letter = (Letter) pair.getValue();
			System.out.println( letter.getLetter() + ", " + letter.getFrequency() + ", " + letter.getScoreValue() );
		}
	}
	
	public static void fillLetterDeck() {
		Iterator iter = alphabet.entrySet().iterator();
		
		while ( iter.hasNext() ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Letter letter = (Letter) pair.getValue();
			letterDeck.add( letter );
		}
	}
	
	public static void shuffleLetterDeck() {
		Collections.shuffle( letterDeck );
	}
	
	public static void printLetterDeck() {
		for ( Letter letter : letterDeck ) {
			System.out.println( letter.getLetter() );
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
		
		// printLetterDeck();
		
		try {
			loadDictionary();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
		sortDictionary();
		// printDictionary();
		
		Player p1 = new Player("Tess");
		p1.dealLetters();
		p1.printLetters();
		p1.findBestWord();
		
		Player p2 = new Player("Fluvia");
		p2.dealLetters();
		// p2.printLetters();
	}
	
	static private class Player {
		private ArrayList<Letter> dealtLetters = new ArrayList<Letter>();
		private static final int LETTER_COUNT = 7;
		private int score = 0;
		private String name;
		
		public Player( String name ) {
			this.name = name;
		}
		
		public void dealLetters() {
			for ( int i=0; i<LETTER_COUNT; i++ ) {
				dealtLetters.add( letterDeck.get(lastDealtIndex) );
				lastDealtIndex++;
			}
		}
		
		public void printLetters() {
			for ( Letter letter : dealtLetters ) 
				System.out.println( letter.getLetter() );
		}
		
		public void findBestWord() {
			//algorithm to find the best acceptable word in the dictionary from a given dealt letter hand.
			for ( Word word : dictionary ) {
				
				String wordString = word.getWordString();
				// System.out.println( "checking " + wordString );
				
				ArrayList<Letter> dealtCopy = (ArrayList<Letter>) dealtLetters.clone();
				ArrayList<Boolean> lettersFound = new ArrayList<Boolean>();
				
				if ( wordString.length() > LETTER_COUNT ) {
					continue;
				}
				
				for ( int i=0; i<wordString.length(); i++ ) {	
					String letter = wordString.substring(i,i+1);
					lettersFound.add( new Boolean(false) );
					
					//System.out.println( "next Letter = " + letter );
					
					for ( int j=0; j<dealtCopy.size(); j++ ) {
						//System.out.println( "dealt copy = " + dealtCopy.get(j) );
						Letter dealtLetter = (Letter) dealtCopy.get(j);
						if ( dealtLetter.getLetter().equals(letter) ) {
							// System.out.println( "we have equality" );
							
							dealtCopy.remove(j);
							lettersFound.set(lettersFound.size()-1, new Boolean(true));
						}
					}
				}
				
				boolean foundIt = true;
				for ( Boolean letterFound : lettersFound ) {
					foundIt = foundIt && letterFound.booleanValue();
				}
				
				if ( foundIt ) {
					System.out.println( wordString + ", found it, score = " + word.getWordScore() );
					return;
				}
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
			//compareTo method for Word to enable sorting of the words by their scores.
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