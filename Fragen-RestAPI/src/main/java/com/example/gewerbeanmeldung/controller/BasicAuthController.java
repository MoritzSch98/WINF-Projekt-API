package com.example.gewerbeanmeldung.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.example.gewerbeanmeldung.bean.AuthenticationBean;



@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {

	private HttpServletResponse servletResponse;

private void allowCrossDomainAccess() {
    if (servletResponse != null) {
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }
}

	@GetMapping(path = "/basicauth")
	public AuthenticationBean helloWorldBean() {
		//allowCrossDomainAccess();
		return new AuthenticationBean("You are authenticated");
	}	
}



/**@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {

	private HttpServletResponse servletResponse;

	private void allowCrossDomainAccess() {
		if (servletResponse != null) {
			servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		}
	}

	@GetMapping(path = "/basicauth")
	public AuthenticationBean helloWorldBean() {
		//allowCrossDomainAccess();
		return new AuthenticationBean("You are authenticated");
	}	
} */
