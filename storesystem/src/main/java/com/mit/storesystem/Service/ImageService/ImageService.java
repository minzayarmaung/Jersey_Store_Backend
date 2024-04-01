package com.mit.storesystem.Service.ImageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.ws.rs.core.Response;

import org.apache.commons.compress.utils.IOUtils;

import com.sun.jersey.multipart.FormDataBodyPart;

public class ImageService {
	
	// Saving Image 
	private static String filePath = "E:\\Jersey Store System\\Backend\\storesystem\\src\\main\\resources\\images\\";

	public static Response getImage (Long id) throws IOException{
		
		File imageFile = new File(filePath + "ProfileImage_" + id + ".jpg");
		
		if(!imageFile.exists() || !imageFile.isFile()) {
			String defaultImagePath = filePath + "DefaultImage" +".jpg";
			imageFile = new File(defaultImagePath);
			FileInputStream fileInputStream = new FileInputStream(imageFile);
			long contentLength = imageFile.length();
			return Response.ok(fileInputStream).header("Content-Length", String.valueOf(contentLength)).build();
		}
		FileInputStream fileInputStream = new FileInputStream(imageFile);
		long contentLength = imageFile.length();
		
		return Response.ok(fileInputStream).header("Content-Length", String.valueOf(contentLength)).build();		
	}
	
	// Updating Image
	public void updateProfileImage(Long id , FormDataBodyPart formData) throws IOException {
		String fileName = "ProfileImage_" + id + ".jpg";
		InputStream inputStream = formData.getValueAs(InputStream.class);
		byte[] imageData = IOUtils.toByteArray(inputStream);
		Path imagePath = Paths.get(filePath + fileName);
		Files.write(imagePath , imageData);
		
	}
}
