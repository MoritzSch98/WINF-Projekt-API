package com.example.gewerbeanmeldung.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Question.QuestionService;

//The formService class. It is having all methods we need to perform as action though form entity.
@Service
public class FormService {

	@Autowired
	private FormRepository formRepo;
	@Autowired
	private QuestionService questionService;
	
	//getting a list of all forms. 
	public List<Form> getAllForms() {
		List<Form> formList = new ArrayList<>();
		formRepo.findAll().forEach(formList::add);	
		return formList;
	}
	
	//getting a form by its name
	public Form getFormByFormname(String formname) {
		return formRepo.findByFormname(formname);
	}
	//getting a form by its id
	public Form getFormById(Integer id) {
		return formRepo.findById(id).orElse(null);
	}
	
	//adding a form to db.
	public String addForm(Form form) {		
		formRepo.save(form);
		
		return "saved successful";
	}
	//editing a form especially editing the formname, we want to not change the id. Also we nee to make sure,
	//that there is no question with this form existing any more.
	public String editForm(String newFormtype, String oldFormtype) {
		Form oldForm = new Form();
		Form newForm = new Form();
		oldForm.setFormname(oldFormtype);
		newForm.setFormname(newFormtype);
		if(!questionService.existsQuestionByFormType(oldFormtype)) {
			deleteForm(oldForm);
		}	
		addForm(newForm);	
		return "edited successful";
	}
	
	//deleting a form
	public String deleteForm(Form form) {
		
		form = getFormByFormname(form.getFormname());
		formRepo.delete(form);
		
		return "deleted";
	}
	
	//Add the formtype to form entity, if not existing
	public String addWhenNotExisting(String formname) {
		
		if(getFormByFormname(formname) == null) {
			Form form = new Form();
			form.setFormname(formname);
			addForm(form);
			return formname+ " added successfull";
		
		}else {
			return formname+ " already existing";
		}
		
	}
}
