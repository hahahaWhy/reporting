package com.example.demo.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
	
//	@RequestMapping("toUserRegister")
//	public RedirectView toUserRegister(HttpServletRequest request,RedirectAttributes redirectAttributes) {
//		String language=(String) request.getSession().getAttribute("l");
//		System.out.println(language);
//		RedirectView redirectTarget = new RedirectView();
//        redirectTarget.setContextRelative(true);
//        redirectTarget.setUrl("userRegister");
//        redirectTarget.set
//        redirectAttributes.addAttribute("l", language);
//		return redirectTarget;
//	}
	
	@RequestMapping("toUserRegister")
	public String toUserRegister() {
		return "userRegister";
	}
	
	private String url="redirect:toUserRegister?l=";
	
	@RequestMapping("/doPatientRegister")
	public String doctorRegister(@RequestParam(value = "mail",required = false) String mail,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "password",required = false) String password,
			Model model,HttpServletResponse response,HttpServletRequest request,RedirectAttributes redirectAttributes
			) {
		//回显
		redirectAttributes.addFlashAttribute("mail", mail);
		redirectAttributes.addFlashAttribute("name", name);
		redirectAttributes.addFlashAttribute("password", password);
		String language=(String) request.getSession().getAttribute("l");
		//判断传入参数是否合法
		if(!EmailVerification.isEmail(mail)) {
			redirectAttributes.addFlashAttribute("error", "email格式错误");
			return url+language;
		}
		if(name=="" || name==null) {
			redirectAttributes.addFlashAttribute("error", "名字不能为空");
			return url+language;
		}
		if(password==""||password==null) {
			redirectAttributes.addFlashAttribute("error", "密码不能为空");
			return url+language;
		}
		//将传入的值封装到对象中
		patient.setMail(mail);
		patient.setName(name);
		patient.setPassword(password);
		//查看mail值是否唯一
		patientList=patientRegisterService.findDcotorByMail(mail);
		if(patientList!=null && !patientList.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "email已经存在");
			return url+language;
		}
		
		try {
			patientRegisterService.saveDoctor(patient);
			response.setContentType("text/html;charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print("<script type='text/javascript'>alert('注册成功!');</script>");
	        return "redirect:toUserLogin?l="+language;
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "注册失败！");
			return url+language;
		}
		
	}
}
