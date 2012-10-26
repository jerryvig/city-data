package com.mktneutral.citydata.server.batch;

public class Letter implements Comparable<Letter> {
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
