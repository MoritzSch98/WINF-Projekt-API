package com.example.gewerbeanmeldung.generator;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

//controller for generating a pdf as a request
@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping(path = "")
public class PDFController {

	@Autowired
	private PDFService pdfService;
	
	//we need to hand a fileId for the pdf
	@RequestMapping(path="/pdfgen/{fileId}")
	public String generatePDF(@PathVariable Integer fileId) throws IOException {	
		return pdfService.generatePDF(fileId);
		
	}

}
