package com.example.demo.provide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;


@Component
public class EmailVerification {
	
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	public static boolean isEmail(String email) {
		if (email == null || "".equals(email))
			return false;
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return email.matches(regex);
	}
	
    public void sendTxtMail(String sendMail,String receptMail,String reportUrl) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(new String[]{receptMail});
        simpleMailMessage.setFrom(sendMail);
        simpleMailMessage.setSubject("健康报告");
        simpleMailMessage.setText(reportUrl);
        // 发送邮件
        mailSender.send(simpleMailMessage);

        System.out.println("邮件已发送");
    }
}
