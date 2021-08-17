package web2021.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import spark.Request;

public class FileService {
	
	public String upload(Request request) throws IOException, ServletException {
		
		File uploadDir = new File("src/main/resources/static/upload");
        
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
		
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
        
        tempFile = tempFile.resolveSibling(tempFile.getFileName() + getExtension(getFileName(request.raw().getPart("file"))));

        try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        
        String[] split = tempFile.toString().split("/");
        String location = "../upload/" + split[split.length-1];
        return location;
    };
    
    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    private String getExtension(String filename) {
    	String extension = FilenameUtils.getExtension(filename);
    	return "." + extension;
    }
}
