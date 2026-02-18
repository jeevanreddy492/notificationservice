package com.micro.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.micro.notificationservice.model.Notification;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    EmailNotificationService(JavaMailSender mailSender){
    	this.mailSender=mailSender;
    }
    public void sendEmail(String email, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        //mail.setTo(notification.getUserId() + "@example.com");
        mail.setTo(email);
        mail.setSubject(subject);
        mail.setText(body);

        mailSender.send(mail);
    }
}
