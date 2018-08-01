package com.ayantsoft.trms.jsf.view;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.hibernate.pojo.Role;
import com.ayantsoft.trms.hibernate.pojo.UserProfile;
import com.ayantsoft.trms.service.LoginService;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1120096922627058606L;

	private String username;
	private String password;
	private boolean admin;
	private boolean loginCheck;
	private UserProfile userProfile;

	@ManagedProperty("#{loginService}")
	private LoginService loginService;

	public String login(){
		admin = false;

		userProfile = loginService.login(username, password);

		if(userProfile != null){
			if(hasRole("ROLE_ADMIN")){
				admin=true;
			}
		}

		if(userProfile != null){
			loginCheck = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,"Sucess : ", "Welcome, " + userProfile.getUsername()  +"Login Success"));
			if(hasRole("ROLE_RECRUITER")){
				return "/recruitment/recruitment.xhtml?faces-redirect=true&i=0";
			}else if(hasRole("ROLE_ADMIN")){
				return "/admin/admin.xhtml?faces-redirect=true&i=1";
			}else if(hasRole("ROLE_ACCOUNT")){
				return "/account/account.xhtml?faces-redirect=true&i=3";
			}else if(hasRole("ROLE_ACCOUNT")){
				return "/report/report.xhtml?faces-redirect=true&i=4";
			}else if(hasRole("ROLE_HR")){
				return "/trainer/trainer.xhtml?faces-redirect=true&i=5";
			}else if(hasRole("ROLE_HR")){
				return "/hr/hr.xhtml?faces-redirect=true&i=6";
			}else if(hasRole("ROLE_SALES")){
				return "/sales/sales.xhtml?faces-redirect=true&i=7";
			}else{
				return null;
			}
		}else{
			loginCheck = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error:", "Login Failed, Username Or Password Incorrect."));
			return null;
		}
	}

	public boolean hasRole(String role){
		for(Role r: userProfile.getRoles()){
			if(r.getRoleType().equals(role)){
				return true;
			}
		}
		return false;
	}


	public String logout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.clear();
		userProfile = null;
		loginCheck = false;
		return "/login.xhtml?faces-redirect=true";
	}
	
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Employee getEmployee(){
		return userProfile.getEmployee();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public boolean getLoginCheck() {
		return loginCheck;
	}
	public void setLoginCheck(boolean loginCheck) {
		this.loginCheck = loginCheck;
	}
}