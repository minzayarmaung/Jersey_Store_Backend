package com.mit.storesystem.Service.ExcelService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;
import com.mit.storesystem.Service.StockService.StockService;

public class importExcelService {
	
	public void importData(InputStream fileStream) throws IOException{
		Workbook workbook = new XSSFWorkbook(fileStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		List<StockRequest> stockRequests = new ArrayList<>();
		
		for(Row row : sheet) {
			if(row.getRowNum() == 0) {
				continue;
			}
			
			InvoiceRequest invoiceRequest = readInvoice(row);
			StockRequest stockRequest = readStock(row , invoiceRequest);
			
			InvoiceService.saveInvoiceData(invoiceRequest);
			stockRequests.add(stockRequest);
			
			try {
				StockService.saveStockData(stockRequests);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
			
		workbook.close();
	}
	
		private InvoiceRequest readInvoice(Row row) {
			
			InvoiceRequest invoiceRequest = new InvoiceRequest();
			
			invoiceRequest.setInvoiceId(getLongCellValue(row.getCell(0)));
			invoiceRequest.setCashierName(getStringCellValue(row.getCell(1)));
			invoiceRequest.setDate(getStringCellValue(row.getCell(2)));
			invoiceRequest.setTime(getStringCellValue(row.getCell(3)));
			invoiceRequest.setBranch(getStringCellValue(row.getCell(4)));
			invoiceRequest.setCenter(getStringCellValue(row.getCell(5)));
			
			return invoiceRequest;
	}

		private StockRequest readStock(Row row, InvoiceRequest invoiceRequest) {
			StockRequest stockRequest = new StockRequest();
			stockRequest.setName(getStringCellValue(row.getCell(6)));

			Cell priceCell = row.getCell(7);
		    if (priceCell.getCellType() == CellType.NUMERIC) {
		        stockRequest.setPrice((float) priceCell.getNumericCellValue());
		    } else {
		        throw new IllegalStateException("Price cell is not numeric");
		    }
			
			Cell quantityCell = row.getCell(8);
			if(quantityCell.getCellType() == CellType.NUMERIC) {
				stockRequest.setQuantity((int) quantityCell.getNumericCellValue());
			} else {
				throw new IllegalStateException("Quantity Cell is not Numberic");
			}
			
			Cell amountCell = row.getCell(9);
		    if (amountCell.getCellType() == CellType.NUMERIC) {
		    	stockRequest.setAmount((float) amountCell.getNumericCellValue());
		    } else {
		        throw new IllegalStateException("Amount cell is not numeric");
		    }

			stockRequest.setInvoiceRequest(invoiceRequest);
			
			return stockRequest;
			
	}
		private String getStringCellValue(Cell cell) {
			return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : null;
	}
		private Long getLongCellValue(Cell cell) {
			 return cell.getCellType() == CellType.NUMERIC ? (long) cell.getNumericCellValue() : null; 
	}

}
