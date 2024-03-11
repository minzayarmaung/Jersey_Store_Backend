package com.mit.storesystem.Controller.InvoiceController;

import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Entity.StockResponse;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;
import com.mit.storesystem.Service.InvoiceService.StockService;

@Path("/stock")
public class StockApi {
	
	// Getting All Stock Data
	@GET
	@Path("/view_stocks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StockResponse> getAllStockData(){
		return StockService.getAllStockData();	
	}
	
	// Getting Stock Data by StockID
	@GET
	@Path("/details/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public StockResponse findById(@PathParam("id") long id ) {
		return StockService.getStockDataById(id);
	}
	
	// Getting Available Stocks
	@GET
	@Path("/getAvailableStockIds")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StockResponse> getAvailableStockIds(){
		return (List<StockResponse>) StockService.getAvailableStockIds();
	}

}
