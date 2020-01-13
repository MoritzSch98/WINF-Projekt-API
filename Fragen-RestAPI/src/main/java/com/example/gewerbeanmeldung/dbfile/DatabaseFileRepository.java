package com.example.gewerbeanmeldung.dbfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gewerbeanmeldung.Answers.Answers;


@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {

	@Query("select f from DatabaseFile f where answers = ?1")
	DatabaseFile getByAnswer(Answers answer);

}