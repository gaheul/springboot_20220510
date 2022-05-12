package com.springboot.study.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.study.web.controller.api.data.User;
import com.springboot.study.web.dto.AccountReqDto;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.SigninReqDto;
import com.springboot.study.web.dto.SignupReqDto;

@RestController
public class UserController {
	
	@GetMapping("/user/{usercode}")
	public ResponseEntity<?> getUser(@PathVariable int usercode){
		System.out.println(usercode);
		return new ResponseEntity<>(10,HttpStatus.BAD_REQUEST) ; //ResponseEntity body:데이터 header: 정보
	}
	
	/*
	 * 사용자이름 중복확인(/auth/signup/check/???username) 
	 * -> User객체에 존재하는 사용자이름과 같으면 사용할 수 없는 사용자이름입니다.
	 * -> 사용할 수 있는 사용자이름입니다.
	 * 
	 * 1.회원가입(/auth/signup)
	 * -> 회원가입 정보 출력(console), 응답 ->회원가입완료
	 * 
	 * 2.로그인(/auth/signin)
	 * ->User객체의 정보와 일치하면 (username,password) 로그인 성공, 로그인 실패
	 * 
	 * 3.회원수정(/account/aaa)
	 * -> name,email 수정 -> 회원수정 완료, 회원수정 실패
	 * 
	 * 4.회원탈퇴(/account/aaa)
	 * -> 회원탈퇴 완료, 회원탈퇴 실패
	 */
	
//	@GetMapping("/auth/signup/check/{username}")
//	public ResponseEntity<?> usernameCheck(@PathVariable String username) {
//		User user = new User();
//		if(user.getUsername().equals(username)) {
//			return new ResponseEntity<>("사용할 수 없는 사용자 이름",HttpStatus.BAD_REQUEST);
//		}else {
//			return new ResponseEntity<>("사용할 수 있는 사용자 이름",HttpStatus.OK);			
//		}
//	}
	@GetMapping("/auth/signup/check/{username}")
	public ResponseEntity<?> usernameCheck(@PathVariable String username) {
		CMRespDto<String> cmRespDto = null; //CMRespDto:json형태로 보내주기 위해서 사용
		HttpStatus status = null;
		User user = new User();
		
		if(user.getUsername().equals(username)) {
			cmRespDto = new CMRespDto<String>(-1,"사용할 수 없는 사용자이름",username);
			status = HttpStatus.BAD_REQUEST;
		}else {
			cmRespDto = new CMRespDto<String>(1,"사용할 수 있는 사용자이름",username);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(cmRespDto,status);
	}
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@Valid SignupReqDto signupReqDto, BindingResult bindingResult){//Valid-BindingResult(무조건 같이 붙여서 써줘야함)
		//valid가 붙은 대상을 빈값체크를 하고나서 빈값이 있으면 bindingresult가 처리
		
		
		
		return new ResponseEntity<>(new CMRespDto<SignupReqDto>(1,"회원가입완료.",signupReqDto), HttpStatus.OK);
	}
	
	@PostMapping("/auth/signin")
	public ResponseEntity<?> signin(@Valid SigninReqDto signinReqDto, BindingResult bindingResult){
		
		
		
		User user = new User();
		if(signinReqDto.getUsername().equals(user.getUsername()) && signinReqDto.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<>(new CMRespDto<User>(1,"로그인성공",user),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new CMRespDto<SigninReqDto>(-1,"로그인실패",signinReqDto),HttpStatus.BAD_REQUEST);
		}
		

	}
	
	@PutMapping("/account/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username, @Valid AccountReqDto accountReqDto, BindingResult bindingResult){
		
		
		User user = new User();
		if(!user.getUsername().equals(username)) {
			return new ResponseEntity<>(new CMRespDto<String>(-1,"회원조회실패",username),HttpStatus.BAD_REQUEST);
		}
		
		user.setName(accountReqDto.getName());
		user.setEmail(accountReqDto.getEmail());
		
		return new ResponseEntity<>(new CMRespDto<User>(1,"회원수정완료",user),HttpStatus.OK);
	}
	
	@DeleteMapping("/account/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username){
		User user = new User();
		if(!user.getUsername().equals(username)) {
			return new ResponseEntity<>(new CMRespDto<String>(-1,"회원탈퇴실패",username),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(new CMRespDto<String>(1,"회원탈퇴완료",username),HttpStatus.OK);
	}
	
//	@GetMapping("/auth/signup")
//	public ResponseEntity<?> signUp(User user) {
//		System.out.println(user);
//		return new ResponseEntity<>("회원가입완료",HttpStatus.OK);
//	}
	
//	@GetMapping("/auth/signin")
//	public ResponseEntity<?> signIn(String username,String password){
//		User user = new User();
//		if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
//			return new ResponseEntity<>("로그인 성공",HttpStatus.OK);
//		}else {       
//			return new ResponseEntity<>("로그인 실패",HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
}
