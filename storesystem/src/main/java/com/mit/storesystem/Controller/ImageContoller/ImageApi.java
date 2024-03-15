package com.mit.storesystem.Controller.ImageContoller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.xmlbeans.impl.common.IOUtil;

import com.mit.storesystem.Service.ImageService.ImageService;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.FormDataBodyPart;

@Path("images")
public class ImageApi {
	
	private static String filePath = "E:\\Jersey Store System\\Backend\\storesystem\\src\\main\\resources\\images\\";
	
	private ImageService imageService  = new ImageService();
	
	// Getting Image
	@GET
	@Path("/image/{id}")
	@Produces("image/jpeg")
	public Response getImage(@PathParam("id") Long id) throws IOException{
		return ImageService.getImage(id);
	}
	
	// Updating Image
	@PUT
	@Path("/imageUpdate/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateImage(@PathParam("id") long id ,
								@FormDataParam("profileImage") FormDataBodyPart filePath){
		 try {
		       	imageService.updateProfileImage(id, filePath);
		        return Response.ok("Updated Successfully").build();
		    } catch (Exception e) {
		        e.printStackTrace();
		        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to Update Image").build();
		    }
		}
	}

