package com.ayantsoft.trms.jsf.view;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
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

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.CandidateSkills;
import com.ayantsoft.trms.hibernate.pojo.ContactAddress;
import com.ayantsoft.trms.hibernate.pojo.Documents;
import com.ayantsoft.trms.hibernate.pojo.EducationCertification;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;
import com.ayantsoft.trms.hibernate.pojo.UserProfile;
import com.ayantsoft.trms.jsf.model.FileUploadModel;
import com.ayantsoft.trms.jsf.model.LazyCandidateCheckListDataModel;
import com.ayantsoft.trms.jsf.model.LazyCandidateDataModelForEnroll;
import com.ayantsoft.trms.jsf.model.LazyProgrameSheduleModel;
import com.ayantsoft.trms.jsf.model.OfferletterModel;
import com.ayantsoft.trms.service.CandidateCheckListService;
import com.ayantsoft.trms.service.CandidateEducationCertificationService;
import com.ayantsoft.trms.service.CandidatePaymentService;
import com.ayantsoft.trms.service.CandidateService;
import com.ayantsoft.trms.service.CandidateSkillsService;
import com.ayantsoft.trms.service.DirectoryCreationService;
import com.ayantsoft.trms.service.DocumentsService;
import com.ayantsoft.trms.service.EmailSendService;
import com.ayantsoft.trms.service.EmployeeService;
import com.ayantsoft.trms.service.PdfGenerator;
import com.ayantsoft.trms.service.ProgrameSheduleService;
import com.ayantsoft.trms.service.UserProfileService;

