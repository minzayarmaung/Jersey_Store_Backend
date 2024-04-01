package com.mit.storesystem.Controller.InvoiceAndStockController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.mit.storesystem.Entity.InvoiceAndStocksDTO;
import com.mit.storesystem.Entity.InvoiceAndStocksDTOWithoutFiles;
import com.mit.storesystem.Entity.InvoiceRequest;
import com.mit.storesystem.Entity.StockRequest;
import com.mit.storesystem.Service.InvoiceAndStockService.InvoiceAndStockService;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Path("/invoiceAndStocks")
public class InvoiceAndStockDataApi {

	// Saving Invoice And Stock Data
    @POST
    @Path("/saveInvoiceAndStockData")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveInvoiceAndStocks(@FormDataParam("invoiceAndStocks") String invoiceAndStocksDTOJson,
                                         @FormDataParam("profileImage") InputStream profileImageStream,
                                         @FormDataParam("profileImage") FormDataContentDisposition fileDetail) {
        try {
            // JSON to DTO conversion
            InvoiceAndStocksDTO invoiceAndStocksDTO = new ObjectMapper().readValue(invoiceAndStocksDTOJson, InvoiceAndStocksDTO.class);

            // Validate Invoice ID
            if (invoiceAndStocksDTO.getInvoice().getInvoiceId() == null || invoiceAndStocksDTO.getInvoice().getInvoiceId() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invoice ID is NULL.").build();
            }

            // Save Profile Image
            // Existing checks
            if (profileImageStream != null && fileDetail != null && fileDetail.getFileName() != null && !fileDetail.getFileName().isEmpty()) {
                String filename = "ProfileImage_" + invoiceAndStocksDTO.getInvoice().getInvoiceId() + ".jpg";
                String filePath = saveProfileImage(profileImageStream, filename); // This is now safe to call
            } else {
                System.out.println("Resource : No Profile Image Received.");
            }


            // Saving Invoice and Stock Data
            InvoiceAndStockService.createNewData(invoiceAndStocksDTO.getInvoice(), invoiceAndStocksDTO.getStocks());
            return Response.ok("Saved Invoice and Stock Data Successfully.").build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error Processing Image Upload").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error Occurred While Saving Invoice and Stock Data").build();
        }
    }

    private String saveProfileImage(InputStream inputStream, String fileName) throws IOException {
    	 if (inputStream == null) {
    	        throw new IllegalArgumentException("Input stream cannot be null");
    	    }
        String directoryPath = "E:\\Jersey Store System\\Backend\\storesystem\\src\\main\\resources\\images\\";
        String filePath = directoryPath + fileName;
        java.nio.file.Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }
    
    // Updating Invoice and Stock Data
    @PUT
    @Path("/updateInvoiceAndStockData/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateInvoiceAndStock(@PathParam("id") long id , InvoiceAndStocksDTOWithoutFiles dto) {
    	System.out.println("Received DTO : " + dto);
    	
    	dto.getStocks().forEach(stock -> System.out.println("DTO Stock ID " + stock.getStockId()));
    	
    	System.out.println("Controller - Updating Invoice Data : " + id);
    	System.out.println("Controller - Invoice Data : " + dto.getInvoiceRequest());
    	System.out.println("Controller - Stock Data : " + dto.getStocks());
    	
    	 // Check if the invoice request is not null
    	dto.setInvoiceId(id);
    	
    	List<StockRequest> stocks = dto.getStocks();
    	
    	 Response response = InvoiceAndStockService.updateInvoiceAndStockData(id ,dto, stocks);
    	    
    	 return response;
    	
    }
}
