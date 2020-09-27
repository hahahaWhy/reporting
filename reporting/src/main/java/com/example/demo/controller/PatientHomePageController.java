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
import com.example.demo.service.PatientHomePageService;

@Controller
public class PatientHomePageController {
	@Autowired
	private PatientHomePageService patientHomePageService;

	@Autowired
	private List<DiagnoseReportDto> reportDtoList;
	
	
	@RequestMapping("toUserHomePage")
	public String toUserHomePage(HttpServletRequest request,Model model,HttpServletResponse response) throws IOException {
		String mail=(String) request.getSession().getAttribute("mail");
		if (mail == null || mail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		} 
		reportDtoList=patientHomePageService.findPatientReport(mail);
		model.addAttribute("reportDtoList",reportDtoList);
		return "userHomePage";
	}

	@RequestMapping("/doPatientPageSearch")
	public String showUserPage(@RequestParam(value = "searchText", required = false) String searchText, Model model,
			HttpServletRequest request,HttpServletResponse response) throws IOException {

		String patientMail = (String) request.getSession().getAttribute("mail");
		if (patientMail == null || patientMail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		} 

		model.addAttribute("searchText", searchText);
		reportDtoList = patientHomePageService.findPatientReportBySearchText(searchText,patientMail);
		model.addAttribute("reportDtoList", reportDtoList);
		return "userHomePage";
	}

}