@ManagedBean
@ViewScoped
public class HrBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8911038636713832274L;

	private String action;
	private String actionIndicator;
	private Integer candidateId;
	private CandidateCheckList candidateCheckList;
	private List<CandidateSkills> candidateSkills;
	private List<EducationCertification>educationCertificateList;
	private String pageSectionArgument;
	private CandidateSkills candidateSkill;
	private EducationCertification educationCertification;
	private List<Documents> documents;
	private Documents document;
	private ArrayList<String> fileNames = new ArrayList<String>();
	private List<CandidateCheckList> candidateCheckLists;
	private LazyDataModel<ProgrammeSchedule> programmeScheduleLazyModel;
	private ProgrammeSchedule programmeSchedule;
	private String[] multipleDay = new String[7];
	private List<ProgrammeSchedule> programmeSchedules;
	private List<Candidate> candidates;
	private List<CandidateCheckList> selectedCandidateCheckList;
	private OfferletterModel offerletterModel; 
	private String checkListFileName;
	private String checkListTypeName;
	private CandidateCheckList selectedcandidate;
	private Candidate candidate;
	private List<Employee> employees;
	private Integer employeeIds[];
	private LazyDataModel<Candidate> candidateLazyModel;
	private LazyDataModel<CandidateCheckList> candidateCheckListLazyModel; 
	private FileUploadModel fileUploadModel;  
	private String mailBody;

	@ManagedProperty(value="#{candidateCheckListService}")
	private CandidateCheckListService candidateCheckListService ;

	@ManagedProperty(value="#{candidateEducationCertificationService}")
	private CandidateEducationCertificationService candidateEducationCertificationService ;

	@ManagedProperty(value="#{candidateSkillsService}")
	private CandidateSkillsService candidateSkillsService ;

	@ManagedProperty(value="#{candidateService}")
	private CandidateService candidateService ;

	@ManagedProperty(value="#{userProfileService}")
	private UserProfileService userProfileService;

	@ManagedProperty(value="#{documentsService}")
	private DocumentsService documentsService;

	@ManagedProperty(value="#{employeeService}")
	private EmployeeService employeeService;

	@ManagedProperty(value="#{programeSheduleService}")
	private ProgrameSheduleService programeSheduleService;

	@ManagedProperty(value="#{candidatePaymentService}")
	private CandidatePaymentService candidatePaymentService;

	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;




	@PostConstruct
	public void init(){
		programmeScheduleLazyModel = new LazyProgrameSheduleModel(programeSheduleService);
	}

	public void candidateDetails()
	{

		if(candidateCheckList.getCandidate() == null){
			candidateCheckList.setCandidate(new Candidate());
		}

		if(candidateCheckList.getCandidate().getContactAddress() == null){
			candidateCheckList.getCandidate().setContactAddress(new ContactAddress());
		}
		if(candidateCheckList.getCandidate().getUserProfile() == null){
			candidateCheckList.getCandidate().setUserProfile(new UserProfile());
		}
		if(candidateSkills != null){
			candidateSkills = null;
		}
		if(educationCertificateList != null){
			educationCertificateList=null;
		}
		if(candidateCheckLists != null){
			candidateCheckLists = null;
		}
		if(documents != null){
			documents = null;
		}
		candidateSkills=candidateSkillsService.getCandidateSkills(candidateCheckList.getCandidate().getCandidateId());
		educationCertificateList=candidateEducationCertificationService.getCandidateEducationDetails(candidateCheckList.getCandidate().getCandidateId());
		candidateCheckLists = candidateCheckListService.findCheckListByCandidateId(candidateCheckList.getCandidate().getCandidateId());
		Candidate candidate = candidateService.getCandidateForDocuments(candidateCheckList.getCandidate().getCandidateId());
		if(candidate != null){
			documents = candidate.getDocumentses();
		}
		action = "CANDIDATEPROFILE";
	}

	public void edit(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		pageSectionArgument = params.get("pageArg");
		action = "CANDIDATEPROFILEUPDATE"; 
	}


	public void addSkill(Integer candidateId)
	{
		candidateSkill = new CandidateSkills();
		Candidate candidate = new Candidate();
		candidate.setCandidateId(candidateId);
		candidateSkill.setCandidate(candidate);
		pageSectionArgument="AddSkill";
		action = "CANDIDATEPROFILEUPDATE";
	}


	public void saveSkill()
	{
		try{
			candidateSkillsService.saveCandidateSkills(candidateSkill);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Skill successfully saved"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Skill successfully not saved"));
		}
		candidateSkills=candidateSkillsService.getCandidateSkills(candidateCheckList.getCandidate().getCandidateId());
		action = "CANDIDATEPROFILE";
	}


	public void addEducation(Integer candidateId)
	{
		educationCertification = new EducationCertification();
		Candidate candidate = new Candidate();
		candidate.setCandidateId(candidateId);
		educationCertification.setCandidate(candidate);
		pageSectionArgument="AddEducation";
		action = "CANDIDATEPROFILEUPDATE";
	}


	public void addDocuments(Integer canId)
	{
		pageSectionArgument="AddDocuments";
		action = "CANDIDATEPROFILEUPDATE";
		candidateId = canId;
		document = new Documents();
	}


	public void uploadCheckListDoc()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String canId = params.get("candidateId");
		String candidateChecklistId = params.get("candidateChecklistId");
		String candidatesUploadedDocumentsFolder = null;
		try{
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
			prop.load(inputStream);
			candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");
		}catch(Exception e){
			e.printStackTrace();
		}

		if(checkListFileName != null){
			checkListFileName = null;
		}
		checkListFileName = params.get("checkListName");
		pageSectionArgument="AddDocumentsForcheckList";
		action = "CANDIDATEPROFILEUPDATE";
		candidateId = Integer.valueOf(canId);
		Set<CandidateCheckList> candidateCheckLists = new HashSet<CandidateCheckList>(); 
		CandidateCheckList checkList = new CandidateCheckList();
		checkList.setId(Integer.valueOf(candidateChecklistId));
		candidateCheckLists.add(checkList);
		document = new Documents();
		document.setName(checkListFileName);
		document.setCandidateCheckLists(candidateCheckLists);
		fileUploadModel = new FileUploadModel();
		fileUploadModel.setCandidateId(Integer.parseInt(params.get("candidateId")));
		fileUploadModel.setCandidateName(params.get("candidateName"));
		fileUploadModel.setRootDir(candidatesUploadedDocumentsFolder);
		if(checkListFileName.equals("Enrollment Form Submission")){
			fileUploadModel.setDirName("enrollment_form");
		}else if(checkListFileName.equals("Offer Letter")){
			fileUploadModel.setDirName("offerletter_form");
		}else if(checkListFileName.equals("Training Completion And Feedback Form")){
			fileUploadModel.setDirName("feedback_form");
		}else if(checkListFileName.equals("Datasheet")){
			fileUploadModel.setDirName("datasheet_form");
		}else if(checkListFileName.equals("Resume Preparation")){
			fileUploadModel.setDirName("resume");
		}else if(checkListFileName.equals("Reference Check")){
			fileUploadModel.setDirName("referencecheck_form");
		}else if(checkListFileName.equals("Payment Declaration Form")){
			fileUploadModel.setDirName("paymentdeclaration_form");
		}
	}



	public void downloadFile() throws IOException 
	{
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String filePath = params.get("filePath");
			File file = new File(filePath);
			String extension = FilenameUtils.getExtension(filePath);
			String fileName = file.getName();
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			if (extension.equals("xls")) {
				response.setContentType("application/xls");
			}else if(extension.equals("pdf")){
				response.setContentType("application/pdf");
			}else if(extension.equals("zip")){
				response.setContentType("application/zip");
			}else if(extension.equals("docx")){
				response.setContentType("application/octet-stream");
			}else if(extension.equals("doc")){
				response.setContentType("application/octet-stream");
			}else if(extension.equals("txt")){
				response.setContentType("application/text");
			}else if(extension.equals("xlsx")){
				response.setContentType("application/xlsx");
			}else if(extension.equals("csv")){
				response.setContentType("application/csv");
			}else if(extension.equals("jpg")){
				response.setContentType("application/jpg");
			}else if(extension.equals("jpeg")){
				response.setContentType("application/jpeg");
			}else if(extension.equals("png")){
				response.setContentType("application/png");
			}else if(extension.equals("svg")){
				response.setContentType("application/svg");
			}else if(extension.equals("gif")){
				response.setContentType("application/gif");
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "File Type Not Supported"));
			}
			response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
			ServletOutputStream outputStream = response.getOutputStream();
			byte[] bFile = new byte[(int)file.length()];
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bFile);
			IOUtils.copy((InputStream)inputStream, (OutputStream)outputStream);
			outputStream.flush();
			outputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void showFile()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String path = params.get("filePath");
		String fileName = params.get("fileName");

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" +fileName+ "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			FileInputStream fileInputStream=null;
			File file = new File(path);
			byte[] bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			InputStream is = null;
			is = new ByteArrayInputStream(bFile);

			IOUtils.copy(is, out);

			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public void showPdf()
	{
		pageSectionArgument="ShowPdf";
		action = "CANDIDATEPROFILEUPDATE";
	}



	public void handleFileUpload(FileUploadEvent event) throws IOException{

		File directory = new File(fileUploadModel.getRootDir()+"/"+fileUploadModel.getCandidateId()+"_"+fileUploadModel.getCandidateName());
		if(directory.isDirectory()){

			if(fileNames.size()>0){
				fileNames.clear();
			}
			UploadedFile uploadFile=event.getFile();
			if(uploadFile.getSize()>0){

				String filePath = fileUploadModel.getRootDir()+"/"+fileUploadModel.getCandidateId()+"_"+fileUploadModel.getCandidateName()+"/upload/"+fileUploadModel.getDirName()+"/";
				byte[] bytes=null;
				if (event.getFile()!=null) {
					bytes = uploadFile.getContents();
					String filename = FilenameUtils.getName(uploadFile.getFileName());
					fileNames.add(filename);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath+filename)));
					stream.write(bytes);
					stream.close();
					fileUploadModel.setFilePath(filePath+filename);
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "File Uploaded Succesfully"));

			} 

			else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Unable to upload File"));
			}


		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","You have to send form first then upload file"));
		}


	}


	public void handleFileUploadForOfferLetter(FileUploadEvent event) throws IOException{

		try{
			for(CandidateCheckList checkList:fileUploadModel.getSelectedCandidateCheckList()){
				File directory = new File(fileUploadModel.getRootDir()+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
				if(directory.isDirectory()){
				}else{
					boolean isCreate = DirectoryCreationService.createDirectories(fileUploadModel.getRootDir(), checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
					if(!isCreate){
						throw new Exception("Directrory creation exception");
					}
				}
			}
			if(fileNames.size()>0){
				fileNames.clear();
			}

			UploadedFile uploadFile=event.getFile();
			if(uploadFile.getSize()>0){

				for(CandidateCheckList cList:fileUploadModel.getSelectedCandidateCheckList()){
					String filePath = fileUploadModel.getRootDir()+"/"+cList.getCandidate().getCandidateId()+"_"+cList.getCandidate().getFirstName()+"/generate/"+fileUploadModel.getDirName()+"/";
					byte[] bytes=null;
					if (event.getFile()!=null) {
						bytes = uploadFile.getContents();
						String filename = FilenameUtils.getName(uploadFile.getFileName());
						fileNames.add(filename);
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath+filename)));
						stream.write(bytes);
						stream.close();
						fileUploadModel.setFilePath(filePath+filename);
					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "File Uploaded Succesfully"));

			} 

		}catch(Exception e){
			e.printStackTrace();
		}
	}











	public void saveDocuments() throws IOException
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String canId = params.get("candidateId");

		Set<Candidate> candidates = new HashSet<Candidate>();
		Candidate candi = new Candidate();
		candi.setCandidateId(Integer.valueOf(canId));
		candidates.add(candi);

		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
		prop.load(inputStream);
		String filepathdirectory = prop.getProperty("candidatesUploadedDocumentsFolder");

		if(fileNames.size() > 0)
		{
			try{
				for(String name:fileNames)
				{
					Documents documents = new Documents();
					documents.setName(document.getName());
					documents.setDescription(document.getDescription());
					documents.setFilename(name);
					documents.setFilepath(filepathdirectory+"/"+name);
					documents.setContenttype(name.substring(name.lastIndexOf('.')+1));
					documents.setCreated(new Date());
					documents.setExpriedDate(document.getExpriedDate());
					documents.setCandidates(candidates);
					Set<CandidateCheckList> CandidateCheckLists = document.getCandidateCheckLists();
					if(CandidateCheckLists !=null )
					{
						documents.setCandidateCheckLists(document.getCandidateCheckLists());	
					}
					documentsService.saveDocuments(documents);
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Document successfully saved"));
			}catch(Exception e)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Document successfully not saved"));
			}
			Candidate candidate = candidateService.getCandidateForDocuments(candidateCheckList.getCandidate().getCandidateId());
			if(candidate != null){
				documents = candidate.getDocumentses();
			}
			action = "CANDIDATEPROFILE";
			fileNames.clear();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Please upload file first"));
		}
	}





	public void deleteFile()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String docId = params.get("docId");
		String filePath = params.get("filePath");
		String candidateId = params.get("candidateId");

		Set<Candidate> candidates = new HashSet<Candidate>();
		Candidate candi = new Candidate();
		candi.setCandidateId(Integer.valueOf(candidateId));
		candidates.add(candi);

		Documents document = new Documents();
		document.setCandidates(candidates);
		document.setId(Integer.valueOf(docId));
		try{
			File file = new File(filePath);
			file.delete();
			documentsService.deleteDocuments(document);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Document successfully deleted"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Document successfully not deleted"));
		}
		Candidate candidate = candidateService.getCandidateForDocuments(candidateCheckList.getCandidate().getCandidateId());
		if(candidate != null){
			documents = candidate.getDocumentses();
		}
		action = "CANDIDATEPROFILE";
	}



	public void saveEducation()
	{
		try{
			candidateEducationCertificationService.saveEducationCertification(educationCertification);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Education successfully saved"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Education successfully not saved"));
		}
		educationCertificateList=candidateEducationCertificationService.getCandidateEducationDetails(candidateCheckList.getCandidate().getCandidateId());
		action = "CANDIDATEPROFILE";
	}


	public void saveCandidateProfile(){
		action = "CANDIDATEPROFILE";
		try{
			userProfileService.saveUserProfile(candidateCheckList.getCandidate().getUserProfile());
			candidateService.saveCandidate(candidateCheckList.getCandidate());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully saved"));	
		}catch(Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Successfully not saved"));
		}
	}


	public void onRowEditForCandidateSkill(RowEditEvent event) {
		CandidateSkills candidateSkills = ((CandidateSkills) event.getObject());
		try{
			candidateSkillsService.saveCandidateSkills(candidateSkills);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully updated"));
		}catch(Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Successfully not updated"));
		}
	}


	public void onRowCancelForCandidateSkill(RowEditEvent event) {

	}


	public void onRowEditForEducation(RowEditEvent event)
	{
		EducationCertification educationCertification = ((EducationCertification) event.getObject());
		try{
			candidateEducationCertificationService.saveEducationCertification(educationCertification);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Successfully updated"));
		}catch(Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Successfully not updated"));
		}

	}


	public void onRowCancelForEducation(RowEditEvent event)
	{

	}

	public void goToProfile()
	{
		action = "CANDIDATEPROFILE";
	}



	public void workMobileValidation(FacesContext context, UIComponent component, Object value)
	{

	}


	public void emailValidate(FacesContext context, UIComponent component, Object value) throws IOException{

	}


	public void checkEnrollmentFormPending() 
	{
		if(candidateCheckList != null){
			candidateCheckList = null;
		}

		if(selectedcandidate != null){
			selectedcandidate = null;
		}

		if(candidateCheckLists !=null){
			candidateCheckLists = null;
		}

		if(selectedCandidateCheckList !=null){
			selectedCandidateCheckList.clear();
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		if(checkListTypeName != null){
			checkListTypeName = null;
		}
		checkListTypeName = params.get("checkListTypeName");
		actionIndicator=null;
		if(checkListTypeName.equals("Enrollment Form Submission")){
			actionIndicator = "EnrollmentForm";
		}
		if(checkListTypeName.equals("Offer Letter")){
			actionIndicator = "OfferLetter";
		}
		if(checkListTypeName.equals("Training Completion And Feedback Form")){
			actionIndicator = "FeedbackForm";
		}
		if(checkListTypeName.equals("Datasheet")){
			actionIndicator = "Datasheet";
		}
		if(checkListTypeName.equals("Mock Preparation")){
			actionIndicator = "MockPreparation";
		}
		if(checkListTypeName.equals("Graduation")){
			actionIndicator = "Graduation";
		}
		if(checkListTypeName.equals("Reference Check")){
			actionIndicator = "ReferenceCheck";
		}
		if(checkListTypeName.equals("Documents")){
			actionIndicator = "Documents";
		}
		if(checkListTypeName.equals("Hotlist")){
			actionIndicator = "Hotlist";
		}
		if(checkListTypeName.equals("Payment Declaration Form")){
			actionIndicator = "PaymentDeclarationForm";
		}
		if(candidateCheckListLazyModel != null){
			candidateCheckListLazyModel = null;
		}
		action = "CANDIDATELIST";	
		candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);

	}


	public void sendEnrollmentForm() throws IOException
	{
		try
		{

			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
			prop.load(inputStream);
			SimpleDateFormat creationformat = new SimpleDateFormat("dd-MM-yyyy");
			String createdDate = creationformat.format(new Date());
			for(CandidateCheckList checkList: selectedCandidateCheckList)
			{
				String candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");
				File directory = new File(candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
				if(directory.isDirectory()){
				}else{
					boolean isCreate = DirectoryCreationService.createDirectories(candidatesUploadedDocumentsFolder, checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
					if(!isCreate){
						throw new Exception("Directrory creation exception");
					}
				}
				if(checkList.getCandidate().getProgrammeSchedules() != null){
					String folderPathWithFIle = candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"/generate/enrollment_form/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"_"+createdDate;

					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					Date date = null;
					try{
						date = checkList.getCandidate().getProgrammeSchedules().get(0).getStartDate();
					}catch(Exception e){
						throw new ArrayIndexOutOfBoundsException();
					}
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					String batchStartDate = df.format(date);
					CandidatePayment payment = candidatePaymentService.showCandidateDueDetals(checkList.getCandidate().getCandidateId()).get(1);

					PdfGenerator.createenrollmentFormPdf(checkList.getCandidate(), Calendar.getInstance().get(Calendar.YEAR),checkList.getCandidate().getCandidateCourse().getCourse(),format.format(new Date()),batchStartDate,payment.getTransactionId(),format.format(new Date()),folderPathWithFIle,payment.getDebit().toString());
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(folderPathWithFIle);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),checkList.getCandidate().getContactAddress().getEmail(),"Enrollment Form","Enrollment Form is being sent through mail",filePaths,null,null);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),"Enrollment Form","Enrollment Form is being sent through mail",filePaths,null,null);
					CandidateCheckList canCheckList = candidateCheckListService.findCheckListByCandidateId(checkList.getCandidate().getCandidateId()).get(0);
					canCheckList.setCheckListStatus("Initiated");
					candidateCheckListService.saveCandidateCheckListForAssign(canCheckList);
					action="ENROLLMENTFORMSUCCESS";
					FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Enrollment Form Successfully Send To "+checkList.getCandidate().getFirstName()));
				}else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Please assign candidates to batch"));
				}
			}
		}catch(ArrayIndexOutOfBoundsException ae){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Please Assign This Candidate To Batch"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Successfully Mail Not Send"));
		}
	}



	public void saveCheckListDocuments() throws IOException
	{

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String canId = params.get("candidateId");
		String checkListTypeName = params.get("checkListFileName");

		if(fileNames.size() > 0)
		{
			try{
				for(String name:fileNames)
				{
					Documents documents = new Documents();
					documents.setName(document.getName());
					documents.setDescription(document.getDescription());
					documents.setFilename(name);
					documents.setFilepath(fileUploadModel.getFilePath());
					documents.setContenttype(name.substring(name.lastIndexOf('.')+1));
					documents.setCreated(new Date());
					documents.setExpriedDate(document.getExpriedDate());
					Set<CandidateCheckList> CandidateCheckLists = document.getCandidateCheckLists();
					if(CandidateCheckLists !=null )
					{
						documents.setCandidateCheckLists(document.getCandidateCheckLists());	
					}
					documentsService.saveDocuments(documents);
				}

				List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(canId));
				if(checkListTypeName.equals("Enrollment Form Submission")||checkListTypeName.equals("Training Completion And Feedback Form")||checkListTypeName.equals("Datasheet")||checkListTypeName.equals("Resume Preparation")||checkListTypeName.equals("Reference Check")){
					CandidateCheckList presentCheckList = null;
					CandidateCheckList nextCheckList = null;
					if(canCheckLists!=null){
						for(CandidateCheckList checkList:canCheckLists)
						{
							if(checkList.getCheckListType().getCheckListTypeName().equals(checkListTypeName)){
								presentCheckList = checkList;
								break;
							}
						}
						presentCheckList.setCheckListStatus("Completed");
						candidateCheckListService.saveCandidateCheckListForAssign(presentCheckList);
						if(checkListTypeName.equals("Datasheet")){
							Candidate candidate = candidateService.getCandidate(Integer.valueOf(canId));
							candidate.setCandidateStatus("Resume Preparation");
							candidateService.saveCandidate(candidate);
						}
						if(checkListTypeName.equals("Resume Preparation")){
							Candidate candidate = candidateService.getCandidate(Integer.valueOf(canId));
							candidate.setCandidateStatus("On Mock");
							candidate.setResumePath(fileUploadModel.getFilePath());
							candidateService.saveCandidate(candidate);
						}
						for(CandidateCheckList checkList:canCheckLists)
						{
							if(checkList.getCheckListType().getIndexOrder()==(presentCheckList.getCheckListType().getIndexOrder()+1)){
								nextCheckList = checkList;
								break;
							}
						}
						nextCheckList.setCheckListStatus("Pending");
						candidateCheckListService.saveCandidateCheckListForAssign(nextCheckList);
					}
				}

				if(checkListTypeName.equals("Offer Letter")){
					CandidateCheckList offerLetterCheckList = null;
					CandidateCheckList trainingCheckList = null;
					if(canCheckLists!=null){
						for(CandidateCheckList checkList:canCheckLists)
						{
							if(checkList.getCheckListType().getCheckListTypeName().equals("Offer Letter")){
								offerLetterCheckList = checkList;
								break;
							}
						}
						offerLetterCheckList.setCheckListStatus("Completed");
						candidateCheckListService.saveCandidateCheckListForAssign(offerLetterCheckList);
						Candidate candidate = candidateService.getCandidate(Integer.valueOf(canId));
						candidate.setCandidateStatus("On Training");
						candidateService.saveCandidate(candidate);
					}
				}

				if(checkListTypeName.equals("Payment Declaration Form")){
					CandidateCheckList paymentDeclarationCheckList = null;
					if(canCheckLists!=null){
						for(CandidateCheckList checkList:canCheckLists)
						{
							if(checkList.getCheckListType().getCheckListTypeName().equals("Payment Declaration Form")){
								paymentDeclarationCheckList = checkList;
								break;
							}
						}
						paymentDeclarationCheckList.setCheckListStatus("Completed");
						candidateCheckListService.saveCandidateCheckListForAssign(paymentDeclarationCheckList);
					}
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Document successfully saved"));
			}catch(Exception e)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Document successfully not saved"));
			}
			Candidate candidate = candidateService.getCandidateForDocuments(candidateCheckList.getCandidate().getCandidateId());
			if(candidate != null){
				documents = candidate.getDocumentses();
			}
			candidateCheckLists = candidateCheckListService.findCheckListByCandidateId(candidateCheckList.getCandidate().getCandidateId());
			action = "CANDIDATEPROFILE";
			fileNames.clear();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Please upload file first"));
		}
	}




	public void offerLetterSendingForm(){
		if(selectedCandidateCheckList.size()>0){
			String candidatesUploadedDocumentsFolder = null;
			try{
				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				prop.load(inputStream);
				candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");
			}catch(Exception e){
				e.printStackTrace();
			}
			action = "OFFERLETTERSENDFORM";
			offerletterModel = new OfferletterModel();
			offerletterModel.setCandidateCheckLists(selectedCandidateCheckList);
			fileUploadModel = new FileUploadModel();
			fileUploadModel.setSelectedCandidateCheckList(selectedCandidateCheckList);
			fileUploadModel.setRootDir(candidatesUploadedDocumentsFolder);
			fileUploadModel.setDirName("offerletter_form");

		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Candidate not selected"));
		}

	}


	public void sendOfferLetter()
	{
		try{
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
			prop.load(inputStream);
			/*String filePath = prop.getProperty("candidatesUploadedDocumentsFolder");*/
			/*List<String> fileLists = new ArrayList<String>();
			for(String fileName:fileNames){
				fileLists.add(filePath+"/"+fileName);
			}*/
			List<String> fileLists = new ArrayList<String>();
			fileLists.add(fileUploadModel.getFilePath());

			for(CandidateCheckList checkList:offerletterModel.getCandidateCheckLists()){
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"), checkList.getCandidate().getContactAddress().getEmail(), offerletterModel.getSubject(),offerletterModel.getBodyContent(), fileLists,null,null);
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"), loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(), offerletterModel.getSubject(),offerletterModel.getBodyContent(), fileLists,null,null);
				List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(checkList.getCandidate().getCandidateId()));
				CandidateCheckList offerletterCheckList = null;
				for(CandidateCheckList clist:canCheckLists)
				{
					if(clist.getCheckListType().getIndexOrder()==2){
						offerletterCheckList = clist;
						break;
					}
				}
				offerletterCheckList.setCheckListStatus("Initiated");
				candidateCheckListService.saveCandidateCheckListForAssign(offerletterCheckList);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Offer Letter Successfully Send"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Offer Letter Not Send"));
		}
	}


	public void trainingChecklistPending(){
		action="TRAININGCHECKLISTPENDING";
		if(candidateCheckList != null){
			candidateCheckList = null;
		}
		if(selectedcandidate != null){
			selectedcandidate = null;
		}

		if(candidateCheckLists !=null){
			candidateCheckLists = null;
		}

		if(selectedCandidateCheckList !=null){
			selectedCandidateCheckList=null;
		}
		if(actionIndicator != null){
			actionIndicator = null;
		}
		if(candidateCheckListLazyModel != null){
			candidateCheckListLazyModel = null;
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String checkListTypename = params.get("checkListTypeName");	
		candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypename);
	}


	public void feedbackSendingForm()
	{
		try{

			if(selectedCandidateCheckList.size()>0){
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = format.format(new Date());
				SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");

				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				prop.load(inputStream);

				for(CandidateCheckList checkList:selectedCandidateCheckList){

					String candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");
					File directory = new File(candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
					if(directory.isDirectory()){
					}else{
						boolean isCreate = DirectoryCreationService.createDirectories(candidatesUploadedDocumentsFolder, checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
						if(!isCreate){
							throw new Exception("Directrory creation exception");
						}
					}

					String filePath = candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"/generate/feedback_form/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"_"+createdDate;
					PdfGenerator.createFeedbackForm(checkList.getCandidate().getFirstName(),checkList.getCandidate().getCandidateCourse().getCourse(),Integer.toString(checkList.getCandidate().getProgrammeSchedules().get(0).getId()),checkList.getCandidate().getProgrammeSchedules().get(0).getTrainer().getTrainerName(),format2.format(checkList.getCandidate().getProgrammeSchedules().get(0).getStartTime()),format.format(checkList.getCandidate().getProgrammeSchedules().get(0).getStartDate()),format.format(checkList.getCandidate().getProgrammeSchedules().get(0).getEndDate()),filePath);
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(filePath); 
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),checkList.getCandidate().getContactAddress().getEmail(),"Training Feedback Form","Training feedback form is being sent through mail", filePaths,null,null);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),"Training Feedback Form","Training feedback form is being sent through mail", filePaths,null,null);
					List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(checkList.getCandidate().getCandidateId()));
					if(canCheckLists!=null){
						CandidateCheckList feedbackCheckList = null;
						for(CandidateCheckList checklist:canCheckLists)
						{
							if(checklist.getCheckListType().getCheckListTypeName().equals("Training Completion And Feedback Form")){
								feedbackCheckList = checkList;
								break;
							}
						}
						feedbackCheckList.setCheckListStatus("Initiated");
						candidateCheckListService.saveCandidateCheckListForAssign(feedbackCheckList);
					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Training FeedBack Form Successfully Send"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Candidate not selected"));
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Training FeedBack Form Not Send"));
		}
	}



	public void mailSendForDatasheet(){
		try{
			if(selectedCandidateCheckList.size()>0){
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = format.format(new Date());

				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				prop.load(inputStream);
				String candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");

				for(CandidateCheckList checkList:selectedCandidateCheckList){

					File directory = new File(candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
					if(directory.isDirectory()){
					}else{
						boolean isCreate = DirectoryCreationService.createDirectories(candidatesUploadedDocumentsFolder, checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
						if(!isCreate){
							throw new Exception("Directrory creation exception");
						}
					}

					String filePath = candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"/generate/datasheet_form/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"_"+createdDate;
					PdfGenerator.createDatasheetForm(filePath);
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(filePath); 
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),checkList.getCandidate().getContactAddress().getEmail(),"Datasheet Form","Datasheet form is being sent through mail", filePaths,null,null);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),"Datasheet Form","Datasheet form is being sent through mail", filePaths,null,null);

					List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(checkList.getCandidate().getCandidateId()));
					if(canCheckLists!=null){
						CandidateCheckList feedbackCheckList = null;
						for(CandidateCheckList checklist:canCheckLists)
						{
							if(checklist.getCheckListType().getCheckListTypeName().equals("Datasheet")){
								feedbackCheckList = checkList;
								break;
							}
						}
						feedbackCheckList.setCheckListStatus("Initiated");
						candidateCheckListService.saveCandidateCheckListForAssign(feedbackCheckList);
					}
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Datasheet Form Successfully Send"));	
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Candidate not selected"));	
			}

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Datasheet Form Not Send"));
		}
	}



	public void resumePending(){
		action="RESUMEPENDING";
		if(candidateCheckList != null){
			candidateCheckList = null;
		}
		if(selectedcandidate != null){
			selectedcandidate = null;
		}

		if(candidateCheckLists !=null){
			candidateCheckLists = null;
		}

		if(selectedCandidateCheckList !=null){
			selectedCandidateCheckList=null;
		}
		if(candidateCheckListLazyModel != null){
			candidateCheckListLazyModel = null;
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String checkListTypename = params.get("checkListTypeName");	
		candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypename);
	}


	public void remarksForMockOrGraduation(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String canId = params.get("candidateId");
		String checkListId = params.get("candidateChecklistId");
		String checkListName = params.get("checkListName");
		offerletterModel = new OfferletterModel();
		offerletterModel.setCandidateId(Integer.valueOf(canId));
		offerletterModel.setCheckListId(Integer.valueOf(checkListId));
		offerletterModel.setCheckListName(checkListName);
		action = "REMARKSFORMOCKORGRADUATION";
	}



	public void saveRemarksForMockOrGraduation(){
		try{
			List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(offerletterModel.getCandidateId());
			CandidateCheckList presentCheckList = null;
			CandidateCheckList nextCheckList = null;
			if(canCheckLists!=null){
				for(CandidateCheckList checkList:canCheckLists)
				{
					if(checkList.getCheckListType().getCheckListTypeName().equals(offerletterModel.getCheckListName())){
						presentCheckList = checkList;
						break;
					}
				}
				if(offerletterModel.getStatus().equals("Completed")){	
					presentCheckList.setCheckListStatus(offerletterModel.getStatus());
					presentCheckList.setRemarks(offerletterModel.getRemarks());
					candidateCheckListService.saveCandidateCheckListForAssign(presentCheckList);

					if(offerletterModel.getCheckListName().equals("Mock Preparation")){
						Candidate candidate = candidateService.getCandidate(offerletterModel.getCandidateId());
						candidate.setCandidateStatus("On Graduation");
						candidateService.saveCandidate(candidate);
					}

					if(offerletterModel.getCheckListName().equals("Documents")){
						Candidate candidate = candidateService.getCandidate(offerletterModel.getCandidateId());
						candidate.setCandidateStatus("On Hotlist");
						candidateService.saveCandidate(candidate);
					}

					for(CandidateCheckList checkList:canCheckLists)
					{
						if(checkList.getCheckListType().getIndexOrder()==(presentCheckList.getCheckListType().getIndexOrder()+1)){
							nextCheckList = checkList;
							break;
						}
					}
					nextCheckList.setCheckListStatus("Pending");
					candidateCheckListService.saveCandidateCheckListForAssign(nextCheckList);
				}else{
					Candidate candidate = candidateService.getCandidate(offerletterModel.getCandidateId());
					if(offerletterModel.getStatus().equals("Terminated")){
						candidate.setCandidateStatus("On Terminated");
						candidateService.saveCandidate(candidate);
					}
					if(candidate.getCandidateStatus() != null && candidate.getCandidateStatus().equals("On Terminated") && !offerletterModel.getStatus().equals("Terminated")){
						if(offerletterModel.getCheckListName().equals("Mock Preparation")){
							candidate.setCandidateStatus("On Mock");
						}else if(offerletterModel.getCheckListName().equals("Graduation")){
							candidate.setCandidateStatus("On Graduation");
						}else if(offerletterModel.getCheckListName().equals("Reference Check")){
							candidate.setCandidateStatus("On Graduation");
						}else if(offerletterModel.getCheckListName().equals("Documents")){
							candidate.setCandidateStatus("On Graduation");
						}
						candidateService.saveCandidate(candidate);
					}
					presentCheckList.setCheckListStatus(offerletterModel.getStatus());
					presentCheckList.setRemarks(offerletterModel.getRemarks());
					candidateCheckListService.saveCandidateCheckListForAssign(presentCheckList);
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Remarks Successfully Saved"));
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Remarks Not Saved"));
		}
	}




	public void paymentDeclarationForm() throws IOException
	{
		if(selectedCandidateCheckList.size()>0){
			try
			{
				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				prop.load(inputStream);
				String candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");

				SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = format1.format(new Date());
				for(CandidateCheckList checkList: selectedCandidateCheckList)
				{

					File directory = new File(candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
					if(directory.isDirectory()){
					}else{
						boolean isCreate = DirectoryCreationService.createDirectories(candidatesUploadedDocumentsFolder, checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
						if(!isCreate){
							throw new Exception("Directrory creation exception");
						}
					}

					//String folderPathWithFIle = candidatesUploadedDocumentsFolder+"/paymentDeclarationForm:"+checkList.getCandidate().getFirstName()+"_"+checkList.getCandidate().getCandidateId()+"_"+createdDate+".pdf";
					String folderPathWithFIle = candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"/generate/paymentdeclaration_form/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"_"+createdDate;
					List<CandidatePayment> payments = candidatePaymentService.getPaymentsByCandidateId(checkList.getCandidate().getCandidateId()); 
					int size = payments.size();
					CandidatePayment cp1 = payments.get(size-2);
					CandidatePayment cp2 = payments.get(size-1);
					Integer picno = cp1.getCandidate().getContactAddress().getPostalCode();
					if(picno==null){
						picno = new Integer(0);
					}
					PdfGenerator.createPaymentDeclarationForm(cp1.getDebit().toString(),cp1.getTransactionId(),format1.format(cp1.getTransactionDate()),cp2.getDebit().toString(),cp2.getTransactionId(),format1.format(cp2.getTransactionDate()),folderPathWithFIle);
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(folderPathWithFIle);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),checkList.getCandidate().getContactAddress().getEmail(),"Payment Declaration Form","Payment Declaration Form is being sent through mail",filePaths,null,null);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),"Payment Declaration Form","Payment Declaration Form is being sent through mail",filePaths,null,null);
					List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(checkList.getCandidate().getCandidateId());
					CandidateCheckList paymentDeclarationCheckList = null;
					if(canCheckLists!=null){
						for(CandidateCheckList canCheckList:canCheckLists)
						{
							if(canCheckList.getCheckListType().getCheckListTypeName().equals("Payment Declaration Form")){
								paymentDeclarationCheckList = checkList;
								break;
							}
						}
						paymentDeclarationCheckList.setCheckListStatus("Initiated");
						candidateCheckListService.saveCandidateCheckListForAssign(paymentDeclarationCheckList);
						FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Payment Declaration Form Successfully Send"));
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Payment Declaration Form Not Send"));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate Not Selected"));
		}

	}


	public void mailSendForm(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String checkListType = params.get("checkListType");
		if(checkListType.equals("Hotlist")){
			if(selectedcandidate != null){
				offerletterModel = new OfferletterModel();
				offerletterModel.setCandidateCheckList(selectedcandidate);
				offerletterModel.setCheckListName(checkListType);
				action="MAILSENDINGFORM";
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate Not Selected"));
			}
		}else{
			if(selectedCandidateCheckList.size()>0){
				offerletterModel = new OfferletterModel();
				offerletterModel.setCandidateCheckLists(selectedCandidateCheckList);
				offerletterModel.setCheckListName(checkListType);
				action="MAILSENDINGFORM";
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate Not Selected"));
			}
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

			if(offerletterModel.getCheckListName().equals("Hotlist")){
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),offerletterModel.getCandidateCheckList().getCandidate().getContactAddress().getEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
			}else{
				for(CandidateCheckList checkList:offerletterModel.getCandidateCheckLists()){
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),checkList.getCandidate().getContactAddress().getEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Mail Successfully Send"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Mail Not Send"));
		}
	}

	public void referenceCheckSendingForm()
	{
		if(selectedCandidateCheckList.size()>0){
			try{
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = format.format(new Date());
				SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");

				Properties prop = new Properties();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
				prop.load(inputStream);
				String candidatesUploadedDocumentsFolder = prop.getProperty("candidatesUploadedDocumentsFolder");

				for(CandidateCheckList checkList:selectedCandidateCheckList){

					File directory = new File(candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
					if(directory.isDirectory()){
					}else{
						boolean isCreate = DirectoryCreationService.createDirectories(candidatesUploadedDocumentsFolder, checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName());
						if(!isCreate){
							throw new Exception("Directrory creation exception");
						}
					}

					String filePath = candidatesUploadedDocumentsFolder+"/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"/generate/referencecheck_form/"+checkList.getCandidate().getCandidateId()+"_"+checkList.getCandidate().getFirstName()+"_"+createdDate;
					PdfGenerator.referenceCheckForm(filePath);
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(filePath); 
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),checkList.getCandidate().getContactAddress().getEmail(),"Reference Check Form","Reference check form is being sent through mail", filePaths,null,null);
					new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"),prop.getProperty("notificationSenderEmailId"),prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(),"Reference Check Form","Reference check form is being sent through mail", filePaths,null,null);
					List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(checkList.getCandidate().getCandidateId()));
					if(canCheckLists!=null){
						CandidateCheckList feedbackCheckList = null;
						for(CandidateCheckList checklist:canCheckLists)
						{
							if(checklist.getCheckListType().getCheckListTypeName().equals("Reference Check")){
								feedbackCheckList = checkList;
								break;
							}
						}
						feedbackCheckList.setCheckListStatus("Initiated");
						candidateCheckListService.saveCandidateCheckListForAssign(feedbackCheckList);
					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Reference Check Form Successfully Send"));
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Reference Check Form Not Send"));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail","Candidate Not Selected"));
		}

	}

	public void mockChecklistPending(){
		action="MOCKCHECKLISTPENDING";
		if(candidateCheckList != null){
			candidateCheckList = null;
		}

		if(candidateCheckLists !=null){
			candidateCheckLists = null;
		}
		if(selectedcandidate != null){
			selectedcandidate = null;
		}

		if(selectedCandidateCheckList !=null){
			selectedCandidateCheckList=null;
		}
		if(actionIndicator != null){
			actionIndicator = null;
		}
		if(selectedcandidate != null){
			selectedcandidate = null;
		}
		if(checkListTypeName !=null){
			checkListTypeName = null;
		}
		if(candidateCheckListLazyModel != null){
			candidateCheckListLazyModel = null;
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		checkListTypeName = params.get("checkListTypeName");	
		candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);
	}


	public void mockOrGraduationPreparation(){
		if(selectedcandidate != null){
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String checkListTypename = params.get("checkListType");	
			action = "MAILFORMOCKORGRADUATIONPREPARATION";
			offerletterModel = new OfferletterModel();
			offerletterModel.setCheckListName(checkListTypename);
			offerletterModel.setCandidateCheckList(selectedcandidate);
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate Not Selected"));
		}
	}


	public void findResume(){
		if(offerletterModel.getResumeMandatory() == true){
			CandidateCheckList checkList = candidateCheckListService.getDocuments(selectedcandidate.getCandidate().getCandidateId(),"Resume Preparation");
			if(checkList.getDocumentses().size()>0){
				offerletterModel.setFileName(checkList.getDocumentses().get(0).getFilename());
				offerletterModel.setFilePath(checkList.getDocumentses().get(0).getFilepath());
			}else{
				offerletterModel.setFileName("File Not Found");
			}
		}


	}


	public void sendMailForMockPreparation(){
		try{
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trms.properties");
			prop.load(inputStream);
			String mockTeam = prop.getProperty("mockpreparationteam");
			String graduationTeam = prop.getProperty("graduationpreparationteam"); 		
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
			if(offerletterModel.getResumeMandatory()){
				List<String> filePath = new ArrayList<String>();
				filePath.add(offerletterModel.getFilePath());
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"), offerletterModel.getCandidateCheckList().getCandidate().getContactAddress().getEmail(), offerletterModel.getSubject(),offerletterModel.getBodyContent(),filePath,ccList,bccList);
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(), offerletterModel.getSubject(),offerletterModel.getBodyContent(),filePath,ccList,bccList);
				if(offerletterModel.getCheckListName().equals("Mock Preparation")){
					StringTokenizer st = new StringTokenizer(mockTeam,",");
					while(st.hasMoreElements()){
						String email = (String) st.nextElement();
						if(email != null){
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"),email, offerletterModel.getSubject(),offerletterModel.getBodyContent(),filePath,ccList,bccList);   
						}
					}
				}else{
					StringTokenizer st = new StringTokenizer(graduationTeam,",");
					while(st.hasMoreElements()){
						String email = (String) st.nextElement();
						if(email != null){
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"),email, offerletterModel.getSubject(),offerletterModel.getBodyContent(),filePath,ccList,bccList);   
						}
					}
				}
			}else{
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"), offerletterModel.getCandidateCheckList().getCandidate().getContactAddress().getEmail(), offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
				new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"),loginBean.getUserProfile().getEmployee().getContactAddress().getEmail(), offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);
				if(offerletterModel.getCheckListName().equals("Mock Preparation")){
					StringTokenizer st = new StringTokenizer(mockTeam,",");
					while(st.hasMoreElements()){
						String email = (String) st.nextElement();
						if(email != null){
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"),email, offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);   
						}
					}
				}else{
					StringTokenizer st = new StringTokenizer(graduationTeam,",");
					while(st.hasMoreElements()){
						String email = (String) st.nextElement();
						if(email != null){
							new EmailSendService().sendMail(prop.getProperty("host"),prop.getProperty("port"), prop.getProperty("notificationSenderEmailId"), prop.getProperty("notificationSenderEmailPassword"),email, offerletterModel.getSubject(),offerletterModel.getBodyContent(),null,ccList,bccList);   
						}
					}
				}
			}

			List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(offerletterModel.getCandidateCheckList().getCandidate().getCandidateId()));
			CandidateCheckList presentCheckList = null;
			for(CandidateCheckList clist:canCheckLists)
			{
				if(clist.getCheckListType().getCheckListTypeName().equals(offerletterModel.getCheckListName())){
					presentCheckList = clist;
					break;
				}
			}
			String mailDetails = "FORM:  "+prop.getProperty("notificationSenderEmailId")+"  ,SUBJECT:  "+offerletterModel.getSubject()+"  ,MESSAGE:  "+offerletterModel.getBodyContent()+"  ,DATE:  "+new Date();
			if(presentCheckList.getMailDetails() != null){
				String message = presentCheckList.getMailDetails();
				message = message +" ###### "+mailDetails;
				presentCheckList.setMailDetails(message);
			}else{
				presentCheckList.setMailDetails(mailDetails);
			}
			if(presentCheckList.getCheckListStatus().equals("Pending")){
				presentCheckList.setCheckListStatus("Initiated");	
			}
			if(presentCheckList.getMailCount() != null){
				presentCheckList.setMailCount(presentCheckList.getMailCount()+1);
			}else{
				presentCheckList.setMailCount(1);
			}
			candidateCheckListService.saveCandidateCheckListForAssign(presentCheckList);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Mail Successfully Send"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Mail Not Send"));
		}
	}

	public void hotListChecklistPending(){
		action="HOTLISTCHECKLISTPENDING";
		if(candidateCheckList != null){
			candidateCheckList = null;
		}

		if(candidateCheckLists !=null){
			candidateCheckLists = null;
		}

		if(selectedCandidateCheckList !=null){
			selectedCandidateCheckList=null;
		}
		if(actionIndicator != null){
			actionIndicator = null;
		}
		if(selectedcandidate !=null){
			selectedcandidate = null;
		}
		if(candidateCheckListLazyModel != null){
			candidateCheckListLazyModel = null;
		}
		employeeIds = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String checkListTypename = params.get("checkListTypeName");	
		candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypename);
	}



	public void hotlistForm(){
		if(selectedcandidate != null){
			action = "HOTLISTFORM";
			candidate = candidateService.getCandidate(selectedcandidate.getCandidate().getCandidateId());
			Set<Employee> salesEmployees = candidate.getEmployees();
			if(salesEmployees.size()>0){
				employeeIds = new Integer[salesEmployees.size()];	
				int i=0;
				for(Employee e:salesEmployees){
					employeeIds[i]=e.getEmpId();
					i++;
				}	
			}
			employees = employeeService.getEmployeeByDepartmentName("Sales");
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate Not Selected"));
		}
	}


	public void saveHotlist(){
		try{
			Set<Employee> empSet = new HashSet<Employee>(0);
			for(Integer id:employeeIds){
				Employee emp = new Employee();
				emp.setEmpId(id);
				empSet.add(emp);
			}
			candidate.setEmployees(empSet);
			candidateService.saveCandidate(candidate);

			List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(candidate.getCandidateId());
			CandidateCheckList hotlistCheckList = null;
			if(canCheckLists!=null){
				for(CandidateCheckList canCheckList:canCheckLists)
				{
					if(canCheckList.getCheckListType().getCheckListTypeName().equals("Hotlist")){
						hotlistCheckList = canCheckList;
						break;
					}
				}
				hotlistCheckList.setCheckListStatus("Initiated");
				candidateCheckListService.saveCandidateCheckListForAssign(hotlistCheckList);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Successfully Saved"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Not Saved"));
		}
	}


	public void hotlistStatus(){
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String candidateId = params.get("candidateId");	
			String checkListTypename = params.get("checkListName"); 
			List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(Integer.valueOf(candidateId));
			CandidateCheckList hotlistCheckList = null;
			if(canCheckLists!=null){
				for(CandidateCheckList canCheckList:canCheckLists)
				{
					if(canCheckList.getCheckListType().getCheckListTypeName().equals("Hotlist")){
						hotlistCheckList = canCheckList;
						break;
					}
				}
				hotlistCheckList.setCheckListStatus("Completed");
				candidateCheckListService.saveCandidateCheckListForAssign(hotlistCheckList);
			}
			action="HOTLISTCHECKLISTPENDING";
			if(candidateCheckList != null){
				candidateCheckList = null;
			}

			if(candidateCheckLists !=null){
				candidateCheckLists = null;
			}

			if(selectedCandidateCheckList !=null){
				selectedCandidateCheckList=null;
			}
			if(actionIndicator != null){
				actionIndicator = null;
			}
			if(selectedcandidate !=null){
				selectedcandidate = null;
			}
			if(candidateCheckListLazyModel != null){
				candidateCheckListLazyModel = null;
			}
			employeeIds = null;
			candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Successfully Saved"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Not Saved"));
		}
	}


	public void skipCandidateToNextProcess(){

		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String checkListTypeFrompage = params.get("checkListType");	
			if(selectedCandidateCheckList.size()>0){
				for(CandidateCheckList checkListForCandidateId:selectedCandidateCheckList){
					List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(checkListForCandidateId.getCandidate().getCandidateId());
					if(checkListTypeFrompage.equals("Enrollment Form Submission")||checkListTypeFrompage.equals("Offer Letter")||checkListTypeFrompage.equals("Training")||checkListTypeFrompage.equals("Training Completion And Feedback Form")||checkListTypeFrompage.equals("Datasheet")||checkListTypeFrompage.equals("Resume Preparation")||checkListTypeFrompage.equals("Reference Check")||checkListTypeFrompage.equals("Documents")){
						CandidateCheckList presentCheckList = null;
						CandidateCheckList nextCheckList = null;
						if(canCheckLists!=null){
							for(CandidateCheckList checkListtype:canCheckLists)
							{
								if(checkListtype.getCheckListType().getCheckListTypeName().equals(checkListTypeFrompage)){
									presentCheckList = checkListtype;
									break;
								}
							}
							presentCheckList.setCheckListStatus("Completed");
							candidateCheckListService.saveCandidateCheckListForAssign(presentCheckList);

							if(checkListTypeFrompage.equals("Datasheet")){
								Candidate candidate = candidateService.getCandidate(checkListForCandidateId.getCandidate().getCandidateId());
								candidate.setCandidateStatus("Resume Preparation");
								candidateService.saveCandidate(candidate);
							}
							if(checkListTypeFrompage.equals("Resume Preparation")){
								Candidate candidate = candidateService.getCandidate(checkListForCandidateId.getCandidate().getCandidateId());
								candidate.setCandidateStatus("On Mock");
								candidateService.saveCandidate(candidate);
							}
							if(checkListTypeFrompage.equals("Offer Letter")){
								Candidate candidate = candidateService.getCandidate(checkListForCandidateId.getCandidate().getCandidateId());
								candidate.setCandidateStatus("On Training");
								candidateService.saveCandidate(candidate);
							}
							if(checkListTypeFrompage.equals("Documents")){
								Candidate candidate = candidateService.getCandidate(checkListForCandidateId.getCandidate().getCandidateId());
								candidate.setCandidateStatus("On Hotlist");
								candidateService.saveCandidate(candidate);
							}
							for(CandidateCheckList checkListtype:canCheckLists)
							{
								if(checkListtype.getCheckListType().getIndexOrder()==(presentCheckList.getCheckListType().getIndexOrder()+1)){
									nextCheckList = checkListtype;
									break;
								}
							}
							nextCheckList.setCheckListStatus("Pending");
							candidateCheckListService.saveCandidateCheckListForAssign(nextCheckList);
						}
					}

				}
				if(candidateCheckListLazyModel != null){
					candidateCheckListLazyModel = null;
				}
				if(checkListTypeFrompage.equals("Enrollment Form Submission")||checkListTypeFrompage.equals("Offer Letter")||checkListTypeFrompage.equals("Training Completion And Feedback Form")||checkListTypeFrompage.equals("Datasheet")||checkListTypeFrompage.equals("Reference Check")||checkListTypeFrompage.equals("Documents")){
					action = "CANDIDATELIST";	
					candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);
				}
				if(checkListTypeFrompage.equals("Training")){
					action = "TRAININGCHECKLISTPENDING";	
					candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);
				}
				if(checkListTypeFrompage.equals("Resume Preparation")){
					action = "RESUMEPENDING";	
					candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate successfully skiped"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not selected"));	
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}




	public void skipCandidateToNextProcessFromSpecialStep(){
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String checkListTypeFrompage = params.get("checkListType");	
			if(selectedcandidate != null){
				List<CandidateCheckList> canCheckLists = candidateCheckListService.findCheckListByCandidateId(selectedcandidate.getCandidate().getCandidateId());
				if(checkListTypeFrompage.equals("Mock Preparation")||checkListTypeFrompage.equals("Graduation")){
					CandidateCheckList presentCheckList = null;
					CandidateCheckList nextCheckList = null;
					if(canCheckLists!=null){
						for(CandidateCheckList checkListtype:canCheckLists)
						{
							if(checkListtype.getCheckListType().getCheckListTypeName().equals(checkListTypeFrompage)){
								presentCheckList = checkListtype;
								break;
							}
						}
						presentCheckList.setCheckListStatus("Completed");
						candidateCheckListService.saveCandidateCheckListForAssign(presentCheckList);

						if(checkListTypeFrompage.equals("Mock Preparation")){
							Candidate candidate = candidateService.getCandidate(selectedcandidate.getCandidate().getCandidateId());
							candidate.setCandidateStatus("On Graduation");
							candidateService.saveCandidate(candidate);
						}
						for(CandidateCheckList checkListtype:canCheckLists)
						{
							if(checkListtype.getCheckListType().getIndexOrder()==(presentCheckList.getCheckListType().getIndexOrder()+1)){
								nextCheckList = checkListtype;
								break;
							}
						}
						nextCheckList.setCheckListStatus("Pending");
						candidateCheckListService.saveCandidateCheckListForAssign(nextCheckList);
					}
				}
				if(candidateCheckListLazyModel != null){
					candidateCheckListLazyModel = null;
				}
				if(checkListTypeFrompage.equals("Mock Preparation")||checkListTypeFrompage.equals("Graduation")){
					action = "MOCKCHECKLISTPENDING";	
					candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,checkListTypeName);
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate successfully skiped"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not selected"));	
			}
		}catch(Exception e){
			e.printStackTrace();
		}


	}

	public void w2List(){
		action="W2LIST";
		candidateLazyModel = new LazyCandidateDataModelForEnroll(candidateService);
	}


	public void terminated(){
		if(selectedCandidateCheckList != null && selectedCandidateCheckList.size() >0){
			try{
				for(CandidateCheckList checkList:selectedCandidateCheckList){
					candidateService.updateCandidateSubStatus(checkList.getCandidate().getCandidateId());
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate successfully terminated"));
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not successfully terminated"));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not selected"));
		}
	}


	public void terminateFromMockAndGraduation(){
		if(selectedcandidate != null){
			try{
				candidateService.updateCandidateSubStatus(selectedcandidate.getCandidate().getCandidateId());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate successfully terminated"));
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not successfully terminated"));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not selected"));
		}
	}


	public void terminateFromTraining(){
		if(selectedCandidateCheckList != null && selectedCandidateCheckList.size() > 0){
			try{
				for(CandidateCheckList checkList:selectedCandidateCheckList){
					Candidate can = candidateService.getBatchByCandidateId(checkList.getCandidate().getCandidateId());
					can.setProgrammeSchedules(null);
					can.setCandidateStatus("On Terminated");
					candidateService.saveCandidate(can);
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate successfully terminated"));
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not successfully terminated"));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Candidate not selected"));
		}
	}


	public void findMailContent(){

		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String messege = params.get("mailId");
			if(messege != null){
				messege = messege.replace(",SUBJECT:","<br>SUBJECT:");
				messege = messege.replace(",MESSAGE:","<br>MESSAGE:<br>");
				messege = messege.replace(",DATE:","<br>DATE:");
				mailBody = messege.replace("######", "<br><br><br>");
			}
			action = "MAILDETAILS";
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	/*public void backTOGraduationTable(){
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String pageIndicator = params.get("indicator");

			action="MOCKCHECKLISTPENDING";
			if(candidateCheckList != null){
				candidateCheckList = null;
			}

			if(candidateCheckLists !=null){
				candidateCheckLists = null;
			}
			if(selectedcandidate != null){
				selectedcandidate = null;
			}

			if(selectedCandidateCheckList !=null){
				selectedCandidateCheckList=null;
			}
			if(actionIndicator != null){
				actionIndicator = null;
			}
			if(selectedcandidate != null){
				selectedcandidate = null;
			}
			if(checkListTypeName !=null){
				checkListTypeName = null;
			}
			if(candidateCheckListLazyModel != null){
				candidateCheckListLazyModel = null;
			}
			if(pageIndicator.equals("Mock Preparation")){
				candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,"Mock Preparation");
			}else{
				candidateCheckListLazyModel = new LazyCandidateCheckListDataModel(candidateCheckListService,"Graduation");
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}*/

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public CandidateCheckListService getCandidateCheckListService() {
		return candidateCheckListService;
	}


	public void setCandidateCheckListService(CandidateCheckListService candidateCheckListService) {
		this.candidateCheckListService = candidateCheckListService;
	}


	public CandidateCheckList getCandidateCheckList() {
		return candidateCheckList;
	}


	public void setCandidateCheckList(CandidateCheckList candidateCheckList) {
		this.candidateCheckList = candidateCheckList;
	}

	public List<CandidateSkills> getCandidateSkills() {
		return candidateSkills;
	}

	public void setCandidateSkills(List<CandidateSkills> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}



	public CandidateEducationCertificationService getCandidateEducationCertificationService() {
		return candidateEducationCertificationService;
	}

	public void setCandidateEducationCertificationService(
			CandidateEducationCertificationService candidateEducationCertificationService) {
		this.candidateEducationCertificationService = candidateEducationCertificationService;
	}

	public List<EducationCertification> getEducationCertificateList() {
		return educationCertificateList;
	}

	public void setEducationCertificateList(List<EducationCertification> educationCertificateList) {
		this.educationCertificateList = educationCertificateList;
	}

	public CandidateSkillsService getCandidateSkillsService() {
		return candidateSkillsService;
	}

	public void setCandidateSkillsService(CandidateSkillsService candidateSkillsService) {
		this.candidateSkillsService = candidateSkillsService;
	}

	public String getPageSectionArgument() {
		return pageSectionArgument;
	}

	public void setPageSectionArgument(String pageSectionArgument) {
		this.pageSectionArgument = pageSectionArgument;
	}

	public CandidateSkills getCandidateSkill() {
		return candidateSkill;
	}

	public void setCandidateSkill(CandidateSkills candidateSkill) {
		this.candidateSkill = candidateSkill;
	}

	public EducationCertification getEducationCertification() {
		return educationCertification;
	}

	public void setEducationCertification(EducationCertification educationCertification) {
		this.educationCertification = educationCertification;
	}

	public CandidateService getCandidateService() {
		return candidateService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public UserProfileService getUserProfileService() {
		return userProfileService;
	}

	public void setUserProfileService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	public List<Documents> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Documents> documents) {
		this.documents = documents;
	}


	public ArrayList<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(ArrayList<String> fileNames) {
		this.fileNames = fileNames;
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public DocumentsService getDocumentsService() {
		return documentsService;
	}

	public void setDocumentsService(DocumentsService documentsService) {
		this.documentsService = documentsService;
	}

	public List<CandidateCheckList> getCandidateCheckLists() {
		return candidateCheckLists;
	}

	public void setCandidateCheckLists(List<CandidateCheckList> candidateCheckLists) {
		this.candidateCheckLists = candidateCheckLists;
	}


	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public ProgrammeSchedule getProgrammeSchedule() {
		return programmeSchedule;
	}


	public void setProgrammeSchedule(ProgrammeSchedule programmeSchedule) {
		this.programmeSchedule = programmeSchedule;
	}


	public ProgrameSheduleService getProgrameSheduleService() {
		return programeSheduleService;
	}


	public void setProgrameSheduleService(ProgrameSheduleService programeSheduleService) {
		this.programeSheduleService = programeSheduleService;
	}


	public LazyDataModel<ProgrammeSchedule> getProgrammeScheduleLazyModel() {
		return programmeScheduleLazyModel;
	}


	public void setProgrammeScheduleLazyModel(LazyDataModel<ProgrammeSchedule> programmeScheduleLazyModel) {
		this.programmeScheduleLazyModel = programmeScheduleLazyModel;
	}


	public String[] getMultipleDay() {
		return multipleDay;
	}


	public void setMultipleDay(String[] multipleDay) {
		this.multipleDay = multipleDay;
	}


	public List<ProgrammeSchedule> getProgrammeSchedules() {
		return programmeSchedules;
	}


	public void setProgrammeSchedules(List<ProgrammeSchedule> programmeSchedules) {
		this.programmeSchedules = programmeSchedules;
	}


	public Documents getDocument() {
		return document;
	}


	public void setDocument(Documents document) {
		this.document = document;
	}


	public List<Candidate> getCandidates() {
		return candidates;
	}


	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}


	public CandidatePaymentService getCandidatePaymentService() {
		return candidatePaymentService;
	}


	public void setCandidatePaymentService(CandidatePaymentService candidatePaymentService) {
		this.candidatePaymentService = candidatePaymentService;
	}

	public List<CandidateCheckList> getSelectedCandidateCheckList() {
		return selectedCandidateCheckList;
	}

	public void setSelectedCandidateCheckList(List<CandidateCheckList> selectedCandidateCheckList) {
		this.selectedCandidateCheckList = selectedCandidateCheckList;
	}

	public String getActionIndicator() {
		return actionIndicator;
	}

	public void setActionIndicator(String actionIndicator) {
		this.actionIndicator = actionIndicator;
	}

	public OfferletterModel getOfferletterModel() {
		return offerletterModel;
	}

	public void setOfferletterModel(OfferletterModel offerletterModel) {
		this.offerletterModel = offerletterModel;
	}

	public String getCheckListFileName() {
		return checkListFileName;
	}

	public void setCheckListFileName(String checkListFileName) {
		this.checkListFileName = checkListFileName;
	}

	public String getCheckListTypeName() {
		return checkListTypeName;
	}

	public void setCheckListTypeName(String checkListTypeName) {
		this.checkListTypeName = checkListTypeName;
	}

	public CandidateCheckList getSelectedcandidate() {
		return selectedcandidate;
	}

	public void setSelectedcandidate(CandidateCheckList selectedcandidate) {
		this.selectedcandidate = selectedcandidate;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Integer[] getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(Integer[] employeeIds) {
		this.employeeIds = employeeIds;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public LazyDataModel<Candidate> getCandidateLazyModel() {
		return candidateLazyModel;
	}

	public void setCandidateLazyModel(LazyDataModel<Candidate> candidateLazyModel) {
		this.candidateLazyModel = candidateLazyModel;
	}

	public LazyDataModel<CandidateCheckList> getCandidateCheckListLazyModel() {
		return candidateCheckListLazyModel;
	}

	public void setCandidateCheckListLazyModel(LazyDataModel<CandidateCheckList> candidateCheckListLazyModel) {
		this.candidateCheckListLazyModel = candidateCheckListLazyModel;
	}

	public FileUploadModel getFileUploadModel() {
		return fileUploadModel;
	}

	public void setFileUploadModel(FileUploadModel fileUploadModel) {
		this.fileUploadModel = fileUploadModel;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}


}