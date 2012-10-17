package com.mktneutral.citydata.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mktneutral.citydata.client.CityDataRecord;
import com.mktneutral.citydata.client.CityDataService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CityDataServiceImpl extends RemoteServiceServlet implements CityDataService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private ArrayList<CityDataRecord> records = new ArrayList<CityDataRecord>();
	
	public CityDataRecord[] getCityDataRecords() {
		try {
			Class.forName("org.sqlite.JDBC");
		
			connection = DriverManager.getConnection("jdbc:sqlite:city_data.sqlite");
			Statement statement = connection.createStatement();
		
			ResultSet resultSet = statement.executeQuery("SELECT * FROM city_data_records");
		
			
			
			while ( resultSet.next() ) {
				CityDataRecord record = new CityDataRecord();
				record.setCityName( resultSet.getString(1) );
				record.setCountyName( resultSet.getString(2) );
				records.add( record );
			}
			
			resultSet.close();
		
			connection.close();
		
		} catch ( ClassNotFoundException cnfe ) {
			cnfe.printStackTrace();
		} catch ( SQLException sqle ) {
			sqle.printStackTrace();
		}
		
		/* CityDataRecord[] records = new CityDataRecord[10];
		//mock set of CityDataRecords
		for ( int i=0; i<10; i++ ) {
			CityDataRecord record = new CityDataRecord();
			record.setCityName("Albuquerque");
			record.setCountyName("Bernalillo");
			record.setMaleCount( 20 );
			records[i] = record;
		} */
		
		CityDataRecord[] returnRecords = new CityDataRecord[1];	
		return records.toArray(returnRecords);
	}
}
