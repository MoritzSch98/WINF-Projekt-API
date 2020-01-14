package com.example.gewerbeanmeldung.starting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gewerbeanmeldung.form.Form;

@Repository
public interface StartingRepository extends CrudRepository<Starting, Integer> {

	@Query("select s from Starting s where formId=?1 and questionCategoryId=?2")
	Starting findByFormAndCategory(Integer form_id, Integer category_id);


}