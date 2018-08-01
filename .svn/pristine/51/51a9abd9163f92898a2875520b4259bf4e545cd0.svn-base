package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.service.CandidatePaymentService;

public class LazyCandidateDataModelForAssign extends LazyDataModel<CandidatePayment> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2684647587363445211L;
	
	private List<CandidatePayment> candidatePayments;
	private CandidatePaymentService candidatePaymentService;
	
	public LazyCandidateDataModelForAssign(CandidatePaymentService candidatePaymentService) {
		this.candidatePaymentService = candidatePaymentService;
	}
	
	@Override
	public Object getRowKey(CandidatePayment candidatePayment) {
		return candidatePayment.getPaymentId();
	}

	@Override
	public CandidatePayment getRowData(String rowKey) {
		for(CandidatePayment cp : candidatePayments) {
			if(cp.getPaymentId() == Integer.parseInt(rowKey))
				return cp;
		}

		return null;
	}

	
	@Override
	public List<CandidatePayment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		Object[] resultWithCount = candidatePaymentService.candidateFilterForAssign(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		candidatePayments=(List<CandidatePayment>) resultWithCount[1];
		return (List<CandidatePayment>) resultWithCount[1];

	}

	public List<CandidatePayment> getCandidatePayments() {
		return candidatePayments;
	}

	public void setCandidatePayments(List<CandidatePayment> candidatePayments) {
		this.candidatePayments = candidatePayments;
	}

	public CandidatePaymentService getCandidatePaymentService() {
		return candidatePaymentService;
	}

	public void setCandidatePaymentService(CandidatePaymentService candidatePaymentService) {
		this.candidatePaymentService = candidatePaymentService;
	}
	
	
	
	

}