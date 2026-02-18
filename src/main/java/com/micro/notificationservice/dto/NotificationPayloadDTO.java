package com.micro.notificationservice.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class NotificationPayloadDTO {

    private String studentEmail;
    private String studentName;
    private String assignmentTitle;
    private Double score;
    private String feedback;
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getAssignmentTitle() {
		return assignmentTitle;
	}
	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
    
}

