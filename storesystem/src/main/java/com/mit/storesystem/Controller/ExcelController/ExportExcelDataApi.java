package com.mit.storesystem.Controller.ExcelController;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Workbook;

import com.mit.storesystem.Entity.ExportDTO;
import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Service.ExcelService.ExcelService;
import com.mit.storesystem.Service.PaginationService.PaginationService;
import com.mit.storesystem.Utils.ConnectionDataSource;


@Path("excel")
public class ExportExcelDataApi {
	
	@GET
	@Path("/exportFile")
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public Response exportExcelFie(@QueryParam("page") @DefaultValue("0") int page,
								   @QueryParam("limit") @DefaultValue("10") int limit ,
								   @QueryParam("search") String searchValue) {
		
		try {
			List<InvoiceAndStockDataResponse> paginatedData = fetchDataPerPage(page , limit , searchValue);
			
			Workbook workbook = ExcelService.exportExcelData(paginatedData);
			
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

	private List<InvoiceAndStockDataResponse> fetchDataPerPage(int page , int limit , String searchValue) throws SQLException {
	    
	    int startIndex = page * limit;
	    
	    try (Connection connection = ConnectionDataSource.getConnection();
	         PreparedStatement statement = connection.prepareStatement(
	        		 
	                 "SELECT i.cashier_name, i.date, i.time, i.branch, s.invoice_id, s.name AS stock_name, s.price, s.quantity, s.amount, i.center " 
	                         + "FROM stock s " 
	                         + "INNER JOIN invoice i ON s.invoice_id = i.invoice_id " 
	                         + "WHERE i.status = 'active' "
	                         + "ORDER BY s.invoice_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"
	        		 )) {
	    	
	    			statement.setInt(1, startIndex);
	    			statement.setInt(2, limit );
	    			return PaginationService.findWithPager(startIndex, limit, searchValue);
	        }
	}
		
//		private List<InvoiceAndStockDataResponse> InvoiceAndStockDataResult(ResultSet resultSet) throws SQLException {
//			List<InvoiceAndStockDataResponse> stockData = new ArrayList<>();
//			while(resultSet.next()) {
//				InvoiceAndStockDataResponse dto = new InvoiceAndStockDataResponse();
//	            dto.setCashierName(resultSet.getString("cashier_name"));
//	            dto.setDate(resultSet.getString("date"));
//	            dto.setTime(resultSet.getString("time"));
//	            dto.setBranch(resultSet.getString("branch"));
//	            dto.setCenter(resultSet.getString("center"));
//	            dto.setInvoiceId(resultSet.getLong("invoice_id"));
//	            dto.setName(resultSet.getString("stock_name"));
//	            dto.setPrice(resultSet.getFloat("price"));
//	            dto.setQuantity(resultSet.getInt("quantity"));
//	            dto.setAmount(resultSet.getFloat("amount"));
//	            dto.setCenter(resultSet.getString("center"));
//	            stockData.add(dto);
//			}
//			
//			return stockData;
//			
//		}
		
}
