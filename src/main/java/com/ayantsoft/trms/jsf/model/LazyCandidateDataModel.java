package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.service.CandidateService;

public class LazyCandidateDataModel extends LazyDataModel<Candidate>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8771554896839259304L;



	private List<Candidate> candidates;
	private CandidateService candidateService;
	private Integer empId;
	private boolean isAdmin;

	public LazyCandidateDataModel(CandidateService candidateService, Integer empId,boolean isAdmin) {
		this.candidateService = candidateService;
		this.empId=empId;
		this.isAdmin=isAdmin;

	}
	

	@Override
	public Object getRowKey(Candidate candidate) {
		return candidate.getCandidateId();
	}

	@Override
	public Candidate getRowData(String rowKey) {
		for(Candidate can : candidates) {
			if(can.getCandidateId()==Integer.parseInt(rowKey))
				return can;
		}

		return null;
	}

	
	@Override
	public List<Candidate> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		Object[] resultWithCount = candidateService.candidateFilter(first, pageSize, sortField, sortOrder, filters, empId,isAdmin);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		candidates=(List<Candidate>) resultWithCount[1];
		return (List<Candidate>) resultWithCount[1];

	}
	

	public CandidateService getCandidateService() {
		return candidateService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}


	public List<Candidate> getCandidates() {
		return candidates;
	}


	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}


}