package com.example.gewerbeanmeldung.Answers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.gewerbeanmeldung.Question.Question;

@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping(path = "/")
public class AnswersController {

	@Autowired
	private AnswersService answerService;

	// Posts an Answer to a Filled Form
	@RequestMapping(method = RequestMethod.POST, path = "forms/{formfilled_id}/question/{question_id}/answers/add")
	public String addAnswerToForm(@RequestBody Answers answer, @PathVariable Integer formfilled_id,@PathVariable Integer question_id) {
		return answerService.addAnswer(answer,formfilled_id, question_id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "forms/{formfilled_id}/question/{question_id}/answers/{answers_id}/edit")
	public void updateAnswerToForm(@RequestBody Answers answer, @PathVariable Integer formfilled_id, @PathVariable Integer question_id, @PathVariable Integer answers_id) {
		answerService.updateAnswer(answer,formfilled_id, question_id, answers_id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "forms/{formfilled_id}/question/{question_id}/answers")
	public String deleteAnswerToForm(@PathVariable Integer formfilled_id, @PathVariable Integer question_id) {
		return answerService.deleteAnswerOfFormFilledByFormAndQuestion(formfilled_id, question_id);
	}
	
	// Posts all Answers to a Filled Form
	@RequestMapping(method = RequestMethod.DELETE, path = "forms/{formfilled_id}/answers/all/delete")
	public String deleteAllAnswersToForm(@PathVariable Integer formfilled_id) {
		return answerService.deleteAllAnswersOfFormFilled(formfilled_id);
	}
	
	// Posts all Answers to a Filled Form
	@RequestMapping(method = RequestMethod.POST, path = "forms/{formfilled_id}/answers/all/add")
	public String addAllAnswersToForm(@RequestBody List<Answers> answers, @PathVariable Integer formfilled_id) {
		return answerService.addAllAnswers(answers,formfilled_id);
	}
	
	//Editing multiple Answers of a FilledForm
	@RequestMapping(method = RequestMethod.PUT, path = "forms/{formfilled_id}/answers/all/edit")
	public String editAllAnswerToForm(@RequestBody List<Answers> answers, @PathVariable Integer formfilled_id) {
		return answerService.editAllAnswers(answers, formfilled_id);
	}
}
