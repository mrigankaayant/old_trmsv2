package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.FollowUpDao;
import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.FollowUp;
import com.ayantsoft.trms.jsf.model.FreePoolCandidatesDto;


@ManagedBean
@ApplicationScoped
public class FollowUpService implements Serializable{

	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 1889971614755795912L;

	@ManagedProperty(value="#{followUpDao}")
	private  FollowUpDao followUpDao ;


	public FollowUpDao getFollowUpDao() {
		return followUpDao;
	}


	public void setFollowUpDao(FollowUpDao followUpDao) {
		this.followUpDao = followUpDao;
	}

	public void saveFollowUp(FollowUp followUp,Candidate candidate){
		followUpDao.saveFollowUp(followUp,candidate);
	}
	
	public void save(FollowUp followUp){
		followUpDao.save(followUp);
	}

	public List<FollowUp> followUpListForCandidate(Integer candidateId){
		return followUpDao.returnFollowUpList(candidateId);
	}
	
	
	public List<FreePoolCandidatesDto> findFreePoolCandidates(Date checkingDate){
		return followUpDao.findFreePoolCandidates(checkingDate); 
	}
}