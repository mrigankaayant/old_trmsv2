package com.ayantsoft.trms.hibernate.pojo;
// Generated 20 Sep, 2017 2:28:09 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EmployeeHasTask generated by hbm2java
 */
@Entity
@Table(name = "employee_has_task", catalog = "ayant_trmsv2")
public class EmployeeHasTask implements java.io.Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 6828375968984726401L;
	
	private EmployeeHasTaskId id;
	private Employee employee;
	private TaskRole taskRole;

	public EmployeeHasTask() {
	}

	public EmployeeHasTask(EmployeeHasTaskId id, Employee employee, TaskRole taskRole) {
		this.id = id;
		this.employee = employee;
		this.taskRole = taskRole;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "employeeId", column = @Column(name = "employee_id", nullable = false)),
			@AttributeOverride(name = "taskId", column = @Column(name = "task_id", nullable = false)) })
	public EmployeeHasTaskId getId() {
		return this.id;
	}

	public void setId(EmployeeHasTaskId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false, insertable = false, updatable = false)
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_role_id", nullable = false)
	public TaskRole getTaskRole() {
		return this.taskRole;
	}

	public void setTaskRole(TaskRole taskRole) {
		this.taskRole = taskRole;
	}

}