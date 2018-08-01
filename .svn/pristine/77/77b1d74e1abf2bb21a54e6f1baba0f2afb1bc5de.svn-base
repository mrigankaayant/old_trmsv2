package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.trms.hibernate.pojo.CandidateRemarks;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CandidateRemarksDao implements Serializable {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5022201859751490272L;

	public List<CandidateRemarks> getcandidateRemarks(){

		List<CandidateRemarks>results=null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(CandidateRemarks.class,"cr");
			criteria.addOrder(Order.asc("cr.orderIndex"));
			results= criteria.list();
		}catch(HibernateException ex){
			ex.getMessage();}
		finally{
			session.close();
		}
		return results;
	}
	
	public List<CandidateRemarks> getcandidateRemarksTillEnrolled(){

		List<CandidateRemarks>results=null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(CandidateRemarks.class,"cr");
			criteria.add(Restrictions.le("cr.orderIndex",9));
			criteria.addOrder(Order.asc("cr.orderIndex"));
			results= criteria.list();
		}catch(HibernateException ex){
			ex.getMessage();}
		finally{
			session.close();
		}
		return results;
	}
	
	
}