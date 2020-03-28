package com.example.gewerbeanmeldung.QuestionType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gewerbeanmeldung.Choices.Choices;

//The service for the logic of the questiontype entity. 
@Service
public class QuestionTypeService {

	@Autowired
	private QuestionTypeRepository questionTypeRepo;

	//Listing all choices of a specific question and questionType
	public List<Choices> getAllChoices(Integer question_id) {
		List<Choices> choices = new ArrayList<>();
		choices = questionTypeRepo.findById(question_id).orElse(null).getChoices();
		return choices;
	}
	//edit the questiontype
	public void editQuestionType(QuestionType qt) {
		questionTypeRepo.save(qt);
	}

}
