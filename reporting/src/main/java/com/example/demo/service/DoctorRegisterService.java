package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DoctorRegisterDao;
import com.example.demo.entity.Doctor;

@Service
//@Cacheable(cacheNames = "doctor")//spring boot默认缓存，将查询出来的东西放入名字叫doctor的容器中
public class DoctorRegisterService {
	
	@Autowired
	private DoctorRegisterDao doctorRegisterDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/**
	 * 保存一个医生信息
	 * @param doctor
	 */
	public void saveDoctor(Doctor doctor) {
		//如果需要自定义主键，可以在这里指定主键，如果不指定，mongodb会自动生主键
		doctorRegisterDao.save(doctor);
	}
	
	/**
	 * 根据id查找doctor
	 * @param id
	 * @return
	 */
	public Doctor findDoctorById(String id) {
		return doctorRegisterDao.findById(id).get();
		
	}
	
	
	public List<Doctor> findDcotorByMail(String mail) {
		return doctorRegisterDao.findByMail(mail);
	}
}
