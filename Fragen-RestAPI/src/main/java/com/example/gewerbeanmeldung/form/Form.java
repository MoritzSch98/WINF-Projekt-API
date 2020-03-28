package com.example.gewerbeanmeldung.form;

//This class is the form entity. It is representing a table within the database, that enables us to create lots 
//of different forms for every kind of form-filling process imaginable.  Just having a name and an id. 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class Form {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String formname;
	

	public Form() {

	}

	public Form(String formname) {
		this.formname = formname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	

}