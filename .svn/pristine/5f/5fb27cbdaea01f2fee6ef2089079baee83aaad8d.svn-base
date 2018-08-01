package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import com.ayantsoft.trms.hibernate.pojo.CheckListType;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CheckListTypeDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -1636355899677717843L;
	
	public List<CheckListType> getCheckListTypes(){

		List<CheckListType> results = null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(CheckListType.class,"ch");
			criteria.addOrder(Order.asc("ch.indexOrder"));
			results= criteria.list();
		}catch(HibernateException ex){
			ex.getMessage();}
		finally{
			session.close();
		}
		return results;
	}

}