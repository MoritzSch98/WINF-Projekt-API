package com.example.gewerbeanmeldung.starting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

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
		String formname;
		String q;
		String categoryname;
		try {
			formname = fService.getFormById(form_id).getFormname();
			q = qService.getQuestionById(starting.getQuestionId()).getQuestion();
			categoryname = qcService.getCategoryById(category_id).getCategory();
		}catch(Exception e) {
			return ""+e;
		}
		sRepo.save(starting);
		return "You saved the starting question for \n"
				+ "this Form: "+ formname +", \n"
				+ "this Category: "+ categoryname +". \n"
				+ "The starting question is: " + q + " \n"
				+ "If you have had a previous starting question for this combination, "
				+ "the previous one was updated automatically";
	}

	public Starting getStarting(Integer form_id, Integer category_id) {
		return sRepo.findByFormAndCategory(form_id, category_id);
	}



	
	public List<Starting> getAllStartings() {
		List<Starting> startingList = new ArrayList<>();
		sRepo.findAll().forEach(startingList::add);
		return startingList;
	}
	
	
	
}
