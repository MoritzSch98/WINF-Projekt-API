package com.example.gewerbeanmeldung.FormFilled;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Question.Question;
import com.example.gewerbeanmeldung.form.Form;
import com.example.gewerbeanmeldung.form.FormService;

//The service class for formFilled, here we have all logical methods for formFilled entity. 
@Service
public class FormFilledService {

	@Autowired
	private FormFilledRepository formsFilledRepo;
	@Autowired
	private FormService formService;


	//Getting a list of all filled forms
	public List<FormFilled> getAllFilledForms() {
		List<FormFilled> fforms = new ArrayList<>();
		formsFilledRepo.findAll().forEach(fforms::add);
		return fforms;
	}

	//adding a filled form
	public FormFilled addFormFilled(FormFilled formFilled) {
		for(int i = 0; i < formFilled.getAllAnswers().size(); i++) {
			formFilled.getAllAnswers().get(i).setFormFilled(formFilled);
		}
		
		return formsFilledRepo.save(formFilled);
	}

	//getting a filledform by its id
	public FormFilled getFilledForm(Integer formfilled_id) {
		return	formsFilledRepo.findById(formfilled_id).orElse(null);
	}
	
	//checking if a form to a filledform exists, through form parameter
	public boolean checkFormExisting(Integer formfilled_id) {
		return formsFilledRepo.existsById(formfilled_id);
	}


	//updating a filledform by id and filledForm entity
	public void updateFormFilled(Integer formfilled_id, FormFilled formFilled) {
		formFilled.setId(formfilled_id);
		formsFilledRepo.save(formFilled);
	}

	//deleting a filledForm
	public void deleteFormFilled(Integer formfilled_id) {
		formsFilledRepo.deleteById(formfilled_id);
	}
}
