package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


import com.ayantsoft.trms.hibernate.dao.CandidateSkillsDao;
import com.ayantsoft.trms.hibernate.pojo.CandidateSkills;

@ManagedBean
@ApplicationScoped
public class CandidateSkillsService implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8784634662929821502L;

	
	@ManagedProperty(value="#{candidateSkillsDao}")
	private  CandidateSkillsDao candidateSkillsDao;
	
	
	public List<CandidateSkills> getCandidateSkills(Integer candidateId)
	{
		return candidateSkillsDao.getCandidateSkills(candidateId);
	}
	
	public void saveCandidateSkills(CandidateSkills candidateSkills)
	{
		candidateSkillsDao.save(candidateSkills);
	}


	public CandidateSkillsDao getCandidateSkillsDao() {
		return candidateSkillsDao;
	}


	public void setCandidateSkillsDao(CandidateSkillsDao candidateSkillsDao) {
		this.candidateSkillsDao = candidateSkillsDao;
	}
	
	
}