package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.trms.hibernate.pojo.UserProfile;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class LoginDao implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8346574485437638743L;
	
	
	public UserProfile loginfromDb(String username, String password){
			Session session = HibernateUtil.getSessionFactory().openSession();
			UserProfile userProfile = null;
			try{
				String hql ="from UserProfile up join fetch up.roles r join fetch up.employee e join fetch e.contactAddress where up.username=:username and up.password=:password";
				Query query = session.createQuery(hql);
				query.setParameter("username", username);
				query.setParameter("password", password);
				userProfile = (UserProfile)query.uniqueResult();
			}catch(HibernateException e){
				e.printStackTrace();
			}finally {
				session.close();
			}
			
			return userProfile;
	}

}