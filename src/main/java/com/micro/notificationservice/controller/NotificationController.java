package com.micro.notificationservice.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.notificationservice.model.Notification;
import com.micro.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;
    NotificationController(NotificationRepository repository){
    	this.repository=repository;
    }
    @GetMapping("/latest")
    public List<Notification> getLatestNotifications() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return repository.findTop10ByReceiverEmailOrderByCreatedAtDesc(email);
    }

//    @GetMapping("/unread-count")
//    public long getUnreadCount() {
//
//        String email = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        return repository
//                .countByReceiverEmailAndIsReadFalse(email);
//    }
}

