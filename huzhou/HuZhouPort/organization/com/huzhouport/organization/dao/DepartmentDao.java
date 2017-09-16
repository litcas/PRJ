package com.huzhouport.organization.dao;

import java.util.List;

import com.huzhouport.organization.model.Department;



public interface DepartmentDao {
	//查询全部
	public List<Department> showDepartmentList(Department department) throws Exception;
	
	//查询 上级部门
	public List<Department> searchParentDepartmentName(Department department) throws Exception;
	
	//增加
	public String addDepartmentInfo(Department department) throws Exception;
	
	//修改
	public String updateDepartmentInfo(Department department) throws Exception;
	
	//删除
	public String deleteDepartmentInfo(Department department) throws Exception;
	
	 //檢查同部門下的部門名
	public List<Department> ckeckDepartmentName(Department department) throws Exception;
	//手机端 分级查找
	public List<?> findUserAndDepartment(Department department) throws Exception;

}
