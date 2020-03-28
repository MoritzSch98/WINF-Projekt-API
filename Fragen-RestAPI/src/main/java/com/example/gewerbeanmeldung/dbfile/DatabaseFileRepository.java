package com.example.gewerbeanmeldung.dbfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gewerbeanmeldung.Answers.Answers;

//This is our Repository Class for the DatabaseFile. Here we create the database table connection through
//crud repository, which allowes us with nearly no selfmade code to make the standard requests to the 
//database.
@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {

	//Custom query getting a file by the answer entity belonging to it
	@Query("select f from DatabaseFile f where answers = ?1")
	DatabaseFile getByAnswer(Answers answer);

}