package com.mktneutral.citydata.server.batch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;

public class ScrapeCityData {
	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;
	private static ArrayList<String> urlList = new ArrayList<String>();
	private static ArrayList<Thread> threadList = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		System.out.println( "Available Processors = " + Runtime.getRuntime().availableProcessors() );
		
		try {
			reader = new BufferedReader(new FileReader("CityDataList.txt"));
			writer = new BufferedWriter(new FileWriter("CityDataRecords.csv"));

			String nextLine;
			while ((nextLine = reader.readLine()) != null) {
				urlList.add(NameToUrl(nextLine.trim()));
			}

			try {
				writer.write("\"City Name\",\"Population\",\"Males\",\"Females\",\"Population Change\",\"Median Resident Age\",\"Median Household Income\",\"Per Capita Income\",\"Median Household Value\",\"Median Rent\",\"White Alone\",\"Hispanic\",\"Black\",\"Asian\",\"American Indian\",\"Native Hawaiian\",\"Cost of Living Index\",\"High School\",\"Bachelors Degree\",\"Graduate Degree\",\"Never Married\",\"Now Married\",\"Separated\",\"Widowed\",\"Divorced\",\"Population Density\",\"Land Area\",\"Elevation\",\"Homicide Rate\"\n");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			for (int i = 0; i < urlList.size(); i++) {
				try {
					if ( i > 0 )
						Thread.sleep(400);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} 
				
				System.out.println(urlList.get(i));
				URL url = new URL(urlList.get(i));

				BufferedReader respReader = new BufferedReader(
						new InputStreamReader(url.openStream()));
				String resp = "";
				String s;
				while ((s = respReader.readLine()) != null) {
					resp += s;
				}
				respReader.close();

				String cityName = getCityName(urlList.get(i));
				System.out.println( cityName );
				
				
				Thread responseThread = new Thread(new ResponseParser(resp, cityName));
				responseThread.start();
				threadList.add( responseThread );
			}
			
			for ( Thread thread : threadList ) {
				try {
					thread.join();
				} catch ( InterruptedException ie ) {
					ie.printStackTrace();
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			reader.close();
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static class ResponseParser implements Runnable {
		private String resp = "";
		private String cityName = "";

		public ResponseParser( String resp, String cityName ) {
			this.resp = resp;
			this.cityName = cityName; 
		}

		// public static void parseResponse(String resp, String _cityName) {
		public void run() {
			String countyName = "";
			String maleCount = "";
			String femaleCount = "";
			String population2010 = "";
			String populationChg = "";
			String medianAge = "";
			String medianHouseholdIncome = "";
			String perCapitaIncome = "";
			String medianHouseValue = "";
			String medianRent = "";
			String whiteAlone = "";
			String hispanic = "";
			String asian = "";
			String black = "";
			String americanIndian = "";
			String nativeHawaiian = "";
			String costLivingIndex = "";
			String highSchool = "";
			String bachelorsDegree = "";
			String graduateDegree = "";
			String neverMarried = "";
			String nowMarried = "";
			String separated = "";
			String widowed = "";
			String divorced = "";
			String popDensity = "";
			String elevation = "";
			String landArea = "";
			String homicideRate = "";
			
			try {
				int maleIdx = resp.indexOf("Males:");
				String nextString = resp.substring(maleIdx, maleIdx + 25);
				int nbIdx = nextString.indexOf("&nb");
				int bIdx = nextString.indexOf("b> ");
				maleCount = nextString.substring(bIdx + 3, nbIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int femaleIdx = resp.indexOf("Females:");

				String nextString = resp.substring(femaleIdx, femaleIdx + 25);
				int nbIdx = nextString.indexOf("&nb");
				int bIdx = nextString.indexOf("b> ");
				femaleCount = nextString.substring(bIdx + 3, nbIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int populationIdx = resp.indexOf("Population in 2010:");

				String nextString = resp.substring(populationIdx,
						populationIdx + 35);
				int endIdx = nextString.indexOf(". <b");
				int startIdx = nextString.indexOf("</b>");
				population2010 = nextString.substring(startIdx + 4, endIdx).trim();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int popChgIdx = resp.indexOf("Population change since");
				String nextString = resp.substring(popChgIdx, popChgIdx + 50);

				int startIdx = nextString.indexOf("</b> ");
				int endIdx = nextString.indexOf("%<br>");
				populationChg = nextString.substring(startIdx + 4, endIdx + 1)
						.trim();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int medianResIdx = resp.indexOf("Median resident age:");
				String nextString = resp
						.substring(medianResIdx, medianResIdx + 150);

				int startIdx = nextString.indexOf(">&nbsp;");
				int endIdx = nextString.indexOf("years</td>");
				medianAge = nextString.substring(startIdx + 7, endIdx).trim();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int medianIncomeIdx = resp
						.indexOf("Estimated median household income");
				String nextString = resp.substring(medianIncomeIdx,
						medianIncomeIdx + 70);

				int startIdx = nextString.indexOf(":</b> ");
				int endIdx = nextString.indexOf("(<b>");
				medianHouseholdIncome = nextString.substring(startIdx + 5, endIdx)
						.trim();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int perCapitaIncomeIdx = resp
						.indexOf("Estimated per capita income");
				String nextString = resp.substring(perCapitaIncomeIdx,
						perCapitaIncomeIdx + 70);
				int startIdx = nextString.indexOf(":</b> ");
				int endIdx = nextString.indexOf("<br><br");

				perCapitaIncome = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int medianHouseValueIdx = resp
						.indexOf("Estimated median house or condo value");
				String nextString = resp.substring(medianHouseValueIdx,
						medianHouseValueIdx + 90);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("(<b>");

				medianHouseValue = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int medianRentIdx = resp.indexOf("Median gross rent");
				String nextString = resp.substring(medianRentIdx,
						medianRentIdx + 70);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf(".<br>");

				medianRent = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int whiteAloneIdx = resp.indexOf("White alone -");

				String nextString = resp.substring(whiteAloneIdx,
						whiteAloneIdx + 60);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("</li>");

				whiteAlone = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int hispanicIdx = resp.indexOf("Hispanic -");
				String nextString = resp.substring(hispanicIdx, hispanicIdx + 50);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("</li>");

				hispanic = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int asianIdx = resp.indexOf("Asian alone -");
				String nextString = resp.substring(asianIdx, asianIdx + 50);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("</li>");

				asian = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int blackIdx = resp.indexOf("Black alone -");
				String nextString = resp.substring(blackIdx, blackIdx + 50);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("</li>");

				black = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int americanIndianIdx = resp.indexOf("American Indian alone -");
				String nextString = resp.substring(americanIndianIdx,
						americanIndianIdx + 50);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("</li>");

				americanIndian = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int nativeHawaiianIdx = resp.indexOf("Native Hawaiian and");
				String nextString = resp.substring(nativeHawaiianIdx,
						nativeHawaiianIdx + 50);
				int startIdx = nextString.indexOf("</b>");
				int endIdx = nextString.indexOf("</li>");

				nativeHawaiian = nextString.substring(startIdx + 4, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int costLivingIdx = resp.indexOf("cost of living index");
				String nextString = resp.substring(costLivingIdx,
						costLivingIdx + 50);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("<b>");

				costLivingIndex = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int highSchoolIdx = resp.indexOf("High school or higher");
				String nextString = resp.substring(highSchoolIdx,
						highSchoolIdx + 50);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</li>");

				highSchool = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int bachelorsIdx = resp.indexOf("Bachelor's degree or higher");
				String nextString = resp.substring(bachelorsIdx, bachelorsIdx + 60);
				int startIdx = nextString.indexOf(":</b> ");
				int endIdx = nextString.indexOf("</li>");

				bachelorsDegree = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int graduateDegreeIdx = resp
						.indexOf("Graduate or professional degree");
				String nextString = resp.substring(graduateDegreeIdx,
						graduateDegreeIdx + 60);
				int startIdx = nextString.indexOf(":</b> ");
				int endIdx = nextString.indexOf("</li>");

				graduateDegree = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int neverMarriedIdx = resp.indexOf("Never married:");
				String nextString = resp.substring(neverMarriedIdx,
						neverMarriedIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</li>");

				neverMarried = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int nowMarriedIdx = resp.indexOf("Now married:");
				String nextString = resp.substring(nowMarriedIdx,
						nowMarriedIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</li>");

				nowMarried = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int separatedIdx = resp.indexOf("Separated:");
				String nextString = resp.substring(separatedIdx, separatedIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</li>");

				separated = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int widowedIdx = resp.indexOf("Widowed:");
				String nextString = resp.substring(widowedIdx, widowedIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</li>");

				widowed = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int divorcedIdx = resp.indexOf("Divorced:");
				String nextString = resp.substring(divorcedIdx, divorcedIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</li>");

				divorced = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int popDensityIdx = resp.indexOf("Population density:");
				String nextString = resp.substring(popDensityIdx,
						popDensityIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("<b>");

				popDensity = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int landAreaIdx = resp.indexOf("Land area:");
				String nextString = resp.substring(landAreaIdx, landAreaIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("<b>");

				landArea = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int elevationIdx = resp.indexOf("Elevation:");
				String nextString = resp.substring(elevationIdx, elevationIdx + 40);
				int startIdx = nextString.indexOf(":</b>");
				int endIdx = nextString.indexOf("</p>");

				elevation = nextString.substring(startIdx + 5, endIdx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int per100KIdx = resp.indexOf("per 100,000</td>");
				String nextString = resp.substring(per100KIdx, per100KIdx + 180);
				int startIdx = nextString.indexOf("<td>");
				int endIdx = nextString.indexOf("</tr>");

				if (endIdx > startIdx) {
					String trString = nextString.substring(startIdx + 4, endIdx);
					String[] cols = trString.split("</td><td>");
					homicideRate = cols[cols.length - 1].replace("</td>", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int countyIdx = resp.indexOf("/county");
				String nextString = resp.substring(countyIdx, countyIdx+80);
				int startIdx = nextString.indexOf("\">");
				int endIdx = nextString.indexOf("</a>");

				countyName = nextString.substring(startIdx+2, endIdx);
				System.out.println( countyName );
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Population 2010 = " + population2010 + "; Males = "
					+ maleCount + "; Females = " + femaleCount + "; PopChg = "
					+ populationChg + "; median age = " + medianAge
					+ "; median Household income = " + medianHouseholdIncome
					+ ";  per capita income = " + perCapitaIncome
					+ "; median House Value = " + medianHouseValue
					+ "; median rent = " + medianRent + "; white alone = "
					+ whiteAlone + "; hispanic = " + hispanic + "; black = "
					+ black + "; american indian = " + americanIndian
					+ "; native hawaiian = " + nativeHawaiian
					+ "; cost of living index = " + costLivingIndex
					+ "; high school = " + highSchool + "; bachelors degree = "
					+ bachelorsDegree + "; graduate degree = " + graduateDegree
					+ "; never married = " + neverMarried + "; now married = "
					+ nowMarried + "; separated = " + separated + "; widowed = "
					+ widowed + "; divorced = " + divorced + "; pop density = "
					+ popDensity + "; land area = " + landArea + "; elevation = "
					+ elevation + "; homicide rate = " + homicideRate);

			try {
				synchronized( writer ) {
					writer.write("\"" + cityName + "\",\"" + population2010 + "\",\""
							+ maleCount + "\",\"" + femaleCount + "\",\""
							+ populationChg + "\",\"" + medianAge + "\",\""
							+ medianHouseholdIncome + "\",\"" + perCapitaIncome
							+ "\",\"" + medianHouseValue + "\",\"" + medianRent
							+ "\",\"" + whiteAlone + "\",\"" + hispanic + "\",\""
							+ black + "\",\"" + asian + "\",\"" + americanIndian
							+ "\",\"" + nativeHawaiian + "\",\"" + costLivingIndex
							+ "\",\"" + highSchool + "\",\"" + bachelorsDegree
							+ "\",\"" + graduateDegree + "\",\"" + neverMarried
							+ "\",\"" + nowMarried + "\",\"" + separated + "\",\""
							+ widowed + "\",\"" + divorced + "\",\"" + popDensity
							+ "\",\"" + landArea + "\",\"" + elevation + "\",\""
							+ homicideRate + "\"\n");
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static String getCityName(String url) {
		String[] pieces = url.split("/");
		String last = pieces[pieces.length - 1];
		String[] pieces2 = last.split("\\.");
		return pieces2[0].replaceAll("-", " ");
	}

	public static String NameToUrl(String name) {
		name = name.replaceAll(" ", "-");
		return "http://www.city-data.com/city/" + name + "-California.html";
	}

}
