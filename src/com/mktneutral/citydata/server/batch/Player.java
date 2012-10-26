package com.mktneutral.citydata.server.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Player {
	private ArrayList<Letter> dealtLetters = new ArrayList<Letter>();
	private ArrayList<Letter> letterDeck;
	private HashMap<String,Letter> alphabet;
	private ArrayList<Word> dictionary;
	
	private static final int LETTER_COUNT = 7;
	private int score = 0;
	private String name;
	
	public Player( String name, ArrayList<Letter> letterDeck, HashMap<String,Letter> alphabet, ArrayList<Word> dictionary ) {
		this.name = name;
		this.letterDeck = letterDeck;
		this.alphabet = alphabet;
		this.dictionary = dictionary;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void dealLetters( Integer lastDealtIndex ) {
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
			combinations.add( new Word( dealtLetters.get(i).getLetter(), alphabet ) );
		}
		
		//Two
		for ( int i=0; i<dealtLetters.size(); i++ ) {
			for ( int j=i+1; j<dealtLetters.size(); j++ ) {
				combinations.add( new Word( dealtLetters.get(i).getLetter() + dealtLetters.get(j).getLetter(), alphabet ) );
			}
		}
		
		//Three
		for ( int i=0; i<dealtLetters.size(); i++ ) {
			for ( int j=i+1; j<dealtLetters.size(); j++ ) {
				for ( int k=j+1; k<dealtLetters.size(); k++ ) {
					combinations.add( new Word( dealtLetters.get(i).getLetter() 
							+ dealtLetters.get(j).getLetter() 
							+ dealtLetters.get(k).getLetter(), alphabet ) );
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
								+ dealtLetters.get(l).getLetter(), alphabet ) );
					}
				}
			}
		}
		
		//Five I got five on it
		/* for ( int i=0; i<dealtLetters.size(); i++ ) {
			for ( int j=i+1; j<dealtLetters.size(); j++ ) {
				for ( int k=j+1; k<dealtLetters.size(); k++ ) {
					for ( int l=k+1; l<dealtLetters.size(); l++ ) {
						for ( int m=l+1; m<dealtLetters.size(); m++ ) {
							combinations.add( new Word( dealtLetters.get(i).getLetter() 
								+ dealtLetters.get(j).getLetter() 
								+ dealtLetters.get(k).getLetter() 
								+ dealtLetters.get(l).getLetter() 
								+ dealtLetters.get(m).getLetter(), alphabet ) );
						}
					}
				}
			}
		} */
		
		//Five - I got five on it.
		for ( int i=0; i<dealtLetters.size(); i++ ) {
			for ( int j=i+1; j<dealtLetters.size(); j++ ) {
				String newWordString = "";
				for ( int k=0; k<dealtLetters.size(); k++ ) {
					if ( k != i && k != j ) {
						newWordString.concat( dealtLetters.get(j).getLetter() );
					}
				}
				combinations.add( new Word( newWordString, alphabet ) );
			}
		}
		
		//Six 
		for ( int i=0; i<dealtLetters.size(); i++ ) {
			String newWordString = "";
			for ( int j=0; j<dealtLetters.size(); j++ ) {
				if ( i != j ) {
					newWordString.concat( dealtLetters.get(j).getLetter() );
				}
			}
			combinations.add( new Word( newWordString, alphabet ) );
		}
		
		//Seven 
		combinations.add( new Word( dealtLetters.get(0).getLetter() 
				+ dealtLetters.get(1).getLetter() 
				+ dealtLetters.get(2).getLetter() 
				+ dealtLetters.get(3).getLetter() 
				+ dealtLetters.get(4).getLetter()
				+ dealtLetters.get(5).getLetter()
				+ dealtLetters.get(6).getLetter(), alphabet ) );
		
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
	
	public void findBestWordII() {
		
	}
}