package com.example.gewerbeanmeldung.Answers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnswersRepository extends CrudRepository<Answers, Integer> {

	@Query("select a from Answers a where question_id = ?1")
	Answers findByQuestionId(Integer question_id);

}
