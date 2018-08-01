package com.ayantsoft.trms.jsf.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;
import com.ayantsoft.trms.hibernate.pojo.CandidateCourse;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.CheckListType;
import com.ayantsoft.trms.hibernate.pojo.ContactAddress;
import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;
import com.ayantsoft.trms.hibernate.pojo.Trainer;
import com.ayantsoft.trms.hibernate.pojo.TrainerInvoice;
import com.ayantsoft.trms.jsf.model.AssignCandidateDto;
import com.ayantsoft.trms.jsf.model.CalenderUtil;
import com.ayantsoft.trms.jsf.model.LazyCandidateDataModelForRemove;
import com.ayantsoft.trms.jsf.model.LazyProgrameSheduleModel;
import com.ayantsoft.trms.jsf.model.LazyTrainerDataModel;
import com.ayantsoft.trms.jsf.model.LazyTrainerInvoiceDataModel;
import com.ayantsoft.trms.jsf.model.OfferletterModel;
import com.ayantsoft.trms.jsf.model.ProgrameScheduleModel;
import com.ayantsoft.trms.service.CandidateCheckListService;
import com.ayantsoft.trms.service.CandidatePaymentService;
import com.ayantsoft.trms.service.CandidateService;
import com.ayantsoft.trms.service.EmailSendService;
import com.ayantsoft.trms.service.PdfGenerator;
import com.ayantsoft.trms.service.ProgrameSheduleService;
import com.ayantsoft.trms.service.TrainerInvoiceService;
import com.ayantsoft.trms.service.TrainerService;
import com.ayantsoft.trms.service.UserProfileService;

@ManagedBean
@ViewScoped
public class TrainerBean implements Serializable{

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 304486286397001721L;

	private String action;
	private Trainer trainer;
	private LazyDataModel<Trainer> trainerLazyModel;
	private List<Trainer> trainers;
	private List<ProgrammeSchedule> ProgrammeSchedules;
	private String invoiceDescriptions;
	private Long duePaymentOfTrainer;
	private int amountInPercent;
	private Long amountInpercentForPayment;
	private Long totalAmount;
	private Integer noOfDays;
	private Date payMonthForInvoice;
	private LazyDataModel<TrainerInvoice> trainerInvoiceLazyModel;
	private List<TrainerInvoice> trainerInvoice;
	private Integer noOfStudentInOneBatch;
	private List<ProgrameScheduleModel> programeScheduleModels;
	private ProgrameScheduleModel selectedProgrameScheduleModel; 
	private List<Candidate> candidates;
	private List<Candidate> selectedCandidates;
	private Candidate candidate;
	private LazyDataModel<Candidate>  candidateLazyModelForRemove;
	private ProgrammeSchedule programmeSchedule;
	private String[] multipleDay = new String[7];
	private List<CandidateCourse> trainerCourses = new ArrayList<CandidateCourse>();
	private LazyDataModel<ProgrammeSchedule> programmeScheduleLazyModel;
	private LazyDataModel<CandidatePayment> candidatePaymentLazyModel;
	private List<AssignCandidateDto> assignCandidates;
	private List<AssignCandidateDto> selectedAssignCandidates;
	private OfferletterModel offerletterModel;
	private List<Candidate> candidatesForTransfer;
	private List<Candidate> transferCandidates;
	private List<ProgrameScheduleModel> programeScheduleModelsForTransfer;
	private ProgrameScheduleModel selectedBatchForTransfer; 
	private String phoneExtension;



	@ManagedProperty(value="#{trainerService}")
	private TrainerService trainerService;

	@ManagedProperty(value="#{programeSheduleService}")
	private ProgrameSheduleService programeSheduleService;

	@ManagedProperty(value="#{trainerInvoiceService}")
	private TrainerInvoiceService trainerInvoiceService;

	@ManagedProperty(value="#{userProfileService}")
	private UserProfileService userProfileService;

	@ManagedProperty(value="#{candidatePaymentService}")
	private CandidatePaymentService candidatePaymentService;

	@ManagedProperty(value="#{candidateCheckListService}")
	private CandidateCheckListService candidateCheckListService;

	@ManagedProperty(value="#{candidateService}")
	private CandidateService candidateService;

	@ManagedProperty(value="#{appDataBean}")
	private AppDataBean appDataBean ;
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean ;

	public void addTrainer()
	{
		trainer = new Trainer();
		trainer.setCandidateCourse(new CandidateCourse());
		trainer.setContactAddress(new ContactAddress());
		action = "ADDTRAINER";
	}


	public void workMobileValidation(FacesContext context, UIComponent component, Object value)
	{
		boolean hasworkMobile=trainerService.checkWorkMobileForValidation((String)value,trainer.getId());
		if(!hasworkMobile){
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mobile duplicate:", "Mobile already exists"));
		}	
	}



	public void emailValidate(FacesContext context, UIComponent component, Object value) throws IOException{
		boolean hasEmail=trainerService.checkUniqueEmail((String)value,trainer.getId());
		if(!hasEmail){
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email duplicate:", "Email already exists"));
		}
	}



	public void saveTrainer()
	{
		try{
			if(trainer.getId() == null)
			{
				trainerService.saveTrainer(trainer);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Trainer Successfully saved"));
			}else{
				trainerService.saveTrainer(trainer);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Trainer Successfully updated"));
			}

		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Trainer Successfully not saved"));
		}
	}



	public void trainerList()
	{
		action = "TRAINERLIST";
		trainerLazyModel = new LazyTrainerDataModel(trainerService);
	}


	public void onRowSelectForTrainer()
	{
		action = "ADDTRAINER";
	}


	public void listTrainerInvoice()
	{
		action = "LISTTRAINERINVOICE";
		trainerInvoiceLazyModel = new LazyTrainerInvoiceDataModel(trainerInvoiceService);
	}



