package com.example.gewerbeanmeldung.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Question.Question;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

//class for generating a pdf out of our input answers from a formfilled. This class is there to create instances 
//from, so we can't have Autowired files and get the data from the database through it. Thats why we seperated it from
//the service class
public class PDFGen {

	private PDFService pdfService;
	
	public final String Überschrift = "Ausgefüllter Antrag zur Bearbeitung";
	 public String leftTable;
	 public String rightTable;
	
	public Paragraph _n = new Paragraph("");
	
	public byte[] createPdf(String dest2, List<Answers> aList, List<Question> qList, String formname) throws IOException{
		 //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest2);
 
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        return formThePDF(pdf, aList, qList, formname);
	}
	
	public byte[] formThePDF(PdfDocument pdf, List<Answers> aList, List<Question> qList, String formname) {
		
		// Initialize document
        Document document = new Document(pdf, PageSize.A4);
        document.setFontSize(12);
        
        erzeugeÜberschrift(document, formname);
     
        // set date and time 
        Paragraph datum = new Paragraph("Eingegangen am" + " " +  currentdatum() + "," + " " + currenttime()); 
        datum.setTextAlignment(TextAlignment.RIGHT);
        document.add(datum);
       
       document.add(_n);
       document.add(_n);
       document.add(_n);
        
        erzeugeTabelle(document, aList, qList);
        
        byte[] data = document.getPdfDocument().getWriter().getOutputStream().toString().getBytes();
        //Close document
        document.close();
        return data;
        
	}
	
	//creating a table and put in the data we got earlier from db
	public Document erzeugeTabelle(Document document, List<Answers> aList, List<Question> qList) {
		// TODO Auto-generated method stub
		Table table = new Table(2);
		table.setTextAlignment(TextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY);
		table = generateTableWithForm(table, aList, qList);
		return document.add(table);
	}

	//generating the headline of the document
	private void erzeugeÜberschrift(Document document, String formname) {

		 Paragraph p1 = new Paragraph(Überschrift); 
	        p1.setTextAlignment(TextAlignment.CENTER);
	        p1.setFontSize(14);
	        p1.setBold();
	        document.add(p1);
	        
	     Paragraph p2 = new Paragraph(formname);
	     	p2.setTextAlignment(TextAlignment.CENTER);
	     	p2.setItalic();
	     	document.add(p2);
	}
	
	//generating the table with two columns for the data input
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
	/*
	 *getting the current date of the system
	 */
	 public String currentdatum() {
			 	
		  java.util.Date date = java.util.Calendar.getInstance().getTime();
	        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
	         String datee = dateFormatter.format(date);
	         return datee;
	        
	 }
	 	 
	 
	 /*
	  * getting the current time of the system. We need it to make right timestamps
	  * 
	  */
	 public String currenttime() {
		 
		 java.util.Date datee = java.util.Calendar.getInstance().getTime();
		 SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		 String timee = time.format(datee);
	         return timee;
	 }

}
