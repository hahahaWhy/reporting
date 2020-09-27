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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.DiagnoseReportDto;
import com.example.demo.service.PatientHomePageService;

@Controller
public class DoctorHomeController {

	@Autowired
	private PatientHomePageService patientHomePageService;

	@Autowired
	private List<DiagnoseReportDto> reportDtoList;
	
	
	@RequestMapping("toDoctorHomePage")
	public String toDoctorHomePage(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String doctorMail = (String) request.getSession().getAttribute("mail");
		if (doctorMail == null || doctorMail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		}
		reportDtoList=patientHomePageService.findAllPatientReport();
		model.addAttribute("reportDtoList",reportDtoList);
		return "doctorHomePage";
	}
	
	@RequestMapping("toSearchPatientNew")
	public String toSearchPatientNew() {
		return "doctorHomePage";
	}
	
	@RequestMapping("/toSearchPatient")
	public String toSearchPatient(@RequestParam(value = "searchText", required = false) String searchText,
			HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws IOException {
		String doctorMail = (String) request.getSession().getAttribute("mail");
		if (doctorMail == null || doctorMail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		}
		redirectAttributes.addFlashAttribute("searchText", searchText);
		reportDtoList = patientHomePageService.findPatientReportByPatientName(searchText);
		redirectAttributes.addFlashAttribute("reportDtoList", reportDtoList);
		String language=(String) request.getSession().getAttribute("l");
		return "redirect:toSearchPatientNew?l="+language;
	}
	
	@RequestMapping("toSendReport")
	public String toSendReport(@RequestParam(value = "searchText", required = false) String searchText,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String doctorMail = (String) request.getSession().getAttribute("mail");
		if (doctorMail == null || doctorMail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		}
		String language=(String) request.getSession().getAttribute("l");
		return "redirect:toSendReportPage?l="+language;
	}

}
