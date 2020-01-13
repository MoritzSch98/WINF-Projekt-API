package com.example.gewerbeanmeldung.starting;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StartingId implements Serializable{

	@Column(name="formId")
	private Integer formId;
	
	@Column(name="questionCategoryId")
	private Integer questionCategoryId;
	
	
	public StartingId(){
		
	}
	
	
	public StartingId(Integer formId, Integer questionCategoryId) {
		this.formId = formId;
		this.questionCategoryId = questionCategoryId;
	}
	
	
	
	@Override
    public boolean equals(Object o) {
		if (this == o) return true;
		 
        if (o == null || getClass() != o.getClass())
            return false;
 
        StartingId that = ( StartingId) o;
        return Objects.equals(formId, that.formId) &&
               Objects.equals(questionCategoryId, that.questionCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId,questionCategoryId);
    }

	public Integer getFormId() {
		return formId;
	}


	public void setFormId(Integer formId) {
		this.formId = formId;
	}


	public Integer getCategoryId() {
		return questionCategoryId;
	}


	public void setCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}	
}
