package com.example.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.DiagnoseReport;

public interface SendReportDao extends MongoRepository<DiagnoseReport, String>{
	List<DiagnoseReport> findByPatientMail(String patientMail);
	
}
