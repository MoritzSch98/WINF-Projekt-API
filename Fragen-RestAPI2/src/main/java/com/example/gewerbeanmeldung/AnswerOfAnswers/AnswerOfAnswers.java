package com.example.gewerbeanmeldung.AnswerOfAnswers;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.example.gewerbeanmeldung.Answers.Answers;
import com.fasterxml.jackson.annotation.JsonIgnore;


//This entity is representing the individual answers for single text answer options and also multiselect 
//answers of text
@Entity
@Table(name = "AnswerOfAnswers")
public class AnswerOfAnswers {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;


	
	private String answer;
	
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="answers_id", nullable=false)
	@JsonIgnore
	private Answers answers;
	
	public AnswerOfAnswers() {

	}

	public AnswerOfAnswers(String answer) {
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Answers getAnswers() {
		return answers;
	}

	public void setAnswers(Answers answers) {
		this.answers = answers;
	}

	
}
