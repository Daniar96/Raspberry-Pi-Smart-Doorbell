package com.group17.JSONObjects;
public class HashedUserCredentials {

	private String username;
	private String salt;
	private String hashed_password;

	public HashedUserCredentials() {
	}

	public HashedUserCredentials(String jsonString) throws NullPointerException {
		if (jsonString.equals("{}")){
			throw new NullPointerException("There is no user credentials");
		}
		String[] jsonArray = jsonString.split("\\\": \"|\\\", \"|\\\"}|\\{\"");
		for (int i = 1; i < jsonArray.length; i++) {
			switch (jsonArray[i]) {
			case "email":
				this.username = jsonArray[i + 1];
				break;
			case "hashed_password":
				this.hashed_password = jsonArray[i + 1].substring(3);
				break;
			case "salt":
				this.salt = jsonArray[i + 1].substring(3);
				break;
			default:
				break;
			}
		}
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHashed_password() {
		return hashed_password;
	}

	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
