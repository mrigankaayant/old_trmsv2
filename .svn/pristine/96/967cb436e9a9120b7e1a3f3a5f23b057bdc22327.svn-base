package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.trms.hibernate.pojo.CandidateSkills;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CandidateSkillsDao implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8966993176174545682L;

	public List<CandidateSkills>getCandidateSkills(Integer candidateId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidateSkills>cs=null;
		try
		{
			Criteria criteria = session.createCriteria(CandidateSkills.class,"CandidateSkills");
			criteria.add(Restrictions.eq("CandidateSkills.candidate.candidateId",candidateId));
			cs=criteria.list();
			return cs;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
			throw new HibernateException("Data save Exception");
		}finally{
			session.close();
		}
	}
	
	
	
	public void save(CandidateSkills candidateSkills)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(candidateSkills);
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