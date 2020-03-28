package com.example.gewerbeanmeldung.Question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import com.example.gewerbeanmeldung.Answers.Answers;
import com.example.gewerbeanmeldung.QuestionCategory.QuestionCategory;
import com.example.gewerbeanmeldung.QuestionType.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

//The question enity. Every Question, which is saved in the question table of the database is
//generated through this class. 
@Entity
public class Question {

	@Id
	@Column(name = "id")
	private Integer id;

	@NotNull
	private String question;

	/*
	 * Every question has a questionType. The questionType is for example: Datum, File-Upload, Multiple Choice,
	 * Text-Input usw..
	 * We made a one to one relation, because a question just can have one type
	 */
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	private QuestionType questionType;
	
	/*
	 * We need mandatory attribute to check, if this question is optional or mandatory
	 */
	@NotNull
	private boolean mandatory;
	
	
	/*
	 * We need this Id, if there is an answer to a question very early in the dialogue, which could have
	 *influence on a question very later in the dialogue. The later one has the lookback_id to first one 
	 *so we check the first answer to see the later question
	 */
	private Integer lookbackId = 0;

	/*
	 * Hint message
	 */
	@Column(length=1000)
	private String hint;
	
	/*
	 * Which formType the question is from. 
	 */
	@NotNull
	private String formType;

	/*
	 * A question can have multiple categories. We want to be able to categorize all questions to make
	 * a better overview
	 */
	@NotNull
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "Question_Category_Relation", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	List<QuestionCategory> questionCategories;

	public Question() {

	}

	public Question(String question) {
		this.question = question;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public List<QuestionCategory> getQuestionCategories() {
		return questionCategories;
	}

	public void setQuestionCategories(List<QuestionCategory> questionCategories) {
		this.questionCategories = questionCategories;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Integer getLookbackId() {
		return lookbackId;
	}

	public void setLookbackId(Integer lookbackId) {
		this.lookbackId = lookbackId;
	}
}
