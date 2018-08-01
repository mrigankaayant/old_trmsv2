package com.ayantsoft.trms.jsf.model;

import java.io.Serializable;
import java.util.Date;

import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;

public class ProgrameScheduleModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2426673390226308880L;
	
	
	private ProgrammeSchedule programeSchedule;
	private int duration;
	private int totalCandidate;
	
	
	public ProgrammeSchedule getProgrameSchedule() {
		return programeSchedule;
	}
	public void setProgrameSchedule(ProgrammeSchedule programeSchedule) {
		this.programeSchedule = programeSchedule;
	}
	
	public int getTotalCandidate() {
		if(this.programeSchedule.getCandidates().size()>0){
			this.totalCandidate = this.programeSchedule.getCandidates().size();
		}else{
			this.totalCandidate = 0;
		}
		return totalCandidate;
	}
	
	public void setTotalCandidate(int totalCandidate) {
		this.totalCandidate = totalCandidate;
	}
	
	public int getDuration() {
		this.duration = (int)( (new Date().getTime() - this.programeSchedule.getStartDate().getTime())/ (1000 * 60 * 60 * 24) );
		return this.duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	

}