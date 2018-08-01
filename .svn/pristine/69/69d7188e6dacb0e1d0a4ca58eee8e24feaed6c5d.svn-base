package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.DepartmentDao;
import com.ayantsoft.trms.hibernate.pojo.Department;

@ManagedBean
@ApplicationScoped
public class DepartmentService implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 706247714867960048L;
	
	@ManagedProperty(value="#{departmentDao}")
	private DepartmentDao departmentDao;
	
	
	public List<Department> getAllDepartment()
	{
		return departmentDao.findAllDepartments();
	}

	
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	

}