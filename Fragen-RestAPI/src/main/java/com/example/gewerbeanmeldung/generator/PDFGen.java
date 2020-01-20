package com.example.gewerbeanmeldung.generator;

import java.io.IOException;
import java.util.List;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Question.Question;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class PDFGen {

	private PDFService pdfService;
	
	public final String Überschrift = "Ausgefüllter Antrag zur Bearbeitung";
	 // Gesprächsbedarf 
	 public static String amt = "Ämter";
	 public String leftTable;
	 public String rightTable;
	// /n Absatz
	public Paragraph _n = new Paragraph("");
	
	public void createPdf(String dest2, List<Answers> aList, List<Question> qList) throws IOException{
		 //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest2);
 
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        formThePDF(pdf, aList, qList);
		
	}
	
	public void formThePDF(PdfDocument pdf, List<Answers> aList, List<Question> qList) {
		
		// Initialize document
        Document document = new Document(pdf, PageSize.A4);
        document.setFontSize(12);
        
        erzeugeÜberschrift(document);
        
       // Setzte drei Absätze
       document.add(_n);
       document.add(_n);
       document.add(_n);
        
        erzeugeTabelle(document, aList, qList);
        
        //Close document
        document.close();
	}
	
	/*
	 * Erzeugt Tabelle
	 * 
	 * @document das Dokument(PDF)
	 * 
	 */
	public Document erzeugeTabelle(Document document, List<Answers> aList, List<Question> qList) {
		// TODO Auto-generated method stub
		Table table = new Table(2);
		table.setTextAlignment(TextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY);
		table = generateTableWithForm(table, aList, qList);
		table.addCell("Antragssteller:");
		table.addCell("Max Musterman");
		return document.add(table);
	}

	
	/*
	 * Erzeugt Überschrift des PDFs 
	 * 
	 * @param document das Dokument(PDF)
	 * 
	 */
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
	}
	
	public Table generateTableWithForm(Table table, List<Answers> aList, List<Question> qList) { 
		for(int i = 0; i < aList.size(); i++) {
			table.addCell(qList.get(i).getQuestion());
			
			//check which type of answer we have, and do appropriate option
			
			//if fileanswer
			if(aList.get(i).getAnswerType().equals("fileanswer")) {
				table.addCell("Bei dieser Frage wurde ein File hochgeladen, welches aktuell noch "
						+ "nicht angezeigt werden kann");
			//of normal answer	
			}else if(aList.get(i).getAnswerType().equals("normal")) {
				String adder = new String();
				for(int k = 0; k <aList.get(i).getAoa().size(); k++) {
					if((k-1) < aList.get(i).getAoa().size()) {
						adder += aList.get(i).getAoa().get(k).getAnswer() + ", ";
					}else {
						adder += aList.get(i).getAoa().get(k).getAnswer();
					}	
					
				}
				table.addCell(adder);
			}else if(aList.get(i).getAnswerType().equals("dateanswer")) {
				table.addCell(aList.get(i).getDateanswer().toString());
			}
			
			
		}
		return table; 
	}

}
