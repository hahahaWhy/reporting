package com.example.demo.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.entity.DiagnoseReport;
import com.example.demo.entity.Patient;
import com.example.demo.provide.EmailVerification;
import com.example.demo.service.PatientRegisterService;

@Controller
public class PatientRegisterController {
	@Autowired
	private Patient patient;
	
	@Autowired
	private List<DiagnoseReport> diagnoseReport;
	
	@Autowired
	private List<Patient> patientList;
	
	@Autowired
	private PatientRegisterService patientRegisterService;
	
	@RequestMapping("toUserRegister")
	public RedirectView toUserRegister(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String language=(String) request.getSession().getAttribute("l");
		System.out.println(language);
		RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("userRegister");

        redirectAttributes.addAttribute("l", language);
		return redirectTarget;
	}
	
	@RequestMapping("/doPatientRegister")
	public String doctorRegister(@RequestParam(value = "mail",required = false) String mail,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "password",required = false) String password,
			Model model,HttpServletResponse response
			) {
		//回显
		model.addAttribute("mail", mail);
		model.addAttribute("name", name);
		model.addAttribute("password", password);
		//判断传入参数是否合法
		if(!EmailVerification.isEmail(mail)) {
			model.addAttribute("error", "email格式错误");
			return "userRegister";
		}
		if(name=="" || name==null) {
			model.addAttribute("error", "名字不能为空");
			return "userRegister";
		}
		if(password==""||password==null) {
			model.addAttribute("error", "密码不能为空");
			return "userRegister";
		}
		//将传入的值封装到对象中
		patient.setMail(mail);
		patient.setName(name);
		patient.setPassword(password);
		//查看mail值是否唯一
		patientList=patientRegisterService.findDcotorByMail(mail);
		if(patientList!=null && !patientList.isEmpty()) {
			model.addAttribute("error", "email已经存在");
			return "userRegister";
		}
		
		try {
			patientRegisterService.saveDoctor(patient);
			response.setContentType("text/html;charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print("<script type='text/javascript'>alert('注册成功!');</script>");
	        return "redirect:toUserLogin";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "注册失败！");
			return "userRegister";
		}
		
	}
}
