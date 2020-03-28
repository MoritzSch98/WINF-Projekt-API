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
import com.itextpdf.layout.Document;

@Service
public class PDFService {

	@Autowired
	private FormFilledService ffService;
	@Autowired
	private FormService fService;
	@Autowired
	private QuestionService qService;
	
	//generating the pdf apropriately and send an email
	public String generatePDF(Integer fId) throws IOException{
		//getting filename
		String filename = generateFilename(fId);
		//crating a file on it's destination
		String DEST = "savedforms/"+filename+".pdf";
		 File file = new File(DEST);
	        file.getParentFile().mkdirs();
	        //getting the lists of answers and questions, that we can late loop through them and put them into
	        //our table on the pdf
	        List<Answers> aList = getAllAnswers(fId);
	        List<Question> qList = getAllQuestions(fId);
	        
	        //getting the formname
	        String formname = fService.getFormById(ffService.getFilledForm(fId).getForm()).getFormname();
	        //getting the bytearray of the pdf
	        byte[] d = new PDFGen().createPdf(DEST, aList, qList, formname);
	      
	        //send pdf as email
	        SendEmail.sendmail(DEST);
	        
	        return "Success";
	}
	
	//getting all answers from ffService (formfilled)
	public List<Answers> getAllAnswers(Integer form_id){
		return ffService.getFilledForm(form_id).getAllAnswers();
	}
	
	//getting all questions belonging to the answers we have in getAllAnswers method
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
	
	//Generating the finename, making it unique though timestamp
	private String generateFilename(Integer form_id) {
		FormFilled ff = ffService.getFilledForm(form_id);
		String person = ff.getFillingPerson();
		String time = getCurrentTimeUsingDate();
		String form = fService.getFormById(ff.getForm()).getFormname();
		return form+"_"+time+"_"+person;	
	}
	
	//getting the current time using date
	private String getCurrentTimeUsingDate() {
	    Date date = new Date();
	    String strDateFormat = "yyyy:MM:dd:hh:mm:ss a";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate= dateFormat.format(date);
	    return formattedDate;
	}
}
