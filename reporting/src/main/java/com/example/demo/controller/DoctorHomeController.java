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
public class DoctorHomeController {

	@Autowired
	private PatientHomePageService patientHomePageService;

	@Autowired
	private List<DiagnoseReportDto> reportDtoList;

	@RequestMapping("/toSearchPatient")
	public String toSearchPatient(@RequestParam(value = "searchText", required = false) String searchText,
			HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String doctorMail = (String) request.getSession().getAttribute("mail");
		if (doctorMail == null || doctorMail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		}
		model.addAttribute("searchText", searchText);
		reportDtoList = patientHomePageService.findPatientReportByPatientName(searchText);
		model.addAttribute("reportDtoList", reportDtoList);
		return "doctorHomePage";
	}
	
	@RequestMapping("/toSendReport")
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
		return "sendReport";
	}

}
