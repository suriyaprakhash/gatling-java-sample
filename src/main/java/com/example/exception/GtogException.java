package com.example.exception;

public class GtogException extends Exception {

	public GtogException() {
		super();
	}

	public GtogException(String string) {
		super(string);
	}
	
	public GtogException(String string,Throwable e) {
		super(string,e);
	}

}
