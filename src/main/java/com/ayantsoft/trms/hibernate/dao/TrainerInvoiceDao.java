package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.TrainerInvoice;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class TrainerInvoiceDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 28489201584643341L;
	
	
	public void save(TrainerInvoice trainerInvoice)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(trainerInvoice);
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save Exception");
		}finally{
			session.close();
		}
	}
	
	
	public Long findTotalAmount(Integer programeScheduleId,Integer trainerId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Long totalAmount = null;
		try
		{
			String hql="select sum(ti.amount) from TrainerInvoice ti where ti.programmeSchedule.id = :id and ti.trainer.id = :tId";
			Query query = session.createQuery(hql);
			query.setParameter("id", programeScheduleId);
			query.setParameter("tId", trainerId);
			totalAmount = (Long) query.uniqueResult();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		
		return totalAmount;
	}
	
	
	public boolean findInvoiceForMonthly(Integer trainerId,String month,String year)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		String results = null;
		try
		{
			String hql="select ti.payMonth from TrainerInvoice ti where ti.trainer.id is not null and ti.trainer.id = :id and ti.payMonth = :payMonth and ti.payYear = :payYear";
			Query query = session.createQuery(hql);
			query.setParameter("id", trainerId);
			query.setParameter("payMonth", month);
			query.setParameter("payYear", year);
			results = (String) query.uniqueResult();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		
		if(results != null){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public Object[] trainerInvoiceFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(TrainerInvoice.class,"trainerInvoice");
			criteria.createAlias("trainerInvoice.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("trainerInvoice.programmeSchedule","programmeSchedule",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("trainerInvoice.trainer","trainer",JoinType.LEFT_OUTER_JOIN);

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("trainerInvoiceId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("programmeSchedule.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("amount")){
						criteria.add(Restrictions.eq(k, Long.parseLong((String)v)));
					}else if(k.equals("invoiceDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("noOfDays")){
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
			List<TrainerInvoice> trainerInvoices = criteria.list();
			resultWithCount[1]=trainerInvoices;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}
	
	
	public List<TrainerInvoice> findTrainerInvoiceByTrainerId(Integer trainerId,Integer programeScheduleId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<TrainerInvoice> results = null;
		try
		{
			String hql="from TrainerInvoice ti join fetch ti.candidateCourse course join fetch ti.programmeSchedule p join fetch ti.trainer t where t.id = :tId and p.id = :pId";
			Query query = session.createQuery(hql);
			query.setParameter("tId", trainerId);
			query.setParameter("pId", programeScheduleId);
			results = query.list();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return results; 
	}
	
	
	
	

}