package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Patient;
import com.example.demo.provide.EmailVerification;
import com.example.demo.service.PatientLoginService;

@Controller
public class PatientLoginController {
	@Autowired
	private Patient patient;
	
	@Autowired
	private List<Patient> patientList;
	
	@Autowired
	private PatientLoginService patientLoginService;
	

	
	@RequestMapping("toUserLogin")
	public String toUserLogin() {	
		return "userLogin";
	}
	
	private String url="redirect:toUserLogin?l=";
	
	@RequestMapping("/doUserLogin")
	public String doctorRegister(@RequestParam(value = "mail",required = false) String mail,
			@RequestParam(value = "password",required = false) String password,
			Model model,HttpServletRequest request,RedirectAttributes redirectAttributes
			) throws IOException {
		//回显
		redirectAttributes.addFlashAttribute("mail", mail);
		redirectAttributes.addFlashAttribute("password", password);
		String language=(String) request.getSession().getAttribute("l");
		//判断传入参数是否合法
		if(!EmailVerification.isEmail(mail)) {
			redirectAttributes.addFlashAttribute("error", "email格式错误");
			return url+language;
		}
		if(password==""||password==null) {
			redirectAttributes.addFlashAttribute("error", "密码不能为空");
			return url+language;
		}
		//查看mail是否存在
		patientList=patientLoginService.findDcotorByMail(mail);
		if(patientList.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "email不存在");
			return url+language;
		}
		else {
			patient=patientList.get(0);
			//登录失败
			if(!patient.getPassword().equals(password)) {
				redirectAttributes.addFlashAttribute("error", "密码错误！");
				return url+language;
			}
			//登录成功
			else {
				//写cookie和session
				request.getSession().setAttribute("mail", mail);
		        return "redirect:toUserHomePage?l="+language;
			}
		}
		
	}
}
