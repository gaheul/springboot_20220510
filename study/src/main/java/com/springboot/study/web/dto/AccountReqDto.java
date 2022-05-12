package com.springboot.study.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountReqDto {
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String email;
}
