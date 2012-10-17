package com.mktneutral.citydata.server.batch;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import au.com.bytecode.opencsv.CSVReader;

public class LoadSqlite3 {
	
	private static Connection connection;
	private static Statement statement;
	private static CSVReader reader;
	
	public static void main( String[] args ) throws SQLException, ClassNotFoundException, IOException {
		Class.forName("org.sqlite.JDBC");
		
		connection = DriverManager.getConnection("jdbc:sqlite:war/city_data.sqlite");
		statement = connection.createStatement();
		
		statement.executeUpdate("DROP TABLE IF EXISTS city_data_records");
		statement.executeUpdate("CREATE TABLE city_data_records ( city_name TEXT, county_name TEXT )");
		
		reader = new CSVReader(new FileReader("CityDataRecords.csv"));
		
		String[] line;
		int lineCount = 0;
		while( (line = reader.readNext()) != null ) {
			if ( lineCount > 0 ) {
				try {
					String statementString = "INSERT INTO city_data_records VALUES ( \"" + line[0] + "\", \"" + line[29] + "\" )";
					statement.executeUpdate( statementString );
				} catch ( ArrayIndexOutOfBoundsException e ) {
					// e.printStackTrace();
				}
			}
			lineCount++;
		}
		
		reader.close();
		connection.close();
	}
}
