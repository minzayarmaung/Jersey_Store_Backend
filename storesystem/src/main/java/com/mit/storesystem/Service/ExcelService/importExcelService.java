package com.mit.storesystem.Service.ExcelService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.sql.rowset.RowSetWarning;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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
			
			if(InvoiceService.getAllInvoiceIdsFromDatabase().contains(invoiceRequest.getInvoiceId())) {
				System.out.println("Skipping Data with Already Contained Invoice ID : " + invoiceRequest.getInvoiceId());
				continue;
			}
			if(!InvoiceService.getValidCenters().contains(invoiceRequest.getCenter())) {
				System.out.println("Skipping Invalid Centers with Invoice ID : " + invoiceRequest.getInvoiceId() + 
						" with Invalid Centers : " + invoiceRequest.getCenter());
				continue;
			}
			
			StockRequest stockRequest = readStock(row , invoiceRequest);
			
			InvoiceService.saveInvoiceData(invoiceRequest);
			stockRequests.add(stockRequest);
			
			try {
				StockService.saveStockData(stockRequests);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				 stockRequests = new ArrayList<>(); 
			}

		}
			
		workbook.close();
	}
	
		private InvoiceRequest readInvoice(Row row) {
			
			InvoiceRequest invoiceRequest = new InvoiceRequest();
			
			invoiceRequest.setInvoiceId(getLongCellValue(row.getCell(0)));
			invoiceRequest.setCashierName(getStringCellValue(row.getCell(1)));
			invoiceRequest.setDate(getStringCellValue(row.getCell(2)));
			
			Time time = new Time(row.getCell(3).getDateCellValue().getTime());
			String formattedTime = formatTime(time);
			invoiceRequest.setTime(getTimeCellValue(String.valueOf(formattedTime)));
			
			invoiceRequest.setBranch(getStringCellValue(row.getCell(4)));
			invoiceRequest.setCenter(getStringCellValue(row.getCell(5)));  
			
			System.out.println("Time : " + time);
			
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
		
		private String getTimeCellValue(String cell) {
			return String.valueOf(cell);
	}
		private String formatTime(Time time) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
			return sdf.format(time);
		}
		
		
		private String getStringCellValue(Cell cell) {
			return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : null;
	}
		
		private Long getLongCellValue(Cell cell) {
		    if (cell == null || cell.getCellType() != CellType.NUMERIC) {
		        return null; 
		    }
		    return (long) cell.getNumericCellValue();
		}

}
