package com.ayantsoft.trms.hibernate.pojo;
// Generated 22 Dec, 2017 6:00:43 PM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "ayant_trmsv2")
public class Role implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8517343795425653537L;
	
	private String roleType;
	private String displayValue;
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

	public Role() {
	}

	public Role(String roleType) {
		this.roleType = roleType;
	}

	public Role(String roleType, String displayValue, Set<UserProfile> userProfiles) {
		this.roleType = roleType;
		this.displayValue = displayValue;
		this.userProfiles = userProfiles;
	}

	@Id

	@Column(name = "role_type", unique = true, nullable = false, length = 45)
	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Column(name = "display_value", length = 45)
	public String getDisplayValue() {
		return this.displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<UserProfile> getUserProfiles() {
		return this.userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

}
