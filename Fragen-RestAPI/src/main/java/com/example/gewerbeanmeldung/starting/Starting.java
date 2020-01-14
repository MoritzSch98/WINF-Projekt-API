package com.example.gewerbeanmeldung.starting;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="categoryStarter")
public class Starting {

	@EmbeddedId
	private StartingId id;
	

	@Column(insertable=false, updatable=false)
	private Integer formId;

	@Column(insertable=false, updatable=false)
	private Integer questionCategoryId;
	
	@Column(unique = true)
	private Integer questionId;
	
	@Column(length=2000)
	private String startText;
	
	public Starting() {
		this.id = new StartingId();
	}
	
	public Starting(Integer formId, Integer QuestionCategoryId) {
		this.formId = formId;
		this.questionCategoryId = QuestionCategoryId;
		this.id = new StartingId(formId,QuestionCategoryId);
		
	}

	public StartingId getId() {
		return id;
	}

	public void setId(StartingId id) {
		this.id = id;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
		this.id.setFormId(formId);
	}

	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
		this.id.setCategoryId(questionCategoryId);
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getStartText() {
		return startText;
	}

	public void setStartText(String startText) {
		this.startText = startText;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        Starting that = ( Starting) o;
        return Objects.equals(formId, that.formId) &&
               Objects.equals(questionCategoryId, that.questionCategoryId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(formId, questionCategoryId);
    }
	
}
