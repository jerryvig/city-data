package com.mktneutral.citydata.server.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
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
		Path path = Paths.get("enable1.txt");
		
		if ( !Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS}) ) {
			throw new IOException("Failed to find dictionary file");
		}
		
		if ( !Files.isReadable(path) ) {
			throw new IOException("Dictionary file is not readable");
		}
		
		//JDK 7 BufferedReader pattern to replace use of new BufferedReader()
		BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
		String line;
		while ( (line = reader.readLine()) != null ) {
			dictionary.add( new Word( line.trim() ) );
		}
		
		reader.close();
	}
	
	public static void sortDictionary() {
		//Code here to sort the dictionary in descending order by the score of the words.
		Collections.sort( dictionary );
		Collections.reverse( dictionary );
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
		long startTime = System.currentTimeMillis();
	
		setAlphabet();
		
		fillLetterDeck();
		
		shuffleLetterDeck();
		
		// printLetterDeck();
		
		try {
			loadDictionary();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
			System.exit(0);
		}
		
		sortDictionary();
		// printDictionary();
		
		for ( Word word : dictionary ) {
			word.sortLetters();
		}
		
		//Player p1 = new Player("TessMunster");
		//p1.dealLetters();
		//p1.printLetters();
		//p1.findBestWordI();
		//p1.getCombinations();
		
		long endTime = System.currentTimeMillis();
		System.out.println( "Time = " + Long.toString(endTime-startTime) + " ms" );
		
		// Player p2 = new Player("Fluvia");
		// p2.dealLetters();
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
		
		public String getName() {
			return this.name;
		}
		
		public int getScore() {
			return this.score;
		}
		
		public void dealLetters() {
			for ( int i=0; i<LETTER_COUNT; i++ ) {
				dealtLetters.add( letterDeck.get(lastDealtIndex) );
				lastDealtIndex++;
			}
			
			Collections.sort(dealtLetters);
		}
		
		public void printLetters() {
			for ( Letter letter : dealtLetters ) 
				System.out.println( letter.getLetter() );
		}
		
		public void getCombinations() {
			//This can work for seven letters in the dealtLetters hand.
			ArrayList<Word> combinations = new ArrayList<Word>();
			
			//One
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				combinations.add( new Word( dealtLetters.get(i).getLetter() ) );
			}
			
			//Two
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				for ( int j=i+1; j<dealtLetters.size(); j++ ) {
					combinations.add( new Word( dealtLetters.get(i).getLetter() + dealtLetters.get(j).getLetter() ) );
				}
			}
			
			//Three
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				for ( int j=i+1; j<dealtLetters.size(); j++ ) {
					for ( int k=j+1; k<dealtLetters.size(); k++ ) {
						combinations.add( new Word( dealtLetters.get(i).getLetter() 
								+ dealtLetters.get(j).getLetter() 
								+ dealtLetters.get(k).getLetter() ) );
					}
				}
			}
			
			//Four
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				for ( int j=i+1; j<dealtLetters.size(); j++ ) {
					for ( int k=j+1; k<dealtLetters.size(); k++ ) {
						for ( int l=k+1; l<dealtLetters.size(); l++ ) {
							combinations.add( new Word( dealtLetters.get(i).getLetter() 
									+ dealtLetters.get(j).getLetter() 
									+ dealtLetters.get(k).getLetter() 
									+ dealtLetters.get(l).getLetter() ) );
						}
					}
				}
			}
			
			//Five I got five on it
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				for ( int j=i+1; j<dealtLetters.size(); j++ ) {
					for ( int k=j+1; k<dealtLetters.size(); k++ ) {
						for ( int l=k+1; l<dealtLetters.size(); l++ ) {
							for ( int m=l+1; m<dealtLetters.size(); m++ ) {
								combinations.add( new Word( dealtLetters.get(i).getLetter() 
									+ dealtLetters.get(j).getLetter() 
									+ dealtLetters.get(k).getLetter() 
									+ dealtLetters.get(l).getLetter() 
									+ dealtLetters.get(m).getLetter() ) );
							}
						}
					}
				}
			}
			
			//Six
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				for ( int j=i+1; j<dealtLetters.size(); j++ ) {
					for ( int k=j+1; k<dealtLetters.size(); k++ ) {
						for ( int l=k+1; l<dealtLetters.size(); l++ ) {
							for ( int m=l+1; m<dealtLetters.size(); m++ ) {
								for ( int n=m+1; n<dealtLetters.size(); n++ ) {
									combinations.add( new Word( dealtLetters.get(i).getLetter() 
											+ dealtLetters.get(j).getLetter() 
											+ dealtLetters.get(k).getLetter() 
											+ dealtLetters.get(l).getLetter() 
											+ dealtLetters.get(m).getLetter()
											+ dealtLetters.get(n).getLetter() ) );
								}
							}
						}
					}
				}
			}
			
			//Seven 
			combinations.add( new Word( dealtLetters.get(0).getLetter() 
					+ dealtLetters.get(1).getLetter() 
					+ dealtLetters.get(2).getLetter() 
					+ dealtLetters.get(3).getLetter() 
					+ dealtLetters.get(4).getLetter()
					+ dealtLetters.get(5).getLetter()
					+ dealtLetters.get(6).getLetter() ) );
			
			Collections.sort( combinations );
			Collections.reverse( combinations );
			
			System.out.println( "----------Comobos Here-----------" );
			for ( Word combo : combinations ) {
				System.out.println( combo.getWordString() );
			}
			
			System.out.println( "Combinations size = " + combinations.size() );
			
		}
		
		public void findBestWordI() {
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
	
	static private class Letter implements Comparable<Letter> {
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
		
		@Override
		public int compareTo(Letter letter) {
			return this.getLetter().compareTo( letter.getLetter() );
		}
	}
	
	static private class Word implements Comparable<Word> {
		private String word;
		private int wordScore;
		
		public Word( String word ) {
			this.word = word;
			this.scoreWord();
		}

		public String getWordString() {
			return this.word;
		}
		
		public int getWordScore() {
			return this.wordScore;
		}
		
		public void scoreWord() {
			//implement word scoring scheme
			this.wordScore = 0;
			for ( int i=0; i<word.length(); i++ ) {
				Letter letter = alphabet.get( word.substring(i,i+1) );
				this.wordScore += letter.getScoreValue();
			}
		}
		
		public void sortLetters() {
			/*
			 * need to rewrite here to make it work.
			*/
			char[] characters = this.word.toCharArray();
			ArrayList<String> sortedLetters = new ArrayList<String>();
			
			for ( char character : characters ) {
				sortedLetters.add( new String(new StringBuilder(character)) );
			}
			
			Collections.sort( sortedLetters );
			
			for ( String letter : sortedLetters ) {
				System.out.println( letter );
			}
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