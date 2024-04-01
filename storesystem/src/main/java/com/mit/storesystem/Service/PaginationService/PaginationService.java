package com.mit.storesystem.Service.PaginationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Utils.ConnectionDataSource;

public class PaginationService {
	
	public static List<InvoiceAndStockDataResponse>	findWithPager(int startIndex , int limit , String searchValue) throws SQLException{
		
		try(Connection connection = ConnectionDataSource.getConnection();
			PreparedStatement statement = buildSearchStatement(connection , startIndex , limit , searchValue)){
			
			ResultSet result = statement.executeQuery();
			List<InvoiceAndStockDataResponse> list = new ArrayList<>();
			while(result.next()) {
				InvoiceAndStockDataResponse response = new InvoiceAndStockDataResponse();
				response.setInvoiceId(result.getLong("invoice_id"));
				response.setCashierName(result.getString("cashier_name"));
				response.setDate(result.getString("date"));
				response.setTime(result.getString("time"));
				response.setBranch(result.getString("branch"));
				response.setCenter(result.getString("center"));
				response.setStockId(result.getLong("stock_id"));
				response.setName(result.getString("name"));
				response.setPrice(result.getFloat("price"));
				response.setQuantity(result.getInt("quantity"));
				response.setAmount(result.getFloat("amount"));
				
				list.add(response);
				
			} int totalCount = PaginationService.getTotalItemCount(searchValue);
			  int totalPages = PaginationService.calculateTotalPages(totalCount , limit);
			  
			  
			  return list; 
		}
	}
	
	private static PreparedStatement buildSearchStatement(Connection connection, int startIndex, int limit,
			String searchValue) throws SQLException {
		
		String baseQuery = "SELECT i.invoice_id AS invoice_Id, i.cashier_name AS cashier_name, i.date AS date,"
				+ " i.time AS time, i.branch, i.center, i.status, s.stock_id AS stock_Id, s.name, s.amount, s.quantity, "
				+ "s.price FROM invoice i LEFT JOIN stock s ON i.invoice_id = s.invoice_id";
		String searchCondition = "";
		
		if(searchValue != null && !searchValue.isEmpty()) {
			searchCondition = " WHERE i.invoice_id LIKE ? OR i.cashier_name LIKE ?";
		}
		
		String finalQuery = baseQuery + searchCondition + " ORDER BY i.invoice_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		
		PreparedStatement statement = connection.prepareStatement(finalQuery);
		int paramIndex = 1;
		
		if(searchValue != null && !searchValue.isEmpty()) {
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
		}
		
		statement.setInt(paramIndex++, startIndex);
		statement.setInt(paramIndex++, limit);
		
		return statement;
	}

	// Calculate Total Pages
	public static int calculateTotalPages(int totalItems, int itemsPerPage) {
		return (int) Math.ceil((double) totalItems / itemsPerPage);
	}

	// Total Count Of Items
	public static int getTotalItemCount(String searchValue) throws SQLException {
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = buildCountStatement(connection , searchValue);
				ResultSet result = statement.executeQuery()){
				
				if(result.next()) {
					return result.getInt(1);
				}
			return 0;
		}
	}

	private static PreparedStatement buildCountStatement(Connection connection, String searchValue) throws SQLException {
	    String baseQuery = "SELECT COUNT(*) FROM invoice";
	    String searchCondition = "";

	    if (searchValue != null && !searchValue.isEmpty()) {
	        searchCondition = " WHERE invoice_id LIKE ? OR cashier_name LIKE ?";
	    }

	    String finalQuery = baseQuery + searchCondition;

	    PreparedStatement statement = connection.prepareStatement(finalQuery);
	    int paramIndex = 1;

	    if (searchValue != null && !searchValue.isEmpty()) {
	        statement.setString(paramIndex++, "%" + searchValue + "%");
	        statement.setString(paramIndex++, "%" + searchValue + "%"); 
	    }
	    return statement;
	}


}
