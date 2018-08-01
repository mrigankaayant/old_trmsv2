package com.ayantsoft.trms.jsf.model;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCourse;
import com.ayantsoft.trms.hibernate.pojo.CandidateRemarks;
import com.ayantsoft.trms.hibernate.pojo.ContactAddress;
import com.ayantsoft.trms.hibernate.pojo.Employee;

public class FollowUpModel {

	private Candidate candidate;
	private ContactAddress contactAddress;
	private CandidateCourse candidateCourse;
	private CandidateRemarks candidateRemarks;
	private Employee employee;
	
	
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public ContactAddress getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(ContactAddress contactAddress) {
		this.contactAddress = contactAddress;
	}
	public CandidateCourse getCandidateCourse() {
		return candidateCourse;
	}
	public void setCandidateCourse(CandidateCourse candidateCourse) {
		this.candidateCourse = candidateCourse;
	}
	public CandidateRemarks getCandidateRemarks() {
		return candidateRemarks;
	}
	public void setCandidateRemarks(CandidateRemarks candidateRemarks) {
		this.candidateRemarks = candidateRemarks;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
	
	
}