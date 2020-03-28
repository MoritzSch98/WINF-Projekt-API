package com.example.gewerbeanmeldung.bean;

//This is the class for the Authentication message, which is displayed after login. 
public class AuthenticationBean {

	private String message;

	public AuthenticationBean(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("HelloWorldBean [message=%s]", message);
	}

}
