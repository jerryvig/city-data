package com.mktneutral.citydata.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CityData implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		RootPanel.getBodyElement().getStyle().setBackgroundColor("black");
		RootPanel.get().add( new Label("here is a label") );
		
		
	}
	
}
