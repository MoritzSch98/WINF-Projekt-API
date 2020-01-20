package com.example.gewerbeanmeldung.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;
import com.example.gewerbeanmeldung.Question.QuestionService;

@Service
public class PDFService {

	@Autowired
	private FormFilledService ffService;
	@Autowired
	private QuestionService qService;
	 
	
	public static final String DEST = "/Users/moritzschelkle/Desktop/test/Hi.pdf";
	
	 
	
	
	public String generatePDF() throws IOException{
		 File file = new File(DEST);
	        file.getParentFile().mkdirs();
	        List<Answers> aList = getAllAnswers(9);
	        List<Question> qList = getAllQuestions(9);
	        
	        new PDFGen().createPdf(DEST, aList, qList);
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
}
