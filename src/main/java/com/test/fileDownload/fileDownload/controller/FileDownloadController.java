package com.test.fileDownload.fileDownload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:8081/","http://localhost:8081" }, maxAge = 3600)
@RequestMapping(value = "/file")
public class FileDownloadController {
	public static int BUFFER_SIZE = 1026;
	public static String FILE_PATH = "/Users/amar/Downloads/a.pdf";
	public static String LARGE_FILE_NAME = "/Users/amar/Downloads/The.Boss.Baby.mkv";

	@RequestMapping(value = "/downloadViaGet", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public void downloadFileViaGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(FILE_PATH);

		try (OutputStream out = response.getOutputStream()) {
			Path path = file.toPath();
			Files.copy(path, out);
		} catch (IOException e) {
			throw e;
		}
	}

	@RequestMapping(value = "/downloadViaPost", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public void downloadFileViaPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(FILE_PATH);

		try (OutputStream out = response.getOutputStream()) {
			Path path = file.toPath();
			Files.copy(path, out);
		} catch (IOException e) {
			throw e;
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void doDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getServletContext();
		String appPath = context.getRealPath("");
		System.out.println("appPath = " + appPath);

		// construct the complete absolute path of the file
		// String fullPath = appPath + filePath;
		File downloadFile = new File(LARGE_FILE_NAME);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(LARGE_FILE_NAME);
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
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		int output = 0;
		while ((bytesRead = inputStream.read(buffer)) != -1) {

			outStream.write(buffer, 0, bytesRead);
			// for(byte b : buffer) {
			// System.out.print(b + ",");
			// }
			output += buffer.length;
		}
		
		inputStream.close();
		outStream.close();

	}

}
