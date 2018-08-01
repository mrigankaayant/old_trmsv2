package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ayantsoft.trms.hibernate.pojo.CandidateVisa;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped

public class CandidateVisaDao implements Serializable{

	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 3037749538966993929L;

	public List<CandidateVisa> getcandidateVisa(){

		List<CandidateVisa>results=null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(CandidateVisa.class,"c");
			criteria.addOrder(Order.asc("c.visa"));
			results= criteria.list();
		}catch(HibernateException ex){
			ex.getMessage();}
		finally{
			session.close();
		}
		return results;
	}
	
	
}