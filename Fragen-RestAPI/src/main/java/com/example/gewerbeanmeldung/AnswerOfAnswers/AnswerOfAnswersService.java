package com.example.gewerbeanmeldung.AnswerOfAnswers;


import java.util.List;

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
	public AnswerOfAnswers addAnswerOfAnswers(AnswerOfAnswers aoa) {
		return aoaRepo.save(aoa);
	}
	//get by id
	public AnswerOfAnswers getAnswerOfAnswersById(Integer id) {
		return aoaRepo.findById(id).orElse(null);
	}
	//list all through answer-id
	public List<AnswerOfAnswers> getAllAnswerOfAnswersByAnswerId(Integer answersId){
		return aoaRepo.findAllByAnswerId(answersId);
	}
	 
	//edit 
	public AnswerOfAnswers editAnswerOfAnswers(AnswerOfAnswers aoa) {
		return aoaRepo.save(aoa);
	}
	
	//delete
	public String deleteAnswerOfAnswers(AnswerOfAnswers aoa) {
		aoaRepo.delete(aoa);	
		return "deleted";
	}
}
