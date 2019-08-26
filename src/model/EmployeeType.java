/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author it18100280
 */
public class EmployeeType {
    
    private int empTypeId;
    private String empType;
    private double empSalary;
    private int empLeave;

    public EmployeeType(String empType, double empSalary, int empLeave) {
        this.empType = empType;
        this.empSalary = empSalary;
        this.empLeave = empLeave;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }

    public int getEmpLeave() {
        return empLeave;
    }

    public void setEmpLeave(int empLeave) {
        this.empLeave = empLeave;
    }

    public int getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(int empTypeId) {
        this.empTypeId = empTypeId;
    }
    
    
    
    
}
