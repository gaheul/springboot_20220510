package com.springboot.study.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int user_code;
	private String email;
	private String name;
	private String username;
	private String password;
	private String roles; //ROLE_USER,ROLE_MANAGER,ROLE_ADMIN / ROLE_ :security형식
	
	public List<String> getRoleList(){
		if(this.roles.length() > 0) { //권한이 있다면
			return Arrays.asList(this.roles.split(",")); //문자열을 각각의 형식에 맞게끔 배열을 리스트로 바꿈 / split:배열로 반환
		}
		return new ArrayList<String>();
	}
	
}
