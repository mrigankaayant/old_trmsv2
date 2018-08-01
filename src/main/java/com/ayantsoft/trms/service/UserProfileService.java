package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.UserProfileDao;
import com.ayantsoft.trms.hibernate.pojo.UserProfile;

@ManagedBean
@ApplicationScoped
public class UserProfileService implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 8254581682097025355L;

	
	@ManagedProperty(value="#{userProfileDao}")
	private  UserProfileDao userProfileDao;
	
	
	public List<String> getEmailByUserRole(String... roles)
	{
		return userProfileDao.findEmailByUserRole(roles);
	}
	
	
	public void saveUserProfile(UserProfile userProfile)
	{
		userProfileDao.save(userProfile);
	}


	public UserProfileDao getUserProfileDao() {
		return userProfileDao;
	}


	public void setUserProfileDao(UserProfileDao userProfileDao) {
		this.userProfileDao = userProfileDao;
	}
	
	
	
	

}