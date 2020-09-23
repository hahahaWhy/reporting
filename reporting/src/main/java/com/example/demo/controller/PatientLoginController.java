package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.DiagnoseReportDto;
import com.example.demo.entity.Patient;
import com.example.demo.provide.EmailVerification;
import com.example.demo.service.PatientHomePageService;
import com.example.demo.service.PatientLoginService;

@Controller
public class PatientLoginController {
	@Autowired
	private Patient patient;
	
	@Autowired
	private List<Patient> patientList;
	
	@Autowired
	private PatientLoginService patientLoginService;
	
	@Autowired
	private PatientHomePageService patientHomePageService;
	
	@Autowired
	private List<DiagnoseReportDto> reportDtoList;
	
	@RequestMapping("/doUserLogin")
	public String doctorRegister(@RequestParam(value = "mail",required = false) String mail,
			@RequestParam(value = "password",required = false) String password,
			Model model,HttpServletRequest request
			) throws IOException {
		//回显
		model.addAttribute("mail", mail);
		model.addAttribute("password", password);
		//判断传入参数是否合法
		if(!EmailVerification.isEmail(mail)) {
			model.addAttribute("error", "email格式错误");
			return "userLogin";
		}
		if(password==""||password==null) {
			model.addAttribute("error", "密码不能为空");
			return "userLogin";
		}
		//查看mail是否存在
		patientList=patientLoginService.findDcotorByMail(mail);
		if(patientList.isEmpty()) {
			model.addAttribute("error", "email不存在");
			return "userLogin";
		}
		else {
			patient=patientList.get(0);
			//登录失败
			if(!patient.getPassword().equals(password)) {
				model.addAttribute("error", "密码错误！");
				return "userLogin";
			}
			//登录成功
			else {
				//写cookie和session
				request.getSession().setAttribute("mail", mail);
				reportDtoList=patientHomePageService.findPatientReport(mail);
				model.addAttribute("reportDtoList",reportDtoList);
		        return "userHomePage";
			}
		}
		
	}
}
