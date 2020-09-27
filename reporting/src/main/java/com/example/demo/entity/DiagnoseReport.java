package com.example.demo.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component	
@Document(collection = "reporting")
public class DiagnoseReport implements Serializable{
	@Id
	private String id;
	private String patientMail;
	private String doctorMail;
	private String description;
	private String medicine;
	private String reportTime;
}
