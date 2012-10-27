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

import static java.lang.System.out;

public class WWF {
	private static final ArrayList<Letter> letterDeck = new ArrayList<Letter>();
	private static final HashMap<String,Letter> alphabet = new HashMap<String,Letter>();
	private static final ArrayList<Word> dictionary = new ArrayList<Word>();
	
	private static final HashMap<String,Word> sortedWords = new HashMap<String,Word>();
	
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
		alphabet.put("_",new Letter("_",2,0));
	}
	
	public static void printAlphabet() {
		Iterator iter = alphabet.entrySet().iterator();
		
		while ( iter.hasNext() ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Letter letter = (Letter) pair.getValue();
			out.println( letter.getLetter() + ", " + letter.getFrequency() + ", " + letter.getScoreValue() );
		}
	}
	
	public static void fillLetterDeck() {
		Iterator iter = alphabet.entrySet().iterator();
		
		while ( iter.hasNext() ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Letter letter = (Letter) pair.getValue();
			
			for ( int i=0; i<letter.getFrequency(); i++ ) {
				letterDeck.add( letter );
			}
		}
	}
	
	public static void shuffleLetterDeck() {
		Collections.shuffle( letterDeck );
	}
	
	public static void printLetterDeck() {
		for ( Letter letter : letterDeck ) {
			out.println( letter.getLetter() );
		}
		
		out.println( "Letter Deck size = " + letterDeck.size() );
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
			out.println( word.getWordString() + ", " + word.getWordScore() );
		}
	}
	
	public static int getLastDealtIndex() {
		return lastDealtIndex;
	}
	
	public static void incrementLastDealtIndex() {
		lastDealtIndex++;
		
		if ( lastDealtIndex >= letterDeck.size() ) {
			System.out.println( "ALL LETTERS DEALT" );
		}
	}
	
	public static ArrayList<Letter> getLetterDeck() {
		return letterDeck;
	}
	
	public static HashMap<String,Letter> getAlphabet() {
		return alphabet;
	}
	
	public static ArrayList<Word> getDictionary() {
		return dictionary;
	}
	
	public static void addSortedWord( String sortedWord, Word word ) {
		sortedWords.put(sortedWord, word);
	}
	
	public static HashMap<String,Word> getSortedWords() {
		return sortedWords;
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
			addSortedWord( word.getSortedLetters(), word );
		}
		
		out.println( "sortedWords Size = " + sortedWords.size() );
		out.println( "dictionary size = " + dictionary.size() );
		
		//Only two players in Zynga version of the game.
		Player player1 = new Player("TessMunster");
		Player player2 = new Player("FluviaLacerda");
		//Player player3 = new Player("DeniseBidot");
		
		while ( lastDealtIndex < letterDeck.size() ) {
			out.println( "---PLAYER 1 ---");
			player1.dealLetters();
			player1.printLetters();
			player1.generateCombinations();
			player1.findBestWordII();
			out.println( "SCORE = " + player1.getScore() );
			
			out.println("---PLAYER 2----");
			player2.dealLetters();
			player2.printLetters();
			player2.generateCombinations();
			player2.findBestWordII();
			out.println( "SCORE = " + player2.getScore() );
			
			//out.println("-----PLAYER 3-----");
			//player3.dealLetters();
			//player3.printLetters();
			//player3.generateCombinations();
			//player3.findBestWordII();
		}
		
		long endTime = System.currentTimeMillis();
		out.println( "Time = " + Long.toString(endTime-startTime) + " ms" );
	}
	
}