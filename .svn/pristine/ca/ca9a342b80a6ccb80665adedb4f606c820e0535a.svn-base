package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.trms.hibernate.pojo.IncentiveStructure;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class IncentiveStructureDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -6776885506938693680L;
	
	
	
	public List<IncentiveStructure> getIncentiveStructure(String incentiveFor){

		Session session=HibernateUtil.getSessionFactory().openSession();
		List<IncentiveStructure> IncentiveStructures = null;

		try{
			
			Criteria criteria = session.createCriteria(IncentiveStructure.class).addOrder(Order.asc("targetLowerLimit"));
			criteria.add(Restrictions.eq("typeFor",incentiveFor));
			IncentiveStructures = criteria.list();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return IncentiveStructures;
	}

}