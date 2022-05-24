package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.study.config.oauth2.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity // 기존의 WebSecurityConfigurerAdapter의 설정을 비활성화 시키고 현재 class(securityConfig)의 설정을 따르겠다
@Configuration //component -> ioc에 등록 / 설정객체(config..)에 달아줌 
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { //모든 security 에 대한 설정들 들어있음
	
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean //@Configuration이 있어서=> bean을 설정할 수 있음 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //생성된게 ioc에 등록됨
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests() // 인증 요청
			.antMatchers("/api/board/**", "/","/board/list") // url(/api/board/**) 요청이 들어오면 -> URI지정 
			.authenticated() //인증이 필요함 -> 권한은 상관없음
			.antMatchers("/api/v1/user/**","/user/account/**") //권한설정
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest() //다른 모든 요청들은
			.permitAll() // 권한 허용 -> 모두에게 권한을 주겠다.
			.and()
			.formLogin() //parameter 받아서 로그인
			.loginPage("/auth/signin") //로그인 페이지 get요청(view) ->페이지 띄워주기위함
			.loginProcessingUrl("/auth/signin") //로그인 post 요청 (PrincipalDetailsService -> loadUserByUsername()이 호출)
			.defaultSuccessUrl("/") //로그인하자마자 보낼 페이지(/ -> index)
			.and()
			.oauth2Login()
			.loginPage("/auth/signin")
			.userInfoEndpoint()
			/*
			 * 1. 코드를 받는다 -> google,naver, kakao 등 로그인 요청을 했을 때 부여되는 코드번호
			 * 2. 에세스 토큰을 발급받는다 -> JWT
			 * 3. 스코프 정보에 접근할 수 있는 권한이 생긴다 
			 * 4. 해당 정보를 시큐리티에서 활용하면 됨
			 */
			.userService(principalOauth2UserService)
			.and()
			.defaultSuccessUrl("/");
	}
}
