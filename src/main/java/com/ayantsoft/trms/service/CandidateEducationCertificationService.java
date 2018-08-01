package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.CanEducationCertiDao;
import com.ayantsoft.trms.hibernate.pojo.EducationCertification;



@ManagedBean
@ApplicationScoped
public class CandidateEducationCertificationService implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6307320497811304209L;
	
	@ManagedProperty(value="#{canEducationCertiDao}")
	private  CanEducationCertiDao canEducationCertiDao;


	public List<EducationCertification> getCandidateEducationDetails(Integer candidateId)
	{
		return canEducationCertiDao.getCandidateEducation(candidateId);
	}
	
	
	public void saveEducationCertification(EducationCertification educationCertification)
	{
		canEducationCertiDao.save(educationCertification);
	}


	public CanEducationCertiDao getCanEducationCertiDao() {
		return canEducationCertiDao;
	}


	public void setCanEducationCertiDao(CanEducationCertiDao canEducationCertiDao) {
		this.canEducationCertiDao = canEducationCertiDao;
	}



	

}