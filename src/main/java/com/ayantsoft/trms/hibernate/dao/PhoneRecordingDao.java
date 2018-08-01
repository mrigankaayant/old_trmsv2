package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import com.ayantsoft.trms.hibernate.pojo.PhoneRecording;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class PhoneRecordingDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 3155233031449927126L;
	
	
	public Object[] callLogFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters,boolean isAdmin,String employeeName){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(PhoneRecording.class,"phoneRecording");
			
			if(!isAdmin){
				Criterion rest1 = Restrictions.eq("phoneRecording.employeeName",employeeName);
				Criterion rest2 = Restrictions.eq("phoneRecording.managerName",employeeName);
				criteria.add(Restrictions.or(rest1,rest2));
			}
			
			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("id")|| k.equals("callDuration")||k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else{
						criteria.add(Restrictions.ilike(k, (String)v, MatchMode.ANYWHERE));
					}
				});
			}
			criteria.setProjection(Projections.rowCount());
			Long resultCount = (Long)criteria.uniqueResult();
			resultWithCount[0]=resultCount;
			criteria.setProjection(null);

			if(sortField != null){
				if(SortOrder.ASCENDING == sortOrder){
					criteria.addOrder(Order.asc(sortField));
				}else if(SortOrder.DESCENDING == sortOrder){
					criteria.addOrder(Order.desc(sortField));
				}
			}

			criteria.setFirstResult(first);
			criteria.setMaxResults(pageSize);

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<PhoneRecording> phoneRecordings = criteria.list();
			resultWithCount[1]=phoneRecordings;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}
	
	
	
	public Object[] callLogFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters,boolean isAdmin,String employeeName,String startDate,String endDate){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(PhoneRecording.class,"phoneRecording");
			
			if(!isAdmin){
				Criterion rest1 = Restrictions.eq("phoneRecording.employeeName",employeeName);
				Criterion rest2 = Restrictions.eq("phoneRecording.managerName",employeeName);
				Criterion rest3 = Restrictions.or(rest1,rest2);
				Criterion rest4 = Restrictions.between("phoneRecording.callingDate",startDate,endDate);
				criteria.add(Restrictions.and(rest3,rest4));
			}else{
				criteria.add(Restrictions.between("phoneRecording.callingDate",startDate,endDate));
			}
			
			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("id")|| k.equals("callDuration")||k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else{
						criteria.add(Restrictions.ilike(k, (String)v, MatchMode.ANYWHERE));
					}
				});
			}
			criteria.setProjection(Projections.rowCount());
			Long resultCount = (Long)criteria.uniqueResult();
			resultWithCount[0]=resultCount;
			criteria.setProjection(null);

			if(sortField != null){
				if(SortOrder.ASCENDING == sortOrder){
					criteria.addOrder(Order.asc(sortField));
				}else if(SortOrder.DESCENDING == sortOrder){
					criteria.addOrder(Order.desc(sortField));
				}
			}

			criteria.setFirstResult(first);
			criteria.setMaxResults(pageSize);

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<PhoneRecording> phoneRecordings = criteria.list();
			resultWithCount[1]=phoneRecordings;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}

}
