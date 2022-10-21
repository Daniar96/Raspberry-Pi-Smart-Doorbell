package com.group17.JSONObjects;

public class UserCredentials {
	private String username;

	private String password;

	private boolean is_online;

	public UserCredentials() {
	}

	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserCredentials(String username, Boolean is_online) {
		this.username = username;
		this.is_online = is_online;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pswrd) {
		this.password = pswrd;
	}

	public boolean isIs_online() {
		return is_online;
	}

	public void setIs_online(boolean is_online) {
		this.is_online = is_online;
	}
}