package com.ayantsoft.trms.jsf.model;

import java.util.Date;

public class AssignCandidateDto {
	
	private Integer candidateId;
	private String candidateName;
	private String candidateEmail;
	private String candidateMobile;
	private String currentLocation;
	private String candidateCourse;
	private String candidateStatus;
	private String batchTitle;
	private Date startDate;
	private Date endDate;
	private Date startTime;
	private Date endTime;
	private String batchStatus;
	
	
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getCandidateEmail() {
		return candidateEmail;
	}
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}
	public String getCandidateMobile() {
		return candidateMobile;
	}
	public void setCandidateMobile(String candidateMobile) {
		this.candidateMobile = candidateMobile;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getCandidateCourse() {
		return candidateCourse;
	}
	public void setCandidateCourse(String candidateCourse) {
		this.candidateCourse = candidateCourse;
	}
	public String getCandidateStatus() {
		return candidateStatus;
	}
	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}
	public String getBatchTitle() {
		return batchTitle;
	}
	public void setBatchTitle(String batchTitle) {
		this.batchTitle = batchTitle;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	
	
	

}