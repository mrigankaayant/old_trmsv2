package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.pojo.Designation;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class DesignationDao implements Serializable {
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	 public List<Designation> findAllDesignations()
	 {
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 List<Designation> results = null;
		 try
		 {
			 Criteria criteria = session.createCriteria(Designation.class);
			 results = criteria.list();
			 return results;
			 
		 }catch(Exception e)
		 {
			e.printStackTrace(); 
		 }
		 
		 finally{
			 session.close();
		 }
		 
		 return results;
	 }

}