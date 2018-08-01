package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.TrainerInvoiceDao;
import com.ayantsoft.trms.hibernate.pojo.TrainerInvoice;

@ManagedBean
@ApplicationScoped
public class TrainerInvoiceService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2678103798360855316L;
	
	@ManagedProperty(value="#{trainerInvoiceDao}")
	private TrainerInvoiceDao trainerInvoiceDao;
	
	
	public void save(TrainerInvoice trainerInvoice)
	{
		trainerInvoiceDao.save(trainerInvoice);
	}
	
	
	public Long getTotalAmount(Integer programeScheduleId,Integer trainerId)
	{
		return trainerInvoiceDao.findTotalAmount(programeScheduleId,trainerId);
	}
	
	public boolean getInvoiceForMonthly(Integer trainerId,String month,String year)
	{
		return trainerInvoiceDao.findInvoiceForMonthly(trainerId, month, year);
	}
	
	
	public Object[] trainerInvoiceFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return trainerInvoiceDao.trainerInvoiceFilter(first, pageSize, sortField, sortOrder, filters);
	}
	
	
	public List<TrainerInvoice> getTrainerInvoiceByTrainerId(Integer trainerId,Integer programeScheduleId){
		return trainerInvoiceDao.findTrainerInvoiceByTrainerId(trainerId, programeScheduleId);
	}


	public TrainerInvoiceDao getTrainerInvoiceDao() {
		return trainerInvoiceDao;
	}


	public void setTrainerInvoiceDao(TrainerInvoiceDao trainerInvoiceDao) {
		this.trainerInvoiceDao = trainerInvoiceDao;
	}
	

}