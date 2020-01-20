package com.example.gewerbeanmeldung.AnswerOfAnswers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.gewerbeanmeldung.Question.Question;


//Empty Controller Class. We never add a specific request only to the AnswerOfAnswers, as its always
//dependend to each answer, so the answer is representing the requests for answerOfAnswers as well
@RestController
@RequestMapping(path = "")
public class AnswerOfAnswersController {

	@Autowired
	private AnswerOfAnswersService answerService;
	
	
}
