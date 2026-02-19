package com.micro.notificationservice.scheduler;

import com.micro.notificationservice.consumer.NotificationConsumer;
import com.micro.notificationservice.dto.NotificationPayloadDTO;
import com.micro.notificationservice.dto.RemainderDTO;
import com.micro.notificationservice.model.Notification;
import com.micro.notificationservice.repository.NotificationRepository;
import com.micro.notificationservice.service.CourseClient;
import com.micro.notificationservice.service.EmailNotificationService;
import com.micro.notificationservice.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final CourseClient courseClient;
    private final EmailNotificationService emailService;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    private static final Logger log =
            LoggerFactory.getLogger(NotificationConsumer.class);


    public ReminderScheduler(CourseClient courseClient, EmailNotificationService emailService,
			NotificationRepository notificationRepository,SimpMessagingTemplate messagingTemplate) {
		
		this.courseClient = courseClient;
		this.emailService = emailService;
		this.notificationRepository = notificationRepository;
		this.messagingTemplate=messagingTemplate;
	}



	//@Scheduled(cron = "0 0 9 * * ?") // 9 AM Daily
	@Scheduled(fixedRate = 300000)
    public void sendReminderEmails() {

        log.info("Running reminder scheduler...");

        List<RemainderDTO> pendingList =
                courseClient.getPendingSubmissions();

        for (RemainderDTO dto : pendingList) {

            String subject = "Reminder: Submit Assignment";
            String body = "Hello " + dto.getStudentName() + ",\n\n"
                    + "Reminder: Please submit your assignment '"
                    + dto.getAssignmentTitle() + "'.\n\n"
                    + "Regards,\nLMS Team";

            // Send Email
            
            emailService.sendEmail(dto.getStudentEmail(), subject, body);
            

//            NotificationPayloadDTO payload = new NotificationPayloadDTO();
//
//            payload.setStudentEmail(dto.getStudentEmail());
//            payload.setStudentName(dto.getStudentName());
//            payload.setAssignmentTitle(dto.getAssignmentTitle());
//            payload.setScore(null);
//            payload.setFeedback(null);
//
//            emailService.sendGradeEmail(payload);


            // Save DB
            Notification notification = new Notification();

            notification.setReceiverEmail(dto.getStudentEmail());
            notification.setReceiverName(dto.getStudentName());
            notification.setTitle("Assignment Reminder");
            notification.setMessage("Reminder for assignment: "
                    + dto.getAssignmentTitle());
            notification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(notification);
            
            messagingTemplate.convertAndSendToUser(dto.getStudentEmail().toString(),"/queue/notifications",dto);


        }
    }
}

