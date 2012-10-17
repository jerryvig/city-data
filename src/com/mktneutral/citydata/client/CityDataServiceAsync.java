package com.mktneutral.citydata.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CityDataServiceAsync {
	void getCityDataRecords(AsyncCallback<CityDataRecord[]> callback);
}
