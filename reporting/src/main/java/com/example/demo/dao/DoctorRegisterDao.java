package com.example.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Doctor;
//String表示id的类型
public interface DoctorRegisterDao extends MongoRepository<Doctor, String>{
	
	List<Doctor> findByMail(String mail);
}
