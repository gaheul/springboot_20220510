package com.springboot.study.web.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.study.annotation.Validation;
import com.springboot.study.config.auth.PrincipalDetails;
import com.springboot.study.domain.user.User;
import com.springboot.study.domain.user.UserRepository;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.SignupReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
	
	@Validation
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@Valid SignupReqDto signupReqDto, BindingResult bindingResult){//Valid-BindingResult(무조건 같이 붙여서 써줘야함)
		//valid가 붙은 대상을 빈값체크를 하고나서 빈값이 있으면 bindingresult가 처리
		
		return new ResponseEntity<>(new CMRespDto<SignupReqDto>(1,"회원가입완료.",signupReqDto), HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //encode:암호화됨
		
		//List<String> roles = List.of("ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"); 
		List<String> roles = List.of("ROLE_USER"); 
		//리스트에 바로 값 넣는 방법
		user.setRoles(String.join(",", roles));
		//join: 리스트에 들어있는 문자열들을 ,(쉼표)로 구분해서 합쳐주는 방법
		
		userRepository.insertUser(user);
		
		return new ResponseEntity<>(new CMRespDto<User>(1,"회원가입완료",user),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/authentication")
	public ResponseEntity<?> getAuthentication(@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		System.out.println(principalDetails.getUser().getUser_code());
		String password= principalDetails.getUser().getPassword();
		System.out.println(bCryptPasswordEncoder.matches("1234", password)); //.matches(원래비밀번호, 암호화된비밀번호) => 같으면 true
		
		return new ResponseEntity<>(new CMRespDto<PrincipalDetails>(1,"세션정보",principalDetails),HttpStatus.OK);
	}
	
//	@GetMapping("/user")
//	public ResponseEntity<?> testUser() {
//		return new ResponseEntity<>(new CMRespDto<String>(1,"유저권한","role_user"),HttpStatus.OK);
//	}
	
	@GetMapping("/manager")
	public ResponseEntity<?> testManager() {
		return new ResponseEntity<>(new CMRespDto<String>(1,"매니저권한","role_manager"),HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<?> testAdmin() {
		return new ResponseEntity<>(new CMRespDto<String>(1,"관리자권한","role_admin"),HttpStatus.OK);
	}
	
	
	
}
