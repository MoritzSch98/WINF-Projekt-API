package com.example.gewerbeanmeldung.FormFilled;

import org.springframework.data.repository.CrudRepository;


//The repository entity for formFilled, to be able to safe, delete and update inputs to the database
//through crudRepository class very easily without writing extra code
public interface FormFilledRepository extends CrudRepository<FormFilled, Integer> {

}
