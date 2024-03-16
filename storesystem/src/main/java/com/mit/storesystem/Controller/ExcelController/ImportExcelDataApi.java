package com.mit.storesystem.Controller.ExcelController;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Service.ExcelService.ExcelService;
import com.mit.storesystem.Service.ExcelService.importExcelService;
import com.mit.storesystem.Service.InvoiceAndStockService.InvoiceAndStockService;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;
import com.mit.storesystem.Service.StockService.StockService;
import com.sun.jersey.multipart.FormDataParam;

@Path("/import")
public class ImportExcelDataApi {
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadExcelFile(@FormDataParam("file") InputStream fileStream) {
		try {
			importExcelService service = new importExcelService();
			service.importData(fileStream);
			return Response.ok("File Imported Successfully....").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Failed to Import...").build();
		}
	}
}
