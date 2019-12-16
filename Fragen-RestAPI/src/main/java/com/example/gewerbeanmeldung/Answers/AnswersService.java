package com.example.gewerbeanmeldung.Answers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswers;
import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswersService;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;
import com.example.gewerbeanmeldung.Question.QuestionService;

@Service
public class AnswersService {

	@Autowired
	private AnswersRepository answerRepo;
	@Autowired 
	private FormFilledService ffService;
	@Autowired
	private AnswerOfAnswersService aoaService;
	@Autowired
	private QuestionService qService;

	public String addAnswer(Answers answer, Integer form_id, Integer question_id) {
		
		if(checkAnswerForQuestionExisting(question_id)) {
			return "this answer is alreay existing. You can't add it twice, but you can edit";
		}
		
		FormFilled ff = ffService.getFilledForm(form_id);
		answer.setQuestion_id(question_id);
		answer = findAnswerType(answer, question_id);
		answer.setFormFilled(ff);
		
		List<AnswerOfAnswers> aoaList = answer.getAoa();
		for(int i = 0; i < aoaList.size(); i++) {
			aoaList.get(i).setAnswers(answer);
			aoaService.addAnswerOfAnswer(aoaList.get(i));
			
		}
		try {
			answerRepo.save(answer);
		}catch(Exception e) {
			return e.getMessage();
		}
		return "You saved the answer successful";
	}
	
	public Answers getAnswer(Integer answerId) {
		Answers a = answerRepo.findById(answerId).orElse(null);
		if(a == null) {
			throw new NullPointerException("The answer you try to get through the PathVariable is not available"); 
		}
		
		return a;
	}
	
	
	//This method is going to set the correct answertype of the question and if some other answers are in it as well,
	//it is removing them
	public Answers findAnswerType(Answers answer, Integer question_id) {
		
		String answerType = "normal";
		
		//Gets the Type of the Question to look which answer type we have to set
		String type = qService.getQuestionById(question_id).getQuestionType().getType();
		
		//Searches which answertype is right and sets it
		if(type.equals("Datum")) {
			answerType = "dateanswer";
		}else if(type.equals("Datei-Upload")) {
			answerType = "fileanswer";
		}
		answer.setAnswerType(answerType);
		removeIrrelevantAnswertypes(answer);
		
		return answer;
	}
	
	
	//Removes not needed answers for not needed answertype
	public Answers removeIrrelevantAnswertypes(Answers a) {
		String answerType = a.getAnswerType();
		if(answerType.equals("fileanswer")) {
			if(a.getAoa() != null) {
				a.setAoa(null);
			}
			if(a.getDateanswer() != null) {
				a.setDateanswer(null);
			}
		}else if(answerType.equals("dateanswer")) {
			if(a.getAoa() != null) {
				a.setAoa(null);
			}
			if(a.getFileanswer() != null) {
				a.setFileanswer(null);
			}
		}else {
			a.setFileanswer(null);
			a.setDateanswer(null);
		}
		
		return a;
	}
	
	public boolean checkAnswerForQuestionExisting(Integer question_id) {
		Answers a = answerRepo.findByQuestionId(question_id);
		if(a == null) {
			return false;
		}else {
			return true;
		}
	}
	
}
