package com.example.gewerbeanmeldung.QuestionCategory;

import org.springframework.data.repository.CrudRepository;
//Repository for question category, we can make easy database commands through crud repository
public interface QuestionCategoryRepository extends CrudRepository<QuestionCategory, Integer> {

	QuestionCategory findByCategory(String category);
}
