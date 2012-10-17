package com.mktneutral.citydata.client;

import java.sql.SQLException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cityDataRecords")
public interface CityDataService extends RemoteService {
	public CityDataRecord[] getCityDataRecords();
}
