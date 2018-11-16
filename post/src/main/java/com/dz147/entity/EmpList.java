package com.dz147.entity;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class EmpList {
    @NotEmpty
    public List<Employee> addEmp;


    @Override
    public String toString() {
        return "EmpList{" +
                "addEmp=" + addEmp +
                '}';
    }

    public List<Employee> getAddEmp() {
        return addEmp;
    }

    public void setAddEmp(List<Employee> addEmp) {
        this.addEmp = addEmp;
    }
}
