package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.DiagnoseReport;
import com.example.demo.entity.Patient;
import com.example.demo.provide.EmailVerification;
import com.example.demo.service.SendReportService;

@Controller
public class SendReportController {

	@Autowired
	private SendReportService SendReportService;
	@Autowired
	private List<Patient> patientList;
	@Autowired
	private Patient patient;
	
	@Autowired
	private DiagnoseReport diagnoseReport;
	
	@Autowired
	private EmailVerification emailVerification;
	
	@RequestMapping("/toSendReportPage")
	public String toSendReport() {
		return "sendReport";
	}
	
	private String url="redirect:toSendReportPage?l=";
	
	@RequestMapping("/doSendReport")
	public String doSendReport(@RequestParam(value = "patientMail", required = false) String patientMail,
			@RequestParam(value = "patientName", required = false) String patientName,
			@RequestParam(value = "report", required = false) String report,
			@RequestParam(value = "medicine", required = false) String medicine, Model model,
			HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) throws IOException {
		
		String doctorMail = (String) request.getSession().getAttribute("mail");
		if (doctorMail == null || doctorMail.length() == 0) {
			// 没有登录
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('没有登录，无法访问!');</script>");
			return "index";
		}
		String language=(String) request.getSession().getAttribute("l");
		//回显
		redirectAttributes.addFlashAttribute("patientMail", patientMail);
		redirectAttributes.addFlashAttribute("patientName", patientName);
		redirectAttributes.addFlashAttribute("report", report);
		redirectAttributes.addFlashAttribute("medicine", medicine);
		// 判断传入参数是否合法
		if (!EmailVerification.isEmail(patientMail)) {
			redirectAttributes.addFlashAttribute("error", "email格式错误");
			return url+language;
		}
		if (patientName.length()==0 || patientName == null) {
			redirectAttributes.addFlashAttribute("error", "名字不能为空");
			return url+language;
		}
		if (report.length()==0 || report == null) {
			redirectAttributes.addFlashAttribute("error", "诊断报告不能为空");
			return url+language;
		}
		if (medicine.length()==0 || medicine == null) {
			redirectAttributes.addFlashAttribute("error", "药物不能为空");
			return url+language;
		}
		patientList=SendReportService.findPatientByEmail(patientMail);
		if(patientList.size()==0 ||patientList==null) {
			redirectAttributes.addFlashAttribute("error", "邮箱不存在");
			return url+language;
		}
		patient=patientList.get(0);
		if(!patient.getName().equals(patientName)) {
			redirectAttributes.addFlashAttribute("error", "名字不正确");
			return url+language;
		}
		//将放入患者对象
		diagnoseReport.setPatientMail(patientMail);
		diagnoseReport.setDescription(report);
		diagnoseReport.setDoctorMail(doctorMail);
		diagnoseReport.setMedicine(medicine);
//		Calendar calendar= Calendar.getInstance();
		Date today=new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
		diagnoseReport.setReportTime(dateFormat.format(today));
		
		
		
		//将患者对象写入mongodb中
		try {
			String serverName=request.getServerName(); //获取域名
			int port=request.getServerPort();
			String url=serverName+":"+port+"/reporting/doUserLogin";	
//			emailVerification.sendTxtMail(doctorMail, patientMail, url);
			try {
				SendReportService.saveReport(diagnoseReport);
				return "redirect:toDoctorHomePage?l="+language;
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("error", "发送邮件成功，但入数据库失败");
				//这里需要添加数据库回滚
				return url+language;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "发送邮件失败");
			return url+language;
		}
			

	}

}
