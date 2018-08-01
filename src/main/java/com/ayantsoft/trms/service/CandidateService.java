package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.CandidateDao;
import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.FollowUp;
import com.ayantsoft.trms.jsf.model.AssignCandidateDto;


@ManagedBean
@ApplicationScoped
public class CandidateService implements Serializable {



	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = -2938323882269349538L;

	@ManagedProperty(value="#{candidateDao}")
	private  CandidateDao candidateDao ;

	public CandidateDao getCandidateDao() {
		return candidateDao;
	}

	public void setCandidateDao(CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}

	
	
	public void updateCandidate(Candidate candidate, FollowUp followUp, CandidatePayment candidatePayment){
		candidateDao.updateCandidate(candidate,followUp,candidatePayment);
	}

	
	public Object[] candidateFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters, Integer empId,boolean isAdmin){
	
		return candidateDao.candidateFilter(first, pageSize, sortField, sortOrder, filters, empId,isAdmin);
	}
	
	
	public Object[] candidateFilterForEnrolled(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)
	{
		return candidateDao.candidateFilterForEnrolled(first, pageSize, sortField, sortOrder, filters);
	}
	
	public Candidate getCandidateForDocuments(Integer candidateId)
	{
		return candidateDao.candidateForDocuments(candidateId);
	}
	
	
	public void saveCandidate(Candidate candidate)
	{
		candidateDao.save(candidate);
	}
	
	public void updateCandidateRecruiterId(int recruiterId,int candidateId){
		candidateDao.updateCandidateRecruiterId(recruiterId,candidateId);
	}
	
	
	public Object[] candidateFilterForReport(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters,Integer empId,boolean isAdmin,boolean isRecruiter,Date startDate,Date endDate){
		return candidateDao.candidateFilterForReport(first, pageSize, sortField, sortOrder, filters, empId, isAdmin,isRecruiter, startDate, endDate);
	}
	
	
	public Object[] candidateFilterForHr(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return candidateDao.candidateFilterForHr(first,pageSize,sortField,sortOrder,filters);
	}
	
	public Object[] candidateFilterForSales(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return candidateDao.candidateFilterForSales(first,pageSize,sortField,sortOrder,filters);
	}
	
	
	public Object[] candidateFilterForPlaced(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return candidateDao.candidateFilterForPlaced(first,pageSize,sortField,sortOrder,filters);
	}
	
	public Object[] candidateFilterForRemove(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return candidateDao.candidateFilterForRemove(first,pageSize,sortField,sortOrder,filters);
	}
	
	
	public Candidate getCandidateCheckByCandidateId(Integer candidateId){
		return candidateDao.findCandidateCheckByCandidateId(candidateId);
	}
	
	
	public Candidate getBatchByCandidateId(Integer candidateId){
		return candidateDao.findBatchByCandidateId(candidateId);
	}
	
	
	public void updateCandidateSubStatus(Integer candidateId){
		candidateDao.updateCandidateSubStatus(candidateId);
	}
	
	public List<AssignCandidateDto> getAssignCandidates(){
	     return candidateDao.findAssignCandidates();	
	}
	
	
	
	public List<Candidate> getCandidatedForEnrolled()
	{
		return candidateDao.findCandidatesForEnrolled();
	}
	
	public List<Candidate> getCandidateByStatus(Integer recruiterId,boolean isAdmin,String candidateStatus){
		return candidateDao.findCandidateByStatus(recruiterId,isAdmin,candidateStatus);
	}
	
	public  Candidate getCandidate(Integer candidateId){
		return candidateDao.getCandidate(candidateId);

	}
	
	public void updateCandidate(Integer candidateId){
		candidateDao.updateCandidate(candidateId);
	}

	public boolean checkUniqueEmail(String email, Integer candidateId){
		return candidateDao.checkUniqueEmail(email,candidateId);
	}

	public boolean checkUniqueMobile(String mobile, Integer candidateId){
		return candidateDao.checkUniqueMobile(mobile,  candidateId);
	}
	
	public boolean checkCandidatePayment(Integer candidateId){
		return candidateDao.getPaymentCount(candidateId);
	}
	
	public List<Object> candidateOfExRecruiters(){
		return candidateDao.candidateOfExRecruiters();
	}
	
	public List<Candidate> getCandidateBySearchValue(String searchBy,String searchValue){
		return candidateDao.getCandidateBySearchValue(searchBy,searchValue);
	}
}