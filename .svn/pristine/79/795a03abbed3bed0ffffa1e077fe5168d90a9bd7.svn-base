package com.ayantsoft.trms.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.CandidateCheckList;
import com.ayantsoft.trms.hibernate.pojo.Documents;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CandidateCheckListDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2314697417701706883L;

	public void save(CandidateCheckList candidateCheckList,String fileName,String filePath)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			if(filePath != null){
				Documents documents = new Documents();
				documents.setDescription("Enrollment form");
				documents.setFilename(fileName);
				documents.setFilepath(filePath);
				documents.setCreated(new Date());
				session.saveOrUpdate(documents);
				List<Documents> docs = new ArrayList<Documents>();
				docs.add(documents);
				candidateCheckList.setDocumentses(docs);
			}
			session.saveOrUpdate(candidateCheckList);
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



	public Object[] candidateCheckListFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters,String checkListTypeName){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{

			Criteria criteria = session.createCriteria(CandidateCheckList.class,"candidateCheckList")
					.createAlias("candidateCheckList.candidate","candidate")
					.createAlias("candidate.candidateCourse","candidateCourse")
					.createAlias("candidate.employee","employee")
					.createAlias("candidate.userProfile","userProfile", JoinType.LEFT_OUTER_JOIN)
					.createAlias("candidate.contactAddress","contactAddress")
					.createAlias("candidateCheckList.checkListType","checkListType")
					.createAlias("candidate.programmeSchedules","programmeSchedules", JoinType.LEFT_OUTER_JOIN)
					.createAlias("programmeSchedules.trainer","trainer", JoinType.LEFT_OUTER_JOIN);
			
			Criterion c1 = Restrictions.eq("checkListType.checkListTypeName", checkListTypeName);
			Criterion c2 = Restrictions.isNotNull("candidateCheckList.checkListStatus");
			Criterion c3 = Restrictions.ne("candidateCheckList.checkListStatus","Completed");
			
			Criterion c4 = Restrictions.ne("candidate.candidateStatus","On Terminated");
			Criterion c5 = Restrictions.isNull("candidate.candidateStatus");
			
			Criterion c6 = Restrictions.and(c1,c2);
			Criterion c7 = Restrictions.and(c6,c3);
			
			Criterion c8 = Restrictions.or(c4,c5);
			
			criteria.add(Restrictions.and(c7, c8));
			
			

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidate.candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidate.candidateCourse.course")){
						criteria.add(Restrictions.eq("candidateCourse.id", Integer.parseInt((String)v)));
					}else if(k.equals("candidate.contactAddress.email")){
						criteria.add(Restrictions.ilike("contactAddress.email", (String)v, MatchMode.ANYWHERE));
					}else if(k.equals("candidate.contactAddress.workMobile")){
						criteria.add(Restrictions.ilike("contactAddress.workMobile", (String)v, MatchMode.ANYWHERE));
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
			List<CandidateCheckList> candidateCheckLists = criteria.list();
			resultWithCount[1]=candidateCheckLists;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}




	public List<CandidateCheckList> findCheckListByCandidateId(Integer candidateId)
	{

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidateCheckList> candidateCheckLists = null;
		try
		{
			String hql = "from CandidateCheckList c join fetch c.checkListType join fetch c.candidate can left join fetch c.documentses where can.candidateId = :canId";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			candidateCheckLists = query.list();

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}

		return candidateCheckLists;

	}


	public boolean enrollmentFormStatusCheck(Integer candidateId,String checkListType)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CandidateCheckList candidateCheckList = null;
		try
		{
			Criteria criteria = session.createCriteria(CandidateCheckList.class,"candidateCheckList");
			criteria.createAlias("candidateCheckList.checkListType", "ch");
			criteria.createAlias("candidateCheckList.candidate", "can");

			Criterion rest1 = Restrictions.eq("can.candidateId",candidateId);
			Criterion rest2 = Restrictions.eq("ch.checkListTypeName",checkListType);

			criteria.add(Restrictions.and(rest1,rest2));
			candidateCheckList = (CandidateCheckList) criteria.uniqueResult();

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}

		if(candidateCheckList != null){
			return true;
		}else{
			return false;
		}
	}



	public void saveCandidateCheckListForAssign(CandidateCheckList candidateCheckList)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(candidateCheckList);
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

	public CandidateCheckList findDocuments(Integer candidateId,String checkListName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		CandidateCheckList candidateCheckList = null;
		try
		{
			String hql = "from CandidateCheckList c left join fetch c.documentses where c.candidate.candidateId = :canId and c.checkListType.checkListTypeName= :checkListName";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			query.setParameter("checkListName",checkListName);
			candidateCheckList = (CandidateCheckList) query.uniqueResult();

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}

		return candidateCheckList;
	}


	public void updateCandidateCheckListbyId(Integer id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			String sQuery = "UPDATE CandidateCheckList checkList SET checkList.checkListStatus = :status WHERE checkList.id = :id";
			Query query= session.createQuery(sQuery );
			query.setParameter("status", null);
			query.setParameter("id", id);
			query.executeUpdate();
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
	
	
	
	public String findMailContent(Integer checkListId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		String message = null;
		try
		{
			session.beginTransaction();
			String hql = "SELECT check.mailDetails FROM CandidateCheckList check WHERE check.id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id",checkListId);
			message = (String) query.uniqueResult();
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return message;
	}
}