package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Trainer;
import com.ayantsoft.trms.service.TrainerService;

public class LazyTrainerDataModel extends LazyDataModel<Trainer> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8490449246482174922L;
	
	private TrainerService trainerService;
	private List<Trainer> trainers;
	
	
	public LazyTrainerDataModel(TrainerService trainerService) {
		this.trainerService = trainerService;
	}
	
	@Override
	public Object getRowKey(Trainer trainer) {
		return trainer.getId();
	}
	
	@Override
	public Trainer getRowData(String rowKey) {
		for(Trainer t : trainers) {
			if(t.getId()==Integer.parseInt(rowKey))
				return t;
		}

		return null;
	}
	
	@Override
	public List<Trainer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

		Object[] resultWithCount = trainerService.trainerFilter(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		trainers = (List<Trainer>) resultWithCount[1];
		return trainers;
	}
	
	

	public TrainerService getTrainerService() {
		return trainerService;
	}

	public void setTrainerService(TrainerService trainerService) {
		this.trainerService = trainerService;
	}

	public List<Trainer> getTrainers() {
		return trainers;
	}

	public void setTrainers(List<Trainer> trainers) {
		this.trainers = trainers;
	}
	
	

}