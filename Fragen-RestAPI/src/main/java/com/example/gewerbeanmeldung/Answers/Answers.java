package com.example.gewerbeanmeldung.Answers;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswers;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.dbfile.DatabaseFile;
import com.fasterxml.jackson.annotation.JsonIgnore;


//This is answer entity, it is unique by the question_id and formFilled_id
//Each answer has answer type, which is checking dateanswer and fileanswer or aoa inputs and then is set correctly
@Entity
@Table(name = "Answers", uniqueConstraints = {@UniqueConstraint(columnNames={"question_id", "formFilled_id"})})
public class Answers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	//Half FK to the question_id of belonging question to the answer, to minimize data transfer,
	//its not having the full question entity included
	@Column(columnDefinition = "Integer not null")
	private Integer question_id;
	
	//The answer type is going to be auto generated and depends on the question_type of the question,
	//this answer is belonging to. The answer_type is helping us, in which table we have to look in the
	//db. It can differ between: fileanswer, dateanswer, normal
	@NotNull
	private String answerType;
	
	//Dateanswer, only filled, if we have a question, where we want to get the actual date as an answer. 
	//Else prop is null
	@Basic
	private java.sql.Date dateanswer;
	
	//FK to the DatabaseFile entity. If we had an fileanswer, this entity will be set. Important to know,
	//you have to create Answers instance first without this property being filled. The server is setting
	//the answer_type to fileanswer. Now you upload the file with the fileupload request and its
	//going to be mapped to the answers instance now. Then we can ask for it also through the answers
	//instance getters and setters
	@OneToMany(mappedBy="answers")
	private List<DatabaseFile> fileanswer;
	
	//FK to AnswerofAnswers. If the answer is just normal answer, we are going to have one or more elements in this
	//List. We need it to make multi select on answers especially checkboxes work. 
	@OneToMany(mappedBy="answers")
	private List<AnswerOfAnswers> aoa;
	
	//FK to the FormFilled entity. Every answers instance belonging to a specific formFilled entity. Form
	//Filled is an instance of every started filling of a form by a user. 
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="formFilled_id", nullable=false)
	@JsonIgnore
    private FormFilled formFilled;
	
	
	public Answers() {

	}

	public Answers(String answerType) {
		this.answerType = answerType;
	}

	
	public Date getDateanswer() {
		return dateanswer;
	}

	public void setDateanswer(java.sql.Date dateanswer) {
		this.dateanswer = dateanswer;
	}

	public Integer getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public FormFilled getFormFilled() {
		return formFilled;
	}

	public void setFormFilled(FormFilled formFilled) {
		this.formFilled = formFilled;
	}

	public List<AnswerOfAnswers> getAoa() {
		return aoa;
	}

	public void setAoa(List<AnswerOfAnswers> aoa) {
		this.aoa = aoa;
	}

	public List<DatabaseFile> getFileanswer() {
		return fileanswer;
	}

	public void setFileanswer(List<DatabaseFile> fileanswer) {
		this.fileanswer = fileanswer;
	}
	
	
}
