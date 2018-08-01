package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.pojo.Role;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class RoleDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public List<Role> findAllRoles()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Role> results = null;
		try
		{
			Criteria criteria = session.createCriteria(Role.class);
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