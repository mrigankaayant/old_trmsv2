package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.CandidateRemarksDao;
import com.ayantsoft.trms.hibernate.pojo.CandidateRemarks;

@ManagedBean
@ApplicationScoped
public class CandidateRemarksService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -532851165514402942L;

	
	@ManagedProperty(value="#{candidateRemarksDao}")
	private  CandidateRemarksDao candidateRemarksDao ;
	
	public CandidateRemarksDao getCandidateRemarksDao() {
		return candidateRemarksDao;
	}

	public void setCandidateRemarksDao(CandidateRemarksDao candidateRemarksDao) {
		this.candidateRemarksDao = candidateRemarksDao;
	}
	
	public List<CandidateRemarks> getAllcandidateStatus(){

		return candidateRemarksDao.getcandidateRemarks();

	}
	
}