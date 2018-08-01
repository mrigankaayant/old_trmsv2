package com.ayantsoft.trms.jsf.model;

import java.util.Date;
import java.util.List;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;

public class OfferletterModel {
	
	private String subject;
	private String bodyContent;
	private String remarks;
	private Integer candidateId;
	private Integer checkListId;
	private String checkListName;
	private String bcc;
	private String cc;
	private List<CandidateCheckList> candidateCheckLists;
	private CandidateCheckList candidateCheckList;
	private String status;
	private boolean resumeMandatory;
	private String filePath;
	private String fileName;
	private List<CandidatePaymentModel> candidatePaymentModels;
	private Candidate candidate;
	private String indicator;
	private Date startDate;
	private Date endDate;
	private List<AssignCandidateDto> selectedAssignCandidates;
	
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBodyContent() {
		return bodyContent;
	}
	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}
	public List<CandidateCheckList> getCandidateCheckLists() {
		return candidateCheckLists;
	}
	public void setCandidateCheckLists(List<CandidateCheckList> candidateCheckLists) {
		this.candidateCheckLists = candidateCheckLists;
	}
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public Integer getCheckListId() {
		return checkListId;
	}
	public void setCheckListId(Integer checkListId) {
		this.checkListId = checkListId;
	}
	public String getCheckListName() {
		return checkListName;
	}
	public void setCheckListName(String checkListName) {
		this.checkListName = checkListName;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public CandidateCheckList getCandidateCheckList() {
		return candidateCheckList;
	}
	public void setCandidateCheckList(CandidateCheckList candidateCheckList) {
		this.candidateCheckList = candidateCheckList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean getResumeMandatory() {
		return resumeMandatory;
	}
	public void setResumeMandatory(boolean resumeMandatory) {
		this.resumeMandatory = resumeMandatory;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<CandidatePaymentModel> getCandidatePaymentModels() {
		return candidatePaymentModels;
	}
	public void setCandidatePaymentModels(List<CandidatePaymentModel> candidatePaymentModels) {
		this.candidatePaymentModels = candidatePaymentModels;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
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
	public List<AssignCandidateDto> getSelectedAssignCandidates() {
		return selectedAssignCandidates;
	}
	public void setSelectedAssignCandidates(List<AssignCandidateDto> selectedAssignCandidates) {
		this.selectedAssignCandidates = selectedAssignCandidates;
	}
	
	
	
	
}