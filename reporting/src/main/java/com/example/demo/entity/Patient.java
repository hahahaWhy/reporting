package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Document(collection = "patient")
public class Patient implements Serializable{
	@Id
	private String id;
	private String mail;
	private String name;
	private String password;
	
}
