package com.ayantsoft.trms.hibernate.dao;
import java.io.Serializable;
import java.math.BigDecimal;
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
import com.ayantsoft.trms.hibernate.pojo.CandidateCourse;
import com.ayantsoft.trms.hibernate.pojo.CandidatePayment;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.hibernate.pojo.FollowUp;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;
import com.ayantsoft.trms.jsf.model.CandidatePaymentModel;
import com.ayantsoft.trms.util.ChartsUtil;


/**
 * @author Somnath Biswas
 *
 *CandidatePaymentDao is implemented to do database operation for CandidatePayment entity
 */

@ManagedBean
@ApplicationScoped
public class CandidatePaymentDao implements Serializable {


	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6080841545715956516L;

	public List<CandidatePaymentModel> showCandidateDue(Integer empId, boolean isRecruiter,boolean isAdmin){

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePaymentModel> cpm=null;
		try{

			String hql="select cp.candidate as candidate, "
					+ "cp.candidate.contactAddress.email as candidateEmail, "
					+ "cp.candidate.contactAddress.workMobile as candidateMobile, "
					+ "cp.candidate.employee.empId as recruiterEmpId, "
					+ "cp.candidate.employee.name as recruiterName, "
					+ "cp.candidate.employee.contactAddress.email as recruiterEmail,"
					+ "cp.candidate.employee.contactAddress.workMobile as recruiterMobile, "
					+ "cp.candidateCourse.course as course, "
					+ "sum(cp.creadit) as totalAmount, "
					+ "sum(cp.debit) as paidAmount, "
					+ "sum(cp.creadit) - sum(cp.debit) as dueAmount "

					+ "FROM CandidatePayment cp ";

			if(isRecruiter && !isAdmin){
				hql = hql + "WHERE cp.candidate.employee.employee.empId = :supervisorEmpId OR cp.candidate.employee.empId = :recruiterEmpId ";
			}

			hql = hql + "GROUP BY "
					+ "cp.candidate, cp.candidate.contactAddress.email, "
					+ "cp.candidate.contactAddress.workMobile, "
					+ "cp.candidate.employee.empId, "
					+ "cp.candidate.employee.name, "
					+ "cp.candidate.employee.contactAddress.email, "
					+ "cp.candidate.employee.contactAddress.workMobile, "
					+ "cp.candidateCourse.course";

			Query query = session.createQuery(hql)
					.setResultTransformer(Transformers.aliasToBean(CandidatePaymentModel.class));

			if(isRecruiter && !isAdmin){
				query.setParameter("supervisorEmpId", empId);
				query.setParameter("recruiterEmpId", empId);
			}

			cpm=query.list();

		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close();
		}
		return cpm;
	}




