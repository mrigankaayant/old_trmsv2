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

import com.ayantsoft.trms.hibernate.pojo.Trainer;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class TrainerDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -7077336393402385695L;
	
	
	public void save(Trainer trainer)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(trainer.getContactAddress());
			session.saveOrUpdate(trainer);
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
	
	
	
	
	public Object[] trainerFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Trainer.class,"trainer");
			criteria.createAlias("trainer.candidateCourse","candidateCourse");
			criteria.createAlias("trainer.contactAddress","contactAddress");
			
			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateCourse.id")){
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
			List<Trainer> trainers = criteria.list();
			resultWithCount[1]=trainers;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}
	
	
	public List<Trainer> getTrainers(){

		List<Trainer>results = null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(Trainer.class,"t");
			criteria.createAlias("t.candidateCourse", "candidateCourse");
			criteria.createAlias("t.contactAddress", "contactAddress");
			results= criteria.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return results;
	}
	
	
	
	
	public List<Trainer> getTrainersForGenerateInvoice(){

		List<Trainer> results = null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			String hql = "from Trainer t join fetch t.candidateCourse course join fetch t.contactAddress address where t.active=true";
			Query query = session.createQuery(hql);
			results= query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return results;
	}
	
	
	public Trainer findTrainerById(Integer trainerId)
	{
		Trainer trainer = null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			String hql = "from Trainer t join fetch t.candidateCourse where t.id = :tId";
			Query query = session.createQuery(hql);
			query.setParameter("tId", trainerId);
			trainer= (Trainer) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return trainer;
	}
	
	
	
	
	public boolean checkWorkMobile(String mobile, Integer trainerId)
	{
		mobile=mobile.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try
		{
			String hql="select count(*) from Trainer t right join t.contactAddress con where (";
			if(trainerId != null){
				hql = hql + "t.id != :tId";
			}else{
				hql = hql + "t.id is not null";
			}

			hql = hql + " or t.id = null) and (con.workMobile = :workmobile or con.homeMobile= :homemobile) ";
			
			Query query = session.createQuery(hql);
			if(trainerId != null){
				query.setParameter("tId", trainerId);
			}

			query.setString("workmobile", mobile);
			query.setString("homemobile", mobile);
			
			resultCount = (Long)query.uniqueResult();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		if(resultCount==0){
			return true;
		}
		return false;
	}

	
	public boolean checkUniqueEmail(String email, Integer trainerId){
		email=email.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try
		{
			String hql="select count(*) from Trainer t right join t.contactAddress con where (";
			if(trainerId != null){
				hql = hql + "t.id != :tId";
			}else{
				hql = hql + "t.id is not null";
			}

			hql = hql + " or t.id = null) and (con.email = :email or con.alternateEmail= :alternateEmail) ";
			
			Query query = session.createQuery(hql);
			if(trainerId != null){
				query.setParameter("tId",trainerId);
			}

			query.setString("email", email);
			query.setString("alternateEmail", email);
			
			resultCount = (Long)query.uniqueResult();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		if(resultCount==0){
			return true;
		}
		return false;
	}
	
	
	public List<Trainer> findTrainerByCourseId(Integer courseId){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Trainer> trainers = null;
		try
		{

			String hql = "from Trainer t join fetch t.candidateCourse course where course.id = :courseId and t.active=true";
			Query query = session.createQuery(hql);
			query.setParameter("courseId", courseId);
			trainers = query.list();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return trainers;
		
	}
	


}