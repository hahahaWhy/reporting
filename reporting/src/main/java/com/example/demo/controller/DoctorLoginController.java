package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("/doDoctorLogin")
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
			return "doctorLogin";
		}
		if(password==""||password==null) {
			model.addAttribute("error", "密码不能为空");
			return "doctorLogin";
		}
		//查看mail是否存在
		doctorList=doctorRegisterService.findDcotorByMail(mail);
		if(doctorList.isEmpty()) {
			model.addAttribute("error", "email不存在");
			return "doctorLogin";
		}
		else {
			doctor=doctorList.get(0);
			//登录失败
			if(!doctor.getPassword().equals(password)) {
				model.addAttribute("error", "密码错误！");
				return "doctorLogin";
			}
			//登录成功
			else {
				//写cookie和session
				request.getSession().setAttribute("mail", mail);
				reportDtoList=patientHomePageService.findAllPatientReport();
				model.addAttribute("reportDtoList",reportDtoList);
		        return "doctorHomePage";
			}
		}
		
	}
}
