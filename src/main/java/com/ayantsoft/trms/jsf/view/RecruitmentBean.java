package com.ayantsoft.trms.jsf.view;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCourse;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.CandidateRemarks;
import com.ayantsoft.trms.hibernate.pojo.ContactAddress;
import com.ayantsoft.trms.hibernate.pojo.FollowUp;
import com.ayantsoft.trms.hibernate.pojo.Freepool;
import com.ayantsoft.trms.hibernate.pojo.PhoneRecording;
import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;
import com.ayantsoft.trms.jsf.converter.ListStringConverter;
import com.ayantsoft.trms.jsf.model.CandidatePaymentModel;
import com.ayantsoft.trms.jsf.model.FollowUpModel;
import com.ayantsoft.trms.jsf.model.FreePoolCandidatesDto;
import com.ayantsoft.trms.jsf.model.LazyCallHistoryDataModel;
import com.ayantsoft.trms.jsf.model.LazyCallHistoryDataModelForReport;
import com.ayantsoft.trms.jsf.model.LazyCandidateDataModel;
import com.ayantsoft.trms.jsf.model.LazyCandidateDataModelForReport;
import com.ayantsoft.trms.jsf.model.LazyFreePoolCandidateModel;
import com.ayantsoft.trms.jsf.model.LazyProgrameSheduleModel;
import com.ayantsoft.trms.jsf.model.OfferletterModel;
import com.ayantsoft.trms.jsf.util.CalendarUtil;
import com.ayantsoft.trms.service.CandidateCheckListService;
import com.ayantsoft.trms.service.CandidatePaymentService;
import com.ayantsoft.trms.service.CandidateService;
import com.ayantsoft.trms.service.EmailSendService;
import com.ayantsoft.trms.service.EmployeeService;
import com.ayantsoft.trms.service.FollowUpService;
import com.ayantsoft.trms.service.FreePoolService;
import com.ayantsoft.trms.service.PhoneRecordingService;
import com.ayantsoft.trms.service.ProgrameSheduleService;
import com.ayantsoft.trms.service.UserProfileService;


/**
 * @author manojgupta
 *
 */
