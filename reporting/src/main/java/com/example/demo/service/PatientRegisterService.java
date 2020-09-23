package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DoctorRegisterDao;
import com.example.demo.dao.PatientRegisterDao;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;

@Service
//@Cacheable(cacheNames = "patient")//spring boot默认缓存，将查询出来的东西放入名字叫patient的容器中
public class PatientRegisterService {
	@Autowired
	private PatientRegisterDao patientRegisterDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/**
	 * 保存一个医生信息
	 * @param doctor
	 */
	public void saveDoctor(Patient patient) {
		//如果需要自定义主键，可以在这里指定主键，如果不指定，mongodb会自动生主键
		patientRegisterDao.save(patient);
	}
	
	public List<Patient> findDcotorByMail(String mail) {
		return patientRegisterDao.findByMail(mail);
	}
}
