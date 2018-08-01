package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class StateDao implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2647795387193406607L;

	public List<String> getStatesByCountry(String countryShortName){

		Session session=HibernateUtil.getSessionFactory().openSession();

		String hql="select concat(s.name,',',s.id)  from States s "
				+ "join s.countries where s.countries.sortname=:countryShortName";
		Query query = session.createQuery(hql);
		query.setParameter("countryShortName", countryShortName);
               List<String>l1=query.list();
		return (List<String>) query.list();
	}
}