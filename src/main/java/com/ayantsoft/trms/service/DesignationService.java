package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.DesignationDao;
import com.ayantsoft.trms.hibernate.pojo.Designation;

@ManagedBean
@ApplicationScoped
public class DesignationService implements Serializable {
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3338915859340876242L;
	
	@ManagedProperty(value="#{designationDao}")
	private DesignationDao designationDao;
	
	
	public List<Designation> getAllDesignations()
	{
		return designationDao.findAllDesignations();
	}


	public DesignationDao getDesignationDao() {
		return designationDao;
	}


	public void setDesignationDao(DesignationDao designationDao) {
		this.designationDao = designationDao;
	}

	
	
	

}