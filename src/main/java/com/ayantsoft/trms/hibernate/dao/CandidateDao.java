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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.hibernate.pojo.FollowUp;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;
import com.ayantsoft.trms.jsf.model.AssignCandidateDto;
import com.ayantsoft.trms.jsf.model.CandidatePaymentModel;

@ManagedBean(eager=true)
@ApplicationScoped
public class CandidateDao implements Serializable {

	//this class taking care for candidate related data saving
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -696031008173174687L;

	/**
	 * saving candidate
	 */

	public void updateCandidate(Candidate candidate, FollowUp followUp, CandidatePayment  candidatePayment){

		if(candidate.getCandidateCourse().getId() == null || candidate.getCandidateCourse().getId() <=0){
			candidate.setCandidateCourse(null);
		}

		candidate.getContactAddress().setEmail(candidate.getContactAddress().getEmail().trim());
		candidate.getContactAddress().setAlternateEmail(candidate.getContactAddress().getAlternateEmail().trim());
		candidate.getContactAddress().setWorkMobile(candidate.getContactAddress().getWorkMobile().trim());
		candidate.getContactAddress().setHomeMobile(candidate.getContactAddress().getHomeMobile().trim());
		//candidate.setEntryDate(new Date());

		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			session.beginTransaction();
			session.saveOrUpdate(candidate.getContactAddress());
			session.saveOrUpdate(candidate);

			if(followUp!=null){
				followUp.setFollowUpDate(new Date());
				followUp.setCandidate(candidate);
				followUp.setNextFollowUpDate(candidate.getNextFollowUpDate());
				session.saveOrUpdate(followUp);
			}

			if(candidatePayment != null){
				candidatePayment.setCandidate(candidate);
				session.saveOrUpdate(candidatePayment);
			}
			List<CandidatePayment> candidatePayments=new ArrayList<CandidatePayment>();
			candidatePayments.add(candidatePayment);
			candidate.setCandidatePayments(candidatePayments);
			session.getTransaction().commit();

		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save Exception");
		}finally{
			session.close();
		}
	}

	/**
	 * return candidate for updation form
	 */
	public Candidate getCandidate(Integer candidateId){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Candidate candidate=null;

		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateRemarks","candidateRemarks");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("candidate.employees","emps",JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("candidate.candidateId",candidateId));

			candidate =(Candidate) criteria.uniqueResult();

		}catch(Exception e){

			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		return candidate;
	}


	public List<Candidate> findCandidateSearchWithPayment(String candidateSearchBy,Object candidateSearchValue){

		Session session=HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates=null;
		Query query = null;
		try
		{			

			if(candidateSearchBy.equals("candidateId"))
			{
				String hql = "from Candidate can join fetch can.contactAddress contact join fetch can.employee emp join fetch can.candidatePayments where can.candidateId = :id group by can.candidateId";
				query = session.createQuery(hql);
				query.setParameter("id",Integer.parseInt((String) candidateSearchValue));
			}else if(candidateSearchBy.equals("name"))
			{	
				String hql = "from Candidate can join fetch can.contactAddress contact join fetch can.employee emp join fetch can.candidatePayments where can.firstName like :name group by can.candidateId";
				query = session.createQuery(hql);
				query.setParameter("name", "%" + (String) candidateSearchValue + "%");


			}else if(candidateSearchBy.equals("email"))
			{
				String hql = "from Candidate can join fetch can.contactAddress contact join fetch can.employee emp join fetch can.candidatePayments where contact.email like :email group by can.candidateId";
				query = session.createQuery(hql);
				query.setParameter("email", "%" + (String) candidateSearchValue + "%");
			}else if(candidateSearchBy.equals("skill"))
			{
				String hql = "from Candidate can join fetch can.contactAddress contact join fetch can.employee emp join fetch can.candidatePayments where can.techSkills like :skill group by can.candidateId";
				query = session.createQuery(hql);
				query.setParameter("skill", "%" + (String) candidateSearchValue + "%");
			}else
			{
				String hql = "from Candidate can join fetch can.contactAddress contact join fetch can.employee emp join fetch can.candidatePayments where contact.workMobile like :mobileNo group by can.candidateId";
				query = session.createQuery(hql);
				query.setParameter("mobileNo", "%" + (String) candidateSearchValue + "%");
			}

			candidates = query.list();
		}catch(Exception e)
		{	
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return candidates;
	}


	/**
	 * return lazy loading of candidate for search button
	 */
	public Object[] candidateFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters,Integer empId,boolean isAdmin){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		List<Employee>empList=new ArrayList<Employee>();
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateRemarks","candidateRemarks");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			if(!isAdmin){
				String hql="select emp.name from Employee as emp where emp.employee.empId=null and emp.empId =:EmpId";
				Query query = session.createQuery(hql);
				query.setParameter("EmpId", empId);
				empList=query.list();
				if(!empList.isEmpty()){
					Criterion rest1=Restrictions.eq("employee.employee.empId",empId);
					Criterion rest2=Restrictions.eq("employee.empId",empId);
					criteria.add(Restrictions.or(rest1,rest2));
				}else{
					criteria.add(Restrictions.eq("employee.empId",empId));
				}
			}

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")||k.equals("immigrationStartDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
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
			List<Candidate> candidates = criteria.list();
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




	public Object[] candidateFilterForEnrolled(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		List<Employee>empList=new ArrayList<Employee>();
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateRemarks","candidateRemarks");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
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
			List<Candidate> candidates = criteria.list();
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


	/**
	 * @param email
	 * @param candidateId
	 * @return
	 * if email is unique return true else false
	 */
	public boolean checkUniqueEmail(String email, Integer candidateId){
		email=email.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try
		{
			String hql="select count(*) from Candidate c right join c.contactAddress con where (";
			if(candidateId != null){
				hql = hql + "c.candidateId != :candidateId";
			}else{
				hql = hql + "c.candidateId is not null";
			}

			hql = hql + " or c.candidateId = null) and (con.email = :email or con.alternateEmail= :alternateEmail) ";

			Query query = session.createQuery(hql);
			if(candidateId != null){
				query.setParameter("candidateId", candidateId);
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




	public boolean checkUniqueMobile(String mobile, Integer candidateId){
		mobile=mobile.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try
		{
			String hql="select count(*) from Candidate c right join c.contactAddress con where (";
			if(candidateId != null){
				hql = hql + "c.candidateId != :candidateId";
			}else{
				hql = hql + "c.candidateId is not null";
			}

			hql = hql + " or c.candidateId = null) and (con.workMobile = :workmobile or con.homeMobile= :homemobile) ";

			Query query = session.createQuery(hql);
			if(candidateId != null){
				query.setParameter("candidateId", candidateId);
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




	public boolean getPaymentCount(Integer candidateId){

		Session session=HibernateUtil.getSessionFactory().openSession();
		List<CandidatePayment> candidatePayment=null;
		boolean isPaymentDone;
		try
		{
			Criteria criteria = session.createCriteria(CandidatePayment.class,"candidatePayment");
			criteria.add(Restrictions.eq("candidatePayment.candidate.candidateId",candidateId));

			candidatePayment=criteria.list();
			if(candidatePayment.size()>0){
				isPaymentDone=true;
			}else{
				isPaymentDone=false;
			}
		}catch(Exception e){

			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		return isPaymentDone;
	}



	public void save(Candidate candidate)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(candidate.getContactAddress());
			session.saveOrUpdate(candidate);
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


	public Candidate candidateForDocuments(Integer candidateId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Candidate candidate = null;
		try{
			String hql="from Candidate can join fetch can.documentses as docs where can.candidateId =:canId";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			candidate = (Candidate) query.uniqueResult();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}

		return candidate;
	}



	public List<Candidate> findCandidatesForEnrolled(){

		List<Candidate> results = null;
		Session session=HibernateUtil.getSessionFactory().openSession();

		try{
			Criteria criteria = session.createCriteria(Candidate.class,"c");
			criteria.createAlias("c.candidateRemarks", "candidateRemarks");
			criteria.createAlias("c.contactAddress", "contactAddress");
			criteria.createAlias("c.candidateCourse", "candidateCourse");
			criteria.add(Restrictions.eq("candidateRemarks.statusType","Enrolled"));
			results= criteria.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return results;
	}


	public List<Candidate> findCandidateByStatus(Integer recruiterId,boolean isAdmin,String candidateStatus){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates = null;
		try{
			String hql="from Candidate can join fetch can.candidateCourse course join fetch can.contactAddress address join fetch can.employee emp where "
					+ " can.candidateStatus = :status";
			
			if(!isAdmin){
				hql = hql + " and emp.empId= :employeeId";
			}
			Query query = session.createQuery(hql);
			query.setParameter("status", candidateStatus);
			if(!isAdmin){
				query.setParameter("employeeId", recruiterId);
			}
			candidates = query.list();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return candidates;
	}


	public Object[] candidateFilterForReport(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters,Integer empId,boolean isAdmin,boolean isRecruiter,Date startDate,Date endDate){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		List<Employee>empList=new ArrayList<Employee>();
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateRemarks","candidateRemarks");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			if(isRecruiter && !isAdmin){
				String hql="select emp.name from Employee as emp where emp.employee.empId=null and emp.empId =:EmpId";
				Query query = session.createQuery(hql);
				query.setParameter("EmpId", empId);
				empList=query.list();
				if(!empList.isEmpty()){
					Criterion rest1=Restrictions.eq("employee.employee.empId",empId);
					Criterion rest2=Restrictions.eq("employee.empId",empId);
					Criterion rest3=Restrictions.or(rest1,rest2);
					Criterion rest4 = Restrictions.between("candidate.entryDate", startDate, endDate);
					criteria.add(Restrictions.and(rest3,rest4));
				}else{
					Criterion rest1 = Restrictions.eq("employee.empId",empId);
					Criterion rest2 = Restrictions.between("candidate.entryDate", startDate, endDate);
					criteria.add(Restrictions.and(rest1,rest2));
				}
			}else{
				criteria.add(Restrictions.between("candidate.entryDate", startDate, endDate));
			}

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")||k.equals("immigrationStartDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
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
			List<Candidate> candidates = criteria.list();
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




	public Candidate findCandidateCheckByCandidateId(Integer candidateId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Candidate candidate = null;
		try{
			String hql="from Candidate c join fetch c.candidateCheckLists clist join fetch clist.checkListType type where c.candidateId = :candidateId";
			Query query = session.createQuery(hql);
			query.setParameter("candidateId", candidateId);
			candidate = (Candidate) query.uniqueResult();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return candidate;
	}




	public Object[] candidateFilterForHr(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateRemarks","candidateRemarks");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);

			Criterion c1 = Restrictions.eq("candidateRemarks.statusType","Enrolled");
			Criterion c2 = Restrictions.isNull("candidateStatus");
			criteria.add(Restrictions.and(c1, c2));

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")||k.equals("immigrationStartDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
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
			List<Candidate> candidates = criteria.list();
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




	public Object[] candidateFilterForSales(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateRemarks","candidateRemarks");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("candidate.employees","emps",JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("candidate.candidateStatus", "On Hotlist"));

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")||k.equals("immigrationStartDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("billRate")){
						criteria.add(Restrictions.eq(k, Long.parseLong((String)v)));
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
			List<Candidate> candidates = criteria.list();
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




	public Object[] candidateFilterForPlaced(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("candidate.candidateStatus", "Placed"));

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")||k.equals("immigrationStartDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("billRate")){
						criteria.add(Restrictions.eq(k, Long.parseLong((String)v)));
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
			List<Candidate> candidates = criteria.list();
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



	/*public List<Candidate> findAssignCandidate(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates = null;
		try{
			String hql="from Candidate c join fetch c.contactAddress add join fetch c.candidateCourse course join fetch c.employee join fetch c.programmeSchedules ps";
			Query query = session.createQuery(hql);
			candidates = query.list();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return candidates;
	}*/



	public Object[] candidateFilterForRemove(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.programmeSchedules","ps");
			/*criteria.createAlias("ps.candidateCourse","course");
			criteria.createAlias("ps.trainer","trainer");*/
			criteria.add(Restrictions.eq("ps.isComplete", "NO"));

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("candidateId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("nextFollowUpDate")|| k.equals("entryDate")||k.equals("immigrationStartDate")){
						criteria.add(Restrictions.eq(k,v));
					}else if(k.equals("candidateCourse.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("candidateRemarks.id")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("billRate")){
						criteria.add(Restrictions.eq(k, Long.parseLong((String)v)));
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
			List<Candidate> candidates = criteria.list();
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




	public Candidate findBatchByCandidateId(Integer candidateId){

		Session session = HibernateUtil.getSessionFactory().openSession();
		Candidate candidate = null;
		try{
			String hql="from Candidate c join fetch c.contactAddress contactAddress join fetch c.programmeSchedules ps join fetch ps.candidateCourse course join fetch ps.trainer t where c.candidateId = :canId";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			candidate = (Candidate) query.uniqueResult();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access Exception");
		}finally{
			session.close();
		}
		return candidate;
	}



	public List<Object> candidateOfExRecruiters(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Object> obj = null;
		try{
			String sql = "SELECT "
					+"candi.candidate_id as CandidateId, "
					+"candi.current_location as Current_loc, "
					+"candi.first_name as CandiateFirstName, "
					+"contactadd.email as Email, "
					+"contactadd.work_mobile as CandidateMobile, "
					+"candicourse.course as CourseName, "
					+"candicourse.id as CourseId, "
					+"remarks.status_type as CandidateStatus, "
					+"remarks.id as RemarksId, "
					+"candi.immigration_type as Visa, "
					+"emp.name as empName, "
					+"emp.emp_id as empId "

                    +"FROM "

                    +"ayant_trmsv2.candidate candi "    
                    +"INNER JOIN ayant_trmsv2.contact_address contactadd ON candi.contact_address_id = contactadd.contact_id "    
                    +"LEFT OUTER JOIN ayant_trmsv2.candidate_course candicourse on candi.tech_skills = candicourse.id "  
                    +"INNER JOIN ayant_trmsv2.candidate_remarks remarks ON candi.candidate_remarks_id = remarks.id "   
                    +"INNER JOIN ayant_trmsv2.employee emp ON candi.recruiter_emp_id = emp.emp_id "   

					+"WHERE " 

			        +"emp.active = false and remarks.status_type not in ('Enrolled')"; 

			SQLQuery query = session.createSQLQuery(sql);
			obj = query.list();

		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close();
		}
		return obj;
	}



	public List<AssignCandidateDto> findAssignCandidates(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AssignCandidateDto> candidates = null;
		try{
			String hql="select "
					+ "c.candidateId as candidateId, "
					+ "c.firstName as candidateName, "
					+ "add.email as candidateEmail, "
					+ "add.workMobile as candidateMobile, "
					+ "c.currentLocation as currentLocation, "
					+ "course.course as candidateCourse, "
					+ "c.candidateStatus as candidateStatus, "
					+ "p.title as batchTitle, "
					+ "p.startDate as startDate, "
					+ "p.endDate as endDate, "
					+ "p.startTime as startTime, "
					+ "p.endTime as endTime, "
					+ "p.isComplete as batchStatus "

					+ "FROM "

					+ "Candidate c "
					+ "INNER JOIN c.contactAddress add "
					+ "INNER JOIN c.employee emp "
					+ "INNER JOIN c.candidateRemarks r "
					+ "LEFT OUTER JOIN c.candidateCourse course "
					+ "INNER JOIN c.programmeSchedules p ";


			Query query = session.createQuery(hql)
					.setResultTransformer(Transformers.aliasToBean(AssignCandidateDto.class));
			candidates=query.list();

		}catch(Exception e){
			e.printStackTrace();
		}
		return candidates;
	}
	//String hql = "update Supplier set name = :newName where name = :name";

	public void updateCandidate(Integer candidateId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			String hql = "update Candidate c set c.candidateStatus = :status where c.candidateId = :id";
			Query query = session.createQuery(hql);
	        query.setString("status", "Placed");
	        query.setParameter("id",candidateId);
	        query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data update Exception");
		}finally{
			session.close();
		}
	}
	
	
	public void updateCandidateSubStatus(Integer candidateId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			String hql = "update Candidate c set c.candidateStatus = :status where c.candidateId = :id";
			Query query = session.createQuery(hql);
	        query.setString("status", "On Terminated");
	        query.setParameter("id",candidateId);
	        query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data update Exception");
		}finally{
			session.close();
		}
	}
	
	
	
	
	public List<Candidate> getCandidateBySearchValue(String searchBy,String searchValue){

		Session session=HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates=null;

		try
		{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAddress","contactAddress");
			criteria.createAlias("candidate.employee","employee");
			criteria.createAlias("candidate.candidateCourse","candidateCourse",JoinType.LEFT_OUTER_JOIN);
			
			if("Name".equals(searchBy)){
				criteria.add(Restrictions.eq("candidate.firstName", searchValue.trim()));
			}
			if("EmailId".equals(searchBy)){
				criteria.add(Restrictions.eq("contactAddress.email", searchValue.trim()));
			}
			if("PhoneNo".equals(searchBy)){
				criteria.add(Restrictions.eq("contactAddress.workMobile", searchValue.trim()));
			}
			
			candidates = criteria.list();

		}catch(Exception e){

			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		return candidates;
	}
	
	
	public void updateCandidateRecruiterId(int recruiterId,int candidateId){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			String hql = "update Candidate c set c.employee.empId = :eId where c.candidateId = :id";
			Query query = session.createQuery(hql);
	        query.setParameter("eId",recruiterId);
	        query.setParameter("id",candidateId);
	        query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data update Exception");
		}finally{
			session.close();
		}
		
	}
	
	
}

