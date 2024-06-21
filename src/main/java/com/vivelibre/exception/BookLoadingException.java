package com.vivelibre.exception;

import java.io.IOException;

public class BookLoadingException extends RuntimeException{
	
	public BookLoadingException() {
		super();
	}

	public BookLoadingException(String message) {
		super(message);
	}
	
	public BookLoadingException(String message,  IOException exception) {
		super(message, exception);		
	}
	
	 

}
