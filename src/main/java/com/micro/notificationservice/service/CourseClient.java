package com.micro.notificationservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.micro.notificationservice.dto.RemainderDTO;
import com.micro.notificationservice.security.JwtUtil;

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseClient {

    private final WebClient.Builder webClientBuilder;
    private final JwtUtil jwtUtil;
    
    public CourseClient(WebClient.Builder webClientBuilder, JwtUtil jwtUtil) {
        this.webClientBuilder = webClientBuilder;
        this.jwtUtil=jwtUtil;
    }
    

	@Value("${course.service.url}")
    private String courseServiceUrl;

    public List<RemainderDTO> getPendingSubmissions() {
    	
        
    	 String token = jwtUtil.generateServiceToken("notification-service");

         return webClientBuilder.baseUrl(courseServiceUrl)
                 .build()
                 .get()
                 .uri("/api/assignments/pending-submissions")
                 .headers(headers -> headers.setBearerAuth(token))
                 .retrieve()
                 .bodyToFlux(RemainderDTO.class)
                 .collectList()
                 .block();
    }
}
// in course service spring.security.oauth2.resourceserver.jwt.secret-key=your-secret-key

