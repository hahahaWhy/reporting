package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PatientLoginDao;
import com.example.demo.entity.Patient;

@Service
//@Cacheable(cacheNames = "patient")
public class PatientLoginService {
	@Autowired
	private PatientLoginDao patientLoginDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Patient> findDcotorByMail(String mail) {
		return patientLoginDao.findByMail(mail);
	}

}
