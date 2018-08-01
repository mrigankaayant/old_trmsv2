package com.ayantsoft.trms.hibernate.pojo;
// Generated 20 Sep, 2017 2:28:09 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TaskTag generated by hbm2java
 */
@Entity
@Table(name = "task_tag", catalog = "ayant_trmsv2")
public class TaskTag implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8621784303619673229L;
	
	private Integer tagId;
	private String description;
	private String tagName;

	public TaskTag() {
	}

	public TaskTag(String description, String tagName) {
		this.description = description;
		this.tagName = tagName;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "tag_id", unique = true, nullable = false)
	public Integer getTagId() {
		return this.tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "tag_name", length = 100)
	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}