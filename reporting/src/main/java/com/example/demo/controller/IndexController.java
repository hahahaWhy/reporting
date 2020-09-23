package com.example.demo.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@RequestMapping("/toindex")
	public String toIndex() {
		return "/index";
	}
	
	//实现index页面跳转到其他页面
	@RequestMapping("/toOtherPage")
	public String toPage(@RequestParam(value = "doctorLogin",required = false) String doctorLogin,
			@RequestParam(value = "doctorRe",required = false) String doctorRe,
			@RequestParam(value = "userLogin",required = false) String userLogin,
			@RequestParam(value = "userRe",required = false) String userRe
			) {
		
		if("1".equals(doctorLogin))
			return "/doctorLogin";
		
		if("2".equals(doctorRe))
			return "/doctorRegister";
		if("3".equals(userLogin))
			return "/userLogin";
		if("4".equals(userRe))
			return "/userRegister";	
		
		return "/index";
	}
}
