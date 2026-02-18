package com.micro.notificationservice.dto;

import lombok.Data;

@Data
public class RemainderDTO {

    private String studentEmail;
    private String studentName;
    private String assignmentTitle;
    
    public RemainderDTO() {}
    
	public RemainderDTO(String studentEmail, String studentName, String assignmentTitle) {
		//super();
		this.studentEmail = studentEmail;
		this.studentName = studentName;
		this.assignmentTitle = assignmentTitle;
	}
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
    
    
}


   
