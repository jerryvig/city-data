package com.mktneutral.citydata.client;

import java.sql.SQLException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.mktneutral.citydata.client.CityDataServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CityData implements EntryPoint {
	/**
	 * This is the entry point method.
	 * 
	 */
	
	private CityDataServiceAsync cityDataService = GWT.create(CityDataService.class);

	
	public void onModuleLoad() {
		
		cityDataService.getCityDataRecords(callback);
		
		RootPanel.get().add( new Label("here is a label") );
		
		
	}
	
	private AsyncCallback<CityDataRecord[]> callback = new AsyncCallback<CityDataRecord[]>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	    	  Window.alert( caught.getMessage() );
	      }

	      public void onSuccess(CityDataRecord[] cityDataRecords) {
	    	  for ( CityDataRecord record : cityDataRecords ) {
	    		  RootPanel.get().add( new Label( record.getCityName() + ", " + record.getCountyName() ) );
	    	  }
	      }
	 };
	
	
}
