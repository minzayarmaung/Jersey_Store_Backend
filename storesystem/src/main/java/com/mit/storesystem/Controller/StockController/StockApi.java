package com.mit.storesystem.Controller.StockController;

import java.sql.SQLException;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Entity.StockResponse;
import com.mit.storesystem.Entity.StockUpdate;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;
import com.mit.storesystem.Service.StockService.StockService;

@Path("/stock")
public class StockApi {
	
	// Saving Stock Data
	 @POST
	 @Path("/saveStockData")
	 @Consumes(MediaType.APPLICATION_JSON)
	    public Response saveStockData(List<StockRequest> stockRequests) {
	        try {
	            StockService.saveStockData(stockRequests);
	            return Response.status(Response.Status.CREATED).entity("Stock Data Added Successfully").build();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to Save Stock Data: " + e.getMessage()).build();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.BAD_REQUEST).entity("Error: " + e.getMessage()).build();
	        }
	    }
	
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
	
	// Updating Stock Data By Id
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
