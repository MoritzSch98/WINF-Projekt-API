package com.example.gewerbeanmeldung.FormFilled;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Question.Question;

@Service
public class FormFilledService {

	@Autowired
	private FormFilledRepository formsFilledRepo;


	public List<FormFilled> getAllFilledForms() {
		List<FormFilled> fforms = new ArrayList<>();
		formsFilledRepo.findAll().forEach(fforms::add);
		return fforms;
	}


	public FormFilled addFormFilled(FormFilled formFilled) {
		for(int i = 0; i < formFilled.getAllAnswers().size(); i++) {
			formFilled.getAllAnswers().get(i).setFormFilled(formFilled);
		}
		
		return formsFilledRepo.save(formFilled);
	}


	public FormFilled getFilledForm(Integer form_id) {
		return	formsFilledRepo.findById(form_id).orElse(null);
	}
	
	public boolean checkFormExisting(Integer form_id) {
		return formsFilledRepo.existsById(form_id);
	}


	
	public void updateFormFilled(Integer form_id, FormFilled formFilled) {
		// TODO Auto-generated method stub // getAllFiledForms()  ?
		for (int i = 0; i < getAllFilledForms().size(); i++) {
			FormFilled form = getAllFilledForms().get(i);
			if(form.getId().equals(form_id)) {
				getAllFilledForms().set(i, formFilled);
				return; 
			}
	}
}


	public void deleteFormFilled(Integer form_id) {
		// TODO Auto-generated method stub
		getAllFilledForms().removeIf(form -> form.getId().equals(form_id));
	}
}
