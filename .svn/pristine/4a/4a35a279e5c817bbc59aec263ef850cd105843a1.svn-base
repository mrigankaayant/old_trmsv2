package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import com.ayantsoft.trms.hibernate.pojo.Department;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;


@ManagedBean(eager=true)
@ApplicationScoped
public class DepartmentDao implements Serializable {
	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2742550814142514178L;


	public DepartmentDao() {
		
	}
	
 
	 public List<Department> findAllDepartments()
	 {
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 List<Department> results = null;
		 try
		 {
			 Criteria criteria = session.createCriteria(Department.class);
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