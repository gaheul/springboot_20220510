package com.springboot.study.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.study.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //권한 / 권한 담고있는 collection
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoleList().forEach(r -> { //foreach =>람다 list에서 하나씩 꺼내서 r에서 ㅓ출력
			System.out.println("권한: "+r);
			authorities.add(() -> r); //객체를 add
		});
		
		authorities.forEach(r -> {System.out.println("리스트에 들어있는 권한: "+r.getAuthority());});
		
		
		return authorities;
	}

	@Override
	public String getPassword() { //security가 관리 -> 여기에서 로그인시켜줌
		return user.getPassword();
	}

	@Override
	public String getUsername() { //security가 관리
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { //계정이 만료되었는지 확인
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { //비밀번호가 지정한 횟수 이상 틀리면 잠김
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //자격 증명이 만료가 되면 계정사용 불가
		return true;
	}

	@Override
	public boolean isEnabled() { //휴먼계정
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String)attributes.get("name");
	}

}
