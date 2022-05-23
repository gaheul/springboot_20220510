package com.springboot.study.config.auth;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
	private String role;
	
	@Override
	public String getAuthority() {
		
		return role;
	}
}
