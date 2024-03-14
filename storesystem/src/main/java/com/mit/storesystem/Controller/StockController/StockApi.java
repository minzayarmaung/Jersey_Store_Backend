package com.mit.storesystem.Controller.StockController;

import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Entity.StockResponse;
import com.mit.storesystem.Entity.StockUpdate;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;
import com.mit.storesystem.Service.StockService.StockService;

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
	
	// Soft Delete Function
	@PUT
	@Path("/softDelete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public StockResponse softDeleteData(@PathParam("id") long id) {
		return StockService.softDelete(id);
	}
	
	// Updating Stock Data
	@PUT
	@Path("/updateStockData/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStockData(@PathParam("id") Long id , StockUpdate stockUpdate) {
		
		try {
			StockResponse stockResponse = StockService.updateStockData(id, stockUpdate);
			if(stockResponse != null ) {
				return Response.status(Response.Status.OK).entity(stockResponse).build();
			}else {
				return Response.status(Response.Status.NOT_FOUND).entity("Stock with ID " + id + " Not Found.").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to Update Stock Data").build();  
		}
		
	}
	

}
