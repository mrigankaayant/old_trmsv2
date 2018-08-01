package com.ayantsoft.trms.jsf.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.jsf.model.CandidatePaymentModel;
import com.ayantsoft.trms.service.CandidatePaymentService;
import com.ayantsoft.trms.service.EmployeeService;
import com.ayantsoft.trms.service.IncentiveStructureService;
import com.ayantsoft.trms.util.CalenderUtil;
import com.ayantsoft.trms.util.ChartsUtil;
import com.ayantsoft.trms.util.IncentiveUtil;
import com.ayantsoft.trms.util.ReportUtil;

@ManagedBean
@ViewScoped
public class ReportBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1429304141637838111L;



	@ManagedProperty(value="#{candidatePaymentService}")
	private CandidatePaymentService candidatePaymentService;
	
	
	@ManagedProperty(value="#{incentiveStructureService}")
	private IncentiveStructureService incentiveStructureService;
	
	@ManagedProperty(value="#{employeeService}")
	private EmployeeService employeeService;


	private String action;
	private Date incentiveMonth;
	private List<CandidatePayment> candidatePaymentsForDebits;
	private List<ChartsUtil> chartsUtils;
	private List<IncentiveUtil> incentiveUtils;
	private Map<Integer,Double> totalAmountByEmp ;
	private Map<Employee,Double> totalPaidAmountForTeamLeader;
	private Set<Employee> teamLeaders;
	private List<CandidatePayment> recruiterUnderTeamLeader;
	private List<CandidatePaymentModel> candidatePaymentModelForSummaryReport;
	private List<CandidatePayment> candidatePaymentsForSummaryReport; 
	private boolean isteamLeaderIncentive;
	private boolean hideMonthlyIncentive;


	public void showMonthlyIncentive()
	{
		action = "MONTHLYINCENTIVE";

	}


	public void IncentiveList() throws ParseException
	{
		hideMonthlyIncentive = false;
		isteamLeaderIncentive = false;
		action = "MONTHLYINCENTIVE";
		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		Date endDate = CalenderUtil.createEndDate(incentiveMonth);
		candidatePaymentsForDebits = candidatePaymentService.getAllCandidatePaymentDebits(startDate, endDate);
		if(candidatePaymentsForDebits != null)
		{	
		    totalAmountByEmp = ReportUtil.getTotalAmountByEmp(candidatePaymentsForDebits);
		    List<Employee> supervisorEmployees = employeeService.getSupervisor();
		    totalPaidAmountForTeamLeader = ReportUtil.getTotalPaidAmountForTeamLeader(supervisorEmployees, candidatePaymentsForDebits);
		    teamLeaders = totalPaidAmountForTeamLeader.keySet();
		    recruiterUnderTeamLeader = ReportUtil.getRecruiterUnderTeamLeader(supervisorEmployees, candidatePaymentsForDebits);
		
		}
		
		
		if(candidatePaymentsForDebits.size()>0){
			hideMonthlyIncentive = true;
		}
		
		
		if(recruiterUnderTeamLeader.size()>0){
			isteamLeaderIncentive = true;
		}
		
		
	}
	
	
	
	
	
	public void summaryReport() throws ParseException
	{
		action = "SUMMARYREPORT";
		candidatePaymentsForSummaryReport = new ArrayList<CandidatePayment>();
		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		Date endDate = CalenderUtil.createEndDate(incentiveMonth);
		candidatePaymentsForDebits = candidatePaymentService.getAllCandidatePaymentDebits(startDate, endDate);
		if(candidatePaymentsForDebits != null)
		{
			for(CandidatePayment cp:candidatePaymentsForDebits)
			{
				if(candidatePaymentsForSummaryReport.size() > 0)
				{
					boolean flag = true;
					for(CandidatePayment c :candidatePaymentsForSummaryReport)
					{
						if(c.getEmployee().getEmpId() == cp.getEmployee().getEmpId()){
							flag = false;
						}
					}
					
					if(flag){
						candidatePaymentsForSummaryReport.add(cp);
					}
					
				}else{
					candidatePaymentsForSummaryReport.add(cp);
				}
			}
			
			totalAmountByEmp = ReportUtil.getTotalAmountByEmp(candidatePaymentsForDebits);	
			
			List<Employee> supervisorEmployees = employeeService.getSupervisor();
			totalPaidAmountForTeamLeader = ReportUtil.getTotalPaidAmountForTeamLeader(supervisorEmployees, candidatePaymentsForDebits);
		}
	}
	

	
	
	

	
	public Double totalPaidAmountOfTeam(Employee employee) throws ParseException
	{
		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		Date endDate = CalenderUtil.createEndDate(incentiveMonth);
		if(employee.getEmployee() == null)
		{
			return candidatePaymentService.getTotalAmountForTeamLeader(employee.getEmpId(), startDate, endDate);
		}else{
			return 0.0;
		}	
	}
	
	
	
	
	public Double xx(Employee employee)
	{
		
		if(employee.getEmployee() == null){
			if(totalPaidAmountForTeamLeader.containsKey(employee.getEmployee()))
			{
				return totalPaidAmountForTeamLeader.get(employee.getEmployee());
			}else{
				return 0.0;
			}
			
		}else{
			return 0.0;
		}
		
	}
	
	
	
	
	public Double totalIncentove(BigDecimal selfIncentive,Double teamIncentive)
	{
		return selfIncentive.doubleValue()+teamIncentive;
	}
	
	

	public Double getTotalAmountByEmp(Integer empId){
		return totalAmountByEmp.get(empId); 
	}



	public BigDecimal getTotalPaidAmount(Integer empId) throws ParseException {

		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		Date endDate = CalenderUtil.createEndDate(incentiveMonth);
		return candidatePaymentService.getTotalDebitsByEmpid(empId, startDate, endDate);
	}

	

	public double getTotalIncentiveForIndividual(BigDecimal totalPaidAmound)
	{
		return incentiveStructureService.getIndividualIncentive(totalPaidAmound.doubleValue());
	}

	

	public double getTotalIncentiveForTeamLeader(Double totalPaidAmound)
	{
		return incentiveStructureService.getLeaderIncentive(totalPaidAmound);
	}
	


    private BarChartModel animatedModel2;
    private BarChartModel animatedModelForTeamLeader; 
 
    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }
    
    public BarChartModel getAnimatedModelForTeamLeader() 
    {
    	return animatedModelForTeamLeader;
    }
 
    private void createAnimatedModels() { 
        animatedModel2 = initBarModel();
        animatedModel2.setTitle("Individual Incentive");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
    }
    
    
    private void createAnimatedModelForTeamLeader() throws ParseException {   
    	animatedModelForTeamLeader = initBarModelForTeamLeader();
    	animatedModelForTeamLeader.setTitle("Team Leader Incentive");
    	animatedModelForTeamLeader.setAnimate(true);
    	animatedModelForTeamLeader.setLegendPosition("ne");
    }
     
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        for(IncentiveUtil iu :incentiveUtils)
        {
        	if(iu.getIncentive()>0.0)
        	{
        		ChartSeries cs = new ChartSeries();
                cs.setLabel(iu.getName());
                cs.set(iu.getMonthName(), iu.getIncentive());
                model.addSeries(cs);
        	}
        	  	
        }
        
        return model;
    }
    
    
    
    private BarChartModel initBarModelForTeamLeader() throws ParseException {     
        BarChartModel model = new BarChartModel();
 
        Date startDate = CalenderUtil.createStartDate(incentiveMonth);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(startDate);
	    String month = CalenderUtil.monthName(cal.get(Calendar.MONTH));
	    
        for(Employee emp:teamLeaders)
        {	
        		ChartSeries cs = new ChartSeries();
                cs.setLabel(emp.getName());
                cs.set(month, getTotalIncentiveForTeamLeader(totalPaidAmountOfTeam(emp)));
                model.addSeries(cs);  	
        }
        return model;
    }
     
    
    
	public void showIncentiveDetailsWithBarCharts() throws ParseException
	{
		action = "BARCHARTFORINCENTIVE";
		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		Date endDate = CalenderUtil.createEndDate(incentiveMonth);
		chartsUtils = candidatePaymentService.getTotalDebitForBarCharts(startDate,endDate);
		if(chartsUtils != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			String month = CalenderUtil.monthName(cal.get(Calendar.MONTH));
			incentiveUtils = new ArrayList<IncentiveUtil>();
			for(ChartsUtil cu:chartsUtils)
			{
				IncentiveUtil iu = new IncentiveUtil();
				iu.setName(cu.getEmpName());
				iu.setIncentive(CalenderUtil.incentiveForIndividual(cu.getPaidAmount().doubleValue()));
				iu.setMonthName(month);
				incentiveUtils.add(iu);
			}
		}
		createAnimatedModels();
	}
	
	
	
	
	
	public void showIncentiveDetailsWithBarChartsForTeamLeader() throws ParseException    
	{
		action = "BARCHARTFORINCENTIVEFORTEAMLEADER";
	    createAnimatedModelForTeamLeader();
	}

	
	
	private PieChartModel pieModel1;
	private PieChartModel pieModelForTeamLeader;  	 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
    
    public PieChartModel getPieModelForTeamLeader()  
    {
    	return pieModelForTeamLeader; 
    }
    
    
	public void showIncentiveDetailsWithPieCharts() throws ParseException
	{
		action = "PIECHARTFORINCENTIVE";
		pieModel1 = new PieChartModel();
		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		Date endDate = CalenderUtil.createEndDate(incentiveMonth);
		chartsUtils = candidatePaymentService.getTotalDebitForBarCharts(startDate,endDate);
		if(chartsUtils != null)
		{
			for(ChartsUtil cu:chartsUtils)
	        {
	        	 pieModel1.set(cu.getEmpName(),CalenderUtil.incentiveForIndividual(cu.getPaidAmount().doubleValue()));
	        }
		}
        Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		String month = CalenderUtil.monthName(cal.get(Calendar.MONTH));
        pieModel1.setTitle("Individual Incentive Of "+month);
        pieModel1.setLegendPosition("w");	
	}
	
	
	
	
	public void showIncentiveDetailsWithPieChartsForTeamLeader() throws ParseException  
	{
		action = "PIECHARTFORINCENTIVEFORTEAMLEADER";
		pieModelForTeamLeader = new PieChartModel();
		Date startDate = CalenderUtil.createStartDate(incentiveMonth);
		
		for(Employee emp:teamLeaders)
	    {
			pieModelForTeamLeader.set(emp.getName(),getTotalIncentiveForTeamLeader(totalPaidAmountOfTeam(emp)));
	    }
			
        Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		String month = CalenderUtil.monthName(cal.get(Calendar.MONTH));
		pieModelForTeamLeader.setTitle("Team Leader Incentive Of "+month);
		pieModelForTeamLeader.setLegendPosition("w");	
	}
    	
	
	private String durationType;
	private Integer durationValue;
	private List<Map<String,Object>> recruitmentReport;
	private Integer empId;
	private Integer courseId;
	private String receiver;
	private String modeOfPayment;
	
	
	public void recruitmentReport()
	{
		action = "RECRUITMENTREPORT";
	}
	
	
    public void viewRecruitmentReport()
	{	
		Date startDate = CalenderUtil.createDate(durationType,durationValue);
		recruitmentReport = candidatePaymentService.getCandidatePaymentByDurationType(durationType,startDate, new Date(),empId,courseId,receiver,modeOfPayment);	
	    if(recruitmentReport == null){
	    	recruitmentReport = new ArrayList<Map<String,Object>>();
	    }
	}
    
	public Integer getEmpId() {
		return empId;
	}


	public void setEmpId(Integer empId) {
		this.empId = empId;
	}


	public Integer getCourseId() {
		return courseId;
	}


	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getModeOfPayment() {
		return modeOfPayment;
	}


	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}


	public String getDurationType() {
		return durationType;
	}


	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}


	public Integer getDurationValue() {
		return durationValue;
	}


	public void setDurationValue(Integer durationValue) {
		this.durationValue = durationValue;
	}

	public List<Map<String, Object>> getRecruitmentReport() {
		return recruitmentReport;
	}


	public void setRecruitmentReport(List<Map<String, Object>> recruitmentReport) {
		this.recruitmentReport = recruitmentReport;
	}

	
	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public Date getIncentiveMonth() {
		return incentiveMonth;
	}


	public void setIncentiveMonth(Date incentiveMonth) {
		this.incentiveMonth = incentiveMonth;
	}


	public CandidatePaymentService getCandidatePaymentService() {
		return candidatePaymentService;
	}


	public void setCandidatePaymentService(CandidatePaymentService candidatePaymentService) {
		this.candidatePaymentService = candidatePaymentService;
	}


	public List<CandidatePayment> getCandidatePaymentsForDebits() {
		return candidatePaymentsForDebits;
	}


	public void setCandidatePaymentsForDebits(List<CandidatePayment> candidatePaymentsForDebits) {
		this.candidatePaymentsForDebits = candidatePaymentsForDebits;
	}


	public List<ChartsUtil> getChartsUtils() {
		return chartsUtils;
	}


	public void setChartsUtils(List<ChartsUtil> chartsUtils) {
		this.chartsUtils = chartsUtils;
	}


	public List<IncentiveUtil> getIncentiveUtils() {
		return incentiveUtils;
	}


	public void setIncentiveUtils(List<IncentiveUtil> incentiveUtils) {
		this.incentiveUtils = incentiveUtils;
	}


	public IncentiveStructureService getIncentiveStructureService() {
		return incentiveStructureService;
	}


	public void setIncentiveStructureService(IncentiveStructureService incentiveStructureService) {
		this.incentiveStructureService = incentiveStructureService;
	}


	public Map<Employee, Double> getTotalPaidAmountForTeamLeader() {
		return totalPaidAmountForTeamLeader;
	}


	public void setTotalPaidAmountForTeamLeader(Map<Employee, Double> totalPaidAmountForTeamLeader) {
		this.totalPaidAmountForTeamLeader = totalPaidAmountForTeamLeader;
	}


	public EmployeeService getEmployeeService() {
		return employeeService;
	}


	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public Set<Employee> getTeamLeaders() {
		return teamLeaders;
	}


	public void setTeamLeaders(Set<Employee> teamLeaders) {
		this.teamLeaders = teamLeaders;
	}


	public List<CandidatePayment> getRecruiterUnderTeamLeader() {
		return recruiterUnderTeamLeader;
	}


	public void setRecruiterUnderTeamLeader(List<CandidatePayment> recruiterUnderTeamLeader) {
		this.recruiterUnderTeamLeader = recruiterUnderTeamLeader;
	}


	public List<CandidatePaymentModel> getCandidatePaymentModelForSummaryReport() {
		return candidatePaymentModelForSummaryReport;
	}


	public void setCandidatePaymentModelForSummaryReport(
			List<CandidatePaymentModel> candidatePaymentModelForSummaryReport) {
		this.candidatePaymentModelForSummaryReport = candidatePaymentModelForSummaryReport;
	}


	public List<CandidatePayment> getCandidatePaymentsForSummaryReport() {
		return candidatePaymentsForSummaryReport;
	}


	public void setCandidatePaymentsForSummaryReport(List<CandidatePayment> candidatePaymentsForSummaryReport) {
		this.candidatePaymentsForSummaryReport = candidatePaymentsForSummaryReport;
	}


	public boolean isIsteamLeaderIncentive() {
		return isteamLeaderIncentive;
	}


	public void setIsteamLeaderIncentive(boolean isteamLeaderIncentive) {
		this.isteamLeaderIncentive = isteamLeaderIncentive;
	}


	public boolean isHideMonthlyIncentive() {
		return hideMonthlyIncentive;
	}


	public void setHideMonthlyIncentive(boolean hideMonthlyIncentive) {
		this.hideMonthlyIncentive = hideMonthlyIncentive;
	}

	
	
	
}