	public List<CandidatePaymentModel> findCandidatePaymentsForSummaryReport(Date startDate,Date endDate)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePaymentModel> cpm =null;
		try{

			String hql = "select cp.employee.empId as empId,cp.employee.employee.empId as supervisorId,cp.employee.name as empName,sum(cp.debit) as paidAmount from CandidatePayment cp where cp.transactionDate between :stDate AND :edDate group by cp.employee.empId";
			Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(CandidatePaymentModel.class));
			query.setParameter("stDate", startDate);
			query.setParameter("edDate", endDate);
			cpm=query.list();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return cpm;
	}



	public Double findTotalAmountForTeamLeader(Integer supervisorId,Date startDate,Date endDate)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal total = null;
		try{

			String hql = "select sum(cp.debit) from CandidatePayment cp where cp.employee.employee.empId = :empId and cp.txnType is null and cp.transactionDate between :stDate AND :edDate";
			Query query = session.createQuery(hql);
			query.setParameter("empId", supervisorId);
			query.setParameter("stDate", startDate);
			query.setParameter("edDate", endDate);
			total=(BigDecimal) query.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}


		if(total != null){
			return total.doubleValue();
		}else{
			return 0.0;
		}


	}



	public List<CandidatePayment> showCandidateDueDetals(Integer candidateId){

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePayment> candidatePayments = null;
		try{

			String hql ="from CandidatePayment cp join fetch cp.candidateCourse join fetch cp.employee"
					+ " where cp.candidate.candidateId =:canId ";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			candidatePayments=query.list();
		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close();
		}

		return candidatePayments;

	}




	public List<CandidateCourse> findCourcesForPayment(Integer candidateId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidateCourse> courses = null;
		try{
			String hql = "select distinct cp.candidateCourse from CandidatePayment cp where cp.candidate.candidateId= :canId";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			courses = query.list();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally {
			session.close();
		}
		return courses;
	}




	public BigDecimal findTotalDueByCandidateId(String course,Integer candidateId){

		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalDueAmount = null;
		try{
			String hql ="select sum(cp.creadit) - sum(cp.debit) from CandidatePayment cp "
					+ "where cp.candidate.candidateId =:canId and cp.candidateCourse.course =:courseName";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			query.setParameter("courseName", course);
			totalDueAmount=(BigDecimal) query.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close();
		}
		return totalDueAmount;
	}



	public void save(CandidatePayment candidatePayment, Employee accountant)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();

			FollowUp followUp = new FollowUp();
			followUp.setCandidate(candidatePayment.getCandidate());
			followUp.setEmployee(accountant);
			followUp.setFollowUpDate(new Date());
			if(candidatePayment.getDebit() == null || candidatePayment.getDebit().compareTo(BigDecimal.ZERO) == 0){
				candidatePayment.setTransactionDate(new Date());
				candidatePayment.setDebit(new BigDecimal(0.00));
				followUp.setFollowUpRemarks("Course Added By Employee Id: "+accountant.getEmpId()+" Employee Name: "
						+ accountant.getName() +"  Course: " +candidatePayment.getCandidateCourse().getCourse()
						+" On: " +candidatePayment.getTransactionDate());
				followUp.setFollowUpType("Course Added");
			}else{
				followUp.setFollowUpRemarks("Payment Updated By Employee Id: "+accountant.getEmpId()+" Employee Name: "
						+ accountant.getName() +"  Amount: " +candidatePayment.getDebit() +" on: " +candidatePayment.getTransactionDate());
				followUp.setFollowUpType("Payment");
			}
			session.save(candidatePayment);
			session.save(followUp);

			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data save exception");
		}finally {
			session.close();
		}
	}


	public List<ChartsUtil> findTotalDebitForBarCharts(Date startDate,Date endDate)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ChartsUtil> chartsUtils = null;
		try
		{
			String hql ="select cp.employee.name as empName,sum(cp.debit) as paidAmount from CandidatePayment cp where cp.txnType is null and cp.transactionDate between :stDate AND :edDate group by cp.employee.empId";
			Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(ChartsUtil.class));
			query.setParameter("stDate", startDate);
			query.setParameter("edDate", endDate);
			chartsUtils = query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data Access Exception");		 
		}finally{
			session.close();
		}

		return chartsUtils;
	}



	public BigDecimal findTotalDebitsByEmpid(Integer empId,Date startDate,Date endDate){

		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalPaidAmount = null;
		try
		{
			Criteria criteria = session.createCriteria(CandidatePayment.class,"cp");
			criteria.createAlias("cp.employee","emp");
			criteria.setProjection(Projections.sum("cp.debit"));
			Criterion cr1 = Restrictions.eq("cp.employee.empId",empId);
			Criterion cr2 = Restrictions.isNull("txnType");
			Criterion cr3 = Restrictions.and(cr1, cr2);
			Criterion cr4 = Restrictions.between("cp.transactionDate", startDate, endDate);
			criteria.add(Restrictions.and(cr3,cr4));
			totalPaidAmount = (BigDecimal) criteria.uniqueResult();	 
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data Access Exception");		 
		}finally{
			session.close();
		}

		return totalPaidAmount;

	}



	public List<CandidatePayment> findAllCandidatePaymentDebits(Date startDate,Date endDate)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePayment> candidatePayments = null;
		try
		{

			Criteria criteria = session.createCriteria(CandidatePayment.class,"cp");
			criteria.createAlias("cp.candidate", "can");
			criteria.createAlias("cp.candidateCourse", "course");
			criteria.createAlias("can.contactAddress", "contactAdd");
			criteria.createAlias("cp.employee","emp");
			criteria.createAlias("emp.employee","tlEmp",JoinType.LEFT_OUTER_JOIN);
			Criterion cr1 = Restrictions.isNotNull("cp.debit");
			Criterion cr2 = Restrictions.isNull("txnType");
			Criterion cr3 = Restrictions.and(cr1, cr2);
			Criterion cr4 = Restrictions.gt("cp.debit",new BigDecimal(0));
			Criterion cr5 = Restrictions.and(cr3, cr4);
			Criterion cr6 = Restrictions.between("cp.transactionDate", startDate, endDate);
			criteria.add(Restrictions.and(cr5,cr6));
			candidatePayments = criteria.list();	 
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data Access Exception");		 
		}finally{
			session.close();
		}

		return candidatePayments;
	}



	public List<CandidatePaymentModel> findCandidatePaymentByDurationType(String durationType,Date startDate,Date endDate)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePaymentModel> cpm = null;

		try{
			String sql = null;

			if(durationType.equals("Monthly"))
			{
				sql = "select sum(debit) as paidAmount,sum(creadit) as dueAmount,DATE_FORMAT(transaction_date,'%M') as formatedDate from candidate_payment where transaction_date between :sDate and :eDate group by month (transaction_date)";
			}else if(durationType.equals("Yearly")){
				sql = "select sum(debit) as paidAmount,sum(creadit) as dueAmount,DATE_FORMAT(transaction_date,'%Y') as formatedDate from candidate_payment where transaction_date between :sDate and :eDate group by year (transaction_date)";
			}else if(durationType.equals("Weekly")){
				sql = "select sum(debit) as paidAmount,sum(creadit) as dueAmount,DATE_FORMAT(transaction_date,'%W') as formatedDate from candidate_payment where transaction_date between :sDate and :eDate group by week (transaction_date)";
			}else{
				sql = "select sum(debit) as paidAmount,sum(creadit) as dueAmount,CONCAT_WS('',DATE_FORMAT(transaction_date,'%a'),transaction_date) as formatedDate from candidate_payment where transaction_date between :sDate and :eDate group by day (transaction_date)";	
			}
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("sDate", startDate);
			query.setParameter("eDate", endDate);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List data = query.list();
			if(data != null)
			{
				cpm = new ArrayList<CandidatePaymentModel>();
				for(Object o:data)
				{
					Map map = (Map) o;
					CandidatePaymentModel c = new CandidatePaymentModel();
					c.setPaidAmount((BigDecimal)map.get("paidAmount"));
					c.setDueAmount((BigDecimal)map.get("dueAmount"));
					cpm.add(c);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally {
			session.close();
		}

		return cpm;
	}


	public List<Map<String,Object>> findCandidatePaymentByDurationType(String durationType,Date startDate,Date endDate,Integer empId,Integer courseId,String receiver,String modeOfPayment)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Map<String,Object>> data = null;
		try{
			String sql = null;

			sql = "select sum(debit) as paidAmount, sum(creadit) as dueAmount, ";

			if(durationType.equals("day")){
				sql = sql + "CONCAT_WS('',DATE_FORMAT(transaction_date,'%a'),transaction_date) as formatedDate ";
			}else if(durationType.equals("week")){
				sql = sql + "DATE_FORMAT(transaction_date,'%W') as formatedDate ";
			}else if(durationType.equals("month")){
				sql = sql + "DATE_FORMAT(transaction_date,'%M') as formatedDate ";
			}else if(durationType.equals("year")){
				sql = sql + "DATE_FORMAT(transaction_date,'%Y') as formatedDate  ";
			}

			sql = sql+"from candidate_payment where transaction_date between :sDate and :eDate ";

			if(empId != null && empId>0){
				sql = sql +"and emp_id= :employeeId ";
			}
			if(courseId != null && courseId>0){
				sql = sql + "and course_id= :courseId ";
			}
			if(receiver != null && !receiver.isEmpty()){
				sql = sql + "and receiver= :receiverName ";
			}
			if(modeOfPayment != null && !modeOfPayment.isEmpty()){
				sql = sql + "and mode_of_payment= :modeOfPayment ";
			}

			sql = sql + "group by transaction_date";

			SQLQuery query = session.createSQLQuery(sql);


			if(empId != null && empId>0){
				query.setParameter("employeeId", empId);
			}
			if(courseId != null  && courseId>0){
				query.setParameter("courseId", courseId);
			}
			if(receiver != null && !receiver.isEmpty()){
				query.setParameter("receiverName", receiver);
			}
			if(modeOfPayment != null  && !modeOfPayment.isEmpty()){
				query.setParameter("modeOfPayment", modeOfPayment);
			}

			query.setParameter("sDate", startDate);
			query.setParameter("eDate", endDate);


			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			data = (List<Map<String,Object>>)query.list();


		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally {
			session.close();
		}

		return data;
	}



	public List<Candidate> findCandidateForAssignToBatch(Integer courseId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates = null;
		try{

			String hql = "select can from CandidatePayment p join p.candidate can join fetch can.candidateCheckLists check join fetch can.contactAddress con left join fetch "
					+ " can.candidateCourse course left join fetch can.employee emp where p.candidateCourse.id = :courseId and check.checkListType.id = :checkListTypeId and "
					+ " check.checkListStatus is null";

			Query query = session.createQuery(hql);
			query.setParameter("courseId", courseId);
			query.setParameter("checkListTypeId", new Integer(3));
			candidates = (List<Candidate>)query.list();

		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally {
			session.close();
		}

		return candidates;
	}



	public List<CandidatePayment> findPaymentsByCandidateId(Integer candidateId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePayment> candidatePayments = null;
		try{

			String hql = " from CandidatePayment payments join fetch payments.candidate can join fetch can.contactAddress address join fetch payments.candidateCourse course "
					+ " where can.candidateId = :canId and payments.debit > 0";
			Query query = session.createQuery(hql);
			query.setParameter("canId", candidateId);
			candidatePayments = (List<CandidatePayment>)query.list();

		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally {
			session.close();
		}

		return candidatePayments;
	}



	public Object[] candidateFilterForAssign(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(CandidatePayment.class,"payment");
			criteria.createAlias("payment.candidate","can");
			criteria.createAlias("can.candidateCheckLists","check");
			criteria.createAlias("can.contactAddress","con");
			criteria.createAlias("can.candidateCourse","course",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("can.employee","emp",JoinType.LEFT_OUTER_JOIN);
			Criterion c1 = Restrictions.eq("check.checkListType.id", new Integer(3));
			Criterion c2 = Restrictions.isNull("check.checkListStatus");
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
			List<CandidatePayment> candidatePayments = criteria.list();
			resultWithCount[1]=candidatePayments;

		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}



	public List<CandidatePayment> findCandidatePaymentByDate(Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CandidatePayment> candidatePayments = null;
		try{

			String hql = "from CandidatePayment cp join fetch cp.candidate c join fetch c.contactAddress join fetch cp.candidateCourse course "
					+"join fetch cp.employee emp where cp.transactionDate >= :startDate and cp.transactionDate <= :endDate and cp.debit is not null and cp.creadit is null";
			Query query = session.createQuery(hql);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			candidatePayments = (List<CandidatePayment>)query.list();

		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally {
			session.close();
		}

		return candidatePayments;
	}



	public void saveDiscountAmount(CandidatePayment candidatePayment, Employee accountant)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();

			FollowUp followUp = new FollowUp();
			followUp.setCandidate(candidatePayment.getCandidate());
			followUp.setEmployee(accountant);
			followUp.setFollowUpDate(new Date());
			followUp.setFollowUpRemarks("Payment Discount By Employee Id: "+accountant.getEmpId()+" Employee Name: "
					+ accountant.getName() +"  Amount: " +candidatePayment.getDebit() +" On: " +candidatePayment.getTransactionDate());
			followUp.setFollowUpType("Payment");

			session.save(candidatePayment);
			session.save(followUp);

			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new HibernateException("Data save exception");
		}finally {
			session.close();
		}
	}


}