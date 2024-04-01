package com.mit.storesystem.Controller.PaginationController;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mit.storesystem.Entity.InvoiceAndStockDataResponse;
import com.mit.storesystem.Entity.PaginationResponse;
import com.mit.storesystem.Service.PaginationService.PaginationService;

@Path("/table")
public class PaginationApi {

	@GET
	@Path("/findWithPager")
	@Produces(MediaType.APPLICATION_JSON)
	public PaginationResponse findWithPager(@QueryParam("page") @DefaultValue("0")int page , 
			@QueryParam("limit") @DefaultValue("2") int limit , 
			@QueryParam("search") String searchValue) throws SQLException {
		
		int startIndex = (page) * limit ;
		List<InvoiceAndStockDataResponse> items = PaginationService.findWithPager(startIndex, limit, searchValue);
		int totalCount = PaginationService.getTotalItemCount(searchValue);
		int totalPages = PaginationService.calculateTotalPages(totalCount, limit);	
		PaginationResponse response = new PaginationResponse(items, totalCount, totalPages);
		
		return response;
	}
}
 