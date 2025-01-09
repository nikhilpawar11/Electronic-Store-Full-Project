package com.nikhil.electronic.store.exception;

public class BadCredentialRequest extends RuntimeException {
	
	public BadCredentialRequest() {
		super("Invalid Username or Password !!");
	}
	
	public BadCredentialRequest(String message) {
		super(message);
	}

}
