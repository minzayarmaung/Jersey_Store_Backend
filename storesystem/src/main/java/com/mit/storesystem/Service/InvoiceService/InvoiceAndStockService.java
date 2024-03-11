package com.mit.storesystem.Service.InvoiceService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.mit.storesystem.Entity.InvoiceAndStockDataRequest;
import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Utils.ConnectionDataSource;

public class InvoiceAndStockService {
	
	private static String filePath = "E:\\Jersey Store System\\Backend\\storesystem\\src\\main\\resources\\images";
	
	public static InvoiceAndStockDataResponse createNewData (InvoiceAndStockDataRequest invoiceAndStockDataRequest) throws IOException {
		
		try(Connection connection = ConnectionDataSource.getConnection()){
			String invoiceInsertQuery = "INSERT INTO invoice (invoice_id , cashier_name, date, time, branch, center) VALUES (?, ?, ?, ?, ?)";
			
			try(PreparedStatement invoiceStatement = connection.prepareStatement(invoiceInsertQuery)) {
				invoiceStatement.setLong(1, invoiceAndStockDataRequest.getInvoiceId());
				invoiceStatement.setString(2, invoiceAndStockDataRequest.getCashierName());
				invoiceStatement.setString(3, invoiceAndStockDataRequest.getDate());
				invoiceStatement.setString(4, invoiceAndStockDataRequest.getTime());
				invoiceStatement.setString(5, invoiceAndStockDataRequest.getBranch());
				invoiceStatement.setString(6, invoiceAndStockDataRequest.getCenter());
				
				int affectedRows = invoiceStatement.executeUpdate();
				
				if(affectedRows == 0) {
					throw new SQLException("Service : Error Creating Invoice Data. No Rows Affected ! ");
				}
			
			String stockInsertQuery = "INSERT INTO stock (invoice_id, name, price, quantity, amount) VALUES (?, ?, ?, ?, ?)";
			
			try(PreparedStatement stockStatement = connection.prepareStatement(stockInsertQuery)) {
				stockStatement.setLong(1, invoiceAndStockDataRequest.getInvoiceId());
				stockStatement.setString(2, invoiceAndStockDataRequest.getName());
				stockStatement.setFloat(3, invoiceAndStockDataRequest.getPrice());
				stockStatement.setInt(4, invoiceAndStockDataRequest.getQuantity());
				stockStatement.setFloat(5, invoiceAndStockDataRequest.getAmount());
				
				int rowsAffected = stockStatement.executeUpdate();
				
				if(rowsAffected == 0) {
					throw new SQLException("Service : Error Creating Stock Data. No Rows Affected ! ");
				}
			}
			
			String filePath = saveImage(invoiceAndStockDataRequest.getFilePath() , invoiceAndStockDataRequest.getInvoiceId());
			return new InvoiceAndStockDataResponse(
					invoiceAndStockDataRequest.getInvoiceId(),
					invoiceAndStockDataRequest.getCashierName(),
					invoiceAndStockDataRequest.getDate(),
					invoiceAndStockDataRequest.getTime(),
					invoiceAndStockDataRequest.getBranch(),
					invoiceAndStockDataRequest.getCenter(),
					null,
					invoiceAndStockDataRequest.getName(),
					invoiceAndStockDataRequest.getPrice(),
					invoiceAndStockDataRequest.getQuantity(),
					invoiceAndStockDataRequest.getAmount(),
					filePath
					);
			}
		} catch (Exception e) {
				e.printStackTrace();
			}
				
		return null;
		
		}
	
	private static String saveImage(InputStream inputStream , Long invoiceId) throws IOException {
	    String fileName = "ProfileImage_"+ invoiceId + ".jpg";
	    Path targetPath = Paths.get(filePath , fileName);
	    try {
	        Files.copy(inputStream , targetPath , StandardCopyOption.REPLACE_EXISTING);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return targetPath.toString();
	}


	}
