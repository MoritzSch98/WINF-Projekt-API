package com.example.gewerbeanmeldung.QuestionType;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import com.example.gewerbeanmeldung.Choices.Choices;
import com.example.gewerbeanmeldung.Question.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

//The questionType entity. We use it to define the types of questions. We need it, because some types might have multiple answers 
//for example checkboxes or dropdowns ... Also some need data input ...
@Entity
public class QuestionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//this is the default questionId for the default next question. 
	private Integer defaultWay = 0;
	
	//If useDefault == true, look for defaultway, else: we know,
	//choices are existing and we look in choices
	private boolean useDefault;
	
	@NotNull
	private String type;

	//many questiontypes can have many choices. 
	//Choices are the different options which might be to choose
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "QuestionType_Choices_Relation", joinColumns = @JoinColumn(name = "question_type_id"), inverseJoinColumns = @JoinColumn(name = "choices_id"))
	private List<Choices> choices;

	//One question can have one question type and vice versa
	@OneToOne(mappedBy = "questionType", cascade = CascadeType.ALL)
	@JsonIgnore
	private Question question;

	public QuestionType() {
		
	}

	public QuestionType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Choices> getChoices() {
		return choices;
	}

	public void setChoices(List<Choices> choices) {
		this.choices = choices;
	}

	public Integer getDefaultWay() {
		return defaultWay;
	}

	public void setDefaultWay(Integer defaultWay) {
		this.defaultWay = defaultWay;
	}

	public boolean isUseDefault() {
		return useDefault;
	}

	public void setUseDefault(boolean useDefault) {
		this.useDefault = useDefault;
	}

}
