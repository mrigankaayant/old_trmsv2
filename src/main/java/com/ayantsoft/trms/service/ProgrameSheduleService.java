package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.ProgrameSheduleDao;
import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;
import com.ayantsoft.trms.jsf.model.ProgrameScheduleModel;

@ManagedBean
@ApplicationScoped
public class ProgrameSheduleService implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -3816132917486904546L;

	@ManagedProperty(value="#{programeSheduleDao}")
	private  ProgrameSheduleDao programeSheduleDao;


	public void saveProgrameShedule(ProgrammeSchedule programmeSchedule)
	{
		programeSheduleDao.save(programmeSchedule);
	}


	public Object[] programeSheduleFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return programeSheduleDao.programeSheduleFilter(first, pageSize, sortField, sortOrder, filters);
	}

	public List<ProgrameScheduleModel> getProgrameScheduleForAssign(){
		return programeSheduleDao.findProgrameScheduleForAssign();
	}


	public int getTotalCandidate(Integer programmeScheduleId)
	{
		return programeSheduleDao.findTotalCandidate(programmeScheduleId);
	}

	public List<ProgrameScheduleModel> getProgrameScheduleByTrainerId(Integer trainerId,String modeOfPayment)
	{
		return programeSheduleDao.findProgrameScheduleByTrainerId(trainerId, modeOfPayment);
	}


	public ProgrammeSchedule getProgrameScheduleById(Integer id)
	{
		return programeSheduleDao.findProgrameScheduleById(id);
	}

	public ProgrammeSchedule getCandidatesById(Integer id)
	{
		return programeSheduleDao.findCandidatesByBatchId(id);
	}

	public ProgrammeSchedule getCandidates(Integer batchId){
		return programeSheduleDao.findCandidates(batchId);
	}
	
	
	public void deleteBatch(ProgrammeSchedule programmeSchedule ){
		programeSheduleDao.deleteBatch(programmeSchedule);
	}


	public ProgrameSheduleDao getProgrameSheduleDao() {
		return programeSheduleDao;
	}


	public void setProgrameSheduleDao(ProgrameSheduleDao programeSheduleDao) {
		this.programeSheduleDao = programeSheduleDao;
	}




}