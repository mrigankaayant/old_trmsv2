package com.ayantsoft.trms.jsf.model;

import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.ayantsoft.trms.hibernate.pojo.PhoneRecording;
import com.ayantsoft.trms.service.PhoneRecordingService;

public class LazyCallHistoryDataModel extends LazyDataModel<PhoneRecording>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 598804670048014449L;
	
	private List<PhoneRecording> phoneRecordings;
	private PhoneRecordingService phoneRecordingService;
	private String employeeName;
	private boolean isAdmin;
	
	
	public LazyCallHistoryDataModel(PhoneRecordingService phoneRecordingService,String employeeName,boolean isAdmin) {
		this.phoneRecordingService = phoneRecordingService;
		this.employeeName=employeeName;
		this.isAdmin=isAdmin;

	}
	
	@Override
	public Object getRowKey(PhoneRecording phoneRecording) {
		return phoneRecording.getId();
	}

	@Override
	public PhoneRecording getRowData(String rowKey) {
		for(PhoneRecording p : phoneRecordings) {
			if(p.getId() == Integer.parseInt(rowKey))
				return p;
		}
		return null;
	}
	
	@Override
	public List<PhoneRecording> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		Object[] resultWithCount = phoneRecordingService.callLogFilter(first, pageSize, sortField, sortOrder, filters,isAdmin,employeeName);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		phoneRecordings=(List<PhoneRecording>) resultWithCount[1];
		return (List<PhoneRecording>) resultWithCount[1];
	}
	
	public List<PhoneRecording> getPhoneRecordings() {
		return phoneRecordings;
	}
	public void setPhoneRecordings(List<PhoneRecording> phoneRecordings) {
		this.phoneRecordings = phoneRecordings;
	}
	public PhoneRecordingService getPhoneRecordingService() {
		return phoneRecordingService;
	}
	public void setPhoneRecordingService(PhoneRecordingService phoneRecordingService) {
		this.phoneRecordingService = phoneRecordingService;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
