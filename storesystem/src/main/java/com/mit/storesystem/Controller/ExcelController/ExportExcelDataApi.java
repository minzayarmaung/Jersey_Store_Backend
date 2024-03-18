package com.mit.storesystem.Controller.ExcelController;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Workbook;

import com.mit.storesystem.Entity.ExportDTO;
import com.mit.storesystem.Service.ExcelService.ExcelService;
import com.mit.storesystem.Utils.ConnectionDataSource;


@Path("excel")
public class ExportExcelDataApi {
	
	@GET
	@Path("/exportFile")
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public Response exportExcelFie() {
		
		try {
			List<ExportDTO> bothData = fetchInvoiceAndStockData();
			
			Workbook workbook = ExcelService.exportExcelData(bothData);
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			workbook.close();
			
			byte[] bytes = outputStream.toByteArray();
			
			return Response.ok(bytes)
					.header("Content-Disposition", "attachment; filename=\"export.xlsx\"")
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Error Getting Excel File")
				.build();
		} 
		
	}

	private List<ExportDTO> fetchInvoiceAndStockData() {
	    List<ExportDTO> stockData = new ArrayList<>();
	    try (Connection connection = ConnectionDataSource.getConnection();
	         PreparedStatement statement = connection.prepareStatement(
	        		 
	                 "SELECT i.cashier_name, i.date, i.time, i.branch, s.invoice_id, s.name AS stock_name, s.price, s.quantity, s.amount, i.center " 
	                 + "FROM stock s " 
	                 +"INNER JOIN invoice i ON s.invoice_id = i.invoice_id " 
	                 +"WHERE i.status = 'active'"
	        				
	        		 )) {
	    	
	        ResultSet result = statement.executeQuery();
	        while (result.next()) {
	            ExportDTO dto = new ExportDTO();
	            dto.setCashierName(result.getString("cashier_name"));
	            dto.setDate(result.getString("date"));
	            dto.setTime(result.getString("time"));
	            dto.setBranch(result.getString("branch"));
	            dto.setCenter(result.getString("center"));
	            dto.setInvoiceId(result.getLong("invoice_id"));
	            dto.setStockName(result.getString("stock_name"));
	            dto.setStockPrice(result.getFloat("price"));
	            dto.setStockQuantity(result.getInt("quantity"));
	            dto.setAmount(result.getFloat("amount"));
	            dto.setCenter(result.getString("center"));
	            stockData.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return stockData;
	}
}
