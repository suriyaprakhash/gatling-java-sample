package com.example.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.exception.GtogException;
import com.example.processor.ContentProcessor;

@Service
public class ContentProcessorService {
	
	public InputStream handleValidInput(MultipartFile inputfile, RedirectAttributes redirectAttributes)
			throws IOException, GtogException {
		InputStream is;
		BufferedReader bfReader;
		// Get the file and save it somewhere
		byte[] bytes = inputfile.getBytes();

		is = new ByteArrayInputStream(bytes);
		bfReader = new BufferedReader(new InputStreamReader(is));
		String inputLine = null;
		StringBuilder data=new StringBuilder("");
		ContentProcessor contentProcessor=new ContentProcessor();
		contentProcessor.setUpData();
		while ((inputLine = bfReader.readLine()) != null) {
			contentProcessor.classifyInput(inputLine);
			data.append(inputLine).append("/n");
		}
		contentProcessor.processDirectData();
		contentProcessor.processIndirectData();
		contentProcessor.processDirectQuestion();
		contentProcessor.processIndirectQuestion();
		contentProcessor.print();
		
		redirectAttributes.addFlashAttribute("message",
				data);
		return is;
	}

}
