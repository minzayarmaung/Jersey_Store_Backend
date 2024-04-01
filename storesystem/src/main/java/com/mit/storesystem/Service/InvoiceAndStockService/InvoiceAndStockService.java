package com.mit.storesystem.Service.InvoiceAndStockService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import com.mit.storesystem.Entity.InvoiceAndStockDataRequest;
import com.mit.storesystem.Entity.InvoiceAndStocksDTO;
import com.mit.storesystem.Entity.InvoiceAndStocksDTOWithoutFiles;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Entity.UpdateDataDTO;
import com.mit.storesystem.Utils.ConnectionDataSource;

public class InvoiceAndStockService {
	
	 
	// Saving Both Invoice and Stock Data
	private static final String filePath = "E:\\Jersey Store System\\Backend\\storesystem\\src\\main\\resources\\images\\";
	
	public static void createNewData(InvoiceAndStocksDTO invoice, List<StockRequest> stocks) throws SQLException , IOException{
		try (Connection connection = ConnectionDataSource.getConnection()){
			connection.setAutoCommit(false);
			
			String fileName = "ProfileImage_" + invoice.getInvoiceId() + ".jpg";
			Path path = Paths.get(filePath, fileName);
			Files.createDirectories(path.getParent());
			
			//Files.copy(invoice.getFilePath() , path, StandardCopyOption.REPLACE_EXISTING);
				
		String invoiceInertQuery = "INSERT INTO invoice (invoice_id, cashier_name, date, time, branch, center, filePath) VALUES (?, ?, ?, ?, ?, ?, ?)";	
		try(PreparedStatement invoiceStatement = connection.prepareStatement(invoiceInertQuery)){
			
			invoiceStatement.setLong(1, invoice.getInvoiceId());
			invoiceStatement.setString(2, invoice.getCashierName());
			invoiceStatement.setString(3, invoice.getDate());
			invoiceStatement.setString(4, invoice.getTime());
			invoiceStatement.setString(5, invoice.getBranch());
			invoiceStatement.setString(6, invoice.getCenter());
			invoiceStatement.setString(7, fileName);
			
			int invoiceAffectedRows = invoiceStatement.executeUpdate();
			if(invoiceAffectedRows == 0) {
				throw new SQLException("Service : Error Creating Invoice Data . No Rows Affected ! ");
			}
		}
		
		String stockInsertQuery = "INSERT INTO stock (invoice_id, name, price, quantity , amount) VALUES (?, ?, ?, ? , ?)";
		for(StockRequest stock : stocks) {
			try(PreparedStatement stockStatement = connection.prepareStatement(stockInsertQuery)){
				
				Float amount = stock.getQuantity() * stock.getPrice(); 
				
				stockStatement.setLong(1, invoice.getInvoiceId());
				stockStatement.setString(2, stock.getName());
				stockStatement.setFloat(3, stock.getPrice());
				stockStatement.setInt(4, stock.getQuantity());
				stockStatement.setFloat(5, amount);
				
				int stockAffectedRows = stockStatement.executeUpdate();
				if(stockAffectedRows == 0) {
					 throw new SQLException("Service: Error Creating Stock Data. No Rows Affected!");
				}	
			}
		}
			connection.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	// Updating Invoice and Stock Data
	public static Response updateInvoiceAndStockData(long id ,InvoiceAndStocksDTOWithoutFiles dto, List<StockRequest> stocks) {
		System.out.println("Service - Received Invoice ID for Update : " + dto.getInvoiceId());
		
		System.out.println("Received Stock IDs : "
						+ stocks.stream().map(StockRequest::getStockId).collect(Collectors.toList()));
		
		System.out.println("Service - Invoice Data : " + dto.toString());
		
		System.out.println("Service - Stock Data : " + stocks.toString());
		
		Long invoiceId = id;
		System.out.println("Service - Invoice ID : " +  invoiceId);
		
		
		//Update Invoice
		InvoiceRequest existingInvoice = dto.getInvoiceRequest();
		
		System.out.println("Invoice Data: " + existingInvoice.toString());
		
		updateInvoice(invoiceId, existingInvoice);
		
		// Update Or Adding New Stock
		for(StockRequest stock : stocks) {
				StockRequest newStock = new StockRequest();
				newStock.setName(stock.getName());
				newStock.setQuantity(stock.getQuantity());
				newStock.setPrice(stock.getPrice());
				newStock.setAmount(stock.getPrice() * stock.getQuantity());
				newStock.setInvoice(existingInvoice);
				
				if(stock.getStockId() <= 0) {
				System.out.println("Adding New Stocks : " + newStock);
				saveStock(id ,newStock);
			} else {
				newStock.setStockId(stock.getStockId());
				System.out.println("Updating stock with ID: " + newStock.getStockId());
				System.out.println("Updating Existing Stocks :" + newStock);
				updateStock(newStock);
			}
		}
		
		return Response.ok("Invoice and Stock Data Updated Successfully.").build();
		
	}
	
	public static void saveStock(long id ,StockRequest newStock) {
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("INSERT INTO stock (name, quantity, price, amount, invoice_id) VALUES (?, ?, ?, ?, ?)")) {
			Long invoiceId = id;
			
			Float price = newStock.getPrice();
			int Quantity = newStock.getQuantity();
			Float amount = price * Quantity;
			
			statement.setString(1, newStock.getName());
			statement.setFloat(2, newStock.getPrice());
			statement.setInt(3, newStock.getQuantity());
			statement.setFloat(4, amount);
			statement.setLong(5, invoiceId);
			
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
	            System.out.println("New stock inserted successfully.");
	        } else {
	            System.out.println("Failed to insert new stock.");
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateStock(StockRequest existingStock) {
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("UPDATE stock SET name = ?, quantity = ?, price = ? , amount = ? WHERE stock_id = ?")) {
			
			System.out.println("Executing SQL: UPDATE stock SET name = ?, quantity = ?, price = ? , amount = ? WHERE stock_id = ?");
			System.out.println("Parameters: Name=" + 
								existingStock.getName() + 
								", Quantity=" + existingStock.getQuantity() + 
								", Price=" + existingStock.getPrice() + 
								", Stock ID=" + existingStock.getStockId());
			
	        statement.setString(1, existingStock.getName());
	        statement.setInt(2, existingStock.getQuantity());
	        statement.setFloat(3, existingStock.getPrice());
	        statement.setFloat(4, existingStock.getAmount());
	        statement.setLong(5, existingStock.getStockId());
	        
	        int rowsUpdated = statement.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Stock updated successfully.");
	        } else {
	            System.out.println("Failed to update stock.");
	        }
			
		} catch (SQLException e) {
			System.err.println("Update Failed : "+ e.getMessage());
			System.err.println("SQL State : " + e.getSQLState());
			System.err.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
		public static void updateInvoice(Long id , InvoiceRequest existingInvoice) {
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("UPDATE invoice SET cashier_name = ?, date = ?, time = ?, branch = ?, center = ? WHERE invoice_id = ?");) {
			
		System.out.println("Preparing to update invoice with ID: " + id + 
	                  ", Cashier Name: " + existingInvoice.getCashierName() + 
	                  ", Date: " + existingInvoice.getDate() + 
	                  ", Time: " + existingInvoice.getTime() +
	                  ", Branch: " + existingInvoice.getBranch() + 
	                  ", Center: " + existingInvoice.getCenter());

			
			statement.setString(1, existingInvoice.getCashierName());
			statement.setString(2, existingInvoice.getDate());
			statement.setString(3, existingInvoice.getTime());
			statement.setString(4, existingInvoice.getBranch());
			statement.setString(5, existingInvoice.getCenter());
			statement.setLong(6, id);
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
	            System.out.println("Invoice updated successfully.");
	        } else {
	            System.out.println("Failed to update invoice.");
	        }
			
			
		} catch (SQLException e) {
			System.err.println("Invoice update failed: " + e.getMessage());
	        System.err.println("SQL State: " + e.getSQLState());
	        System.err.println("Error Code: " + e.getErrorCode());
	        e.printStackTrace();
		}
	}
	
}
	
