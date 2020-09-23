package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PatientLoginDao;
import com.example.demo.dao.SendReportDao;
import com.example.demo.entity.DiagnoseReport;
import com.example.demo.entity.Patient;

@Service
public class SendReportService {
	@Autowired
	private SendReportDao sendReportDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Autowired
	private PatientLoginDao patientLoginDao;
	
	
	public void saveReport(DiagnoseReport diagnoseReport) {
		sendReportDao.save(diagnoseReport);
	}
	public List<Patient> findPatientByEmail(String patientMail) {
		return patientLoginDao.findByMail(patientMail);
	}
}
