package com.micro.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.notificationservice.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
