package com.example.gewerbeanmeldung.FormFilled;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.Question.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "FormFilled")
public class FormFilled {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	private String formType;
	
	
	private String fillingPerson;

	
	@OneToMany(mappedBy="formFilled",cascade = CascadeType.ALL)
    private List<Answers> allAnswers;
	
	public FormFilled() {

	}


	public FormFilled(String formType) {
		this.formType = formType;
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

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getFillingPerson() {
		return fillingPerson;
	}

	public void setFillingPerson(String fillingPerson) {
		this.fillingPerson = fillingPerson;
	}
	
}
