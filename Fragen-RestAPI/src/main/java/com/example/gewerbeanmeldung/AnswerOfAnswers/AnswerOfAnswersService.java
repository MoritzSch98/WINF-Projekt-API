package com.example.gewerbeanmeldung.AnswerOfAnswers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;

@Service
public class AnswerOfAnswersService {

	@Autowired
	private AnswerOfAnswersRepository aoaRepo;

	//save
	public AnswerOfAnswers addAnswerOfAnswer(AnswerOfAnswers aoa) {
		return aoaRepo.save(aoa);
	}
	public AnswerOfAnswers getAnswerOfAnswerById(Integer id) {
		return aoaRepo.findById(id).orElse(null);
	}
	
	//ändern, löschen, holen 
	
	public String deleteAnswerOfAnswer(AnswerOfAnswers answerOfAnswers) {
		aoaRepo.delete(answerOfAnswers);	
		return "deleted";
	}
}
