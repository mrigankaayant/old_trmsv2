package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.CandidatePaymentDao;
import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCourse;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.jsf.model.CandidatePaymentModel;
import com.ayantsoft.trms.util.ChartsUtil;

@ManagedBean
@ApplicationScoped
public class CandidatePaymentService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2989318601776802934L;

	@ManagedProperty(value="#{candidatePaymentDao}")
	private  CandidatePaymentDao candidatePaymentDao;


	public CandidatePaymentDao getCandidatePaymentDao() {
		return candidatePaymentDao;
	}

	public void setCandidatePaymentDao(CandidatePaymentDao candidatePaymentDao) {
		this.candidatePaymentDao = candidatePaymentDao;
	}

	public List<CandidatePaymentModel> getCandidatePaymentDue(Integer empId,boolean isRecruiter,boolean isAdmin){
		return  candidatePaymentDao.showCandidateDue(empId,isRecruiter,isAdmin);
	}
	
	public Double getTotalAmountForTeamLeader(Integer supervisorId,Date startDate,Date endDate)
	{
		return  candidatePaymentDao.findTotalAmountForTeamLeader(supervisorId,startDate,endDate);
	}
	
	public List<CandidatePayment> showCandidateDueDetals(Integer candidateId)
	{
		return candidatePaymentDao.showCandidateDueDetals(candidateId);
	}
	
	public List<CandidatePayment> getCandidatePaymentByDate(Date startDate,Date endDate){
		return candidatePaymentDao.findCandidatePaymentByDate(startDate, endDate);
	}
	
	public BigDecimal getTotalDueByCandidateId(String course,Integer candidateId){
		return candidatePaymentDao.findTotalDueByCandidateId(course,candidateId);
	}
	
	
	public void savePayment(CandidatePayment candidatePayment, Employee accountant)
	{
		candidatePaymentDao.save(candidatePayment, accountant);
	}
	
	
	 public List<CandidatePayment> getAllCandidatePaymentDebits(Date startDate,Date endDate)
     {
		 return candidatePaymentDao.findAllCandidatePaymentDebits(startDate,endDate);
     }
	 
	 
	 public BigDecimal getTotalDebitsByEmpid(Integer empId,Date startDate,Date endDate){
		 return candidatePaymentDao.findTotalDebitsByEmpid(empId, startDate, endDate);
	 }
	 
	 
	 public List<ChartsUtil> getTotalDebitForBarCharts(Date startDate,Date endDate){
		 return candidatePaymentDao.findTotalDebitForBarCharts(startDate,endDate);
	 }
	
	 public List<CandidatePaymentModel> getCandidatePaymentByDurationType(String durationType,Date startDate,Date endDate){
		 return candidatePaymentDao.findCandidatePaymentByDurationType(durationType,startDate, endDate);
	 }
	 
	 
	 public List<Candidate> getCandidateForAssignToBatch(Integer courseId)
	 {
		 return candidatePaymentDao.findCandidateForAssignToBatch(courseId);
	 }
	 
	 public Object[] candidateFilterForAssign(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		 return candidatePaymentDao.candidateFilterForAssign(first,pageSize,sortField,sortOrder,filters);
	 }
	 
	 public void saveDiscountAmount(CandidatePayment candidatePayment, Employee accountant){
		 candidatePaymentDao.saveDiscountAmount(candidatePayment, accountant);
	 }
	 
	 public List<CandidatePayment> getPaymentsByCandidateId(Integer candidateId){
		 return candidatePaymentDao.findPaymentsByCandidateId(candidateId); 
	 }
	 
	 
	 public List<Map<String,Object>> getCandidatePaymentByDurationType(String durationType,Date startDate,Date endDate,Integer empId,Integer courseId,String receiver,String modeOfPayment){
		 return candidatePaymentDao.findCandidatePaymentByDurationType(durationType,startDate, endDate,empId,courseId,receiver,modeOfPayment);
	 }
	 
	 
	 public List<CandidateCourse> getCourcesForPayment(Integer candidateId)
	 {
		 return candidatePaymentDao.findCourcesForPayment(candidateId);
	 }
	 
	 
	 public List<CandidatePaymentModel> getCandidatePaymentsForSummaryReport(Date startDate,Date endDate)
	 {
		 return candidatePaymentDao.findCandidatePaymentsForSummaryReport(startDate, endDate);
	 }

}