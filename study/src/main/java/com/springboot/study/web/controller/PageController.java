package com.springboot.study.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
	
	@GetMapping("/index")
	public String index() {
		return "index"; //jsp경로
	}
	
	@GetMapping("/board/list")
	public String boardList(){
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
