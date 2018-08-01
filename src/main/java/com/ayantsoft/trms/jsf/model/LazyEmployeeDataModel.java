package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.service.EmployeeService;

public class LazyEmployeeDataModel  extends LazyDataModel<Employee> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 541241118965605627L;
	
	private EmployeeService employeeService;

	public LazyEmployeeDataModel(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	@Override
	public Object getRowKey(Employee employee) {
		return employee.getEmpId();
	}
	
	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

		Object[] resultWithCount = employeeService.employeeFilter(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		return (List<Employee>) resultWithCount[1];
	}


	public EmployeeService getEmployeeService() {
		return employeeService;
	}


	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	

}