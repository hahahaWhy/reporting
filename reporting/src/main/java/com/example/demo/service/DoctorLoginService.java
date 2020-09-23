package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DoctorLoginDao;
import com.example.demo.entity.Doctor;

@Service
//@Cacheable(cacheNames = "doctor")
public class DoctorLoginService {
	@Autowired
	private DoctorLoginDao doctorLoginDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Doctor> findDcotorByMail(String mail) {
		return doctorLoginDao.findByMail(mail);
	}

}
