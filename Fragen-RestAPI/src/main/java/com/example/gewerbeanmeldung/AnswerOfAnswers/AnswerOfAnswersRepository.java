package com.example.gewerbeanmeldung.AnswerOfAnswers;
/*This is the Repository Class for AnswerOfAnswers, here we make the connection to the sql Server, the 
 * Crud Repository Implementation does this nearly automatically
 */
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnswerOfAnswersRepository extends CrudRepository<AnswerOfAnswers, Integer> {

	//Custom request finding all AnswerOfAnswers by the answers_id
	@Query("select aoa from AnswerOfAnswers aoa where answers_id = ?1")
	List<AnswerOfAnswers> findAllByAnswerId(Integer answers_id);

}
