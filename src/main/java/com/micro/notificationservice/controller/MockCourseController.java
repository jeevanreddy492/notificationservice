package com.micro.notificationservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.notificationservice.dto.RemainderDTO;

@RestController
@RequestMapping("/api/assignments")
public class MockCourseController {

    @GetMapping("/pending-submissions")
    public List<RemainderDTO> getMockData() {

        RemainderDTO dto = new RemainderDTO();
        dto.setStudentEmail("jeevanreddypappu@gmail.com");
        dto.setStudentName("Jeevan Reddy");
        dto.setAssignmentTitle("Microservices HW");

        return List.of(dto);
    }
}

