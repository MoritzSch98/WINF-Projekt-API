package com.example.gewerbeanmeldung.Question;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//Question Repository, is the actual class, to do inserts, deletes and edits to the database
//CrudRepository instance makes it quite easy to do standard SQL commands by just writing a single method in java
public interface QuestionRepository extends CrudRepository<Question, Integer> {

	//custom query to find questions by formtype
	List<Question> findByFormType(String formType);

	//custom query to check, if question exists by formType
	@Query("select case when count(q)> 1 then true else false end "
			+ "from Question q where formType=?1")
	boolean existsQuestionByFormType(@Param("formType") String formtype);
}
