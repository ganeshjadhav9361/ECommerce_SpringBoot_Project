package com.ecommerce.project.security.response;

import java.util.List;

public class LoginResponse {

	private Long id;
	private String jwtCookie;
	private String username;
	private List<String> roles;

	public LoginResponse(Long id, String username, List<String> roles, String jwtCookie) {
		this.id = id;
		this.jwtCookie = jwtCookie;
		this.username = username;
		this.roles = roles;
	}

	public String getJwtCookie() {
		return jwtCookie;
	}

	public void setJwtCookie(String jwtCookie) {
		this.jwtCookie = jwtCookie;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
