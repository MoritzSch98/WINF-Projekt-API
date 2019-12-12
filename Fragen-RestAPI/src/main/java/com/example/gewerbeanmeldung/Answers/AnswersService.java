package com.example.gewerbeanmeldung.Answers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswers;
import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswersService;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;

@Service
public class AnswersService {

	@Autowired
	private AnswersRepository answerRepo;
	@Autowired 
	private FormFilledService ffService;
	@Autowired
	private AnswerOfAnswersService aoaService;

	public Answers addAnswer(Answers answer, Integer form_id, Integer question_id) {
		FormFilled ff = ffService.getFilledForm(form_id);
		answer.setQuestion_id(question_id);
		answer.setFormFilled(ff);
		
		List<AnswerOfAnswers> aoaList = answer.getAoa();
		for(int i = 0; i < aoaList.size(); i++) {
			aoaList.get(i).setAnswers(answer);
			aoaService.addAnswerOfAnswer(aoaList.get(i));
			
		}
		return answerRepo.save(answer);
	}
}
