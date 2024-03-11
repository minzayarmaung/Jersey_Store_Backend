package com.mit.storesystem.Controller.InvoiceController;



import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.InvoiceResponse;
import com.mit.storesystem.Service.InvoiceService.InvoiceService;


@Path("/invoice")
public class InvoiceApi {
	
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
	// Getting Invoice Data with Stock Details
	@GET
	@Path("/getInvoiceWithStockDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceAndStockDataResponse> getInvoiceWithStockDetails(){
		return InvoiceService.getInvoiceWithStockDetails(); 
	}
	// Soft Deleting Function
	@PUT
	@Path("softDelete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InvoiceResponse softDeleteData(@PathParam("id") long id) {
		return InvoiceService.softDelete(id);
	}
	
}