package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.pojo.Documents;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class DocumentsDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2494497587983654266L;
	

	public void save(Documents documents)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(documents);
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
	
	
	public void delete(Documents documents)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.delete(documents);
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data delete Exception");
		}finally{
			session.close();
		}
	}
	
}