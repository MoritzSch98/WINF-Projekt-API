package com.example.gewerbeanmeldung.pdffile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;


@Repository
public interface PdfFileRepository extends JpaRepository<PdfFile, String> {

	@Query("select f from PdfFile f where formFilled = ?1")
	PdfFile getByFormFilled(FormFilled ff);

}