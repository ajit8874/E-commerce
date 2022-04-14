package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	@JsonProperty
	private String confirmpassword;

	public String getPassword() {
		try {
			return password;

		}catch (IllegalArgumentException ex){
			ex.printStackTrace();
		}
		return null;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		try{
			return username;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setUsername(String username) {
		try{
			this.username = username;

		}catch (IllegalArgumentException ex){
			ex.printStackTrace();
		}
	}


	public String getConfirmpassword() {
		try{
			return confirmpassword;


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getConfirmPassword() {
		return null;
	}
}



