package com.example.gewerbeanmeldung.controller;

//This is the Controller, which is letting you perform requests for testing purpose. 
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gewerbeanmeldung.bean.Greeting;

@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping("/api/v1")
public class GreetingController {

	//private static final String template = "Hello, %s!";
	public String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	//returns the greeting.
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/moinsen")
	public String saveQuestions() {
		this.template = "Hello Jonas, %s!";
		return "Moinsen";
	}
}
