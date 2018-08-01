package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.TrainerInvoice;
import com.ayantsoft.trms.service.TrainerInvoiceService;

public class LazyTrainerInvoiceDataModel  extends LazyDataModel<TrainerInvoice> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 541241118965605627L;
	
	private TrainerInvoiceService trainerInvoiceService;

	public LazyTrainerInvoiceDataModel(TrainerInvoiceService trainerInvoiceService) {
		this.trainerInvoiceService = trainerInvoiceService;
	}


	@Override
	public Object getRowKey(TrainerInvoice trainerInvoice) {
		return trainerInvoice.getTrainerInvoiceId();
	}
	
	@Override
	public List<TrainerInvoice> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

		Object[] resultWithCount = trainerInvoiceService.trainerInvoiceFilter(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		return (List<TrainerInvoice>) resultWithCount[1];
	}


	public TrainerInvoiceService getTrainerInvoiceService() {
		return trainerInvoiceService;
	}


	public void setTrainerInvoiceService(TrainerInvoiceService trainerInvoiceService) {
		this.trainerInvoiceService = trainerInvoiceService;
	}



	

}