package com.example.gewerbeanmeldung.Question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(path = "")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	// Lists all Questions
	@RequestMapping(path = "/frage")
	public List<Question> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	
	
	// Get a specific Question by ID
	@RequestMapping(path = "frage/{id}")
	public Question getQuestionById(@PathVariable Integer id) {
		return questionService.getQuestionById(id);
	}

	// Gets all Questions with specific form-type
	@RequestMapping(path = "type/{form_id}")
	public List<Question> getByFormType(@PathVariable Integer form_id) {
		return questionService.getByFormType(form_id);
	}

	// Get all questions within a formtype which have a specific category
	@RequestMapping(path = "/type/{form_id}/category/{category_id}")
	public List<Question> getAllQuestionsOfFormTypeWithinCategory(@PathVariable Integer form_id,
			@PathVariable Integer category_id) {

		return questionService.getAllQuestionsOfFormTypeWithinCategory(form_id, category_id);
	}
	
	// Add a new Question
	@RequestMapping(method = RequestMethod.POST, value = "frage/add")
	public String saveQuestion(@RequestBody Question question) {
		return questionService.saveQuestion(question);
	}

	// Adds the following question to an existing question
	@RequestMapping(method = RequestMethod.PUT, value = "frage/{id}/addfollowing")
	public String addFollowingQuestion(@PathVariable Integer id, @RequestBody Question question) {
		return questionService.addFollowingQuestion(id, question);
	}

	// Edit a specific Question
	@RequestMapping(method = RequestMethod.PUT, value = "frage/{id}/edit")
	public Question editQuestion(@PathVariable Integer id, @RequestBody Question question) {
		return questionService.editQuestion(question, id);
	}

	// Deletes a specific Question
	@RequestMapping(method = RequestMethod.DELETE, value = "frage/{id}/delete")
	public String deleteQuestionById(@PathVariable Integer id) {	
		return questionService.deleteQuestionById(id);
	}

}
