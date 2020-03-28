package com.example.gewerbeanmeldung.form;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//The repository for form entity. We use CrudRepository to have very 
//limited code needed to make working SQL requests in behind.
public interface FormRepository extends CrudRepository<Form, Integer> {

	Form findByFormname(String formname);


}
