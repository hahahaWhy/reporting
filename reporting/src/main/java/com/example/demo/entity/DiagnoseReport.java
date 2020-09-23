package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;


@Component	
@Document(collection = "reporting")
public class DiagnoseReport {
	@Id
	private String id;
	private String patientMail;
	private String doctorMail;
	private String description;
	private String medicine;
	private String reportTime;
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
	@Override
	public String toString() {
		return "DiagnoseReport [id=" + id + ", patientMail=" + patientMail + ", doctorMail=" + doctorMail
				+ ", description=" + description + ", medicine=" + medicine + ", reportTime=" + reportTime + "]";
	}
	
}
