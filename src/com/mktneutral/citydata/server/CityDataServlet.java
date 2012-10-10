package com.mktneutral.citydata.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class CityDataServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		try {
			Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection("jdbc:sqlite:primary.sqlite");
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT 5");
			
			resultSet.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ServletException( e1.getMessage() );
		} catch ( ClassNotFoundException cnfe ) {
			cnfe.printStackTrace();
			throw new ServletException( cnfe.getMessage() );
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException( e.getMessage() );
		}
		
		response.getWriter().write("Here is some text");
	}
}