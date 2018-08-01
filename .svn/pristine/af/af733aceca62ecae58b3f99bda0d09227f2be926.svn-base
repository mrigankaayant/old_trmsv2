package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.pojo.UserProfile;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class UserProfileDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4291089341574152872L;
	
	
	public List<String> findEmailByUserRole(String... roles){

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<String> officeEmails = null;
		try
		{
			String hql = "select up.officeEmail from UserProfile up left join up.roles r where ";
			int i = 0;
			for(String role:roles){  
			 if(i == 0)
		     {
				 hql = hql + "r.roleType = \'"+role+"\'";
			 }else{
			     hql = hql + " or r.roleType = \'"+role+"\'"; 
			 }		  
			 i++;
			 
			 Query query = session.createQuery(hql);
			 officeEmails = query.list();
	    } 
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception");
		}
		finally{
			session.close();
		}
		
		return officeEmails; 
	}
	
	
	
	public void save(UserProfile userProfile)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(userProfile);
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