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

import com.mit.storesystem.Entity.InvoiceAndStocksDTO;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.StockRequest;
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
		
		String stockInsertQuery = "INSERT INTO stock (invoice_id, name, price, quantity) VALUES (?, ?, ?, ?)";
		for(StockRequest stock : stocks) {
			try(PreparedStatement stockStatement = connection.prepareStatement(stockInsertQuery)){
				stockStatement.setLong(1, invoice.getInvoiceId());
				stockStatement.setString(2, stock.getName());
				stockStatement.setFloat(3, stock.getPrice());
				stockStatement.setInt(4, stock.getQuantity());
				
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
	
}
	
