package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.trms.hibernate.pojo.EducationCertification;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;


@ManagedBean
@ApplicationScoped
public class CanEducationCertiDao implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5689510052129412935L;

	public List<EducationCertification>getCandidateEducation(Integer candidateId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<EducationCertification>ce=null;
		try
		{
			Criteria criteria = session.createCriteria(EducationCertification.class,"educationCertification");
			criteria.add(Restrictions.eq("educationCertification.candidate.candidateId",candidateId));
			ce=criteria.list();
			return ce;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
			throw new HibernateException("Data save Exception");
		}finally{
			session.close();
		}
	}
	
	
	
	public void save(EducationCertification educationCertification)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(educationCertification);
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save Exception");
		}finally{
			session.close();
		}
	}
	
	
}