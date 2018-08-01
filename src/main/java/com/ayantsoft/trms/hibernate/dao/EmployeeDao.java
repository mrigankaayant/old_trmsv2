package com.ayantsoft.trms.hibernate.dao;

import java.awt.HeadlessException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.primefaces.model.SortOrder;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.ayantsoft.trms.hibernate.pojo.Employee;
import com.ayantsoft.trms.hibernate.pojo.Role;
import com.ayantsoft.trms.hibernate.pojo.UserProfile;
import com.ayantsoft.trms.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class EmployeeDao implements Serializable {



	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7380774775023151021L;

	public void save(Employee employee,Set<Role> roles)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(employee.getContactAddress());
			employee.getUserProfile().setOfficeEmail((employee.getContactAddress().getEmail()));
			employee.getUserProfile().setRoles(roles);
			session.saveOrUpdate(employee.getUserProfile());
			if(employee.getEmployee().getEmpId() == null)
			{
				employee.setEmployee(null);
			}
			session.saveOrUpdate(employee);
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



	public boolean findUsername(String username)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		String employeeUserName = null;
		try
		{
			Criteria criteria = session.createCriteria(UserProfile.class);
			criteria.add(Restrictions.eq("username", username));
			criteria.setProjection(Projections.property("username"));
			employeeUserName = (String) criteria.uniqueResult(); 
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}finally{
			session.close();
		}

		if(employeeUserName != null){
			return true;
		}else{
			return false;
		}
	}



	public Object[] employeeFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){

		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Employee.class,"employee");
			criteria.createAlias("employee.contactAddress","contactAddress");
			criteria.createAlias("employee.department","department");
			criteria.createAlias("employee.designation","designation");
			criteria.createAlias("employee.userProfile","userProfile");

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("empId")){
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
			List<Employee> employees = criteria.list();
			resultWithCount[1]=employees;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}


	public boolean checkUniqueEmail(String email, Integer employeeId){
		email=email.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try
		{
			String hql="select count(*) from Employee emp right join emp.contactAddress con where (";
			if(employeeId != null){
				hql = hql + "emp.empId != :empId";
			}else{
				hql = hql + "emp.empId is not null";
			}

			hql = hql + " or emp.empId = null) and (con.email = :email or con.alternateEmail= :alternateEmail) ";

			Query query = session.createQuery(hql);
			if(employeeId != null){
				query.setParameter("empId",employeeId);
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


	public Employee findEmployeeById(Integer empId)
	{

		Session session = HibernateUtil.getSessionFactory().openSession();
		Employee employee = null;
		try
		{

			String hql ="select emp from Employee emp join fetch emp.userProfile up join fetch emp.department left outer join emp.employee join fetch emp.designation "
					+ " join fetch emp.contactAddress join fetch up.roles where emp.empId = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id",empId);
			employee = (Employee) query.uniqueResult();
		}catch(HeadlessException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access exception.");
		}finally {
			session.close();
		}

		return employee;

	}


	public boolean findPassword(Integer empId , String password)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		String employeePassword = null;
		try
		{
			Criteria criteria = session.createCriteria(UserProfile.class,"up");
			criteria.createAlias("up.employee", "emp");
			Criterion cr1 = Restrictions.eq("emp.empId",empId);
			Criterion cr2 = Restrictions.eq("up.password",password);
			criteria.setProjection(Projections.property("up.password"));
			criteria.add(Restrictions.and(cr1, cr2));
			employeePassword = (String) criteria.uniqueResult();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data access exception.");
		}finally{
			session.close();
		}

		if(employeePassword != null){
			return true;
		}else{
			return false;
		}
	}



	public boolean savePassword(Integer empId,String password)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean IsPassword = false;
		try
		{
			Criteria criteria = session.createCriteria(UserProfile.class,"up");
			criteria.createAlias("up.employee", "emp");
			criteria.add(Restrictions.eq("emp.empId",empId));
			UserProfile userProfile = (UserProfile) criteria.uniqueResult();
			if(userProfile != null)
			{
				userProfile.setPassword(password);
				session.beginTransaction();
				session.saveOrUpdate(userProfile);
				session.getTransaction().commit();	
				IsPassword = true;
			}

		}catch(HibernateException he)
		{
			he.printStackTrace();
			throw new HibernateException("Data save exception.");
		}finally{
			session.close();
		}

		if(IsPassword){
			return true;	
		}else{
			return false;
		}

	}

	public List<Employee> getEmployeesForSuperviser(Integer empId){

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Employee> employees = null;
		try
		{
			Criteria criteria = session.createCriteria(Employee.class,"emp");
			criteria.createAlias("emp.employee","innerEmp");
			criteria.add(Restrictions.eq("innerEmp.empId", empId));
			employees = criteria.list();
		}catch(HibernateException ex){ex.getMessage();}
		finally{
			session.close();
		}
		if(employees.size()!=0){
			return employees;
		}
		return null;
	}


	public boolean checkWorkMobile(String mobile, Integer employeeId)
	{
		mobile=mobile.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try
		{
			String hql="select count(*) from Employee emp right join emp.contactAddress con where (";
			if(employeeId != null){
				hql = hql + "emp.empId != :empId";
			}else{
				hql = hql + "emp.empId is not null";
			}

			hql = hql + " or emp.empId = null) and (con.workMobile = :workmobile or con.homeMobile= :homemobile) ";

			Query query = session.createQuery(hql);
			if(employeeId != null){
				query.setParameter("empId", employeeId);
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


	public List<Employee> findAllEmployee()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Employee> employeeList = null;
		try
		{
			Criteria criteria = session.createCriteria(Employee.class);
			employeeList = criteria.list();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return employeeList;

	}


	public List<Employee> findSupervisor()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Employee> employeeList = null;
		try
		{

			String sql = "select * from employee where supervisor_id is null";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Employee.class);
			employeeList = query.list();	
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return employeeList;
	}



	public List<Employee> findEmployeeByDepartmentName(String departmentName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Employee> employeeList = null;
		try
		{

			String hql = "from Employee emp join fetch emp.contactAddress address where emp.department.departmentName= :deptName and emp.active=true";
			Query query = session.createQuery(hql);
			query.setString("deptName",departmentName);
			employeeList = query.list();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HibernateException("Data access exception");
		}finally{
			session.close();
		}

		return employeeList;
	
	}
	
	
	public List<Candidate> findHotlistCandidate(){

		Session session=HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates = null;
		try
		{
			String hql = "from Candidate c join fetch c.candidateCourse course join fetch c.contactAddress address join fetch c.employee emp join fetch c.employees salesEmp where c.candidateStatus is 'On Hotlist'";
			Query query = session.createQuery(hql);
			candidates = query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		return candidates;
	}
	
	
	
	public List<Candidate> findPlacedCandidate(){

		Session session=HibernateUtil.getSessionFactory().openSession();
		List<Candidate> candidates = null;
		try
		{
			String hql = "from Candidate c join fetch c.candidateCourse course join fetch c.contactAddress address join fetch c.employee emp where c.candidateStatus is 'Placed'";
			Query query = session.createQuery(hql);
			candidates = query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		return candidates;
	}
	
	
	
	
	public List<Employee> findAllRecruiter(){
		Session session=HibernateUtil.getSessionFactory().openSession();
		List<Employee> employees = null;
		try
		{
			String hql = "FROM Employee employee "
					   + "JOIN FETCH employee.department dept "
					   + "WHERE dept.departmentName = :deptName AND employee.active = 1 ";
			Query query = session.createQuery(hql);
			query.setParameter("deptName","Recruitment");
			employees = query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}
		return employees;
	}
}