package com.example.gewerbeanmeldung.FormFilled;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Question.*;
import com.example.gewerbeanmeldung.dbfile.DatabaseFile;
import com.example.gewerbeanmeldung.form.Form;
import com.fasterxml.jackson.annotation.JsonIgnore;

//Formfilled entiry, representing a table in the database, which is showing all filled forms.
//It is holding every answer and answerofanswer in it. As well as we are able to get the fileanswers
//though it.
@Entity
@Table(name = "FormFilled")
public class FormFilled {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//Actual formId (not filled), we do not use a foreign key to make constraints easier
	@NotNull
	private Integer form;
	
	//filling person to be identified
	private String fillingPerson;
	
	//A list of all answers belonging to a filledForm
	@OneToMany(mappedBy="formFilled",cascade = CascadeType.ALL)
    private List<Answers> allAnswers;
	
	public FormFilled() {

	}


	public FormFilled(Integer form) {
		this.form = form;
	}
	
	public List<Answers> getAllAnswers() {
		return allAnswers;
	}

	public void setAllAnswers(List<Answers> allAnswers) {
		this.allAnswers = allAnswers;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getForm() {
		return form;
	}

	public void setForm(Integer form) {
		this.form = form;
	}

	public String getFillingPerson() {
		return fillingPerson;
	}

	public void setFillingPerson(String fillingPerson) {
		this.fillingPerson = fillingPerson;
	}
}
