package com.mktneutral.citydata.client;

import java.io.Serializable;

public class CityDataRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String cityName;
	public String countyName;
	public int maleCount;
	public int femaleCount;
	public int population2010;
	public double populationChg;
	public  double medianAge;
	public int medianHouseholdIncome;
	public int perCapitaIncome;
	public int medianHouseValue;
	public int medianRent;
	public double whiteAlone;
	public double hispanic;
	public double asian;
	public double black;
	public double americanIndian;
	public double nativeHawaiian;
	public double costLivingIndex;
	public double highSchool;
	public double bachelorsDegree;
	public double graduateDegree;
	public double neverMarried;
	public double nowMarried;
	public double separated;
	public double widowed;
	public double divorced;
	public double popDensity;
	public int elevation;
	public double landArea;
	public double homicideRate;
	
    public CityDataRecord() {
    }
	
	public String getCityName() {
		return cityName;
	}
	
	public String getCountyName() {
		return countyName;
	}
	
	public int getPopulation() {
		return population2010;
	}
	
	public void setCityName( String cityName ) {
		this.cityName = cityName;
	}
	
	public void setCountyName( String countyName ) {
		this.countyName = countyName;
	}
	
	public void setMaleCount( int maleCount ) {
		this.maleCount = maleCount;
	}
	
	public void setFemaleCount( int femaleCount ) {
		this.femaleCount = femaleCount;
	}
	
	public void setPopulation2010( int population2010 ) {
		this.population2010 = population2010;
	}
}
