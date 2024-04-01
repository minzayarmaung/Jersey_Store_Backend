package com.mit.storesystem.Service.StockService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Entity.StockResponse;
import com.mit.storesystem.Entity.StockUpdate;
import com.mit.storesystem.Utils.ConnectionDataSource;

public class StockService {
	
	// Saving Stock Data 
	public static void saveStockData(List<StockRequest> stockRequests) throws Exception {
        try (Connection connection = ConnectionDataSource.getConnection()) {
        	connection.setAutoCommit(false);
        
        	 try {
                 for (StockRequest stockRequest : stockRequests) {
                     if (!invoiceExists(stockRequest.getInvoiceRequest().getInvoiceId(), connection)) {
                         throw new SQLException("Invoice with ID " + stockRequest.getInvoiceRequest().getInvoiceId() + " does not exist.");
                     }
                     saveStock(stockRequest, connection);
                 }
                 connection.commit(); // Commit transaction
             } catch (Exception e) {
                 connection.rollback(); // Rollback transaction
                 throw e; // Rethrow exception to be handled by API class
             }
         }
     }
	
		private static boolean invoiceExists(Long invoiceId, Connection connection) throws SQLException {
	        String checkQuery = "SELECT COUNT(*) FROM invoice WHERE invoice_id = ?";
	        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
	            checkStmt.setLong(1, invoiceId);
	            ResultSet rs = checkStmt.executeQuery();
	            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private static void saveStock(StockRequest stockRequest, Connection connection) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO stock (name, price, quantity, amount, invoice_id) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, stockRequest.getName());
            statement.setFloat(2, stockRequest.getPrice());
            statement.setInt(3, stockRequest.getQuantity());
            statement.setFloat(4, stockRequest.getAmount());
            
            Long invoiceId = stockRequest.getInvoiceRequest().getInvoiceId();	
            
            if(invoiceId == null) {
            	throw new IllegalArgumentException("Invoice Id is NULL..");
            }
            statement.setLong(5, invoiceId);
           

            statement.executeUpdate();
        }
    }
	
	// Getting All Stock Data
	public static List<StockResponse> getAllStockData() {
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * from stock")) {
			ResultSet result = statement.executeQuery();
			List<StockResponse> list = new ArrayList<StockResponse>();
			
			while(result.next()) {
				StockResponse response = new StockResponse();
				response.setStockId(result.getLong("stock_id"));
				response.setName(result.getString("name"));
				response.setInvoiceId(result.getLong("invoice_id"));
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
	
	// Getting Stock Data By ID
	public static StockResponse getStockDataById(long id) {
		
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM stock where stock_id = ?")) {
			
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				return new StockResponse(
						result.getLong("stock_id"),
						result.getLong("invoice_id"),
						result.getString("name"),
						result.getInt("quantity"),
						result.getFloat("price"),
						result.getFloat("amount"),
						result.getString("status"));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Getting Available Stock Ids
		public static List<StockResponse> getAvailableStockIds(){
			
			List<StockResponse> list = new ArrayList<StockResponse>();
			try(Connection connection = ConnectionDataSource.getConnection();
					PreparedStatement statement = connection.prepareStatement
							("SELECT stock_id FROM stock WHERE status = 'active'")) {
				
				ResultSet result = statement.executeQuery();
				
				
				while(result.next()) {
					StockResponse response = new StockResponse(result.getLong("stock_id"));
					list.add(response);
				}
				return list;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}  
	
	// Updating Stocks by ID
	public static StockResponse updateStockData(Long id , StockUpdate stockUpdate) {
				
				try(Connection connection = ConnectionDataSource.getConnection();
						PreparedStatement statement = connection.prepareStatement
								("UPDATE stock SET name = ? , price = ? , quantity = ? , amount = ? WHERE stock_id = ?")
						) {
					
					Float amount = stockUpdate.getPrice() * stockUpdate.getQuantity();
					System.out.println(amount);
					
					statement.setString(1, stockUpdate.getName());
					statement.setFloat(2, stockUpdate.getPrice());
					statement.setInt(3,stockUpdate.getQuantity());
					statement.setFloat(4, amount);
					statement.setLong(5, id);
					
				int result = statement.executeUpdate();
				
				if(result > 0) {
					StockResponse response = new StockResponse();
					response.setName(stockUpdate.getName());
					response.setPrice(stockUpdate.getPrice());
					response.setQuantity(stockUpdate.getQuantity());
					response.setAmount(amount);
					return response;
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	// Soft Delete Stock Data
	public static StockResponse softDelete(Long id) {
		try(Connection connection = ConnectionDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement
						("UPDATE stock SET stock.status ='inactive' WHERE stock_id = ?")) {
			
			statement.setLong(1, id);
			
			int affectedRows = statement.executeUpdate();
			
			if(affectedRows > 0 ) {
				return getStockDataById(id);
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
