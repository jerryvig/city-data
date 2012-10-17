package com.mktneutral.citydata.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mktneutral.citydata.client.CityDataRecord;
import com.mktneutral.citydata.client.CityDataService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CityDataServiceImpl extends RemoteServiceServlet implements CityDataService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
	public CityDataRecord[] getCityDataRecords() {
		try {
			Class.forName("org.sqlite.JDBC");
		
			connection = DriverManager.getConnection("jdbc:sqlite:city_data.sqlite");
			Statement statement = connection.createStatement();
		
			ResultSet resultSet = statement.executeQuery("SELECT 5");
		
			resultSet.close();
		
			
		
			connection.close();
		
		} catch ( ClassNotFoundException cnfe ) {
			cnfe.printStackTrace();
		} catch ( SQLException sqle ) {
			sqle.printStackTrace();
		}
		
		CityDataRecord[] records = new CityDataRecord[10];
		//mock set of CityDataRecords
		for ( int i=0; i<10; i++ ) {
			CityDataRecord record = new CityDataRecord();
			record.setCityName("Albuquerque");
			record.setCountyName("Bernalillo");
			records[i] = record;
		}
		
		return records;
	}
}
