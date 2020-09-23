package com.example.demo.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	@RequestMapping("/toDoctorRegister")
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
			return "doctorRegister";
		}
		if(name=="" || name==null) {
			model.addAttribute("error", "名字不能为空");
			return "doctorRegister";
		}
		if(password==""||password==null) {
			model.addAttribute("error", "密码不能为空");
			return "doctorRegister";
		}
		//将传入的值封装到对象中
		doctor.setMail(mail);
		doctor.setName(name);
		doctor.setPassword(password);
		//查看mail值是否唯一
		doctorList=doctorRegisterService.findDcotorByMail(mail);
		if(doctorList!=null && !doctorList.isEmpty()) {
			model.addAttribute("error", "email已经存在");
			return "doctorRegister";
		}
		try {
			doctorRegisterService.saveDoctor(doctor);
			response.setContentType("text/html;charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print("<script type='text/javascript'>alert('注册成功!');</script>");
	        return "doctorLogin";
		}catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error", "注册失败！");
			return "doctorRegister";
		}
		
	}
	
}
