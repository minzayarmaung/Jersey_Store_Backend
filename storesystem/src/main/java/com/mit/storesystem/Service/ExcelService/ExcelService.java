package com.mit.storesystem.Service.ExcelService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.mit.storesystem.Entity.ExportDTO;
import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.PaginationResponse;
import com.mit.storesystem.Entity.StockRequest;

public class ExcelService {
	
	// Export Excel 
	public static Workbook exportExcelData(List<InvoiceAndStockDataResponse> invoiceAndStockData) {
		
		Collections.sort(invoiceAndStockData, Comparator.comparing(InvoiceAndStockDataResponse  ::getInvoiceId));
		
	    Workbook workbook = new XSSFWorkbook();
	    
	    Sheet sheet = workbook.createSheet("CombinedData");
	    
	    String[] headers = {"Invoice ID", "Cashier Name", "Date", "Time", "Branch", "Center",
	            "Stock Name", "Price", "Quantity", "Amount"};

	    createHeaderRow(sheet, headers);

	    int rowNum = 1;
	    for (InvoiceAndStockDataResponse data : invoiceAndStockData) {
	        Row row = sheet.createRow(rowNum++);
	        populateRowWithExportDTO(row, data);
	    }

	    return workbook;
	}

    private static void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }
    private static void populateRowWithExportDTO(Row row, InvoiceAndStockDataResponse data) {

    	if(data.getInvoiceId() != null) {
    	row.createCell(0).setCellValue(data.getInvoiceId());
    	} else {
    		row.createCell(0).setCellValue("");
    	}
    	
        row.createCell(1).setCellValue(data.getCashierName());
        row.createCell(2).setCellValue(data.getDate());
        row.createCell(3).setCellValue(data.getTime()); 
        row.createCell(4).setCellValue(data.getBranch());
        row.createCell(5).setCellValue(data.getCenter());
        row.createCell(6).setCellValue(data.getName());
        row.createCell(7).setCellValue(data.getPrice());
        row.createCell(8).setCellValue(data.getQuantity());
        row.createCell(9).setCellValue(data.getAmount());
        
        System.out.println("Price :" + data.getPrice());
        System.out.println("Quantity :" + data.getQuantity());
        System.out.println("Amount :" + data.getAmount());
    } 
}
