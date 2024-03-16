package com.mit.storesystem.Controller.ExcelController;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;
import com.sun.jersey.multipart.FormDataParam;

@Path("/import")
public class ImportExcelDataApi {
	
	public Response uploadExcelFile(@FormDataParam("file") InputStream fileStream) {
		
		try {
			Workbook workbook = WorkbookFactory.create(fileStream);
			
			Sheet sheet = workbook.getSheetAt(0);
			
			Iterator<Row> rowIterator = sheet.iterator();
			if(rowIterator.hasNext()) {
				rowIterator.next();
			}
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				InvoiceRequest invoice = new InvoiceRequest();
				StockRequest stock = new StockRequest();
				
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					
					switch(cell.getCellType()) {
					case STRING:
						String stringValue = cell.getStringCellValue();
						
						if(columnIndex == 1) {
							invoice.setCashierName(stringValue);
						} else if (columnIndex == 2) {
							invoice.setBranch(stringValue);
						} else if(columnIndex == 3) {
							invoice.setCenter(stringValue);
						} else if (columnIndex == 4) {
							invoice.setDate(stringValue);
						} else if (columnIndex == 5) {
							invoice.setTime(stringValue);
						} else if (columnIndex == 6) {
							stock.setName(stringValue);
						}
						break;
					
					case NUMERIC:
						double numericValue = cell.getNumericCellValue();
						if(columnIndex == 0) {
							Long invoiceId = (long) cell.getNumericCellValue();
							System.out.println("Invoice ID : " + invoiceId);
							invoice.setInvoiceId(invoiceId);
						}
						if(columnIndex == 7) {
							stock.setPrice( (float) numericValue);
						} else if (columnIndex == 8) {
							stock.setQuantity((int) numericValue);
						} else if (columnIndex == 9) {
							stock.setAmount((float) numericValue);
						}
						break;
					
					default : 
						System.out.println("Default");
						break;
					}
				}
				
				InvoiceService.saveInvoiceData(invoice);
				
				stock.setInvoice(invoice);
				
			}
			
			workbook.close();
			return Response.ok("File Imported Successfully......").build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to Upload File").build();
		}
		
	}
}
