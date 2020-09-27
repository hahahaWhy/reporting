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

import com.example.demo.dto.DiagnoseReportDto;
import com.example.demo.entity.Doctor;
import com.example.demo.provide.EmailVerification;
import com.example.demo.service.DoctorRegisterService;
import com.example.demo.service.PatientHomePageService;

@Controller
public class DoctorLoginController {

	@Autowired
	private Doctor doctor;
	
	@Autowired
	private List<Doctor> doctorList;
	
	@Autowired
	private DoctorRegisterService doctorRegisterService;
	
	@Autowired
	private List<DiagnoseReportDto> reportDtoList;
	
	
	@Autowired
	private PatientHomePageService patientHomePageService;	
	
	@RequestMapping("toDoctorLogin")
	public String toDoctorLogin() {
		return "doctorLogin";
	}
	
	private String url="redirect:toDoctorLogin?l=";
	
	@RequestMapping("/doDoctorLogin")
	public String doctorLogin(@RequestParam(value = "mail",required = false) String mail,
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
		doctorList=doctorRegisterService.findDcotorByMail(mail);
		if(doctorList.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "email不存在");
			return url+language;
		}
		else {
			doctor=doctorList.get(0);
			//登录失败
			if(!doctor.getPassword().equals(password)) {
				redirectAttributes.addFlashAttribute("error", "密码错误！");
				return url+language;
			}
			//登录成功
			else {
				//写cookie和session
				request.getSession().setAttribute("mail", mail);
		        return "redirect:toDoctorHomePage?l="+language;
			}
		}
		
	}
}
