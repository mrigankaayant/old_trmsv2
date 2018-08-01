package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.EmployeeDao;
import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.hibernate.pojo.Role;

@ManagedBean
@ApplicationScoped
public class EmployeeService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 777157810825218899L;

	@ManagedProperty(value="#{employeeDao}")
	private EmployeeDao employeeDao;


	public void saveEmployee(Employee employee,Set<Role> roles)
	{
		employeeDao.save(employee,roles);
	}

	public boolean checkUniqueEmail(String email, Integer empId){
		return employeeDao.checkUniqueEmail(email,empId);
	}

	public Employee getEmployeeById(Integer empId)
	{
		return employeeDao.findEmployeeById(empId);
	}


	public boolean getPassword(Integer empId,String password)
	{
		return employeeDao.findPassword(empId, password);
	}


	public boolean savePasswordForUpdate(Integer empId,String password)
	{
		return employeeDao.savePassword(empId, password);
	}


	public boolean getUsername(String username)
	{
		return employeeDao.findUsername(username);
	}

	public List<Employee> getAllEmployee()
	{
		return employeeDao.findAllEmployee(); 
	}

	public Object[] employeeFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return employeeDao.employeeFilter(first, pageSize, sortField, sortOrder, filters);
	}

	public List<Candidate> getHotlistCandidate(){
		return employeeDao.findHotlistCandidate();
	}

	public List<Candidate> getPlacedCandidate(){
		return employeeDao.findPlacedCandidate();
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}


	public List<Employee> getEmployeesForSuperviser(Integer empId)
	{
		return employeeDao.getEmployeesForSuperviser(empId);

	}


	public List<Employee> getEmployeeByDepartmentName(String departmentName){
		return employeeDao.findEmployeeByDepartmentName(departmentName);
	}

	public boolean checkWorkMobileForValidation(String mobile, Integer employeeId)
	{
		return employeeDao.checkWorkMobile(mobile,employeeId);
	}


	public List<Employee> getSupervisor()
	{
		return employeeDao.findSupervisor();
	}

}