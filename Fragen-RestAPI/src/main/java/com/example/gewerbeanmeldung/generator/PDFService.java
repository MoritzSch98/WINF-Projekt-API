package com.example.gewerbeanmeldung.generator;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;
import com.example.gewerbeanmeldung.Question.QuestionService;
import com.example.gewerbeanmeldung.form.FormService;
import com.example.gewerbeanmeldung.pdffile.PdfFileService;
import com.itextpdf.layout.Document;

@Service
public class PDFService {

	@Autowired
	private FormFilledService ffService;
	@Autowired
	private FormService fService;
	@Autowired
	private QuestionService qService;
	@Autowired
	private PdfFileService pdffService;
	
	
	public String generatePDF(Integer fId) throws IOException{
		String filename = generateFilename(fId);
		String DEST = "localhost:8090/"+filename+".pdf";
		 File file = new File(DEST);
	        file.getParentFile().mkdirs();
	        List<Answers> aList = getAllAnswers(fId);
	        List<Question> qList = getAllQuestions(fId);
	        
	        String formname = fService.getFormById(ffService.getFilledForm(fId).getForm()).getFormname();
	        
	        byte[] d = new PDFGen().createPdf(DEST, aList, qList, formname);
	        pdffService.storeFile(d, fId, filename);
	        SendEmail.sendmail(DEST);
	        
	        return "Success";
	}
	
	public List<Answers> getAllAnswers(Integer form_id){
		return ffService.getFilledForm(form_id).getAllAnswers();
	}
	
	public List<Question> getAllQuestions(Integer form_id){
		FormFilled ff = ffService.getFilledForm(form_id);
		List<Question> qList = new ArrayList<Question>();
		for(int i = 0; i < ff.getAllAnswers().size(); i++) {
			Integer qId = ff.getAllAnswers().get(i).getQuestion_id();
			Question q = qService.getQuestionById(qId);
			qList.add(q);
		}
		return qList;
	}
	
	private String generateFilename(Integer form_id) {
		FormFilled ff = ffService.getFilledForm(form_id);
		String person = ff.getFillingPerson();
		String time = getCurrentTimeUsingDate();
		String form = fService.getFormById(ff.getForm()).getFormname();
		return form+"_"+time+"_"+person;	
	}
	
	private String getCurrentTimeUsingDate() {
	    Date date = new Date();
	    String strDateFormat = "yyyy:MM:dd:hh:mm:ss a";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate= dateFormat.format(date);
	    return formattedDate;
	}
}
