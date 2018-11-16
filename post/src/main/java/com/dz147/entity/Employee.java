package com.dz147.entity;

import com.dz147.controller.Lengths;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class Employee {

    @NotBlank(message = "{err.key}")
    private String number;

    @NotBlank
    @Lengths
    private String empName;

    private String empSex;

    private String education;

    @NotNull()
    @Min(value = 5000,message = "{err.price}")
    private Long monthly;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex == null ? null : empSex.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public Long getMonthly() {
        return monthly;
    }

    public void setMonthly(Long monthly) {
        this.monthly = monthly;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "number='" + number + '\'' +
                ", empName='" + empName + '\'' +
                ", empSex='" + empSex + '\'' +
                ", education='" + education + '\'' +
                ", monthly=" + monthly +
                '}';
    }
}