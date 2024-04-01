package com.mit.storesystem.Service.InvoiceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Utils.ConnectionDataSource;

public class InvoiceService {
	
	// Saving Invoice Data
	public static void saveInvoiceData(InvoiceRequest invoiceRequest) {
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement  = connection.prepareStatement
						("INSERT INTO invoice (invoice_Id, cashier_name, date, time, branch, center) VALUES (?, ?, ?, ?, ?, ?)") ) {
			
			statement.setLong(1, invoiceRequest.getInvoiceId());
			statement.setString(2, invoiceRequest.getCashierName());
			statement.setString(3, invoiceRequest.getDate());
			statement.setString(4, invoiceRequest.getTime());
			statement.setString(5, invoiceRequest.getBranch());
			statement.setString(6, invoiceRequest.getCenter());
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	// Getting All Invoice Data 
	public static List<InvoiceResponse> getAllInvoiceData() throws SQLException{
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("SELECT invoice_id , cashier_name , date , time , branch , center FROM invoice")) {
			ResultSet result = statement.executeQuery();
			List<InvoiceResponse> list = new ArrayList<InvoiceResponse>();
			
			while(result.next()) {
				InvoiceResponse response = new InvoiceResponse();
				response.setInvoiceId(result.getLong("invoice_id"));
				response.setCashierName(result.getString("cashier_name"));
				response.setDate(result.getString("date"));
				response.setTime(result.getString("time"));
				response.setBranch(result.getString("branch"));
				response.setCenter(result.getString("center"));
				
				list.add(response);
			}
			return list;
			
		} catch (SQLException e) {
			throw new SQLException("Error Getting Data from Database." , e.getMessage() , e);
		}
	}
	
	// Getting Invoice Data By Invoice ID
	public static InvoiceResponse getInvoiceDataById(long id) throws SQLException {
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("select * from invoice where invoice_id = ?")) {
			
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) { 
				return new InvoiceResponse(
						result.getLong("invoice_id"),
						result.getString("cashier_name"),
						result.getString("date"),
						result.getString("time"),
						result.getString("branch"),
						result.getString("center"),
						result.getString("status"),
						result.getString("filePath"));
			}
			
		} catch (SQLException e) {
			throw new SQLException("Error Getting Data by ID from Invoice." , e.getMessage() , e);
		}
		
		return null;	
	}
	
	// Getting All Invoice Ids From the Database to check Validation
	public static List<Long> getAllInvoiceIdsFromDatabase(){
		List<Long> list = new ArrayList<>();
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("SELECT invoice_id from invoice")) {
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				long invoiceIds = result.getLong("invoice_id");
				list.add(invoiceIds);
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Getting Available Invoice Ids
	public static List<InvoiceResponse> getAvailableInvoiceIds() {
		
		List<InvoiceResponse> list = new ArrayList<InvoiceResponse>();
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("SELECT invoice_id FROM invoice WHERE status = 'active'")) {
			
			ResultSet result = statement.executeQuery();
			
			
			while(result.next()) {
				InvoiceResponse response = new InvoiceResponse();
				response.setInvoiceId(result.getLong("invoice_id"));
				list.add(response);
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	
	// Getting Invoice Data with Stock Details
	public static List<InvoiceAndStockDataResponse> getInvoiceWithStockDetails() {
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("SELECT i.invoice_id AS invoice_Id, i.cashier_name AS cashier_name, i.date AS date, i.time AS time, i.branch, i.center, i.status, s.stock_id AS stock_Id, s.name, s.amount, s.quantity, s.price FROM invoice i LEFT JOIN stock s ON i.invoice_id = s.invoice_id")) {
			
			ResultSet result = statement.executeQuery();
			List<InvoiceAndStockDataResponse> list = new ArrayList<InvoiceAndStockDataResponse>();
			
			while(result.next()) {
				InvoiceAndStockDataResponse response = new InvoiceAndStockDataResponse();
				response.setInvoiceId(result.getLong("invoice_Id"));
				response.setCashierName(result.getString("cashier_name"));
				response.setDate(result.getString("date"));
				response.setTime(result.getString("time"));
				response.setBranch(result.getString("branch"));
				response.setCenter(result.getString("center"));
				response.setStockId(result.getLong("stock_Id"));
				response.setName(result.getString("name"));
				response.setPrice(result.getFloat("price"));
				response.setQuantity(result.getInt("quantity"));
				response.setAmount(result.getFloat("amount"));
				response.setStatus(result.getString("status"));
			
			
				list.add(response);
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}
	
	// Soft Delete Invoice Data
	public static InvoiceResponse softDelete(long id) {
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("UPDATE invoice SET status = 'inactive' WHERE invoice_id = ?")) {
			
			statement.setLong(1, id);
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected > 0) {
				return getInvoiceDataById(id);
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}   
	
	// Getting Invoice Data with Stock Details By ID 
	public static InvoiceAndStockDataResponse getInvoiceAndStockDataById(long id) throws SQLException{
		List<StockRequest> stockItems = new ArrayList<>();
		InvoiceAndStockDataResponse response = null;
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("SELECT i.cashier_name, i.date, i.time, i.branch, i.center,s.stock_id , s.name, s.price, s.quantity, s.status FROM invoice i JOIN stock s ON i.invoice_id = s.invoice_id WHERE i.invoice_id = ?")){
		
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				if(response == null) {
					response = new InvoiceAndStockDataResponse();
					response.setInvoiceId(id);
					response.setCashierName(result.getString("cashier_name"));
					response.setDate(result.getString("date"));
					response.setTime(result.getString("time"));
					response.setBranch(result.getString("branch"));
					response.setCenter(result.getString("center"));
					response.setStocks(new ArrayList<>());
				}
				
				StockRequest stockRequest = new StockRequest();
				stockRequest.setStockId(result.getLong("stock_id"));
				stockRequest.setName(result.getString("name"));
				stockRequest.setPrice(result.getFloat("price"));
				stockRequest.setQuantity(result.getInt("quantity"));
				stockRequest.setStatus(result.getString("status"));
				
				response.getStocks().add(stockRequest);
			} 	
		}
		return response;
	}

	public static List<String> getValidCenters() {
        String[] centers = {"Hleden Center", "City Mall", "Junction Square", "Junction City", "Ga Mhone Pwint Center"};
        List<String> centerList = new ArrayList<>();
        for(String center : centers) {
        	centerList.add(center);
        }
        return centerList; 
        // Figured out Another way : return Array.asList(centers);
	}
}