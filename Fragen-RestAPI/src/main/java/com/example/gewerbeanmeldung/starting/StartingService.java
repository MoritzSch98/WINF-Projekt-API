package com.example.gewerbeanmeldung.starting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Question.QuestionService;
import com.example.gewerbeanmeldung.QuestionCategory.QuestionCategoryService;
import com.example.gewerbeanmeldung.form.FormService;

@Service
public class StartingService {

	@Autowired
	private StartingRepository sRepo;
	@Autowired 
	private FormService fService;
	@Autowired
	private QuestionCategoryService qcService;
	@Autowired 
	private QuestionService qService;

	public String addStarting(Starting starting, Integer form_id, Integer category_id) {
		starting.setFormId(form_id);
		starting.setQuestionCategoryId(category_id);
		sRepo.save(starting);
		String formname = fService.getFormById(form_id).getFormname();
		String q = qService.getQuestionById(starting.getQuestionId()).getQuestion();
		String categoryname = qcService.getCategoryById(category_id).getCategory();
		return "You saved the starting question for this "+ formname +" and category "+
				categoryname +". The starting question is: " + q;
	}
	
	
	
}
