package com.example.gewerbeanmeldung.controller;


//This class is the basic authentication Controller. We can allow/deny a user to get access to other requests.
//He needs to be authenticated, so that we can perform special Admin commands. We are using BasicAuth mechanism. 

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

	//We allow CrossDomainAccess
private void allowCrossDomainAccess() {
    if (servletResponse != null) {
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }
}
	//Of everytging is correct, we allow the connection. We just return message here. 
	@GetMapping(path = "/basicauth")
	public AuthenticationBean helloWorldBean() {
		//allowCrossDomainAccess();
		return new AuthenticationBean("You are authenticated");
	}	
}
