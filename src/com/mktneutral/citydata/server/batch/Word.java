package com.mktneutral.citydata.server.batch;

import java.util.ArrayList;
import java.util.Collections;

public class Word implements Comparable<Word> {
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
			Letter letter = WWF.getAlphabet().get( word.substring(i,i+1) );
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
		
		/* for ( String letter : sortedLetters ) {
			System.out.println( letter );
		} */
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