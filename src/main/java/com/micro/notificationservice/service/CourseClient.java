package com.micro.notificationservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.micro.notificationservice.dto.RemainderDTO;

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseClient {

    private final WebClient.Builder webClientBuilder;
    
    public CourseClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    

	@Value("${course.service.url}")
    private String courseServiceUrl;

    public List<RemainderDTO> getPendingSubmissions() {

        return webClientBuilder.baseUrl(courseServiceUrl)
                .build()
                .get()
                .uri("/api/assignments/pending-submissions")
                .retrieve()
                .bodyToFlux(RemainderDTO.class)
                .collectList()
                .block();
    }
}
