package com.example.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.exception.GtogException;
import com.example.service.ContentProcessorService;

@Controller
public class FileUploadController {
	private static final Logger logger = LogManager.getLogger(FileUploadController.class);

	@Autowired
	ContentProcessorService processorService;

	@GetMapping("gtog")
	public String mymethod() {
		logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("Hey, This is a warning!");
		logger.error("Oops! We have an Error. OK");
		logger.fatal("Damn! Fatal error. Please fix me.");
		return "ready suriya";
	}

	@GetMapping("/")
	public String index(RedirectAttributes redirectAttributes) {
		return "upload";
	}

	@PostMapping("/upload")
	public String fileUpdload(@RequestParam("inputfile") MultipartFile inputfile,
			RedirectAttributes redirectAttributes) {

		InputStream is = null;
		BufferedReader bfReader = null;
		if (inputfile.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:result";
		}

		try {

			if (!inputfile.getOriginalFilename().endsWith(".txt")) {
				redirectAttributes.addFlashAttribute("message", "Please upload text file");
			} else {

				is = processorService.handleValidInput(inputfile, redirectAttributes);
			}

		} catch (IOException e ) {
			e.printStackTrace();
		} catch (GtogException eg) {
			eg.printStackTrace();
		}
		
		finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {

			}
		}
		return "redirect:/result";
	}



	@GetMapping("/result")
	public String result() {
		return "result";
	}
}
