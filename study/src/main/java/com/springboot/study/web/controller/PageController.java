package com.springboot.study.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.study.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/index")
	public String index() {
		return "index"; //jsp경로
	}
	
	@GetMapping("/auth/signin")
	public String signin() {
		return "auth/signin";
	}
	
	@GetMapping("/user/account/mypage")
	public String mypage() {
		return "account/mypage";
	}
	
	@GetMapping("/board/list")
	public String boardList(@AuthenticationPrincipal PrincipalDetails principalDetails){
		return "board/board-list2";
	}
	
	@GetMapping("/board-info/{boardCode}")
	public String boardDtl(@PathVariable int boardCode) {
		return "board/board-dtl";
	}
	
	@GetMapping("/board")
	public String boardInsert() {
		return "board/board-insert";
	}
	
	@GetMapping("/board/{boardCode}")
	public String boardUpdate() {
		return "board/board-update";
	}
	
}
