package com.ayantsoft.trms.hibernate.pojo;
// Generated 20 Sep, 2017 2:28:09 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProfileImage generated by hbm2java
 */
@Entity
@Table(name = "profile_image", catalog = "ayant_trmsv2")
public class ProfileImage implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1986132658312388939L;
	
	private Integer id;
	private Employee employee;
	private byte[] profileImage;

	public ProfileImage() {
	}

	public ProfileImage(Employee employee) {
		this.employee = employee;
	}

	public ProfileImage(Employee employee, byte[] profileImage) {
		this.employee = employee;
		this.profileImage = profileImage;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_Id", nullable = false)
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Column(name = "profile_image")
	public byte[] getProfileImage() {
		return this.profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

}