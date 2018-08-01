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
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.ProgrammeSchedule;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;
import com.ayantsoft.trms.jsf.model.ProgrameScheduleModel;

@ManagedBean
@ApplicationScoped
public class ProgrameSheduleDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2142013875432470036L;


	public void save(ProgrammeSchedule programmeSchedule)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(programmeSchedule);
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




	public Object[] programeSheduleFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(ProgrammeSchedule.class,"programmeSchedule");
			criteria.createAlias("programmeSchedule.candidateCourse","candidateCourse");
			criteria.createAlias("programmeSchedule.trainer","trainer");

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("trainer.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("startDate")|| k.equals("endDate")|| k.equals("startTime")|| k.equals("endTime")){
						criteria.add(Restrictions.eq(k,v));
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
			List<ProgrammeSchedule> programmeSchedules = criteria.list();
			resultWithCount[1] = programmeSchedules;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}
	
	
	public List<ProgrameScheduleModel> findProgrameScheduleForAssign(){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProgrameScheduleModel> psm =null;
		try{
			String hql = "select p as programeSchedule from ProgrammeSchedule p join fetch p.candidateCourse c "
					+ " join fetch p.trainer t left join fetch p.candidates cans where p.isComplete ='NO'";
			Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(ProgrameScheduleModel.class));
			psm=query.list();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}
		return psm;
	}




	public ProgrammeSchedule findProgrameScheduleById(Integer id){

		ProgrammeSchedule results = null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(ProgrammeSchedule.class,"p");
			criteria.createAlias("p.candidateCourse", "candidateCourse");
			criteria.createAlias("p.trainer", "trainer");
			criteria.add(Restrictions.eq("p.id",id));
			results= (ProgrammeSchedule) criteria.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return results;
	}



	public int findTotalCandidate(Integer programmeScheduleId)
	{
		Session session=HibernateUtil.getSessionFactory().openSession();
		int totalCandidate = 0;
		try{
			String hql = "from ProgrammeSchedule p join fetch p.candidates where id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", programmeScheduleId);
			ProgrammeSchedule programmeSchedule = (ProgrammeSchedule) query.uniqueResult();
			if(programmeSchedule != null){
				totalCandidate = programmeSchedule.getCandidates().size();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}

		return totalCandidate;

	}



	public List<ProgrameScheduleModel> findProgrameScheduleByTrainerId(Integer trainerId,String modeOfPayment)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProgrameScheduleModel> psm =null;
		try{
			String hql = "select p as programeSchedule from ProgrammeSchedule p join fetch p.candidateCourse c "
					+ " join fetch p.trainer t where t.id = :trainerId and t.modeOfPayment = :modeOfPayment ";
			Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(ProgrameScheduleModel.class));
			query.setParameter("trainerId", trainerId);
			query.setParameter("modeOfPayment", modeOfPayment);
			psm=query.list();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return psm;
	}

	
	
	public ProgrammeSchedule findCandidatesByBatchId(Integer id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		ProgrammeSchedule ps =null;
		try{
			String hql = "from ProgrammeSchedule p left join fetch p.candidates can where p.id = :pid";
			Query query = session.createQuery(hql);
			query.setParameter("pid", id);
			ps=(ProgrammeSchedule) query.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return ps;
	}
	
	
	
	public ProgrammeSchedule findCandidates(Integer batchId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		ProgrammeSchedule ps =null;
		try{
			String hql = "from ProgrammeSchedule p join fetch p.candidates can join fetch can.candidateCourse course join fetch can.contactAddress address where p.id = :pid";
			Query query = session.createQuery(hql);
			query.setParameter("pid", batchId);
			ps=(ProgrammeSchedule) query.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return ps;
	}
	
	
	public void deleteBatch(ProgrammeSchedule programmeSchedule ){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.delete(programmeSchedule);
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}
	}
	
	
	
	
	

}