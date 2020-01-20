package com.example.gewerbeanmeldung.pdfgen;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledController;
import com.example.gewerbeanmeldung.FormFilled.FormFilledRepository;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;
import com.example.gewerbeanmeldung.Question.QuestionService;
import com.example.gewerbeanmeldung.form.FormService;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PDFGeneratorService {

	@Autowired
	private FormFilledService ffService;
	@Autowired
	private QuestionService qService;
	@Autowired
	private FormService fService;
	
	/* public final String DEST = "/Users/moritzschelkle/Desktop/test/Hi.pdf";
	 public final String Überschrift = "Ausgefüllter Antrag zur Bearbeitung";
	 // Gesprächsbedarf 
	 public String amt = "Ämter";
	 public String leftTable;
	 public String rightTable;
	 // /n Absatz
	 public Paragraph _n = new Paragraph("");
	    
	 
	 	public String startPDFGen() throws IOException {
	 		 File file = new File(DEST);
		        file.getParentFile().mkdirs();
		        new PDFGeneratorService().createPdf(DEST);
		        return "Success";
	 	}
	    
	    public void createPdf(String dest) throws IOException {
	        //Initialize PDF writer
	        PdfWriter writer = new PdfWriter(dest);
	 
	        //Initialize PDF document
	        PdfDocument pdf = new PdfDocument(writer);
	        
	        formThePDF(pdf);
	  
	    }

	    
		private void formThePDF(PdfDocument pdf) {
			
			// Initialize document
	        Document document = new Document(pdf, PageSize.A4);
	        document.setFontSize(12);
	        
	        erzeugeÜberschrift(document);
	        
	       // Setzte drei Absätze
	       document.add(_n);
	       document.add(_n);
	       document.add(_n);
	        
	        erzeugeTabelle(document);
	        
	        //Close document
	        document.close();
		}

		
		/*
		 * Erzeugt Tabelle
		 * 
		 * @document das Dokument(PDF)
		 * 
		 */
	/*
		private void erzeugeTabelle(Document document) {
			// TODO Auto-generated method stub
			Table table = new Table(2);
			table.setTextAlignment(TextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY);
			table = generateTableWithForm(8, table);
			document.add(table);
			
		}

		
		/*
		 * Erzeugt Überschrift des PDFs 
		 * 
		 * @param document das Dokument(PDF)
		 * 
		 
		private void erzeugeÜberschrift(Document document) {
			// TODO Auto-generated method stub
			 Paragraph p1 = new Paragraph(Überschrift); 
		        p1.setTextAlignment(TextAlignment.CENTER);
		        p1.setFontSize(14);
		        p1.setBold();
		        document.add(p1);
		        
		     Paragraph p2 = new Paragraph(amt);
		     	p2.setTextAlignment(TextAlignment.CENTER);
		     	p2.setItalic();
		     	document.add(p2);
		}*/
		
		public Table generateTableWithForm(Integer form_id, Table table) {
			System.out.println(form_id);
			FormFilled ff = ffService.getFilledForm(form_id);
			List<Question> qList = new ArrayList<Question>();
			for(int i = 0; i < ff.getAllAnswers().size(); i++) {
				Integer qId = ff.getAllAnswers().get(i).getQuestion_id();
				Question q = qService.getQuestionById(qId);
				qList.add(q);
			}
			List<Answers> aList = ff.getAllAnswers(); 
			
			for(int i = 0; i < ff.getAllAnswers().size(); i++) {
				table.addCell(qList.get(i).getQuestion());
				//table.addCell(aList.get(i).ge)
			}
			return table; 
		}
	}

