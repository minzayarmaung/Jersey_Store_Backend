package com.mit.storesystem.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class ConnectionDataSource {
	
	public static Connection getConnection() throws SQLException{
		
		try {
			SQLServerDataSource sql = new SQLServerDataSource();
			sql.setUser("sa");
			sql.setPassword("123");
			sql.setServerName("localhost");
			sql.setPortNumber(1433);
			sql.setDatabaseName("Assignment");
			sql.setTrustServerCertificate(true);
			
			return sql.getConnection();
		} catch (Exception e) {
			System.out.println("Connection Data Source : Error Connecting ........");
			throw new SQLException("Error Connecting to Database" , e);
		}
			
	}

}
