package com.group17.JSONObjects;

public class ServerError {
	private String error;

	public ServerError(String error) {
		this.error = error;

	}

	public String geterror() {
		return error;
	}

	public void seterror(String error) {
		this.error = error;
	}

}
