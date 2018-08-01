package com.ayantsoft.trms.hibernate.pojo;
// Generated 20 Sep, 2017 2:28:09 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EducationCertification generated by hbm2java
 */
@Entity
@Table(name = "education_certification", catalog = "ayant_trmsv2")
public class EducationCertification implements java.io.Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 2593066009361487151L;
	
	private Integer id;
	private Candidate candidate;
	private String country;
	private String credential;
	private String credentialType;
	private Short pergentage;
	private String university;
	private Date yearOfPassing;

	public EducationCertification() {
	}

	public EducationCertification(Candidate candidate) {
		this.candidate = candidate;
	}

	public EducationCertification(Candidate candidate, String country, String credential, String credentialType,
			Short pergentage, String university, Date yearOfPassing) {
		this.candidate = candidate;
		this.country = country;
		this.credential = credential;
		this.credentialType = credentialType;
		this.pergentage = pergentage;
		this.university = university;
		this.yearOfPassing = yearOfPassing;
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
	@JoinColumn(name = "candidate_id", nullable = false)
	public Candidate getCandidate() {
		return this.candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	@Column(name = "country", length = 45)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "credential", length = 45)
	public String getCredential() {
		return this.credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	@Column(name = "credential_type", length = 45)
	public String getCredentialType() {
		return this.credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	@Column(name = "pergentage")
	public Short getPergentage() {
		return this.pergentage;
	}

	public void setPergentage(Short pergentage) {
		this.pergentage = pergentage;
	}

	@Column(name = "university", length = 145)
	public String getUniversity() {
		return this.university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "year_of_passing", length = 10)
	public Date getYearOfPassing() {
		return this.yearOfPassing;
	}

	public void setYearOfPassing(Date yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

}