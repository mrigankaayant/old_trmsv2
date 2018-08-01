package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Freepool;
import com.ayantsoft.trms.service.FreePoolService;

public class LazyFreePoolCandidateModel extends LazyDataModel<Freepool> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3113339371849538946L;
	
	
	private List<Freepool> freePoolCandidates;
	private FreePoolService freePoolService;
	
	public LazyFreePoolCandidateModel(FreePoolService freePoolService){
		this.freePoolService = freePoolService;
	}
	
	@Override
	public Object getRowKey(Freepool freepool) {
		return freepool.getId();
	}
	
	
	@Override
	public Freepool getRowData(String rowKey) {
		for(Freepool f : freePoolCandidates) {
			if(f.getId() == Integer.parseInt(rowKey))
				return f;
		}
		return null;
	}
	
	
	@Override
	public List<Freepool> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		Object[] resultWithCount = freePoolService.freePoolCandidatesFilter(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		freePoolCandidates=(List<Freepool>) resultWithCount[1];
		return (List<Freepool>) resultWithCount[1];
	}
	
	

	public List<Freepool> getFreePoolCandidates() {
		return freePoolCandidates;
	}

	public void setFreePoolCandidates(List<Freepool> freePoolCandidates) {
		this.freePoolCandidates = freePoolCandidates;
	}

	public FreePoolService getFreePoolService() {
		return freePoolService;
	}

	public void setFreePoolService(FreePoolService freePoolService) {
		this.freePoolService = freePoolService;
	}

}
