package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration //component -> ioc에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter { //모든 security 에 대한 설정들 들어있음
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //생성된게 ioc에 등록됨
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/api/board/**", "/","/board/list") // /api/board/** 요청이 들어오면 
			.authenticated() //인증이 필요함
			.antMatchers("/api/v1/user/**") //권한설정
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest()
			.permitAll() //나머지 요청들은 권한 허용
			.and()
			.formLogin() //parameter 받아서 로그인
			.loginPage("/auth/signin") //로그인 페이지 get요청(view) ->페이지 띄워주기위함
			.loginProcessingUrl("/auth/signin") //로그인 post 요청 (PrincipalDetailsService -> loadUserByUsername()이 호출)
			.defaultSuccessUrl("/"); //로그인하자마자 보낼 페이지(/ -> index)
		
	}
}
