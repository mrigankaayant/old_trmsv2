package com.ayantsoft.trms.jsf.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;
import com.ayantsoft.trms.hibernate.pojo.CandidateSkills;
import com.ayantsoft.trms.hibernate.pojo.Documents;
import com.ayantsoft.trms.hibernate.pojo.EducationCertification;
import com.ayantsoft.trms.jsf.model.LazyCandidateDataModelForPlaced;
import com.ayantsoft.trms.jsf.model.LazyCandidateDataModelForSales;
import com.ayantsoft.trms.jsf.model.OfferletterModel;
import com.ayantsoft.trms.service.CandidateCheckListService;
import com.ayantsoft.trms.service.CandidateEducationCertificationService;
import com.ayantsoft.trms.service.CandidateService;
import com.ayantsoft.trms.service.CandidateSkillsService;
import com.ayantsoft.trms.service.EmployeeService;

@ManagedBean
@ViewScoped
public class SalesBean implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -8415036648449513889L;

	private String action;
	private Candidate selectedCandidate;
	private List<CandidateSkills> candidateSkills;
	private List<EducationCertification>educationCertificateList;
	private List<Documents> documents;
	private OfferletterModel offerletterModel;
	private LazyDataModel<Candidate> candidateLazyModel;
	private LazyDataModel<Candidate> candidateLazyModelForPlaced; 

	@ManagedProperty(value="#{employeeService}")
	private EmployeeService employeeService;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@ManagedProperty("#{candidateSkillsService}")
	private CandidateSkillsService candidateSkillsService;

	@ManagedProperty("#{candidateEducationCertificationService}")
	private CandidateEducationCertificationService candidateEducationCertificationService;

	@ManagedProperty("#{candidateService}")
	private CandidateService candidateService;

	@ManagedProperty("#{candidateCheckListService}")
	private CandidateCheckListService candidateCheckListService;


	public void hotlistCandidate(){
		action="HOTLISTCANDIDATE"; 
		candidateLazyModel = new LazyCandidateDataModelForSales(candidateService);

	}


	public void onRowSelect(SelectEvent event){
		action = "CANDIDATEPROFILE";
		candidateSkills=candidateSkillsService.getCandidateSkills(selectedCandidate.getCandidateId());
		educationCertificateList=candidateEducationCertificationService.getCandidateEducationDetails(selectedCandidate.getCandidateId());
		Candidate candidate = candidateService.getCandidateForDocuments(selectedCandidate.getCandidateId());
		if(candidate != null){
			documents = candidate.getDocumentses();
		}
		CandidateCheckList checkList = candidateCheckListService.getDocuments(selectedCandidate.getCandidateId(),"Resume Preparation");
		if(checkList.getDocumentses().size()>0){
			offerletterModel = new OfferletterModel();
			offerletterModel.setFileName(checkList.getDocumentses().get(0).getFilename());
			offerletterModel.setFilePath(checkList.getDocumentses().get(0).getFilepath());
		}else{
			offerletterModel.setFileName("File Not Found");
		}
	}



	public void downloadFile() throws IOException 
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String path = params.get("filePath");
		FacesContext context = FacesContext.getCurrentInstance();  
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();  
		File file = new File(path);  
		if (!file.exists()) {  
			response.sendError(HttpServletResponse.SC_NOT_FOUND);  
			return;  
		}  
		response.reset();  
		//response.setBufferSize(6990);  
		response.setContentType("application/pdf");  
		response.setHeader("Content-Length", String.valueOf(file.length()));  
		response.setHeader("Content-Disposition", "attachment;filename=\""  
				+ file.getName() + "\"");  
		BufferedInputStream input = null;  
		BufferedOutputStream output = null;  
		try {  
			input = new BufferedInputStream(new FileInputStream(file),6990);  
			output = new BufferedOutputStream(response.getOutputStream(),6990);  
			byte[] buffer = new byte[6990];  
			int length;  
			while ((length = input.read(buffer)) > 0) {  
				output.write(buffer, 0, length);  
			}  
		} finally {  
			input.close();  
			output.close();  
		}  
		context.responseComplete();  
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


	public void placedCandidate(){
		action="PLACEDCANDIDATE"; 
		candidateLazyModelForPlaced = new LazyCandidateDataModelForPlaced(candidateService);
	}


	public void candidateAssignToPlaced(){
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
			String canId = params.get("candidateId");
			candidateService.updateCandidate(Integer.valueOf(canId));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Candidate Successfully Placed"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail", "Candidate Not Placed"));
		}
	}


	// setter and getter

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public Candidate getSelectedCandidate() {
		return selectedCandidate;
	}



	public void setSelectedCandidate(Candidate selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}


	public List<CandidateSkills> getCandidateSkills() {
		return candidateSkills;
	}


	public void setCandidateSkills(List<CandidateSkills> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}


	public List<EducationCertification> getEducationCertificateList() {
		return educationCertificateList;
	}


	public void setEducationCertificateList(List<EducationCertification> educationCertificateList) {
		this.educationCertificateList = educationCertificateList;
	}


	public List<Documents> getDocuments() {
		return documents;
	}


	public void setDocuments(List<Documents> documents) {
		this.documents = documents;
	}


	public CandidateSkillsService getCandidateSkillsService() {
		return candidateSkillsService;
	}


	public void setCandidateSkillsService(CandidateSkillsService candidateSkillsService) {
		this.candidateSkillsService = candidateSkillsService;
	}


	public CandidateEducationCertificationService getCandidateEducationCertificationService() {
		return candidateEducationCertificationService;
	}


	public void setCandidateEducationCertificationService(
			CandidateEducationCertificationService candidateEducationCertificationService) {
		this.candidateEducationCertificationService = candidateEducationCertificationService;
	}


	public CandidateService getCandidateService() {
		return candidateService;
	}


	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}


	public OfferletterModel getOfferletterModel() {
		return offerletterModel;
	}


	public void setOfferletterModel(OfferletterModel offerletterModel) {
		this.offerletterModel = offerletterModel;
	}


	public CandidateCheckListService getCandidateCheckListService() {
		return candidateCheckListService;
	}


	public void setCandidateCheckListService(CandidateCheckListService candidateCheckListService) {
		this.candidateCheckListService = candidateCheckListService;
	}


	public LazyDataModel<Candidate> getCandidateLazyModel() {
		return candidateLazyModel;
	}


	public void setCandidateLazyModel(LazyDataModel<Candidate> candidateLazyModel) {
		this.candidateLazyModel = candidateLazyModel;
	}


	public LazyDataModel<Candidate> getCandidateLazyModelForPlaced() {
		return candidateLazyModelForPlaced;
	}


	public void setCandidateLazyModelForPlaced(LazyDataModel<Candidate> candidateLazyModelForPlaced) {
		this.candidateLazyModelForPlaced = candidateLazyModelForPlaced;
	}




}