package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.CandidateCheckListDao;
import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;

@ManagedBean
@ApplicationScoped
public class CandidateCheckListService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5139849027231140514L;

	@ManagedProperty(value = "#{candidateCheckListDao}")
	private CandidateCheckListDao candidateCheckListDao;

	public void saveCandidateCheckList(CandidateCheckList candidateCheckList, String fileName, String filePath) {
		candidateCheckListDao.save(candidateCheckList, fileName, filePath);
	}

	public Object[] candidateCheckListFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String checkListTypeName) {
		return candidateCheckListDao.candidateCheckListFilter(first, pageSize, sortField, sortOrder, filters, checkListTypeName);
	}

	public List<CandidateCheckList> findCheckListByCandidateId(Integer candidateId) {
		return candidateCheckListDao.findCheckListByCandidateId(candidateId);
	}

	public void saveCandidateCheckListForAssign(CandidateCheckList candidateCheckList) {
		candidateCheckListDao.saveCandidateCheckListForAssign(candidateCheckList);
	}

	public String findMailContent(Integer checkListId){
		return candidateCheckListDao.findMailContent(checkListId);
	}

	public void updateCandidateCheckListbyId(Integer id){
		candidateCheckListDao.updateCandidateCheckListbyId(id);
	}

	public boolean enrollmentFormStatusCheck(Integer candidateId, String checkListType) {
		return candidateCheckListDao.enrollmentFormStatusCheck(candidateId, checkListType);
	}

	public CandidateCheckList getDocuments(Integer candidateId, String checkListName) {
		return candidateCheckListDao.findDocuments(candidateId, checkListName);
	}

	public CandidateCheckListDao getCandidateCheckListDao() {
		return candidateCheckListDao;
	}

	public void setCandidateCheckListDao(CandidateCheckListDao candidateCheckListDao) {
		this.candidateCheckListDao = candidateCheckListDao;
	}
}