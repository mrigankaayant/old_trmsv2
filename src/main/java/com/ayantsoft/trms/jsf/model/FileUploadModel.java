package com.ayantsoft.trms.jsf.model;

import java.util.List;

import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;

public class FileUploadModel {
	
	private Integer candidateId;
	private String candidateName;
	private String filePath;
	private String rootDir;
	private String dirName;
	private List<CandidateCheckList> selectedCandidateCheckList;

	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRootDir() {
		return rootDir;
	}
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public List<CandidateCheckList> getSelectedCandidateCheckList() {
		return selectedCandidateCheckList;
	}
	public void setSelectedCandidateCheckList(List<CandidateCheckList> selectedCandidateCheckList) {
		this.selectedCandidateCheckList = selectedCandidateCheckList;
	}

}