package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.RoleDao;
import com.ayantsoft.trms.hibernate.pojo.Role;

@ManagedBean
@ApplicationScoped
public class RoleService implements Serializable {
	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9022084478683187060L;
	
	@ManagedProperty(value="#{roleDao}")
	private RoleDao roleDao;
	
	
	public List<Role> getAllRoles()
	{
		return roleDao.findAllRoles();
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	

}