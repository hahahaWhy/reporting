package com.example.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Patient;

public interface PatientRegisterDao extends MongoRepository<Patient, String>{
	List<Patient> findByMail(String mail);
}
