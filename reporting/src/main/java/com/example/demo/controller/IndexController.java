package com.example.demo.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class IndexController {

	@RequestMapping("/toindex")
	public String toIndex(HttpServletRequest request) {	
//		得到index的页面设置
		String strReferrer = request.getParameter("l");
		if(strReferrer==null || strReferrer.length()==0)
		{
			strReferrer="zh_CN";
		}
		request.getSession().setAttribute("l", strReferrer);
		return "index";
	}

	// 实现index页面跳转到其他页面
	@RequestMapping("/toOtherPage")
	public String toPage(@RequestParam(value = "doctorLogin", required = false) String doctorLogin,
			@RequestParam(value = "doctorRe", required = false) String doctorRe,
			@RequestParam(value = "userLogin", required = false) String userLogin,
			@RequestParam(value = "userRe", required = false) String userRe,HttpServletRequest request) {
		String language=(String) request.getSession().getAttribute("l");

		if ("1".equals(doctorLogin)) {
			return "redirect:toDoctorLogin?l="+language;
		}

		if ("2".equals(doctorRe))
			return "redirect:toDoctorRegister?l="+language;
		if ("3".equals(userLogin))
			return "redirect:toUserLogin?l="+language;
		if ("4".equals(userRe))
			return "redirect:toUserRegister?l="+language;

		return "index";
	}
	
	
	//登出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session=request.getSession();
        session.invalidate();
		return "redirect:toindex";
	}
}
