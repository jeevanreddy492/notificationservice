package com.micro.notificationservice.consumer;

import java.time.LocalDateTime;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.micro.notificationservice.dto.NotificationPayloadDTO;
import com.micro.notificationservice.model.Notification;
import com.micro.notificationservice.repository.NotificationRepository;
import com.micro.notificationservice.service.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger log =
            (Logger) LoggerFactory.getLogger(NotificationConsumer.class);

    NotificationConsumer(EmailService emailService, NotificationRepository notificationRepository,SimpMessagingTemplate messagingTemplate ){
    	this.emailService=emailService;
    	this.notificationRepository=notificationRepository;
    	this.messagingTemplate=messagingTemplate;
    }
    @RabbitListener(queues = "notification_queue")
    public void handleNotification(NotificationPayloadDTO payload) {

        log.info("Received notification for {}", payload.getStudentEmail());

        // 1️⃣ Send Email
        emailService.sendGradeEmail(payload);

        // 2️⃣ Save In-App Notification
        Notification notification = new Notification();

        notification.setReceiverEmail(payload.getStudentEmail());
        notification.setReceiverName(payload.getStudentName());
        notification.setTitle("Assignment Graded");
        notification.setMessage("Your assignment '"
                + payload.getAssignmentTitle()
                + "' was graded. Score: "
                + payload.getScore());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
        
        messagingTemplate.convertAndSendToUser(payload.getStudentEmail().toString(),"/queue/notifications",payload);

    }
}

/*
 * 
 * 
const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {

    stompClient.subscribe('/user/queue/notifications', function(message) {
        const notification = JSON.parse(message.body);
        console.log("New notification:", notification);
    });

});

 */

