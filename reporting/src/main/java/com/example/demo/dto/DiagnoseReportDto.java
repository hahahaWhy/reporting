package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;

@Component
public class DiagnoseReportDto {

	private String id;
	private String patientMail;
	private String doctorMail;
	private String description;
	private String medicine;
	private String reportTime;
	private Patient patient;
	private Doctor doctor;
	
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPatientMail() {
		return patientMail;
	}
	public void setPatientMail(String patientMail) {
		this.patientMail = patientMail;
	}
	public String getDoctorMail() {
		return doctorMail;
	}
	public void setDoctorMail(String doctorMail) {
		this.doctorMail = doctorMail;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	@Override
	public String toString() {
		return "DiagnoseReportDto [id=" + id + ", patientMail=" + patientMail + ", doctorMail=" + doctorMail
				+ ", description=" + description + ", medicine=" + medicine + ", reportTime=" + reportTime
				+ ", patient=" + patient + ", doctor=" + doctor + "]";
	}

	
	
}
