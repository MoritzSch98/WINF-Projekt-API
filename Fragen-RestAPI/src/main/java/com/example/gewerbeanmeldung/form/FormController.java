package com.example.gewerbeanmeldung.form;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//The controller for form entity. We just need the option to make a request to see all forms. So we only implemented
//this option. Form entities are created through using the formservice for the most part. When we making questions. 
@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping(path = "")
public class FormController {
	
	@Autowired
	private FormService formService;
	// Lists all Questions
		@RequestMapping(path = "/forms/all")
		public List<Form> getAllForms() {
			return formService.getAllForms();
		}
}
