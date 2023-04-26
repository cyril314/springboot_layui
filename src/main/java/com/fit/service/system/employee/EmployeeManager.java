package com.fit.service.system.employee;

import java.util.List;

import com.fit.entity.system.Employee;
import com.fit.entity.PageData;

/**
 * 员工业务处理类
 * @author liguisheng
 *
 */
public interface EmployeeManager {

	/**
	 * 查询员工信息
	 * @return
	 * @throws Exception
	 */
	public List<Employee> listAll()throws Exception;
	/**
	 * 查询员工信息
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeById(PageData pd)throws Exception;
	
	/**
	 * 保存员工信息
	 * @param employee
	 * @throws Exception
	 */
	public void saveEmployee(Employee employee) throws Exception;
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void editEmployee(Employee employee) throws Exception;
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteEmployee(PageData pd) throws Exception;
}
