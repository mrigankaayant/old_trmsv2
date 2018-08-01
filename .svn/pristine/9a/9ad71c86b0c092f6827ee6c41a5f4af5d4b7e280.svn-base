package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;
import com.ayantsoft.trms.service.CandidateCheckListService;

public class LazyCandidateCheckListDataModel extends LazyDataModel<CandidateCheckList> {
	
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -5035744720486063226L;
	
	private List<CandidateCheckList> candidateCheckLists;
	private CandidateCheckListService candidateCheckListService;
	private String checkListTypeName;
	
	public LazyCandidateCheckListDataModel(CandidateCheckListService candidateCheckListService,String checkListTypeName) {
		this.candidateCheckListService = candidateCheckListService;
		this.checkListTypeName = checkListTypeName;
	}
	

	@Override
	public Object getRowKey(CandidateCheckList candidateCheckList) {
		return candidateCheckList.getId();
	}

	@Override
	public CandidateCheckList getRowData(String rowKey) {
		for(CandidateCheckList candidateCheckList : candidateCheckLists) {
			if(candidateCheckList.getId()==Integer.parseInt(rowKey))
				return candidateCheckList;
		}
		return null;
	}

	
	@Override
	public List<CandidateCheckList> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		Object[] resultWithCount = candidateCheckListService.candidateCheckListFilter(first, pageSize, sortField, sortOrder, filters, checkListTypeName);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		
		candidateCheckLists = (List<CandidateCheckList>) resultWithCount[1];		
		return (List<CandidateCheckList>) resultWithCount[1];
	}
	
	
	
	public List<CandidateCheckList> getCandidateCheckLists() {
		return candidateCheckLists;
	}


	public void setCandidateCheckLists(List<CandidateCheckList> candidateCheckLists) {
		this.candidateCheckLists = candidateCheckLists;
	}


	public CandidateCheckListService getCandidateCheckListService() {
		return candidateCheckListService;
	}
	public void setCandidateCheckListService(CandidateCheckListService candidateCheckListService) {
		this.candidateCheckListService = candidateCheckListService;
	}


	public String getCheckListTypeName() {
		return checkListTypeName;
	}


	public void setCheckListTypeName(String checkListTypeName) {
		this.checkListTypeName = checkListTypeName;
	}
	
	
	
	

}