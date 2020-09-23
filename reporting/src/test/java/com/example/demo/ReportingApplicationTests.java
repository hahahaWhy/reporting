package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.SendReportController;
import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorRegisterService;

@SpringBootTest
class ReportingApplicationTests {
	@Autowired
	private SendReportController sendReportController;
	@Test
	void contextLoads() {
		
	}

}
