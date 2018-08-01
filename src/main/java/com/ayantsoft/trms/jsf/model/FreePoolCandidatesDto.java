package com.ayantsoft.trms.jsf.model;

import com.ayantsoft.trms.hibernate.pojo.Candidate;

public class FreePoolCandidatesDto {
	
	private Integer candidateId;
	private String currentLocation;
	private String candidateName;
	private String preferedLocation;
	private String visa;
	private String candidateRemarks;
	private Integer candidateRemarksId;
	private String candidateEmail;
	private String candidateMobile;
	private String candidateCourse;
	private Integer candidateCourseId;
	private String recruiterName;
	private Integer recruiterId;
	private Candidate candidate;
	
	
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getPreferedLocation() {
		return preferedLocation;
	}
	public void setPreferedLocation(String preferedLocation) {
		this.preferedLocation = preferedLocation;
	}
	public String getVisa() {
		return visa;
	}
	public void setVisa(String visa) {
		this.visa = visa;
	}
	public String getCandidateRemarks() {
		return candidateRemarks;
	}
	public void setCandidateRemarks(String candidateRemarks) {
		this.candidateRemarks = candidateRemarks;
	}
	public Integer getCandidateRemarksId() {
		return candidateRemarksId;
	}
	public void setCandidateRemarksId(Integer candidateRemarksId) {
		this.candidateRemarksId = candidateRemarksId;
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
	public String getCandidateCourse() {
		return candidateCourse;
	}
	public void setCandidateCourse(String candidateCourse) {
		this.candidateCourse = candidateCourse;
	}
	public Integer getCandidateCourseId() {
		return candidateCourseId;
	}
	public void setCandidateCourseId(Integer candidateCourseId) {
		this.candidateCourseId = candidateCourseId;
	}
	public String getRecruiterName() {
		return recruiterName;
	}
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	public Integer getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(Integer recruiterId) {
		this.recruiterId = recruiterId;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}