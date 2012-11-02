package com.mktneutral.citydata.server.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.System.out;

public class Player {
	private ArrayList<Letter> dealtLetters = new ArrayList<Letter>();
	private ArrayList<Word> combinations = new ArrayList<Word>();
	
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
		out.println( "LETTER DECK SIZE = " + WWF.getLetterDeck().size() );
		
			for ( int i=dealtLetters.size(); i<LETTER_COUNT; i++ ) {
				if ( WWF.getLastDealtIndex() < WWF.getLetterDeck().size() ) {
					dealtLetters.add( WWF.getLetterDeck().get(WWF.getLastDealtIndex()) );
					out.println( "Dealt letter = " + WWF.getLetterDeck().get(WWF.getLastDealtIndex()).getLetter() );
					WWF.incrementLastDealtIndex();
				} 
				else {
					out.println( "ALL LETTERS DEALT -- GAME ENDS HERE" );
				}
			}
			Collections.sort(dealtLetters);
	}
	
	public void printLetters() {
		for ( Letter letter : dealtLetters ) 
			out.println( letter.getLetter() );
	}
	
	public void incrementScore( int bestWordScore ) {
		this.score += bestWordScore;
	}
	
	public void removeFromDealtLetters( String wordToRemove ) {
		char[] chars = wordToRemove.toCharArray();
		
		for ( char character : chars ) {
			char[] charArray = { character };
			String letterToRemove = new String( charArray );
			for ( int i=0; i<dealtLetters.size(); i++ ) {
				if ( letterToRemove.equals(dealtLetters.get(i).getLetter()) ) {
					dealtLetters.remove(i);
					out.println( "WE REMOVED A LETTER - " + letterToRemove + " , " + dealtLetters.size() );
					break;
				}
			}
		}
	}
	
	public void generateCombinations() {
		combinations.clear();

		//This can work for seven letters in the dealtLetters hand.
		//One, two, three, and four
		for ( int i=0; i<dealtLetters.size(); i++ ) {
			
			combinations.add( new Word( dealtLetters.get(i).getLetter() ) );
			
			for ( int j=i+1; j<dealtLetters.size(); j++ ) {
				combinations.add( new Word( dealtLetters.get(i).getLetter() 
							+ dealtLetters.get(j).getLetter() ) );
				
				for ( int k=j+1; k<dealtLetters.size(); k++ ) {
					combinations.add( new Word( dealtLetters.get(i).getLetter() 
							+ dealtLetters.get(j).getLetter() 
							+ dealtLetters.get(k).getLetter() ) );
					
					for ( int l=k+1; l<dealtLetters.size(); l++ ) {
						combinations.add( new Word( dealtLetters.get(i).getLetter() 
								+ dealtLetters.get(j).getLetter() 
								+ dealtLetters.get(k).getLetter() 
								+ dealtLetters.get(l).getLetter() ) );
					}
				}
			}
		}
		
		//Five - I got five on it.
		String sevenLetterWord = "";
		for ( int i=0; i<dealtLetters.size(); i++ ) {
			//Five is here
			for ( int j=i+1; j<dealtLetters.size(); j++ ) {
				String newWordString = "";
				for ( int k=0; k<dealtLetters.size(); k++ ) {
					if ( k != i && k != j ) {
						newWordString = newWordString.concat( dealtLetters.get(k).getLetter() );
					}
				}
				combinations.add( new Word( newWordString ) );
			}
			
			//Six is here
			String newWordString = "";
			for ( int j=0; j<dealtLetters.size(); j++ ) {
				if ( j != i ) {
					newWordString = newWordString.concat( dealtLetters.get(j).getLetter() );
				}
			}
			combinations.add( new Word( newWordString ) );
			
			sevenLetterWord = sevenLetterWord.concat( dealtLetters.get(i).getLetter() );
		}
		
		//Seven 
		combinations.add( new Word( sevenLetterWord ) );
		
		//sort the combos in descending order.
		Collections.sort( combinations );
		Collections.reverse( combinations );
		
		/*
		out.println( "----------Combos Here-----------" );
		
		for ( int i=0; i<combinations.size(); i++ ) {
			// out.println( i + ", " + combinations.get(i).getWordString() );
		}
		*/
		
		out.println( "Combinations size = " + combinations.size() );
		 
	}
	
	public void findBestWordI() {
		//algorithm to find the best acceptable word in the dictionary from a given dealt letter hand.
		for ( Word word : WWF.getDictionary() ) {
			
			String wordString = word.getWordString();
			// out.println( "checking " + wordString );
			
			ArrayList<Letter> dealtCopy = (ArrayList<Letter>) dealtLetters.clone();
			ArrayList<Boolean> lettersFound = new ArrayList<Boolean>();
			
			if ( wordString.length() > LETTER_COUNT ) {
				continue;
			}
			
			for ( int i=0; i<wordString.length(); i++ ) {	
				String letter = wordString.substring(i,i+1);
				lettersFound.add( new Boolean(false) );
				
				//out.println( "next Letter = " + letter );
				
				for ( int j=0; j<dealtCopy.size(); j++ ) {
					//out.println( "dealt copy = " + dealtCopy.get(j) );
					Letter dealtLetter = (Letter) dealtCopy.get(j);
					if ( dealtLetter.getLetter().equals(letter) ) {
						// out.println( "we have equality" );
						
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
				out.println( wordString + ", found it, score = " + word.getWordScore() );
				return;
			}
		}
	}
	
	public void findBestWordII() {
		//code to find best word based on generated combos.
		HashMap<String,Word> sortedWords = WWF.getSortedWords();
		
		for ( Word combo : combinations) {
			if ( sortedWords.get( combo.getWordString() ) != null ) {
				Word bestWord = WWF.getSortedWords().get( combo.getWordString() );
				
				out.println( "best word = " + bestWord.getWordString() +", score = " + bestWord.getWordScore() );
				
				incrementScore( bestWord.getWordScore() );
				removeFromDealtLetters( bestWord.getWordString() );
				
				return;
			}
		}
	}
}