package com.springboot.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration //component -> ioc에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter { //모든 security 에 대한 설정들 들어있음
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.anyRequest()
			.permitAll();
	}
}
