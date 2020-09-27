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

import com.example.demo.entity.Doctor;
import com.example.demo.provide.EmailVerification;
import com.example.demo.service.DoctorRegisterService;

@Controller
public class DoctorRegisterController {
	
	@Autowired
	private Doctor doctor;
	
	@Autowired
	private List<Doctor> doctorList;
	
	@Autowired
	private DoctorRegisterService doctorRegisterService;
	
	@RequestMapping("toDoctorRegister")
	public String toDoctorRegister() {
		return "doctorRegister";
	}
	private String url="redirect:toDoctorRegister?l=";
	@RequestMapping("/doDoctorRegister")
	public String doctorRegister(@RequestParam(value = "mail",required = false) String mail,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "password",required = false) String password,
			Model model,HttpServletResponse response,RedirectAttributes redirectAttributes,HttpServletRequest request
			) {
		String language=(String) request.getSession().getAttribute("l");
		//回显
		redirectAttributes.addFlashAttribute("mail", mail);
		redirectAttributes.addFlashAttribute("name", name);
		redirectAttributes.addFlashAttribute("password", password);
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
		doctor.setMail(mail);
		doctor.setName(name);
		doctor.setPassword(password);
		//查看mail值是否唯一
		doctorList=doctorRegisterService.findDcotorByMail(mail);
		if(doctorList!=null && !doctorList.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "email已经存在");
			return url+language;
		}
		try {
			doctorRegisterService.saveDoctor(doctor);
			response.setContentType("text/html;charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print("<script type='text/javascript'>alert('注册成功!');</script>");
	        return "redirect:toDoctorLogin?l="+language;
		}catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("error", "注册失败！");
			return url+language;
		}
		
	}
	
}
