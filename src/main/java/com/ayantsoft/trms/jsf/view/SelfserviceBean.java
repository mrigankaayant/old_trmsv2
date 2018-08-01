package com.ayantsoft.trms.jsf.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.service.EmployeeService;

@ManagedBean
@ViewScoped
public class SelfserviceBean implements Serializable{

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -722545781173558161L;
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;
	
	
	@ManagedProperty("#{employeeService}")
	private EmployeeService employeeService;
	
	private String action;
	private Employee employee;
	
	
	public void employeeProfile()
	{
		action = "PROFILE";
		Integer empId = loginBean.getUserProfile().getEmployee().getEmpId();
		employee = new Employee();
		employee = employeeService.getEmployeeById(empId);
	}


	//setter and getter
	
	
	
	public String getAction() {
		return action;
	}


	public EmployeeService getEmployeeService() {
		return employeeService;
	}






	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}






	public Employee getEmployee() {
		return employee;
	}






	public void setEmployee(Employee employee) {
		this.employee = employee;
	}






	public void setAction(String action) {
		this.action = action;
	}






	public LoginBean getLoginBean() {
		return loginBean;
	}






	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	

}