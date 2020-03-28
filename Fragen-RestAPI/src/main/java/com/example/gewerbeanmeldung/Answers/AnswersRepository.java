package com.example.gewerbeanmeldung.Answers;
/*AnswerRepository: It is creating SQL requests through CrudRepository Class for our MySQL Database
 */
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.gewerbeanmeldung.FormFilled.FormFilled;

public interface AnswersRepository extends CrudRepository<Answers, Integer> {

	//Custom Query to select an Answers by its question id and FormFilled, so it must be unique
	@Query("select a from Answers a where question_id = ?1 and formFilled = ?2")
	Answers findByQuestionId(Integer question_id, FormFilled ff);

	//Custom Query finding all Answers from a specific formFilled
	@Query("select a from Answers a where formFilled = ?1")
	List<Answers> findAllByFormId(FormFilled ff);

	//Custom Query to find all Answers by a QuestionID, so all answers to one specific question
	@Query("select a from Answers a where question_id = ?1")
	List<Answers> findAllByQuestionId(Integer question_id);

}
