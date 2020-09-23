package com.example.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Doctor;

public interface DoctorLoginDao extends MongoRepository<Doctor, String>{
	
	public List<Doctor> findByMail(String mail);
	
}
