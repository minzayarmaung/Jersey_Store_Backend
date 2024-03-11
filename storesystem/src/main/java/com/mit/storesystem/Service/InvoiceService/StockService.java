package com.mit.storesystem.Service.InvoiceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Entity.StockResponse;
import com.mit.storesystem.Utils.ConnectionDataSource;

public class StockService {
	
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

}