@ManagedBean
@ViewScoped
public class RecruitmentBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2221447891807466554L;

	private Candidate candidate;
	private FollowUp followUp;
	private String action;
	private Integer empId;
	private List<String> preffredLocation;
	private List<CandidatePaymentModel>candidatePaymentModel;
	private CandidatePaymentModel candidatePayment;
	private List<CandidateRemarks>candidateStatuses=new ArrayList<CandidateRemarks>();
	private boolean isCandidateEnrolled;
	private LazyDataModel<ProgrammeSchedule> programmeScheduleLazyModel;
	private String candidateStatus;
	private List<CandidatePaymentModel> selectedCandidates;
	private OfferletterModel offerletterModel;
	private LazyDataModel<Candidate> candidateLazyModelForReport;
	private List<Object> candidatesForFreePool;
	private FollowUpModel followUpModel;
	private FreePoolCandidatesDto freePoolCandidatesDto;
	private CandidatePayment selectedCandidatePayment;
	private LazyDataModel<Candidate> candidateLazyModel;
	private String searchBy;
	private String searchValue;
	private List<Candidate> searchingCandidateList;
	private String phoneExtension;
	private LazyDataModel<Freepool> freepoolCandidatesLazyModel;
	private Freepool selectedFreePoolCandidate;
	private List<FollowUp> recruiterFollowupList;
	private CandidateCourse candidateCourse;
	private CandidateRemarks candidateRemarks;
	private Integer recruiterId;
	private List<Candidate> candidates;
	private LazyDataModel<PhoneRecording> callLogLazyModel;
	private LazyDataModel<PhoneRecording> callLogLazyModelForReport;

	private boolean hasCandidateEmail = true;
	private boolean hasCandidateWorkphone = true;
	private boolean hasCandidateAltEmail = true;
	private boolean hasCandidateHomePhone = true;

	private String dateRangeCheck;
	private Date startDate;
	private Date endDate;
	private boolean dateRangePanelSelector;
	private String duration;
	private String startDateInString;
	private String endDateInString;


	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@ManagedProperty(value="#{candidateService}")
	private CandidateService candidateService ;

	@ManagedProperty(value="#{followUpService}")
	private FollowUpService followUpService ;

	@ManagedProperty(value="#{candidatePaymentService}")
	private CandidatePaymentService candidatePaymentService ;

	@ManagedProperty(value="#{employeeService}")
	private EmployeeService employeeService ;

	@ManagedProperty(value="#{userProfileService}")
	private UserProfileService userProfileService ;

	@ManagedProperty(value="#{appDataBean}")
	private AppDataBean appDataBean ;

	@ManagedProperty(value="#{candidateCheckListService}")
	private CandidateCheckListService candidateCheckListService ;

	@ManagedProperty(value="#{programeSheduleService}")
	private ProgrameSheduleService programeSheduleService;

	@ManagedProperty(value="#{freePoolService}")
	private FreePoolService freePoolService;

	@ManagedProperty(value="#{phoneRecordingService}")
	private PhoneRecordingService phoneRecordingService;


	public FreePoolService getFreePoolService() {
		return freePoolService;
	}


	public void setFreePoolService(FreePoolService freePoolService) {
		this.freePoolService = freePoolService;
	}


	public void candidateAdvanceSearch(){

		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		if(candidate != null){
			candidate = null;
		}
		action="ADVANCESEARCH";
		hasCandidateEmail = true;
		hasCandidateWorkphone = true;
		hasCandidateAltEmail = true;
		hasCandidateHomePhone = true;
	}


	public void candidatePaymentReminder(){
		action="PAYMENTREMINDER";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		if(selectedCandidates != null){
			selectedCandidates.clear();
		}
		empId=loginBean.getUserProfile().getEmployee().getEmpId();
		candidatePaymentModel=candidatePaymentService.getCandidatePaymentDue(empId, loginBean.hasRole("ROLE_RECRUITER"),loginBean.hasRole("ROLE_ADMIN"));
		if(candidatePaymentModel != null)
		{
			Iterator<CandidatePaymentModel> ite = candidatePaymentModel.iterator();
			while(ite.hasNext()) {
				CandidatePaymentModel cpm = ite.next();

				if(cpm.getDueAmount() != null){
					if ((cpm.getDueAmount()).compareTo(BigDecimal.ZERO) == 0){
						ite.remove();
					}
				}
			}
		}
	}

	public void onRowSelect(SelectEvent event) {
		preffredLocation = null;
		candidate = (Candidate)event.getObject();
		String preLocation = candidate.getPreferedLocation();
		if(preLocation != null){
			preffredLocation = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(preLocation,"#");
			while (st.hasMoreElements()) {
				preffredLocation.add((String) st.nextElement());
			}
		}
		List<FollowUp> followUpList=followUpService.followUpListForCandidate(candidate.getCandidateId());
		candidate.setFollowUps(followUpList);
		candidate.setNextFollowUpDate(null);
		if(candidate.getCandidateCourse() == null){
			candidate.setCandidateCourse(new CandidateCourse());
		}

		followUp = new FollowUp();
		RequestContext.getCurrentInstance().reset("followup-form");

		hasCandidateEmail = true;
		hasCandidateWorkphone = true;
		hasCandidateAltEmail = true;
		hasCandidateHomePhone = true;

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Selected candidate added at below"));

	}



	public void showFollowUpForPayment(SelectEvent event) {
		candidatePayment=(CandidatePaymentModel) event.getObject();
		candidatePayment.getCandidate().getCandidateId();
		candidate=candidatePayment.getCandidate();

	}

	public void newCandidateForm(){

		this.action ="NEW";
		candidate = new Candidate();
		candidate.setContactAddress(new ContactAddress());
		CandidateRemarks cr = new CandidateRemarks();
		cr.setId(1);
		cr.setStatusType("New Entry");
		candidate.setCandidateRemarks(cr);
		candidate.setCandidateCourse(new CandidateCourse());

		followUp=new FollowUp();
		followUp.setFollowUpRemarks(" Candidate created. ");

		preffredLocation=null;
		hasCandidateEmail = true;
		hasCandidateWorkphone = true;
		hasCandidateAltEmail = true;
		hasCandidateHomePhone = true;

	}



	/**
	 * used for candidate and followUp entry saving
	 */

	public void saveCanidateWithFollowUp(){

		if(candidate.getNextFollowUpDate()==null){
			candidate.setNextFollowUpDate(new Date());
		}
		followUp.setEmployee(loginBean.getEmployee());


		candidate.setPreferedLocation(ListStringConverter.getAsString(preffredLocation));
		if(candidate.getCandidateId() == null){
			candidate.setEmployee(loginBean.getEmployee());
			candidate.setEntryDate(new Date());     //add this line 01022017
		}
		candidateStatuses=appDataBean.getCandidateStatuses();
		String status=candidate.getCandidateRemarks().getStatusType();
		try {
			for(CandidateRemarks cr:candidateStatuses){
				if(cr.getId()==candidate.getCandidateRemarks().getId()){
					candidate.getCandidateRemarks().setStatusType(cr.getStatusType());
				}
			}

			followUp.setFollowUpType(candidate.getCandidateRemarks().getStatusType());
			isCandidateEnrolled=candidateService.checkCandidatePayment(candidate.getCandidateId());

			if(candidate.getCandidateRemarks().getStatusType().equals("Enrolled") && !isCandidateEnrolled){

				if(candidate.getCandidateCourse().getId() == null || candidate.getCandidateCourse().getId() <= 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "For Enrolled Candidate Skill is must"));
					candidate.getCandidateRemarks().setStatusType(status);
					return;

				}

				if(candidate.getTotalAmount() == null || candidate.getTotalAmount() <= 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "For Enrolled Fee is must"));
					candidate.getCandidateRemarks().setStatusType(status);
					return;

				}

				followUp.setFollowUpRemarks(followUp.getFollowUpRemarks()+" Candidate enrolled. ");

				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file not found");
				}


				CandidatePayment candidatePayment=new CandidatePayment();
				candidatePayment.setCreadit(BigDecimal.valueOf((candidate.getTotalAmount())));
				candidatePayment.setEmployee(loginBean.getEmployee());
				candidatePayment.setCandidateCourse(candidate.getCandidateCourse());
				candidatePayment.setTransactionDate(new Date());
				candidatePayment.setDebit(new BigDecimal(0.00));
				candidate.setEnrollmentDate(new Date());
				candidateService.updateCandidate(candidate,followUp,candidatePayment);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate enrolled"));
			}else{

				if(candidate.getCandidateId()==null){
					candidateService.updateCandidate(candidate,followUp,null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate  saved"));


				}else{
					candidateService.updateCandidate(candidate,followUp,null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "FollowUp  saved"));
				}

				if(candidate.getCandidateCourse() == null){
					candidate.setCandidateCourse(new CandidateCourse());
				}
			}

			// For Free pool

			followUp=new FollowUp();
			List<FollowUp> followUpList=followUpService.followUpListForCandidate(candidate.getCandidateId());
			candidate.setFollowUps(followUpList);
			candidate.setNextFollowUpDate(null);
			preffredLocation = ListStringConverter.getAsList(candidate.getPreferedLocation());
			action="ADVANCESEARCH";
		} catch (Exception e) {
			candidate.getCandidateRemarks().setStatusType(status);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "FollowUp  not saved"));
		}

	}




	public void saveCanidateWithFollowUpForFreePool(){

		if(candidate.getNextFollowUpDate()==null){
			candidate.setNextFollowUpDate(new Date());
		}
		followUp.setEmployee(loginBean.getEmployee());


		candidate.setPreferedLocation(ListStringConverter.getAsString(preffredLocation));

		candidate.setEmployee(loginBean.getEmployee());
		candidate.setEntryDate(new Date());     //add this line 01022017

		candidateStatuses=appDataBean.getCandidateStatuses();
		String status=candidate.getCandidateRemarks().getStatusType();
		try {
			for(CandidateRemarks cr:candidateStatuses){
				if(cr.getId()==candidate.getCandidateRemarks().getId()){
					candidate.getCandidateRemarks().setStatusType(cr.getStatusType());
				}
			}

			followUp.setFollowUpType(candidate.getCandidateRemarks().getStatusType());
			isCandidateEnrolled=candidateService.checkCandidatePayment(candidate.getCandidateId());

			if(candidate.getCandidateRemarks().getStatusType().equals("Enrolled") && !isCandidateEnrolled){

				if(candidate.getCandidateCourse().getId() == null || candidate.getCandidateCourse().getId() <= 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "For Enrolled Candidate Skill is must"));
					candidate.getCandidateRemarks().setStatusType(status);
					return;

				}

				if(candidate.getTotalAmount() == null || candidate.getTotalAmount() <= 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "For Enrolled Fee is must"));
					candidate.getCandidateRemarks().setStatusType(status);
					return;

				}

				followUp.setFollowUpRemarks(followUp.getFollowUpRemarks()+" Candidate enrolled. ");

				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file not found");
				}


				List<String> emailsForHr = userProfileService.getEmailByUserRole("ROLE_HR");
				List<String> emailsForAccountant = userProfileService.getEmailByUserRole("ROLE_ACCOUNT");
				if(emailsForHr != null){
					for(String email:emailsForHr){
						String message = "candidate Id : "+candidate.getCandidateId()+" name: "+candidate.getFirstName()+" "
								+ "for course: "+candidate.getCandidateCourse().getCourse()+" and amount: "+candidate.getTotalAmount()+" has been enrolled check details";
						new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Entrolled Candidate Info",message,null,null,null);
					}
				}

				if(emailsForAccountant != null){
					for(String email:emailsForAccountant)
					{
						String message = "candidate Id : "+candidate.getCandidateId()+" name: "+candidate.getFirstName()+" "
								+ "for course: "+candidate.getCandidateCourse().getCourse()+" and amount: "+candidate.getTotalAmount()+" has been enrolled kindly update payment";
						new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Entrolled Candidate Info",message,null,null,null);
					}
				}


				CandidatePayment candidatePayment=new CandidatePayment();
				candidatePayment.setCreadit(BigDecimal.valueOf((candidate.getTotalAmount())));
				candidatePayment.setEmployee(loginBean.getEmployee());
				candidatePayment.setCandidateCourse(candidate.getCandidateCourse());
				candidatePayment.setTransactionDate(new Date());
				candidatePayment.setDebit(new BigDecimal(0.00));
				candidateService.updateCandidate(candidate,followUp,candidatePayment);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate enrolled"));
			}else{

				if(candidate.getCandidateId()==null){
					candidateService.updateCandidate(candidate,followUp,null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate  saved"));


				}else{
					candidateService.updateCandidate(candidate,followUp,null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "FollowUp  saved"));
				}

				if(candidate.getCandidateCourse() == null){
					candidate.setCandidateCourse(new CandidateCourse());
				}
			}
			followUp=new FollowUp();
			List<FollowUp> followUpList=followUpService.followUpListForCandidate(candidate.getCandidateId());
			candidate.setFollowUps(followUpList);
			candidate.setNextFollowUpDate(null);
			preffredLocation = ListStringConverter.getAsList(candidate.getPreferedLocation());
			action="FREEPOOLLIST";
			candidate = null;
			freePoolCandidatesDto = null;
			/*Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -30);
			Date dateBefore45Days = cal.getTime();
			freePoolcandidates = followUpService.findFreePoolCandidates(dateBefore45Days);*/
			freepoolCandidatesLazyModel = new LazyFreePoolCandidateModel(freePoolService);
		} catch (Exception e) {
			candidate.getCandidateRemarks().setStatusType(status);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "FollowUp  not saved"));
		}

	}



	@PostConstruct
	public void init(){
		Map<String, String> params =FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap();
		String candidateId = params.get("candidateId");
		if(candidateId != null){
			candidate = candidateService.getCandidate(Integer.parseInt(candidateId));
			followUp=new FollowUp();
			List<FollowUp> followUpList=followUpService.followUpListForCandidate(candidate.getCandidateId());
			candidate.setFollowUps(followUpList);
			candidate.setNextFollowUpDate(null);
			preffredLocation = ListStringConverter.getAsList(candidate.getPreferedLocation());
			if(candidate.getCandidateCourse() == null){
				candidate.setCandidateCourse(new CandidateCourse());
			}
		}

		empId=loginBean.getUserProfile().getEmployee().getEmpId();
		boolean isAdmin=loginBean.hasRole("ROLE_ADMIN");
		candidateLazyModel = new LazyCandidateDataModel(candidateService, loginBean.getUserProfile().getEmployee().getEmpId(),isAdmin);

	}


	public void followUpDateValidate(FacesContext context, UIComponent component, Object value){

		if(!candidate.getCandidateRemarks().getStatusType().equals("Enrolled")){
			if(value == null){
				FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Enter Next followUp date"));
			}

		}
	}


	public void emailValidate(FacesContext context, UIComponent component, Object value){

		boolean hasEmail=candidateService.checkUniqueEmail((String)value,candidate.getCandidateId());
		if(!hasEmail){
			hasCandidateEmail = false;
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email duplicate:", "Email already exists"));
		}else{
			hasCandidateEmail = true;
		}
	}

	public void altEmailValidate(FacesContext context, UIComponent component, Object value){
		boolean hasEmail=candidateService.checkUniqueEmail((String)value,candidate.getCandidateId());
		if(!hasEmail){
			hasCandidateAltEmail = false;
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email duplicate:", "Email already exists"));
		}else{
			hasCandidateAltEmail = true;
		}
	}

	public void mobileValidate(FacesContext context, UIComponent component, Object value){
		boolean hasworkMobile=candidateService.checkUniqueMobile((String)value,candidate.getCandidateId());
		if(!hasworkMobile){
			hasCandidateWorkphone = false;
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mobile duplicate:", "Mobile already exists"));
		}else{
			hasCandidateWorkphone = true;
		}

	}

	public void homeValidate(FacesContext context, UIComponent component, Object value){
		boolean hasworkMobile=candidateService.checkUniqueMobile((String)value,candidate.getCandidateId());
		if(!hasworkMobile){
			hasCandidateHomePhone = false;
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mobile duplicate:", "Mobile already exists"));
		}else{
			hasCandidateHomePhone = true;
		}
	}


	public void candidateCourseValidator(FacesContext context, UIComponent component, Object value){
		SelectOneMenu candidateStatusUI = (SelectOneMenu)component.findComponent("candidate-statusId") ;
		Integer candidateStatus = (Integer)candidateStatusUI.getValue();
		if(6==candidateStatus) {
			if(value == null || ((Integer)value) <=0){
				FacesContext.getCurrentInstance().addMessage(component.getClientId(), 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Course Validation:", "For enrollment, must enter skills"));
			}
		}
	}


	public void batchList()
	{
		action ="BATCHLIST";
		programmeScheduleLazyModel = new LazyProgrameSheduleModel(programeSheduleService);
	}


	public int getTotalCandidate(Integer programeSheduleId)
	{
		return programeSheduleService.getTotalCandidate(programeSheduleId);
	}


	public LazyDataModel<Candidate> getCandidateLazyModel() {
		return candidateLazyModel;
	}

	public void setCandidateLazyModel(LazyDataModel<Candidate> candidateLazyModel) {
		this.candidateLazyModel = candidateLazyModel;
	}



	public List<Candidate> getCandidates() {
		return candidates;
	}


	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}


	public void candidateReportByStatus(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		candidateStatus = params.get("status");
		candidates = candidateService.getCandidateByStatus(loginBean.getUserProfile().getEmployee().getEmpId(),loginBean.hasRole("ROLE_ADMIN"),candidateStatus);
		action = "CANDIDATELISTFORREPORT";
	}



	public void emailSend(){
		if(selectedCandidates.size()>0){
			action = "MAILSENDING";
			offerletterModel = new OfferletterModel();
			offerletterModel.setCandidatePaymentModels(selectedCandidates);
			offerletterModel.setIndicator("multiple");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate Not Selected"));
		}

	}


	public void mailSendToCandidate(){
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String indicator = params.get("indicator");

			List<String> ccList = new ArrayList<String>();
			List<String> bccList = new ArrayList<String>();
			if(offerletterModel.getCc() !=null){
				StringTokenizer strToken = new StringTokenizer(offerletterModel.getCc(), ",");
				while (strToken.hasMoreTokens()) {
					ccList.add((String)strToken.nextElement());
				}
			}
			if(offerletterModel.getBcc() !=null){
				StringTokenizer strToken = new StringTokenizer(offerletterModel.getBcc(), ",");
				while (strToken.hasMoreTokens()) {
					bccList.add((String)strToken.nextElement());
				}
			}
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
			prop.load(inputStream);
			if(indicator.equals("multiple")){
				for(CandidatePaymentModel cpm:selectedCandidates){
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),cpm.getCandidateEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
				}
			}else{
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),offerletterModel.getCandidate().getContactAddress().getEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Mail Successfully Send"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Mail Not Send"));
		}
	}



	public void candidateReport(){
		if(candidate != null){
			candidate = null;
		}
		action="CANDIDATEREPORT";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
	}


	public void emailToCandidate(){
		if(candidate != null){
			action = "MAILSENDING";
			offerletterModel = new OfferletterModel();
			offerletterModel.setCandidate(candidate);
			offerletterModel.setIndicator("single");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate Not Selected"));
		}

	}


	public void getDateForm(){
		action = "DATEFORMFORREPORT";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		offerletterModel = new OfferletterModel();

	}


	public void candidateForReport() throws ParseException{
		candidateLazyModelForReport = new LazyCandidateDataModelForReport(candidateService, loginBean.getUserProfile().getEmployee().getEmpId(), loginBean.hasRole("ROLE_ADMIN"), loginBean.hasRole("ROLE_RECRUITER"), offerletterModel.getStartDate(), offerletterModel.getEndDate());
		action="REPORT";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
	}



	public void candidateForFreePool(){
		try{
			action = "FREEPOOLLIST";
			selectedFreePoolCandidate = null;
			recruiterFollowupList = null;
			candidate = new Candidate();
			candidateCourse = new CandidateCourse();
			candidateRemarks = new CandidateRemarks();
			freepoolCandidatesLazyModel = new LazyFreePoolCandidateModel(freePoolService);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void onRowSelectForFreePool(SelectEvent event) {
		try{

			recruiterFollowupList = followUpService.followUpListForCandidate(selectedFreePoolCandidate.getCandidateId());
			candidate = candidateService.getCandidate(selectedFreePoolCandidate.getCandidateId());
			if(candidate.getCandidateCourse() != null){
				candidateCourse = candidate.getCandidateCourse();
			}
			if(candidate.getCandidateRemarks() != null){
				candidateRemarks = candidate.getCandidateRemarks();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void getCandidateFromFreePool(){

		try{
			candidate.setEmployee(loginBean.getEmployee());
			candidate.setCandidateCourse(candidateCourse);
			candidate.setCandidateRemarks(candidateRemarks);
			candidateService.saveCandidate(candidate);
			freePoolService.delete(candidate.getCandidateId());
			FollowUp followUp = new FollowUp();
			followUp.setFollowUpDate(new Date());
			followUp.setFollowUpRemarks("got the candidate from freepool");
			List<CandidateRemarks> candidateRemarksList = appDataBean.getCandidateStatuses();
			for(CandidateRemarks cr:candidateRemarksList){
				if(cr.getId() == candidateRemarks.getId()){
					followUp.setFollowUpType(cr.getStatusType());
					break;
				}
			}
			followUp.setCandidate(candidate);
			followUp.setEmployee(loginBean.getEmployee());
			followUpService.save(followUp);

			freepoolCandidatesLazyModel = new LazyFreePoolCandidateModel(freePoolService);

			recruiterFollowupList = followUpService.followUpListForCandidate(candidate.getCandidateId());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate Successfully Updated"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate Not Updated"));
		}
	}



	public void saveCanidatefromFreePool(){
		if(candidate.getNextFollowUpDate()==null){
			candidate.setNextFollowUpDate(new Date());
		}
		followUp.setEmployee(loginBean.getEmployee());

		candidate.setPreferedLocation(ListStringConverter.getAsString(preffredLocation));

		candidate.setEmployee(loginBean.getEmployee());

		candidateStatuses=appDataBean.getCandidateStatuses();
		String status=candidate.getCandidateRemarks().getStatusType();
		try {
			for(CandidateRemarks cr:candidateStatuses){
				if(cr.getId()==candidate.getCandidateRemarks().getId()){
					candidate.getCandidateRemarks().setStatusType(cr.getStatusType());
				}
			}

			followUp.setFollowUpType(candidate.getCandidateRemarks().getStatusType());
			isCandidateEnrolled=candidateService.checkCandidatePayment(candidate.getCandidateId());

			if(candidate.getCandidateRemarks().getStatusType().equals("Enrolled") && !isCandidateEnrolled){

				if(candidate.getCandidateCourse().getId() == null || candidate.getCandidateCourse().getId() <= 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "For Enrolled Candidate Skill is must"));
					candidate.getCandidateRemarks().setStatusType(status);
					return;

				}

				if(candidate.getTotalAmount() == null || candidate.getTotalAmount() <= 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "For Enrolled Fee is must"));
					candidate.getCandidateRemarks().setStatusType(status);
					return;

				}

				followUp.setFollowUpRemarks(followUp.getFollowUpRemarks()+" Candidate enrolled. ");

				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file not found");
				}


				List<String> emailsForHr = userProfileService.getEmailByUserRole("ROLE_HR");
				List<String> emailsForAccountant = userProfileService.getEmailByUserRole("ROLE_ACCOUNT");
				if(emailsForHr != null){
					for(String email:emailsForHr){
						String message = "candidate Id : "+candidate.getCandidateId()+" name: "+candidate.getFirstName()+" "
								+ "for course: "+candidate.getCandidateCourse().getCourse()+" and amount: "+candidate.getTotalAmount()+" has been enrolled check details";
						new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Entrolled Candidate Info",message,null,null,null);
					}
				}

				if(emailsForAccountant != null){
					for(String email:emailsForAccountant)
					{
						String message = "candidate Id : "+candidate.getCandidateId()+" name: "+candidate.getFirstName()+" "
								+ "for course: "+candidate.getCandidateCourse().getCourse()+" and amount: "+candidate.getTotalAmount()+" has been enrolled kindly update payment";
						new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Entrolled Candidate Info",message,null,null,null);
					}
				}


				CandidatePayment candidatePayment=new CandidatePayment();
				candidatePayment.setCreadit(BigDecimal.valueOf((candidate.getTotalAmount())));
				candidatePayment.setEmployee(loginBean.getEmployee());
				candidatePayment.setCandidateCourse(candidate.getCandidateCourse());
				candidatePayment.setTransactionDate(new Date());
				candidatePayment.setDebit(new BigDecimal(0.00));
				candidateService.updateCandidate(candidate,followUp,candidatePayment);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate enrolled"));
			}else{

				if(candidate.getCandidateId()==null){
					candidateService.updateCandidate(candidate,followUp,null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate  saved"));


				}else{
					candidateService.updateCandidate(candidate,followUp,null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "FollowUp  saved"));
				}

				if(candidate.getCandidateCourse() == null){
					candidate.setCandidateCourse(new CandidateCourse());
				}
			}
			candidateForFreePool();
		} catch (Exception e) {
			candidate.getCandidateRemarks().setStatusType(status);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "FollowUp  not saved"));
		}
	}



	public void showFollowupPage(){
		try{
			if(freePoolCandidatesDto !=null){
				preffredLocation = null;
				candidate = candidateService.getCandidate(freePoolCandidatesDto.getCandidateId());
				String preLocation = candidate.getPreferedLocation();
				if(preLocation != null){
					preffredLocation = new ArrayList<String>();
					StringTokenizer st = new StringTokenizer(preLocation,"#");
					while (st.hasMoreElements()) {
						preffredLocation.add((String) st.nextElement());
					}
				}
				List<FollowUp> followUpList=followUpService.followUpListForCandidate(candidate.getCandidateId());
				candidate.setFollowUps(followUpList);
				candidate.setNextFollowUpDate(null);
				if(candidate.getCandidateCourse() == null){
					candidate.setCandidateCourse(new CandidateCourse());
				}
				if(candidate.getContactAddress() == null){
					candidate.setContactAddress(new ContactAddress());
				}
				if(candidate.getCandidateRemarks() == null){
					candidate.setCandidateRemarks(new CandidateRemarks());
				}
				followUp = new FollowUp();
				action="SHOWFOLLOWUPPAGE";
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate Not Selected"));	
			}	
		}catch(Exception e){

		}
	}


	public void candidateCheck(){
		action = "CANDIDATECHECK";
		if(searchBy != null){
			searchBy = null;
		}

		if(searchValue != null){
			searchValue = null;
		}

		if(searchingCandidateList != null && searchingCandidateList.size() >0){
			searchingCandidateList.clear();
		}
	}


	public void getCandidateListBySearchValue(){
		searchingCandidateList = candidateService.getCandidateBySearchValue(searchBy,searchValue);
	}

	public void getTransferForm(){
		recruiterId = null;

	}

	public void transferCandidate(){
		try{

			Freepool freePool = freePoolService.findFreepoolByCanId(candidate.getCandidateId());
			if(freePool != null){
				freePoolService.delete(candidate.getCandidateId());
			}

			candidateService.updateCandidateRecruiterId(recruiterId, candidate.getCandidateId());

			FollowUp f = new FollowUp();
			f.setFollowUpDate(new Date());
			f.setFollowUpRemarks(loginBean.getEmployee().getName()+"  transferred this candidate");
			f.setFollowUpType(candidate.getCandidateRemarks().getStatusType());
			f.setCandidate(candidate);
			f.setEmployee(loginBean.getEmployee());
			followUpService.save(f);

			candidate = null;
			candidateLazyModel = new LazyCandidateDataModel(candidateService, loginBean.getUserProfile().getEmployee().getEmpId(),loginBean.hasRole("ROLE_ADMIN"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate Successfully Transfered"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getCallLog(){
		action = "CALLLOG";
		try{
			callLogLazyModel = new LazyCallHistoryDataModel(phoneRecordingService,loginBean.getUserProfile().getEmployee().getName(),loginBean.hasRole("ROLE_ADMIN"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void callLogReportForm(){
		action = "CALLLOGREPORTFORM";
		dateRangeCheck = null;
		dateRangePanelSelector = false;
		startDate = null;
		endDate = null;
		startDateInString = null;
		endDateInString = null;
	}


	public void dateRangeCheck(FacesContext context, UIComponent component, Object value){
		try{
			duration = (String) value;
			if("Current Month".equals(duration)){
				dateRangePanelSelector = false;
				startDate = CalendarUtil.getStartDateOfCurrentMonth();
				endDate = CalendarUtil.getEndDateOfCurrentMonth();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startDateInString = df.format(startDate);
				endDateInString = df.format(endDate);
				callLogLazyModelForReport = new LazyCallHistoryDataModelForReport(phoneRecordingService,loginBean.getUserProfile().getEmployee().getName(),loginBean.hasRole("ROLE_ADMIN"),startDateInString,endDateInString);
			}
			if("Current Month Plus Previous Month".equals(duration)){
				dateRangePanelSelector = false;
				startDate = CalendarUtil.getStartDateOfPreviousMonth();
				endDate = CalendarUtil.getEndDateOfCurrentMonth();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startDateInString = df.format(startDate);
				endDateInString = df.format(endDate);
				callLogLazyModelForReport = new LazyCallHistoryDataModelForReport(phoneRecordingService,loginBean.getUserProfile().getEmployee().getName(),loginBean.hasRole("ROLE_ADMIN"),startDateInString,endDateInString);
			}
			if("Current Month Plus Previous Two Month".equals(duration)){
				dateRangePanelSelector = false;
				startDate = CalendarUtil.getStartDateOfPreviousMonth();
				endDate = CalendarUtil.getEndDateOfCurrentMonth();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startDateInString = df.format(startDate);
				endDateInString = df.format(endDate);
				callLogLazyModelForReport = new LazyCallHistoryDataModelForReport(phoneRecordingService,loginBean.getUserProfile().getEmployee().getName(),loginBean.hasRole("ROLE_ADMIN"),startDateInString,endDateInString);
			}
			if("Date Range".equals(duration)){
				dateRangePanelSelector = true;
				startDate = null;
				endDate = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void findCallLogByStartDateAndEndDate(){
		try{

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDateInString = df.format(startDate);
			endDateInString = df.format(endDate);
			callLogLazyModelForReport = new LazyCallHistoryDataModelForReport(phoneRecordingService,loginBean.getUserProfile().getEmployee().getName(),loginBean.hasRole("ROLE_ADMIN"),startDateInString,endDateInString);
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public String getSearchBy() {
		return searchBy;
	}


	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}


	public String getSearchValue() {
		return searchValue;
	}


	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}


	public List<Candidate> getSearchingCandidateList() {
		return searchingCandidateList;
	}


	public void setSearchingCandidateList(List<Candidate> searchingCandidateList) {
		this.searchingCandidateList = searchingCandidateList;
	}


	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public FollowUp getFollowUp() {
		return followUp;
	}

	public void setFollowUp(FollowUp followUp) {
		this.followUp = followUp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public CandidateService getCandidateService() {
		return candidateService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public FollowUpService getFollowUpService() {
		return followUpService;
	}

	public void setFollowUpService(FollowUpService followUpService) {
		this.followUpService = followUpService;
	}

	public com.ayantsoft.trms.service.CandidatePaymentService getCandidatePaymentService() {
		return candidatePaymentService;
	}

	public void setCandidatePaymentService(com.ayantsoft.trms.service.CandidatePaymentService candidatePaymentService) {
		this.candidatePaymentService = candidatePaymentService;
	}

	public CandidatePayment getSelectedCandidatePayment() {
		return selectedCandidatePayment;
	}

	public void setSelectedCandidatePayment(CandidatePayment selectedCandidatePayment) {
		this.selectedCandidatePayment = selectedCandidatePayment;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}


	public List<String> getPreffredLocation() {
		return preffredLocation;
	}

	public void setPreffredLocation(List<String> preffredLocation) {
		this.preffredLocation = preffredLocation;
	}

	public List<CandidatePaymentModel> getCandidatePaymentModel() {
		return candidatePaymentModel;
	}

	public void setCandidatePaymentModel(List<CandidatePaymentModel> candidatePaymentModel) {
		this.candidatePaymentModel = candidatePaymentModel;
	}

	public CandidatePaymentModel getCandidatePayment() {
		return candidatePayment;
	}

	public void setCandidatePayment(CandidatePaymentModel candidatePayment) {
		this.candidatePayment = candidatePayment;
	}


	public List<CandidateRemarks> getCandidateStatuses() {
		return candidateStatuses;
	}


	public void setCandidateStatuses(List<CandidateRemarks> candidateStatuses) {
		this.candidateStatuses = candidateStatuses;
	}


	public AppDataBean getAppDataBean() {
		return appDataBean;
	}


	public void setAppDataBean(AppDataBean appDataBean) {
		this.appDataBean = appDataBean;
	}


	public UserProfileService getUserProfileService() {
		return userProfileService;
	}


	public void setUserProfileService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}


	public boolean isCandidateEnrolled() {
		return isCandidateEnrolled;
	}


	public void setCandidateEnrolled(boolean isCandidateEnrolled) {
		this.isCandidateEnrolled = isCandidateEnrolled;
	}


	public CandidateCheckListService getCandidateCheckListService() {
		return candidateCheckListService;
	}


	public void setCandidateCheckListService(CandidateCheckListService candidateCheckListService) {
		this.candidateCheckListService = candidateCheckListService;
	}


	public LazyDataModel<ProgrammeSchedule> getProgrammeScheduleLazyModel() {
		return programmeScheduleLazyModel;
	}


	public void setProgrammeScheduleLazyModel(LazyDataModel<ProgrammeSchedule> programmeScheduleLazyModel) {
		this.programmeScheduleLazyModel = programmeScheduleLazyModel;
	}


	public ProgrameSheduleService getProgrameSheduleService() {
		return programeSheduleService;
	}


	public void setProgrameSheduleService(ProgrameSheduleService programeSheduleService) {
		this.programeSheduleService = programeSheduleService;
	}


	public String getCandidateStatus() {
		return candidateStatus;
	}


	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}


	public List<CandidatePaymentModel> getSelectedCandidates() {
		return selectedCandidates;
	}


	public void setSelectedCandidates(List<CandidatePaymentModel> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}


	public OfferletterModel getOfferletterModel() {
		return offerletterModel;
	}


	public void setOfferletterModel(OfferletterModel offerletterModel) {
		this.offerletterModel = offerletterModel;
	}


	public LazyDataModel<Candidate> getCandidateLazyModelForReport() {
		return candidateLazyModelForReport;
	}


	public void setCandidateLazyModelForReport(LazyDataModel<Candidate> candidateLazyModelForReport) {
		this.candidateLazyModelForReport = candidateLazyModelForReport;
	}


	public List<Object> getCandidatesForFreePool() {
		return candidatesForFreePool;
	}


	public void setCandidatesForFreePool(List<Object> candidatesForFreePool) {
		this.candidatesForFreePool = candidatesForFreePool;
	}


	public FollowUpModel getFollowUpModel() {
		return followUpModel;
	}


	public void setFollowUpModel(FollowUpModel followUpModel) {
		this.followUpModel = followUpModel;
	}


	public FreePoolCandidatesDto getFreePoolCandidatesDto() {
		return freePoolCandidatesDto;
	}


	public void setFreePoolCandidatesDto(FreePoolCandidatesDto freePoolCandidatesDto) {
		this.freePoolCandidatesDto = freePoolCandidatesDto;
	}


	public String getPhoneExtension() {
		return phoneExtension;
	}


	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}


	public LazyDataModel<Freepool> getFreepoolCandidatesLazyModel() {
		return freepoolCandidatesLazyModel;
	}


	public void setFreepoolCandidatesLazyModel(LazyDataModel<Freepool> freepoolCandidatesLazyModel) {
		this.freepoolCandidatesLazyModel = freepoolCandidatesLazyModel;
	}


	public Freepool getSelectedFreePoolCandidate() {
		return selectedFreePoolCandidate;
	}


	public void setSelectedFreePoolCandidate(Freepool selectedFreePoolCandidate) {
		this.selectedFreePoolCandidate = selectedFreePoolCandidate;
	}


	public List<FollowUp> getRecruiterFollowupList() {
		return recruiterFollowupList;
	}


	public void setRecruiterFollowupList(List<FollowUp> recruiterFollowupList) {
		this.recruiterFollowupList = recruiterFollowupList;
	}


	public Integer getRecruiterId() {
		return recruiterId;
	}


	public void setRecruiterId(Integer recruiterId) {
		this.recruiterId = recruiterId;
	}


	public boolean isHasCandidateEmail() {
		return hasCandidateEmail;
	}


	public void setHasCandidateEmail(boolean hasCandidateEmail) {
		this.hasCandidateEmail = hasCandidateEmail;
	}


	public boolean isHasCandidateWorkphone() {
		return hasCandidateWorkphone;
	}

	public void setHasCandidateWorkphone(boolean hasCandidateWorkphone) {
		this.hasCandidateWorkphone = hasCandidateWorkphone;
	}

	public boolean isHasCandidateAltEmail() {
		return hasCandidateAltEmail;
	}

	public void setHasCandidateAltEmail(boolean hasCandidateAltEmail) {
		this.hasCandidateAltEmail = hasCandidateAltEmail;
	}


	public boolean isHasCandidateHomePhone() {
		return hasCandidateHomePhone;
	}

	public void setHasCandidateHomePhone(boolean hasCandidateHomePhone) {
		this.hasCandidateHomePhone = hasCandidateHomePhone;
	}

	public CandidateCourse getCandidateCourse() {
		return candidateCourse;
	}

	public void setCandidateCourse(CandidateCourse candidateCourse) {
		this.candidateCourse = candidateCourse;
	}

	public CandidateRemarks getCandidateRemarks() {
		return candidateRemarks;
	}

	public void setCandidateRemarks(CandidateRemarks candidateRemarks) {
		this.candidateRemarks = candidateRemarks;
	}

	public LazyDataModel<PhoneRecording> getCallLogLazyModel() {
		return callLogLazyModel;
	}

	public void setCallLogLazyModel(LazyDataModel<PhoneRecording> callLogLazyModel) {
		this.callLogLazyModel = callLogLazyModel;
	}

	public PhoneRecordingService getPhoneRecordingService() {
		return phoneRecordingService;
	}

	public void setPhoneRecordingService(PhoneRecordingService phoneRecordingService) {
		this.phoneRecordingService = phoneRecordingService;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDateRangeCheck() {
		return dateRangeCheck;
	}

	public void setDateRangeCheck(String dateRangeCheck) {
		this.dateRangeCheck = dateRangeCheck;
	}

	public boolean isDateRangePanelSelector() {
		return dateRangePanelSelector;
	}

	public void setDateRangePanelSelector(boolean dateRangePanelSelector) {
		this.dateRangePanelSelector = dateRangePanelSelector;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getStartDateInString() {
		return startDateInString;
	}


	public void setStartDateInString(String startDateInString) {
		this.startDateInString = startDateInString;
	}


	public String getEndDateInString() {
		return endDateInString;
	}


	public void setEndDateInString(String endDateInString) {
		this.endDateInString = endDateInString;
	}

	public LazyDataModel<PhoneRecording> getCallLogLazyModelForReport() {
		return callLogLazyModelForReport;
	}


	public void setCallLogLazyModelForReport(LazyDataModel<PhoneRecording> callLogLazyModelForReport) {
		this.callLogLazyModelForReport = callLogLazyModelForReport;
	}
}