	public void generateInvoice()
	{
		action = "TRAINERLISTFORINVOICE";
		if(trainer != null)
		{
			trainer = null;
		}
		if(ProgrammeSchedules != null){
			ProgrammeSchedules.clear();
		}
		if(amountInpercentForPayment != null){
			amountInpercentForPayment = null;
		}
		if(invoiceDescriptions != null){
			invoiceDescriptions = null;
		}
		if(noOfDays != null){
			noOfDays = null;
		}
		if(payMonthForInvoice != null){
			payMonthForInvoice = null;
		}

		if(selectedProgrameScheduleModel != null){
			selectedProgrameScheduleModel = null;
		}

		try{
			if(trainers != null){
				trainers.clear();
			}
			trainers = trainerService.findTrainersForGenerateInvoice();
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void findBatchByTrainerId()
	{
		if(trainer != null){
			try{
				String modeOfPayment = trainer.getModeOfPayment();
				programeScheduleModels = programeSheduleService.getProgrameScheduleByTrainerId(trainer.getId(),trainer.getModeOfPayment());
				if(modeOfPayment.equals("Batch Wise"))
				{
					action = "BATCHLIST";
				}else if(modeOfPayment.equals("Month Wise")||modeOfPayment.equals("Student Wise")){ 
					action = "TRAINERDETAILSOFMONTHWISE";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Trainer Not Selected"));	
		}		
	}




	public void getAmountForPayment()
	{
		if(selectedProgrameScheduleModel != null){
			try
			{
				trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());

				if(trainer.getModeOfPayment().equals("Batch Wise")){
					action = "INVOICPAYAMOUNTFORM";
					totalAmount = trainerInvoiceService.getTotalAmount(selectedProgrameScheduleModel.getProgrameSchedule().getId(),trainer.getId());
					if(totalAmount != null){
						duePaymentOfTrainer = trainer.getSalary() - totalAmount;
					}else{
						duePaymentOfTrainer = trainer.getSalary(); 
					}
				}


				if(trainer.getModeOfPayment().equals("Month Wise")){
					action = "INVOICPAYAMOUNTFORMFORMONTH";
				}


				if(trainer.getModeOfPayment().equals("Student Wise")){
					action = "INVOICPAYAMOUNTFORMFORStUDENTWISE";
					totalAmount = trainerInvoiceService.getTotalAmount(selectedProgrameScheduleModel.getProgrameSchedule().getId(),trainer.getId());
					ProgrammeSchedule batch = programeSheduleService.getCandidates(selectedProgrameScheduleModel.getProgrameSchedule().getId());
					int totalCandidate = 0;
					if(batch!= null){
						totalCandidate = batch.getCandidates().size();
					}
					noOfStudentInOneBatch = totalCandidate; 
					if(totalAmount != null){
						duePaymentOfTrainer = (trainer.getSalary()*totalCandidate) - totalAmount;
					}else{
						duePaymentOfTrainer = trainer.getSalary()*totalCandidate; 
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Batch Not Selected"));
		}


	}



	public void createInvoice()
	{
		try
		{
			TrainerInvoice invoice = new TrainerInvoice();
			invoice.setCandidateCourse(trainer.getCandidateCourse());
			invoice.setInvoiceDate(new Date());
			invoice.setTrainerPanNo(trainer.getpancardNo());
			invoice.setTrainerBankName(trainer.getTrainerBankName());
			invoice.setBankAccount(trainer.getBankAccount());
			invoice.setBankIfsc(trainer.getBankIfsc());
			invoice.setInvoiceDescription(invoiceDescriptions);

			List<String> emailsForHr = userProfileService.getEmailByUserRole("ROLE_HR");
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
			prop.load(inputStream);
			String trainerInvoiceDirectory = prop.getProperty("trainerInvoiceFolder");

			String status = null;
			if(selectedProgrameScheduleModel.getProgrameSchedule().getIsComplete().equals("YES")){
				status = "Completed";
			}else{
				status = "Not Completed";
			}
			String directoryName = trainerInvoiceDirectory+"/"+trainer.getId()+"_"+trainer.getTrainerName();
			File directory = new File(directoryName);
			if(directory.isDirectory()){
			}else{
				new File(directoryName).mkdir();
				new File(directoryName+"/invoices").mkdir();
			}

			if(trainer.getModeOfPayment().equals("Batch Wise"))
			{
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = format.format(new Date());
				invoice.setProgrammeSchedule(selectedProgrameScheduleModel.getProgrameSchedule());
				ProgrammeSchedule batch = programeSheduleService.getCandidates(selectedProgrameScheduleModel.getProgrameSchedule().getId());
				int totalCandidate = 0;
				if(batch!= null){
					totalCandidate = batch.getCandidates().size();
				}
				invoice.setNoOfStudent(totalCandidate);
				invoice.setTrainer(trainer);
				if(selectedProgrameScheduleModel.getProgrameSchedule().getIsComplete().equals("NO")){
					if(totalAmount == null){
						totalAmount = 0l;
					}
					if(((trainer.getSalary()*50)/100) >= (totalAmount+amountInpercentForPayment)){
						invoice.setAmount(amountInpercentForPayment);
						trainerInvoiceService.save(invoice);
						String folderPathWithFIle = directoryName+"/invoices/"+trainer.getId()+"_b"+selectedProgrameScheduleModel.getProgrameSchedule().getId()+"_"+invoice.getTrainerInvoiceId()+"_"+createdDate+".pdf";
						PdfGenerator.createInvoicePdf(createdDate,trainer.getCandidateCourse().getCourse(),trainer.getTrainerName(),Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId()),invoiceDescriptions+" Batch Id: "+Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId())+" Batch Title: "+selectedProgrameScheduleModel.getProgrameSchedule().getTitle(),status,folderPathWithFIle,"",Integer.toString(totalCandidate),Long.toString(amountInpercentForPayment),Long.toString(amountInpercentForPayment),trainer.getpancardNo(),trainer.getTrainerBankName(),trainer.getBankBranchName(),trainer.getBankIfsc(),trainer.getBankAccount(),Integer.toString(invoice.getTrainerInvoiceId()));
						List<String> filePaths = new ArrayList<String>();
						filePaths.add(folderPathWithFIle);
						if(emailsForHr != null){
							for(String email:emailsForHr){
								new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
							}
						}
						new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),trainer.getContactAddress().getEmail(),"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
						trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
						totalAmount = trainerInvoiceService.getTotalAmount(selectedProgrameScheduleModel.getProgrameSchedule().getId(),trainer.getId());
						trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
						if(totalAmount != null){
							duePaymentOfTrainer = trainer.getSalary() - totalAmount;
						}else{
							duePaymentOfTrainer = trainer.getSalary(); 
						}
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully Invoice Created"));
					}else{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Maximum 50% allowed as advance payment for non completed batch "));
					}
				}


				if(selectedProgrameScheduleModel.getProgrameSchedule().getIsComplete().equals("YES")){
					if(totalAmount == null){
						totalAmount = 0l;
					}
					if((trainer.getSalary()) >= totalAmount){
						if((trainer.getSalary()) >= (totalAmount+amountInpercentForPayment)){
							invoice.setAmount(amountInpercentForPayment);
							trainerInvoiceService.save(invoice);
							String folderPathWithFIle = directoryName+"/invoices/"+trainer.getId()+"_b"+selectedProgrameScheduleModel.getProgrameSchedule().getId()+"_"+invoice.getTrainerInvoiceId()+"_"+createdDate+".pdf";
							PdfGenerator.createInvoicePdf(createdDate,trainer.getCandidateCourse().getCourse(),trainer.getTrainerName(),Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId()),invoiceDescriptions+" Batch Id: "+Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId())+" Batch Title: "+selectedProgrameScheduleModel.getProgrameSchedule().getTitle(),status,folderPathWithFIle,"",Integer.toString(totalCandidate),Long.toString(amountInpercentForPayment),Long.toString(amountInpercentForPayment),trainer.getpancardNo(),trainer.getTrainerBankName(),trainer.getBankBranchName(),trainer.getBankIfsc(),trainer.getBankAccount(),Integer.toString(invoice.getTrainerInvoiceId()));
							List<String> filePaths = new ArrayList<String>();
							filePaths.add(folderPathWithFIle);
							if(emailsForHr != null){
								for(String email:emailsForHr){
									new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
								}
							}
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),trainer.getContactAddress().getEmail(),"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
							trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
							totalAmount = trainerInvoiceService.getTotalAmount(selectedProgrameScheduleModel.getProgrameSchedule().getId(),trainer.getId());
							trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
							if(totalAmount != null){
								duePaymentOfTrainer = trainer.getSalary() - totalAmount;
							}else{
								duePaymentOfTrainer = trainer.getSalary(); 
							}
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully Invoice Created"));
						}else{
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Maximum 50% allowed as advance payment for non completed batch "));
						}
					}else{
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Invoice already created"));
					}

				}
			}	

			if(trainer.getModeOfPayment().equals("Month Wise"))
			{
				ProgrammeSchedule batch = programeSheduleService.getCandidates(selectedProgrameScheduleModel.getProgrameSchedule().getId());
				int totalCandidate = 0;
				if(batch!= null){
					totalCandidate = batch.getCandidates().size();
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(payMonthForInvoice);
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				SimpleDateFormat createdformat = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = createdformat.format(new Date());
				boolean isInvoice = trainerInvoiceService.getInvoiceForMonthly(trainer.getId(), CalenderUtil.monthName(month), String.valueOf(year));
				if(isInvoice){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Invoice already completed"));
				}else{
					invoice.setPayMonth(CalenderUtil.monthName(month));
					invoice.setAmount((trainer.getSalary()/30)*noOfDays);
					invoice.setNoOfDays(noOfDays);
					invoice.setInvoiceDescription(invoiceDescriptions);
					invoice.setPayYear(String.valueOf(year));
					invoice.setTrainer(trainer);
					invoice.setProgrammeSchedule(selectedProgrameScheduleModel.getProgrameSchedule());
					trainerInvoiceService.save(invoice);
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					String folderPathWithFIle = directoryName+"/invoices/"+trainer.getId()+"_"+CalenderUtil.monthName(month)+"_"+invoice.getTrainerInvoiceId()+"_"+createdDate+".pdf";
					PdfGenerator.createInvoicePdf(format.format(new Date()), trainer.getCandidateCourse().getCourse(), trainer.getTrainerName(),Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId()),invoiceDescriptions+" Batch Id: "+Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId())+" Batch Title: "+selectedProgrameScheduleModel.getProgrameSchedule().getTitle(),status,folderPathWithFIle,Integer.toString(noOfDays),Integer.toString(totalCandidate),Long.toString((trainer.getSalary()/30)*noOfDays),Long.toString((trainer.getSalary()/30)*noOfDays),trainer.getpancardNo(),trainer.getTrainerBankName(),trainer.getBankBranchName(),trainer.getBankIfsc(),trainer.getBankAccount(),Integer.toString(invoice.getTrainerInvoiceId()));
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(folderPathWithFIle);
					if(emailsForHr != null){
						for(String email:emailsForHr){
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
						}
					}
					trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),trainer.getContactAddress().getEmail(),"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully Invoice Created"));
				}
			}



			if(trainer.getModeOfPayment().equals("Student Wise"))
			{
				ProgrammeSchedule batch = programeSheduleService.getCandidates(selectedProgrameScheduleModel.getProgrameSchedule().getId());
				int totalCandidate = 0;
				if(batch!= null){
					totalCandidate = batch.getCandidates().size();
				}
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = format.format(new Date());
				invoice.setProgrammeSchedule(selectedProgrameScheduleModel.getProgrameSchedule());
				invoice.setNoOfStudent(totalCandidate);
				invoice.setTrainer(trainer);
				if(selectedProgrameScheduleModel.getProgrameSchedule().getIsComplete().equals("NO")){
					if(totalAmount == null){
						totalAmount = 0l;
					}
					if((((trainer.getSalary()*totalCandidate)*50)/100) >= (totalAmount+amountInpercentForPayment)){
						invoice.setAmount(amountInpercentForPayment);
						trainerInvoiceService.save(invoice);
						String folderPathWithFIle = directoryName+"/invoices/"+trainer.getId()+"_b"+selectedProgrameScheduleModel.getProgrameSchedule().getId()+"_"+invoice.getTrainerInvoiceId()+"_"+createdDate+".pdf";
						PdfGenerator.createInvoicePdf(createdDate,trainer.getCandidateCourse().getCourse(),trainer.getTrainerName(),Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId()),invoiceDescriptions+" Batch Id: "+Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId())+" Batch Title: "+selectedProgrameScheduleModel.getProgrameSchedule().getTitle(),status,folderPathWithFIle,"",Integer.toString(totalCandidate),Long.toString(amountInpercentForPayment),Long.toString(amountInpercentForPayment),trainer.getpancardNo(),trainer.getTrainerBankName(),trainer.getBankBranchName(),trainer.getBankIfsc(),trainer.getBankAccount(),Integer.toString(invoice.getTrainerInvoiceId()));
						List<String> filePaths = new ArrayList<String>();
						filePaths.add(folderPathWithFIle);
						if(emailsForHr != null){
							for(String email:emailsForHr){
								new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
							}
						}
						new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),trainer.getContactAddress().getEmail(),"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
						trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
						totalAmount = trainerInvoiceService.getTotalAmount(selectedProgrameScheduleModel.getProgrameSchedule().getId(),trainer.getId());
						trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
						if(totalAmount != null){
							duePaymentOfTrainer = (trainer.getSalary()*totalCandidate) - totalAmount;
						}else{
							duePaymentOfTrainer = trainer.getSalary()*totalCandidate; 
						}
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully Invoice Created"));
					}else{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Maximum 50% allowed as advance payment for non completed batch "));
					}
				}


				if(selectedProgrameScheduleModel.getProgrameSchedule().getIsComplete().equals("YES")){
					if(totalAmount == null){
						totalAmount = 0l;
					}
					if((trainer.getSalary()*totalCandidate) != totalAmount){
						if((trainer.getSalary()*totalCandidate) >= (totalAmount+amountInpercentForPayment)){
							invoice.setAmount(amountInpercentForPayment);
							trainerInvoiceService.save(invoice);
							String folderPathWithFIle = directoryName+"/invoices/"+trainer.getId()+"_b"+selectedProgrameScheduleModel.getProgrameSchedule().getId()+"_"+invoice.getTrainerInvoiceId()+"_"+createdDate+".pdf";
							PdfGenerator.createInvoicePdf(createdDate,trainer.getCandidateCourse().getCourse(),trainer.getTrainerName(),Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId()),invoiceDescriptions+" Batch Id: "+Integer.toString(selectedProgrameScheduleModel.getProgrameSchedule().getId())+" Batch Title: "+selectedProgrameScheduleModel.getProgrameSchedule().getTitle(),status,folderPathWithFIle,"",Integer.toString(totalCandidate),Long.toString(amountInpercentForPayment),Long.toString(amountInpercentForPayment),trainer.getpancardNo(),trainer.getTrainerBankName(),trainer.getBankBranchName(),trainer.getBankIfsc(),trainer.getBankAccount(),Integer.toString(invoice.getTrainerInvoiceId()));
							List<String> filePaths = new ArrayList<String>();
							filePaths.add(folderPathWithFIle);
							if(emailsForHr != null){
								for(String email:emailsForHr){
									new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
								}
							}
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),trainer.getContactAddress().getEmail(),"Trainer Invoice","Trainer invoice generated.",filePaths,null,null);
							trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
							totalAmount = trainerInvoiceService.getTotalAmount(selectedProgrameScheduleModel.getProgrameSchedule().getId(),trainer.getId());
							trainerInvoice = trainerInvoiceService.getTrainerInvoiceByTrainerId(trainer.getId(),selectedProgrameScheduleModel.getProgrameSchedule().getId());
							if(totalAmount != null){
								duePaymentOfTrainer = (trainer.getSalary()*totalCandidate) - totalAmount;
							}else{
								duePaymentOfTrainer = trainer.getSalary()*totalCandidate; 
							}
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully Invoice Created"));
						}else{
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Maximum 50% allowed as advance payment for non completed batch "));
						}
					}else{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"","Invoice already created."));
					}

				}
			}	

		}catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Invoice Successfully Not Created"));
		}
	}

	public void addBatch()
	{
		programmeSchedule = new ProgrammeSchedule();
		programmeSchedule.setCandidateCourse(new CandidateCourse());
		programmeSchedule.setTrainer(new Trainer());
		action = "ADDBATCH";
		multipleDay = new String[7]; 
		try
		{
			if(trainers != null){
				trainers.clear();
			}
			trainers = trainerService.getTrainers();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void addTrainerByCourseId(FacesContext context, UIComponent component, Object value)
	{
		Integer courseId = (Integer) value;
		try{
			if(trainers !=null){
				trainers.clear();
			}
			trainers = trainerService.getTrainerByCourseId(courseId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void saveBatch()
	{
		try
		{
			if(multipleDay.length > 0)
			{
				String day = "";
				for(String s:multipleDay)
				{
					day = day + s+"   ";
				}
				programmeSchedule.setDayOfWeek(day.trim());
			}
			if(programmeSchedule.getId() !=null && programmeSchedule.getIsComplete().equals("YES")){
				ProgrammeSchedule batch = programeSheduleService.getCandidatesById(programmeSchedule.getId());			    
				if(batch.getCandidates() !=null && batch.getCandidates().size()>0 ){
					for(Candidate can:batch.getCandidates()){
						Candidate candi = candidateService.getCandidateCheckByCandidateId(can.getCandidateId());
						if(candi.getCandidateCheckLists() != null && candi.getCandidateCheckLists().size()>0){
							List<CandidateCheckList> canCheckLists=candi.getCandidateCheckLists();
							CandidateCheckList trainingCheckList = null;
							CandidateCheckList trainingFeedBackCheckList = null;
							if(canCheckLists != null){
								for(CandidateCheckList checkList:canCheckLists)
								{
									if(checkList.getCheckListType().getCheckListTypeName().equals("Training")){
										trainingCheckList = checkList;
										break;
									}
								}
								trainingCheckList.setCheckListStatus("Completed");
								candidateCheckListService.saveCandidateCheckListForAssign(trainingCheckList);

								for(CandidateCheckList checkList:canCheckLists)
								{
									if(checkList.getCheckListType().getIndexOrder()==(trainingCheckList.getCheckListType().getIndexOrder()+1)){
										trainingFeedBackCheckList = checkList;
										break;
									}
								}
								trainingFeedBackCheckList.setCheckListStatus("Pending");
								candidateCheckListService.saveCandidateCheckListForAssign(trainingFeedBackCheckList);
							}
						}
					}
				}

			}
			programeSheduleService.saveProgrameShedule(programmeSchedule); 
			if(programmeSchedule.getIsComplete().equals("NO") && programmeSchedule.getId()==null){
				try
				{
					Properties prop = new Properties();
					InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
					prop.load(inputStream);

					List<String> emailsForRecruiter = userProfileService.getEmailByUserRole("ROLE_RECRUITER");
					List<String> emailsForHr = userProfileService.getEmailByUserRole("ROLE_HR");
					List<String> emailListForHrAndRecruiter = new ArrayList<String>();
					emailListForHrAndRecruiter.addAll(emailsForRecruiter);
					emailListForHrAndRecruiter.addAll(emailsForHr);
					if(emailListForHrAndRecruiter.size() > 0){
						for(String email:emailListForHrAndRecruiter){
							String message = "Hi Recruiter,One batch has created.Details of this batch is - Batch Title: "+programmeSchedule.getTitle()+" Course Name: "
									+ ""+programmeSchedule.getCandidateCourse().getCourse()+" Trainer Name: "+programmeSchedule.getTrainer().getTrainerName()+" and Start Date: "
									+ ""+programmeSchedule.getStartDate();
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),email,"Entrolled Candidate Info",message,null,null,null);
						}
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Btach Successfully saved"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Btach Successfully not saved"));
		}
	}




	public void batchList()
	{
		action = "PROGRAMESCHEDULELIST";
		programmeScheduleLazyModel = new LazyProgrameSheduleModel(programeSheduleService);
		try
		{
			if(programmeSchedule != null){
				programmeSchedule = null;
			}
			if(multipleDay != null){
				multipleDay = new String[7]; 
			}
			if(candidates != null && candidates.size() > 0){
				candidates.clear();
			}
			if(trainers != null){
				trainers.clear();
			}
			trainers = trainerService.getTrainers();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void onRowSelectForProgrameShedule()
	{
		try{
			action = "ADDBATCH";
			String day_of_week = programmeSchedule.getDayOfWeek();
			StringTokenizer st2 = new StringTokenizer(day_of_week, " ");
			int i = 0;
			while (st2.hasMoreElements()) {
				multipleDay[i] = (String) st2.nextElement();
				i++;
			}

			if(trainerCourses.size()>0)
			{
				trainerCourses.clear();
			}
			trainerCourses.add(programmeSchedule.getTrainer().getCandidateCourse());
			ProgrammeSchedule ps = programeSheduleService.getCandidates(programmeSchedule.getId());
			if(candidates != null){
				candidates.clear();
			}
			if(ps != null){
				candidates = ps.getCandidates();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}


	public void removeBatch(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String batchId = params.get("batchId");	
		ProgrammeSchedule programmeSchedule = programeSheduleService.getCandidatesById(Integer.valueOf(batchId));
		List<Candidate> candidates = programmeSchedule.getCandidates();
		if(candidates.size() > 0){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Sorry:","This batch contains : "+candidates.size()+" candidates . Please Transefer or remove candidate from this batch before delete batch."));
		}else{
			try{
				 programeSheduleService.deleteBatch(programmeSchedule);
				 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Btach Successfully deleted"));
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","Btach Successfully not deleted"));
			}
		}
	}


	public LazyDataModel<ProgrammeSchedule> getProgrammeScheduleLazyModel() {
		return programmeScheduleLazyModel;
	}



	public void setProgrammeScheduleLazyModel(LazyDataModel<ProgrammeSchedule> programmeScheduleLazyModel) {
		this.programmeScheduleLazyModel = programmeScheduleLazyModel;
	}



	public ProgrammeSchedule getProgrammeSchedule() {
		return programmeSchedule;
	}



	public void setProgrammeSchedule(ProgrammeSchedule programmeSchedule) {
		this.programmeSchedule = programmeSchedule;
	}


	public String[] getMultipleDay() {
		return multipleDay;
	}



	public void setMultipleDay(String[] multipleDay) {
		this.multipleDay = multipleDay;
	}

	public List<CandidateCourse> getTrainerCourses() {
		return trainerCourses;
	}



	public void setTrainerCourses(List<CandidateCourse> trainerCourses) {
		this.trainerCourses = trainerCourses;
	}

	public void programeScheduleListForAssign()
	{
		if(programmeSchedule != null){
			programmeSchedule = null;
		}
		if(selectedCandidates != null){
			selectedCandidates.clear();
		}
		if(candidates !=null){
			candidates.clear();
		}
		if(selectedProgrameScheduleModel !=null){
			selectedProgrameScheduleModel=null;
		}
		if(candidatesForTransfer != null){
			candidatesForTransfer.clear();
		}
		action = "ASSIGNCANDIDATE";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		List<ProgrameScheduleModel> result = programeSheduleService.getProgrameScheduleForAssign();
		programeScheduleModels = new ArrayList<ProgrameScheduleModel>();
		Set<Integer> batchIds = new HashSet<Integer>();
		if(result != null){
			for( ProgrameScheduleModel p: result ) {
				if(batchIds.add(p.getProgrameSchedule().getId())) {
					programeScheduleModels.add(p);
				}
			}
		}
	}



	public void candidateListForAssign()
	{
		if(selectedProgrameScheduleModel !=null){
			try
			{
				List<Candidate> result = candidatePaymentService.getCandidateForAssignToBatch(selectedProgrameScheduleModel.getProgrameSchedule().getCandidateCourse().getId()); 
				candidates = new ArrayList<Candidate>();
				Set<Integer> canIdes = new HashSet<Integer>();
				if(result != null){
					for( Candidate can: result ) {
						if(canIdes.add(can.getCandidateId())) {
							candidates.add( can );
						}
					}
				}
				action = "CANDIDATEFORASSIGN";
				phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Batch Not Selected"));	
		}

	}



	public void assignCandidate()
	{	
		if(selectedCandidates.size()>0){
			try
			{
				for(Candidate can:selectedCandidates)
				{
					Candidate candi = candidateService.getCandidate(can.getCandidateId());
					List<ProgrammeSchedule> setProgrammeSchedules = new ArrayList<ProgrammeSchedule>();
					setProgrammeSchedules.add(selectedProgrameScheduleModel.getProgrameSchedule());
					candi.setProgrammeSchedules(setProgrammeSchedules);
					candi.setCandidateStatus("On Training");
					candidateService.saveCandidate(candi);
				}

				CheckListType checklistType = new CheckListType();
				checklistType.setId(3);
				for(Candidate c:selectedCandidates)
				{
					for(CandidateCheckList ch:c.getCandidateCheckLists())
					{
						ch.setCheckListType(checklistType);
						ch.setCheckListStatus("Initiated");
						candidateCheckListService.saveCandidateCheckListForAssign(ch);
					}
				}
				action="CANDIDATEASSIGNSUCCESS";
				FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate successfully assigned"));
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate successfully not assigned"));
			}
			candidates=null;
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Select candidate first to assign"));
		}

	}



	public void traineeList(){
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		if(selectedAssignCandidates != null && selectedAssignCandidates.size()>0){
			selectedAssignCandidates.clear();
		}
		assignCandidates = candidateService.getAssignCandidates();
		action="TRAINEELIST";
	}


	public void removeCandidateFromBatch(){
		action = "CANDIDATEFORREMOVE";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		if(candidate != null){
			candidate = null;
		}
		candidateLazyModelForRemove = new LazyCandidateDataModelForRemove(candidateService);
	}


	public void removeCandidateDetails(){

		try{
			if(candidate != null){
				action = "REMOVECANDIDATEDETAILS";
				if(programmeSchedule != null){
					programmeSchedule = null;
				}
				Candidate can = candidateService.getBatchByCandidateId(candidate.getCandidateId());
				if(can != null){
					programmeSchedule = can.getProgrammeSchedules().get(0);
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate not selected"));	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void candidateRemoveFromBatch(){
		try{
			action = "CANDIDATEFORREMOVE";
			Candidate can = candidateService.getBatchByCandidateId(candidate.getCandidateId());
			if(candidate != null){
				candidate = null;
			}
			can.setProgrammeSchedules(null);
			candidateService.saveCandidate(can);

			List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(can.getCandidateId());
			CandidateCheckList trainingCheckList = null;
			if(canCheckLists!=null){
				for(CandidateCheckList checkListtype:canCheckLists)
				{
					if(checkListtype.getCheckListType().getCheckListTypeName().equals("Training")){
						trainingCheckList = checkListtype;
						break;
					}
				}
			}
			candidateCheckListService.updateCandidateCheckListbyId(trainingCheckList.getId());



			candidateLazyModelForRemove = new LazyCandidateDataModelForRemove(candidateService);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate Successfully removed"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate not removed"));	
		}

	}

	public void transferCandidateFromBatch(){
		action = "CANDIDATEFORTRANSFER";
		phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
		if(candidate != null){
			candidate = null;
		}
		candidateLazyModelForRemove = new LazyCandidateDataModelForRemove(candidateService);
	}


	public void batchListForTransfer(){
		try{

			if(candidate != null){
				action ="BATCHLISTFORTRANSFER";
				phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
				if(programmeSchedule != null){
					programmeSchedule = null;
				}
				if(selectedProgrameScheduleModel != null){
					selectedProgrameScheduleModel = null;
				}

				Candidate can = candidateService.getBatchByCandidateId(candidate.getCandidateId());
				if(can != null){
					programmeSchedule = can.getProgrammeSchedules().get(0);
				}

				List<ProgrameScheduleModel> result = programeSheduleService.getProgrameScheduleForAssign();
				programeScheduleModels = new ArrayList<ProgrameScheduleModel>();
				Set<Integer> batchIds = new HashSet<Integer>();
				if(result != null){
					for( ProgrameScheduleModel p: result ) {
						if(batchIds.add(p.getProgrameSchedule().getId())) {
							programeScheduleModels.add(p);
						}
					}
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate not selected")); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void transferBatchDetails(){
		if(selectedProgrameScheduleModel != null){
			action = "TRANSFERBATCHDETAILS";
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Batch not selected")); 
		}
	}


	public void transferCandidate(){
		try{
			Candidate can = candidateService.getBatchByCandidateId(candidate.getCandidateId());
			if(candidate != null){
				candidate = null;
			}
			can.setProgrammeSchedules(null);
			candidateService.saveCandidate(can);

			List<ProgrammeSchedule> setProgrammeSchedules = new ArrayList<ProgrammeSchedule>();
			setProgrammeSchedules.add(selectedProgrameScheduleModel.getProgrameSchedule());
			can.setProgrammeSchedules(setProgrammeSchedules);
			candidateService.saveCandidate(can);

			if(programmeSchedule != null){
				programmeSchedule = null;
			}
			if(selectedCandidates != null){
				selectedCandidates.clear();
			}
			if(candidates !=null){
				candidates.clear();
			}
			if(selectedProgrameScheduleModel !=null){
				selectedProgrameScheduleModel=null;
			}

			action = "ASSIGNCANDIDATE";
			List<ProgrameScheduleModel> result = programeSheduleService.getProgrameScheduleForAssign();
			programeScheduleModels = new ArrayList<ProgrameScheduleModel>();
			Set<Integer> batchIds = new HashSet<Integer>();
			if(result != null){
				for( ProgrameScheduleModel p: result ) {
					if(batchIds.add(p.getProgrameSchedule().getId())) {
						programeScheduleModels.add(p);
					}
				}
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Candidate Successfully transfered"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Candidate not transfered")); 
		}
	}
	
	
	public void mailSendForm(){
			if(selectedAssignCandidates.size()>0){
				offerletterModel = new OfferletterModel();
				offerletterModel.setSelectedAssignCandidates(selectedAssignCandidates);
				action="MAILSENDINGFORM";
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate Not Selected"));
			}
	}
	
	
	public void mailSendToCandidate(){
		try{
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
			
			for(AssignCandidateDto can : offerletterModel.getSelectedAssignCandidates()){
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),can.getCandidateEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Mail Successfully Send"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Mail Not Send"));
		}
	}

	public void candidateListInABatch(){
		try{
			if(transferCandidates != null){
				transferCandidates.clear();
			}
			if(selectedBatchForTransfer != null){
				selectedBatchForTransfer = null;
			}
			if(selectedProgrameScheduleModel !=null){
				ProgrammeSchedule ps = programeSheduleService.getCandidates(selectedProgrameScheduleModel.getProgrameSchedule().getId());
				List<Candidate> candidates = ps.getCandidates();
				if(candidates != null && candidates.size() >0){
					candidatesForTransfer = candidates;
					action = "CANDIDATELISTINABATCH";
					phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
					List<ProgrameScheduleModel> result = programeSheduleService.getProgrameScheduleForAssign();
					programeScheduleModelsForTransfer = new ArrayList<ProgrameScheduleModel>();
					Set<Integer> batchIds = new HashSet<Integer>();
					if(result != null){
						for( ProgrameScheduleModel p: result ) {
							if(batchIds.add(p.getProgrameSchedule().getId())) {
								programeScheduleModelsForTransfer.add(p);
							}
						}
					}
				}else{
				 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "There is no candidate in this batch"));
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Batch is not selected"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void reassignCandidates(){
		try{
			if((transferCandidates != null && transferCandidates.size() >0) && selectedBatchForTransfer != null){
					List<ProgrammeSchedule> setProgrammeSchedules = new ArrayList<ProgrammeSchedule>();
					setProgrammeSchedules.add(selectedBatchForTransfer.getProgrameSchedule());
					for(Candidate candidate:transferCandidates){
						Candidate can = candidateService.getBatchByCandidateId(candidate.getCandidateId());
						can.setProgrammeSchedules(null);
						candidateService.saveCandidate(can);
						
						can.setProgrammeSchedules(setProgrammeSchedules);
						candidateService.saveCandidate(can);
					}
					action = "CANDIDATEDETAILSAFTERTRANSFER";
					phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate Successfully Transfer"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Either Candidate or Batch Not Selected"));	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void candidateListInABatchForRemove(){
		try{
			if(transferCandidates != null){
				transferCandidates.clear();
			}
			if(selectedBatchForTransfer != null){
				selectedBatchForTransfer = null;
			}
			if(selectedProgrameScheduleModel !=null){
				ProgrammeSchedule ps = programeSheduleService.getCandidates(selectedProgrameScheduleModel.getProgrameSchedule().getId());
				List<Candidate> candidates = ps.getCandidates();
				if(candidates != null && candidates.size() >0){
					candidatesForTransfer = candidates;
					action = "CANDIDATELISTINABATCHFORREMOVE";
					phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
				}else{
				 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "There is no candidate in this batch"));
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Batch is not selected"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void deleteCandidates(){
		try{
			if(transferCandidates != null && transferCandidates.size() >0){
				for(Candidate candidate:transferCandidates){
					Candidate can = candidateService.getBatchByCandidateId(candidate.getCandidateId());
					can.setProgrammeSchedules(null);
					candidateService.saveCandidate(can);
				}
				action = "CANDIDATEDETAILSAFTERREMOVE";
				phoneExtension = loginBean.getUserProfile().getEmployee().getPhoneExtension();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate Successfully Removed"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidates Not Selected"));
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidates Successfully Deleted"));
		}
	}
	
	
	public List<Candidate> getSelectedCandidates() {
		return selectedCandidates;
	}



	public void setSelectedCandidates(List<Candidate> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}



	public List<Candidate> getCandidates() {
		return candidates;
	}



	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public Trainer getTrainer() {
		return trainer;
	}


	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}



	public TrainerService getTrainerService() {
		return trainerService;
	}



	public void setTrainerService(TrainerService trainerService) {
		this.trainerService = trainerService;
	}



	public LazyDataModel<Trainer> getTrainerLazyModel() {
		return trainerLazyModel;
	}



	public void setTrainerLazyModel(LazyDataModel<Trainer> trainerLazyModel) {
		this.trainerLazyModel = trainerLazyModel;
	}



	public List<Trainer> getTrainers() {
		return trainers;
	}



	public void setTrainers(List<Trainer> trainers) {
		this.trainers = trainers;
	}



	public List<ProgrammeSchedule> getProgrammeSchedules() {
		return ProgrammeSchedules;
	}



	public void setProgrammeSchedules(List<ProgrammeSchedule> programmeSchedules) {
		ProgrammeSchedules = programmeSchedules;
	}

	public ProgrameSheduleService getProgrameSheduleService() {
		return programeSheduleService;
	}



	public void setProgrameSheduleService(ProgrameSheduleService programeSheduleService) {
		this.programeSheduleService = programeSheduleService;
	}



	public TrainerInvoiceService getTrainerInvoiceService() {
		return trainerInvoiceService;
	}



	public void setTrainerInvoiceService(TrainerInvoiceService trainerInvoiceService) {
		this.trainerInvoiceService = trainerInvoiceService;
	}

	public String getInvoiceDescriptions() {
		return invoiceDescriptions;
	}



	public void setInvoiceDescriptions(String invoiceDescriptions) {
		this.invoiceDescriptions = invoiceDescriptions;
	}



	public Long getDuePaymentOfTrainer() {
		return duePaymentOfTrainer;
	}



	public void setDuePaymentOfTrainer(Long duePaymentOfTrainer) {
		this.duePaymentOfTrainer = duePaymentOfTrainer;
	}



	public int getAmountInPercent() {
		return amountInPercent;
	}



	public void setAmountInPercent(int amountInPercent) {
		this.amountInPercent = amountInPercent;
	}



	public Long getAmountInpercentForPayment() {
		return amountInpercentForPayment;
	}



	public void setAmountInpercentForPayment(Long amountInpercentForPayment) {
		this.amountInpercentForPayment = amountInpercentForPayment;
	}



	public Long getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}



	public Integer getNoOfDays() {
		return noOfDays;
	}



	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}



	public Date getPayMonthForInvoice() {
		return payMonthForInvoice;
	}



	public void setPayMonthForInvoice(Date payMonthForInvoice) {
		this.payMonthForInvoice = payMonthForInvoice;
	}



	public UserProfileService getUserProfileService() {
		return userProfileService;
	}



	public void setUserProfileService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}



	public LazyDataModel<TrainerInvoice> getTrainerInvoiceLazyModel() {
		return trainerInvoiceLazyModel;
	}



	public void setTrainerInvoiceLazyModel(LazyDataModel<TrainerInvoice> trainerInvoiceLazyModel) {
		this.trainerInvoiceLazyModel = trainerInvoiceLazyModel;
	}



	public List<TrainerInvoice> getTrainerInvoice() {
		return trainerInvoice;
	}



	public void setTrainerInvoice(List<TrainerInvoice> trainerInvoice) {
		this.trainerInvoice = trainerInvoice;
	}



	public Integer getNoOfStudentInOneBatch() {
		return noOfStudentInOneBatch;
	}



	public void setNoOfStudentInOneBatch(Integer noOfStudentInOneBatch) {
		this.noOfStudentInOneBatch = noOfStudentInOneBatch;
	}



	public CandidatePaymentService getCandidatePaymentService() {
		return candidatePaymentService;
	}



	public void setCandidatePaymentService(CandidatePaymentService candidatePaymentService) {
		this.candidatePaymentService = candidatePaymentService;
	}



	public CandidateCheckListService getCandidateCheckListService() {
		return candidateCheckListService;
	}



	public void setCandidateCheckListService(CandidateCheckListService candidateCheckListService) {
		this.candidateCheckListService = candidateCheckListService;
	}



	public CandidateService getCandidateService() {
		return candidateService;
	}



	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}



	public List<ProgrameScheduleModel> getProgrameScheduleModels() {
		return programeScheduleModels;
	}



	public void setProgrameScheduleModels(List<ProgrameScheduleModel> programeScheduleModels) {
		this.programeScheduleModels = programeScheduleModels;
	}



	public ProgrameScheduleModel getSelectedProgrameScheduleModel() {
		return selectedProgrameScheduleModel;
	}



	public void setSelectedProgrameScheduleModel(ProgrameScheduleModel selectedProgrameScheduleModel) {
		this.selectedProgrameScheduleModel = selectedProgrameScheduleModel;
	}


	public AppDataBean getAppDataBean() {
		return appDataBean;
	}


	public void setAppDataBean(AppDataBean appDataBean) {
		this.appDataBean = appDataBean;
	}

	public Candidate getCandidate() {
		return candidate;
	}


	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}


	public LazyDataModel<Candidate> getCandidateLazyModelForRemove() {
		return candidateLazyModelForRemove;
	}


	public void setCandidateLazyModelForRemove(LazyDataModel<Candidate> candidateLazyModelForRemove) {
		this.candidateLazyModelForRemove = candidateLazyModelForRemove;
	}


	public LazyDataModel<CandidatePayment> getCandidatePaymentLazyModel() {
		return candidatePaymentLazyModel;
	}


	public void setCandidatePaymentLazyModel(LazyDataModel<CandidatePayment> candidatePaymentLazyModel) {
		this.candidatePaymentLazyModel = candidatePaymentLazyModel;
	}


	public List<AssignCandidateDto> getAssignCandidates() {
		return assignCandidates;
	}


	public void setAssignCandidates(List<AssignCandidateDto> assignCandidates) {
		this.assignCandidates = assignCandidates;
	}


	public List<AssignCandidateDto> getSelectedAssignCandidates() {
		return selectedAssignCandidates;
	}


	public void setSelectedAssignCandidates(List<AssignCandidateDto> selectedAssignCandidates) {
		this.selectedAssignCandidates = selectedAssignCandidates;
	}


	public OfferletterModel getOfferletterModel() {
		return offerletterModel;
	}


	public void setOfferletterModel(OfferletterModel offerletterModel) {
		this.offerletterModel = offerletterModel;
	}


	public LoginBean getLoginBean() {
		return loginBean;
	}


	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}


	public List<Candidate> getCandidatesForTransfer() {
		return candidatesForTransfer;
	}


	public void setCandidatesForTransfer(List<Candidate> candidatesForTransfer) {
		this.candidatesForTransfer = candidatesForTransfer;
	}


	public List<Candidate> getTransferCandidates() {
		return transferCandidates;
	}


	public void setTransferCandidates(List<Candidate> transferCandidates) {
		this.transferCandidates = transferCandidates;
	}


	public List<ProgrameScheduleModel> getProgrameScheduleModelsForTransfer() {
		return programeScheduleModelsForTransfer;
	}


	public void setProgrameScheduleModelsForTransfer(List<ProgrameScheduleModel> programeScheduleModelsForTransfer) {
		this.programeScheduleModelsForTransfer = programeScheduleModelsForTransfer;
	}


	public ProgrameScheduleModel getSelectedBatchForTransfer() {
		return selectedBatchForTransfer;
	}


	public void setSelectedBatchForTransfer(ProgrameScheduleModel selectedBatchForTransfer) {
		this.selectedBatchForTransfer = selectedBatchForTransfer;
	}


	public String getPhoneExtension() {
		return phoneExtension;
	}


	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}

}