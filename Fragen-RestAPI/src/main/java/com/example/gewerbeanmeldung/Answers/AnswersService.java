package com.example.gewerbeanmeldung.Answers;

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
		
		
		if(ffService.checkFormExisting(form_id)) {
			 FormFilled ff = ffService.getFilledForm(form_id);
			 answer.setFormFilled(ff);
		}else {
			return "The form you try to add an answer to, is not existing, please add the form first";
		}
		if(checkAnswerForQuestionIdExisting(question_id)) {
			return "this answer is alreay existing for this form. You can't add it twice, but you can edit";
		}
		
		if(qService.checkIfQuestionExists(question_id)) {
			answer.setQuestion_id(question_id);
			answer = findAnswerType(answer, question_id);
		}else {
			return "This question you try to add an answer to, is not existing!";
		}
		
		
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
		Integer nextQuestion = changesQuestionFlow(answer, question_id);
		return "You saved the answer successful, the next Question is the question with the Id: "+ nextQuestion;
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
	
	public boolean checkAnswerForQuestionIdExisting(Integer question_id) {
		Answers a = answerRepo.findByQuestionId(question_id);
		if(a == null) {
			return false;
		}else {
			return true;
		}
	}
	
	
	//Looks if the question flow is changed and returns the next questionid, 
	//which needs to be displayed
	public Integer changesQuestionFlow(Answers a, Integer question_id) {
		
		Integer nextQuestion = null;
		Question q = qService.getQuestionById(question_id);
		
		//Checks if we need to look for the NextQuestionId within the chosen choice
		//Indirectly we now know that this question is a question, where we can chose only one
		//out of many options. Multi select or Date or File upload is not allowed
		//so we just need to look at the very first element of aoa within the answer to get the made input
		//also its not a free text input. Has to be Dropdown or RadioButton
		if(q.getQuestionType().getNextQuestionId() == -1) {
			String input = a.getAoa().get(0).getAnswer();
			int i = 0;
			
			//Try to find the matching choice for the answer and saves the nextQuestion of it, 
			//to be returned soon
			while(i < q.getQuestionType().getChoices().size()) {
				if(q.getQuestionType().getChoices().get(i).getChoice().equals(input)) {
					nextQuestion = q.getQuestionType().getChoices().get(i).getNextQuestionId();
				}
				i++;
			}
		}else {
			nextQuestion = q.getQuestionType().getNextQuestionId();
		}
		
		return nextQuestion;
	}
	
}
