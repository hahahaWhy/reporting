package com.example.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Patient;

public interface PatientLoginDao extends MongoRepository<Patient, String>{
	public List<Patient> findByMail(String mail);
}
