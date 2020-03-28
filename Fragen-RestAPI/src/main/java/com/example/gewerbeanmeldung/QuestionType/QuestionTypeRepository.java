package com.example.gewerbeanmeldung.QuestionType;

import org.springframework.data.repository.CrudRepository;

//repository for the questionType entity and so the database table. CrudRepository makes it easy to 
//do basic insert, edit and delete into the db by just one method call
public interface QuestionTypeRepository extends CrudRepository<QuestionType, Integer> {

}
