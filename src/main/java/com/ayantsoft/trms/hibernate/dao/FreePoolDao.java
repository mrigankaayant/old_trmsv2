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
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.Freepool;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class FreePoolDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -2220378045292676909L;

	
	public Object[] freePoolCandidatesFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		
		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Freepool.class,"freepool");

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("id") || k.equals("candidateId") || k.equals("recruiterId")){
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
			List<Freepool> candidates = criteria.list();
			resultWithCount[1]=candidates;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
		
	}
	
	
	
	public void delete(int id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			String hql = "DELETE FROM Freepool f WHERE f.candidateId = :cid ";
			Query query = session.createQuery(hql);
			query.setParameter("cid",id);
			query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data delete Exception");
		}finally{
			session.close();
		}
	}
	
	
	public Freepool findFreepoolByCanId(int candidateId){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Freepool freepool = null;
		try
		{
			Criteria criteria = session.createCriteria(Freepool.class,"f");
			criteria.add(Restrictions.eq("f.candidateId",candidateId));
			freepool = (Freepool) criteria.uniqueResult();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data delete Exception");
		}finally{
			session.close();
		}
		
		return freepool;
		
	}
	
	
	
	
	
}
