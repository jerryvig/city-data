package com.mktneutral.citydata.server.model;

public class CityDataRecord {
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
	
	public String getCityName() {
		return cityName;
	}
	
	public String getCountyName() {
		return countyName;
	}
}
