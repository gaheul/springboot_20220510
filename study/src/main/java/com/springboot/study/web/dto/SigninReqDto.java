package com.springboot.study.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SigninReqDto {
	@NotBlank
	private String username;
	@NotBlank
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).{8,20}",//해당값에 0-9/a-z,A-Z/특수문자/최소8자,최대20자
	message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상은 포함되어야하며 8~20자의 비밀번호여야 합니다.")
	private String password;
}
