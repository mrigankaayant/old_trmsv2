package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.dao.FreePoolDao;
import com.ayantsoft.trms.hibernate.pojo.Freepool;

@ManagedBean
@ApplicationScoped
public class FreePoolService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8882768618292122958L;
	
	@ManagedProperty(value="#{freePoolDao}")
	private  FreePoolDao freePoolDao ;
	
	public Object[] freePoolCandidatesFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return freePoolDao.freePoolCandidatesFilter(first, pageSize, sortField, sortOrder, filters);
	}
	
	public Freepool findFreepoolByCanId(int candidateId){
		return freePoolDao.findFreepoolByCanId(candidateId);
	}
	
	public void delete(int id){
		freePoolDao.delete(id);
	}
	
	public FreePoolDao getFreePoolDao() {
		return freePoolDao;
	}

	public void setFreePoolDao(FreePoolDao freePoolDao) {
		this.freePoolDao = freePoolDao;
	}
}
