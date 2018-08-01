package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.pojo.Cities;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CityDao implements Serializable{


	//this class provides Dao support for getting US cities list
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3429748263100358212L;

	public List<String> getCitiesByCountry(String countryShortName){

		Session session=HibernateUtil.getSessionFactory().openSession();

		String hql="select concat(c.name,',',s.name,',',s.timeZone)  from Cities c "
				+ "join c.states s where s.countries.sortname=:countryShortName";
		Query query = session.createQuery(hql);
		query.setParameter("countryShortName", countryShortName);
               List<String>l1=query.list();
		return (List<String>) query.list();
	}
	
	
	public void save(Cities city){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(city);
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