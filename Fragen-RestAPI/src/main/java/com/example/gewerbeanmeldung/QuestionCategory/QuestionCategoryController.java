package com.example.gewerbeanmeldung.QuestionCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gewerbeanmeldung.Question.Question;

@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping(path = "")
public class QuestionCategoryController {

	@Autowired
	private QuestionCategoryService questionCategoryService;

	// Gets all Questions with specific form-type
	@RequestMapping(path = "/category/{category_id}")
	public List<Question> getByCategory(@PathVariable Integer category_id) {
		return questionCategoryService.getQuestionByCategoryId(category_id);
	}
	
	@RequestMapping(path = "/category/all")
	public List<QuestionCategory> getAllCategories() {
		return questionCategoryService.getAllCategories();
	}
}
