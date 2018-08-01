package com.ayantsoft.trms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.Employee;

public class ReportUtil {


	public static Map<Integer,Double> getTotalAmountByEmp(List<CandidatePayment> candidatePaymentsForDebits)
	{
		Map<Integer,Double> totalAmountByEmp = null;
		if(candidatePaymentsForDebits !=null){
			totalAmountByEmp = new HashMap<>() ;
			for(CandidatePayment cp : candidatePaymentsForDebits ){

				Integer empId = cp.getEmployee().getEmpId();

				if(totalAmountByEmp.containsKey(empId)){
					double paidAmount = totalAmountByEmp.get(empId);	
					paidAmount = paidAmount + cp.getDebit().doubleValue();
					totalAmountByEmp.put(empId, paidAmount);
				}else{
					totalAmountByEmp.put(empId,cp.getDebit().doubleValue());
				}
			}

		}
		return totalAmountByEmp;

	}


	public static Map<Employee,Double> getTotalPaidAmountForTeamLeader(List<Employee> supervisorEmployees,List<CandidatePayment> candidatePaymentsForDebits)
	{
		Map<Employee,Double> totalPaidAmountForTeamLeader =null;
		if(candidatePaymentsForDebits != null){
			totalPaidAmountForTeamLeader = new HashMap<>();
			for(int i=0;i<supervisorEmployees.size();i++)
			{
				Employee supervisorEmployee = supervisorEmployees.get(i);
				for(CandidatePayment cp:candidatePaymentsForDebits)
				{
					Employee supEmp = cp.getEmployee().getEmployee();
					if(supEmp != null)
					{
						if(supEmp.getEmpId() == supervisorEmployee.getEmpId())
						{
							if(totalPaidAmountForTeamLeader.containsKey(supervisorEmployee)){
								double paidAmount = totalPaidAmountForTeamLeader.get(supervisorEmployee);
								paidAmount = paidAmount + cp.getDebit().doubleValue();
								totalPaidAmountForTeamLeader.remove(supervisorEmployee);
								totalPaidAmountForTeamLeader.put(supervisorEmployee, paidAmount);
							}else{
								totalPaidAmountForTeamLeader.put(supervisorEmployee, cp.getDebit().doubleValue());
							}
						}
					}
				}
			}

		}		
		return totalPaidAmountForTeamLeader;
	}
	
	
	
	
	public static List<CandidatePayment> getRecruiterUnderTeamLeader(List<Employee> supervisorEmployees,List<CandidatePayment> candidatePaymentsForDebits)
	{
		
		List<CandidatePayment> recruiterUnderTeamLeader = null;
		if(candidatePaymentsForDebits != null){
		recruiterUnderTeamLeader = new ArrayList<>();
		for(int i=0;i<supervisorEmployees.size();i++){
			for(CandidatePayment cp :candidatePaymentsForDebits){
				if(cp.getEmployee().getEmployee() != null){
					if(cp.getEmployee().getEmployee().getEmpId() == supervisorEmployees.get(i).getEmpId()){
						if(recruiterUnderTeamLeader.size() > 0){
							boolean flag = true;
							for(CandidatePayment c: recruiterUnderTeamLeader){
								if(c.getEmployee().getEmpId() == cp.getEmployee().getEmpId()){
									flag = false;
								}
							}
							
							if(flag){
								recruiterUnderTeamLeader.add(cp);
							}
						}else{
							recruiterUnderTeamLeader.add(cp);
						}
					}
				}
			}
		}
		
		}
		
		return recruiterUnderTeamLeader; 
		
	}

}