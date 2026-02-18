package com.micro.notificationservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.micro.notificationservice.dto.NotificationPayloadDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    EmailService(JavaMailSender mailSender){
    	this.mailSender=mailSender;
    }

    public void sendGradeEmail(NotificationPayloadDTO payload) {

        String subject = "Assignment Graded: " + payload.getAssignmentTitle();

        String body = "Hello " + payload.getStudentName() + ",\n\n"
                + "Your assignment '" + payload.getAssignmentTitle() + "' has been graded.\n"
                + "Score: " + payload.getScore() + "\n"
                + "Feedback: " + payload.getFeedback() + "\n\n"
                + "Regards,\nLMS Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(payload.getStudentEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}

