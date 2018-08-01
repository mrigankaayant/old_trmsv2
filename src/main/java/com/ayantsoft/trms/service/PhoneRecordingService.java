package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.SortOrder;
import com.ayantsoft.trms.hibernate.dao.PhoneRecordingDao;

@ManagedBean
@ApplicationScoped
public class PhoneRecordingService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3178690840033560320L;
	
	
	@ManagedProperty(value="#{phoneRecordingDao}")
	private  PhoneRecordingDao phoneRecordingDao ;
	
	
	
	public Object[] callLogFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters, boolean isAdmin,String employeeName){
		return phoneRecordingDao.callLogFilter(first, pageSize, sortField, sortOrder, filters,isAdmin,employeeName);
	}

	
	public Object[] callLogFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters, boolean isAdmin,String employeeName,String startDate,String endDate){
		return phoneRecordingDao.callLogFilter(first, pageSize, sortField, sortOrder, filters,isAdmin,employeeName,startDate,endDate);
	}


	public PhoneRecordingDao getPhoneRecordingDao() {
		return phoneRecordingDao;
	}


	public void setPhoneRecordingDao(PhoneRecordingDao phoneRecordingDao) {
		this.phoneRecordingDao = phoneRecordingDao;
	}
}
