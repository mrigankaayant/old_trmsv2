package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.TrainerDao;
import com.ayantsoft.trms.hibernate.pojo.Trainer;

@ManagedBean
@ApplicationScoped
public class TrainerService implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -6829038862768536095L;

	@ManagedProperty(value="#{trainerDao}")
	private  TrainerDao trainerDao ;


	public void saveTrainer(Trainer trainer)
	{
		trainerDao.save(trainer);
	}


	public Object[] trainerFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return trainerDao.trainerFilter(first, pageSize, sortField, sortOrder, filters);
	}

	public List<Trainer> getTrainers()
	{
		return trainerDao.getTrainers();
	}

	public List<Trainer> findTrainersForGenerateInvoice(){
		return trainerDao.getTrainersForGenerateInvoice();
	}
	
	public List<Trainer> getTrainerByCourseId(Integer courseId){
		return trainerDao.findTrainerByCourseId(courseId);
	}


	public boolean checkWorkMobileForValidation(String mobile, Integer employeeId)
	{
		return trainerDao.checkWorkMobile(mobile, employeeId);
	}
	
	public boolean checkUniqueEmail(String email, Integer empId){
		return trainerDao.checkUniqueEmail(email,empId);
	}


	public Trainer getTrainerById(Integer trainerId){
		return trainerDao.findTrainerById(trainerId);
	}

	public TrainerDao getTrainerDao() {
		return trainerDao;
	}

	public void setTrainerDao(TrainerDao trainerDao) {
		this.trainerDao = trainerDao;
	}





}