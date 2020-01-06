package com.example.gewerbeanmeldung.Answers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.gewerbeanmeldung.Question.Question;

@RestController
@RequestMapping(path = "")
public class AnswersController {

	@Autowired
	private AnswersService answerService;

	// Posts an Answer to a Filled Form
	@RequestMapping(method = RequestMethod.POST, path = "forms/{form_id}/question/{question_id}/answers/add")
	public String addAnswerToForm(@RequestBody Answers answer, @PathVariable Integer form_id,@PathVariable Integer question_id) {
		return answerService.addAnswer(answer,form_id, question_id);
	}
	// Posts all Answers to a Filled Form
	@RequestMapping(method = RequestMethod.POST, path = "forms/{form_id}/answers/all/add")
	public String addAllAnswersToForm(@RequestBody List<Answers> answers, @PathVariable Integer form_id) {
		return answerService.addAllAnswers(answers,form_id);
	}
	
}
