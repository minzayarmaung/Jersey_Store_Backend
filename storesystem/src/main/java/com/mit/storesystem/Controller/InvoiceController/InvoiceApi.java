package com.mit.storesystem.Controller.InvoiceController;



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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;


@Path("/invoice")
public class InvoiceApi {
	
	// Saving Invoice Data
	@POST
	@Path("/saveInvoiceData")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setInvoiceData(InvoiceRequest invoiceRequest) {
		
		try {
			InvoiceService.saveInvoiceData(invoiceRequest);
			return Response.ok("Invoice Data Saved Successfully !").build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Failed to Save Invoice Data").build();
		}
	}
	
	// Getting All Invoice Data
	@GET
	@Path("getAllInvoiceData")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceResponse> getInvoiceData() throws SQLException{
		return InvoiceService.getAllInvoiceData();
	}
	// Getting Invoice Data By ID
	@GET
	@Path("/details/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InvoiceResponse findBydId(@PathParam("id") long id) throws SQLException {
		return InvoiceService.getInvoiceDataById(id);
	}
	// Getting Available InvoiceIds 
	@GET
	@Path("/getAvailableInvoiceIds")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceResponse> getAvailableInvoiceIds(){
		return (List<InvoiceResponse>) InvoiceService.getAvailableInvoiceIds(); 
	}
	// Getting All Invoice IDs from the Database
	@GET
	@Path("/getAllInvoiceIdsfromDatabase")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Long> getAllInvoiceIdsfromDatabase(){
		return InvoiceService.getAllInvoiceIdsFromDatabase();
	}
	// Getting Invoice Data with Stock Details
	@GET
	@Path("/getInvoiceWithStockDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceAndStockDataResponse> getInvoiceWithStockDetails(){
		return InvoiceService.getInvoiceWithStockDetails(); 
	}
	// Getting Valid Centers 
	@GET
	@Path("/validateCenters")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getValidCenters(){
		return InvoiceService.getValidCenters(); 
	}
	// Soft Deleting Function
	@PUT
	@Path("softDelete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InvoiceResponse softDeleteData(@PathParam("id") long id) {
		return InvoiceService.softDelete(id);
	}
	// Get Invoice Data with Stock Details by ID
	@GET
	@Path("/getInvoiceWithStockDataById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvoiceWithStockDataById(@PathParam("id") long id) {
		
		try {
			InvoiceAndStockDataResponse response = InvoiceService.getInvoiceAndStockDataById(id);
			if(response != null) {
				
				return Response.ok(response)
						.build();		
			}else {
				 return Response.status(Response.Status.NOT_FOUND).entity("Invoice with ID " + id + " not found").build();
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
			 return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to fetch invoice data").build();
		}
		
	}
	
}