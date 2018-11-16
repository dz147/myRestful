package com.dz147.dao;

import com.dz147.entity.Employee;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(String number);

    int insert(Employee record);

    Employee selectByPrimaryKey(String number);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);
}