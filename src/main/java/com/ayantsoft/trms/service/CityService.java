package com.ayantsoft.trms.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


import com.ayantsoft.trms.hibernate.dao.CityDao;
import com.ayantsoft.trms.hibernate.pojo.Cities;

@ManagedBean
@ApplicationScoped
public class CityService implements Serializable{
     
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9116031771281973851L;
	
	@ManagedProperty(value="#{cityDao}")
	private  CityDao cityDao ;
	
	public List<String> getCityList(){

		return cityDao.getCitiesByCountry("US");
	}
	
	
	public void saveCity(Cities city){
		cityDao.save(city);
	}
	
	
	
	public CityDao getCityDao() {
		return cityDao;
	}


	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

}