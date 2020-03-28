package com.example.gewerbeanmeldung.Choices;

import org.springframework.data.repository.CrudRepository;


//This is the java class to establish a database connection to the Choices Table of the database. 
//CrudRepository allows us to do basic insertions, deletions, editings automatically, without writing
//further code. 
public interface ChoicesRepository extends CrudRepository<Choices, Integer> {

}
