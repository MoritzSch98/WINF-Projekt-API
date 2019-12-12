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
}
