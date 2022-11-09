package com.group17.JSONObjects;

public class UserCredentials {
	private String username;

	private String password;

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	private String rfid;

	public UserCredentials() {
	}
	public UserCredentials(String username, String password, String rfid) {
		this.username = username;
		this.password = password;
		this.rfid = rfid;
	}

	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
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

}