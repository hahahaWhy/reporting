package com.example.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DoctorLoginDao;
import com.example.demo.dao.PatientLoginDao;
import com.example.demo.dao.SendReportDao;
import com.example.demo.dto.DiagnoseReportDto;
import com.example.demo.entity.DiagnoseReport;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;

@Service
public class PatientHomePageService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private DoctorLoginDao doctorLoginDao;
	
	@Autowired 
	private PatientLoginDao patientLoginDao;
	
	@Autowired
	private SendReportDao sendReportDao;
	
	@Autowired
	private DiagnoseReport diagnoseReport;
	
	@Autowired
	private List<Doctor> doctorList;
	
	@Autowired
	private List<Patient> patientList;
	
	public List<DiagnoseReportDto> findPatientReport(String patientMail) {
		List<DiagnoseReport> reportList=sendReportDao.findByPatientMail(patientMail);
		
		List<DiagnoseReportDto> reportDtoList=new ArrayList<>();
		for (DiagnoseReport report : reportList) {
			DiagnoseReportDto reportDto=new DiagnoseReportDto();
			doctorList=doctorLoginDao.findByMail(report.getDoctorMail());
			patientList=patientLoginDao.findByMail(report.getPatientMail());
			BeanUtils.copyProperties(report, reportDto);
			reportDto.setDoctor(doctorList.get(0));
			reportDto.setPatient(patientList.get(0));
			reportDtoList.add(reportDto);
		}
		return reportDtoList;
		
		
	}

	public List<DiagnoseReportDto> findPatientReportBySearchText(String searchText,String patientMail) {
		List<DiagnoseReportDto> reportDtoList=new ArrayList<>();
		Query query = new Query();
	    Criteria criteria =new Criteria();
	    criteria.and("description").regex("^.*"+searchText+".*$").and("patientMail").is(patientMail);
	    query.addCriteria(criteria);
	    List<DiagnoseReport>  reportList=mongoTemplate.find(query,DiagnoseReport.class);    
	    
	    for (DiagnoseReport report : reportList) {
			DiagnoseReportDto reportDto=new DiagnoseReportDto();
			doctorList=doctorLoginDao.findByMail(report.getDoctorMail());
			patientList=patientLoginDao.findByMail(report.getPatientMail());
			BeanUtils.copyProperties(report, reportDto);
			reportDto.setDoctor(doctorList.get(0));
			reportDto.setPatient(patientList.get(0));
			reportDtoList.add(reportDto);
		}
        return reportDtoList;
	}

	public List<DiagnoseReportDto> findAllPatientReport() {
		List<DiagnoseReport> reportList=sendReportDao.findAll();
		
		List<DiagnoseReportDto> reportDtoList=new ArrayList<>();
		for (DiagnoseReport report : reportList) {
			DiagnoseReportDto reportDto=new DiagnoseReportDto();
			doctorList=doctorLoginDao.findByMail(report.getDoctorMail());
			patientList=patientLoginDao.findByMail(report.getPatientMail());
			BeanUtils.copyProperties(report, reportDto);
			reportDto.setDoctor(doctorList.get(0));
			reportDto.setPatient(patientList.get(0));
			reportDtoList.add(reportDto);
		}
		return reportDtoList;
	}

	public List<DiagnoseReportDto> findPatientReportByPatientName(String searchText) {
		List<DiagnoseReportDto> reportDtoList=new ArrayList<>();
		List<DiagnoseReport> reportList=sendReportDao.findAll();
		    
	    
	    for (DiagnoseReport report : reportList) {
			DiagnoseReportDto reportDto=new DiagnoseReportDto();
			doctorList=doctorLoginDao.findByMail(report.getDoctorMail());
			patientList=patientLoginDao.findByMail(report.getPatientMail());
			BeanUtils.copyProperties(report, reportDto);
			reportDto.setDoctor(doctorList.get(0));
			reportDto.setPatient(patientList.get(0));
			if(reportDto.getPatient().getName().contains(searchText))
			{
				reportDtoList.add(reportDto);
			}			
		}
	    
	    
        return reportDtoList;
	}


	
	
}
