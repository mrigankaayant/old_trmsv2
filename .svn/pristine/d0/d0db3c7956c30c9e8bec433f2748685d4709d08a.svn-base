package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;
import com.ayantsoft.trms.service.ProgrameSheduleService;

public class LazyProgrameSheduleModel extends LazyDataModel<ProgrammeSchedule> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2022946253229275357L;
	
	
	private ProgrameSheduleService programeSheduleService;
	private List<ProgrammeSchedule> programmeSchedules;
	
	
	public LazyProgrameSheduleModel(ProgrameSheduleService programeSheduleService) {
		this.programeSheduleService = programeSheduleService;
	}
	
	
	@Override
	public Object getRowKey(ProgrammeSchedule programmeSchedules) {
		return programmeSchedules.getId();
	}
	
	
	@Override
	public ProgrammeSchedule getRowData(String rowKey) {
		for(ProgrammeSchedule p : programmeSchedules) {
			if(p.getId()==Integer.parseInt(rowKey))
				return p;
		}

		return null;
	}
	
	
	
	@Override
	public List<ProgrammeSchedule> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

		Object[] resultWithCount = programeSheduleService.programeSheduleFilter(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		programmeSchedules = (List<ProgrammeSchedule>) resultWithCount[1];
		return programmeSchedules;
	}
	
	
	
	
	public ProgrameSheduleService getProgrameSheduleService() {
		return programeSheduleService;
	}
	public void setProgrameSheduleService(ProgrameSheduleService programeSheduleService) {
		this.programeSheduleService = programeSheduleService;
	}
	public List<ProgrammeSchedule> getProgrammeSchedules() {
		return programmeSchedules;
	}
	public void setProgrammeSchedules(List<ProgrammeSchedule> programmeSchedules) {
		this.programmeSchedules = programmeSchedules;
	}
	
	
	
	
	
	

}