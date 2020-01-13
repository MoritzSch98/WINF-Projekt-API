package com.example.gewerbeanmeldung.starting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gewerbeanmeldung.form.Form;

@Repository
public interface StartingRepository extends CrudRepository<Starting, Integer> {


}