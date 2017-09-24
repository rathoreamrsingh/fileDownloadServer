package com.test.fileDownload.fileDownload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/file")
public class FileDownloadController {
	public static int BUFFER_SIZE = 1024;
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		FileInputStream input = new FileInputStream("/Users/amar/Downloads/AmarSingh.pdf");

		response.setContentType("application/pdf");
		int c = 0;
		byte[] data = new byte[1024];
		/*
		 * while((c = input.read()) != -1){ Base64.encodeInteger(c);
		 * outputStream.write(c); }
		 */

		while ((c = input.read(data, 0, data.length)) != -1) {
			String encodeBase64String = Base64.encodeBase64String(data);
			//outputStream.write(data, 0, c);
			outputStream.write(data, 0, c);
		}
		
		outputStream.flush();
		outputStream.close();
	}
	
	
	@RequestMapping(value = "/download2", method = RequestMethod.POST)
    public void doDownload(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
 
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);
 
        // construct the complete absolute path of the file
        //String fullPath = appPath + filePath;      
        File downloadFile = new File("/Users/amar/Downloads/AmarSingh.pdf");
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = context.getMimeType("/Users/amar/Downloads/AmarSingh.pdf");
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
            for(byte b : buffer) {
            	System.out.print(b + ",");
            }
        }
 
        inputStream.close();
        outStream.close();
 
    }
	
	@RequestMapping(value = "/download2", method = RequestMethod.GET)
    public void doDownloadGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
 
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);
 
        // construct the complete absolute path of the file
        //String fullPath = appPath + filePath;      
        File downloadFile = new File("/Users/amar/Downloads/AmarSingh.pdf");
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = context.getMimeType("/Users/amar/Downloads/AmarSingh.pdf");
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
            System.out.println(buffer);
        }
 
        inputStream.close();
        outStream.close();
 
    }
}
