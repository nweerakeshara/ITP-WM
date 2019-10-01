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
public class EmployeeLeaveModel {
    
    private int leaveID;
    private int empID;
    private String startDate;
    private String endDate;
    private int noOfDays;
    private boolean approved;
    private boolean rejected;
    private String reason;

    public EmployeeLeaveModel(int leaveID, int empID, String startDate, String endDate, int noOfDays, boolean approved, boolean rejected, String reason) {
        this.leaveID = leaveID;
        this.empID = empID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.noOfDays = noOfDays;
        this.approved = approved;
        this.rejected = rejected;
        this.reason = reason;
    }
    
     public EmployeeLeaveModel(int empID, String startDate, String endDate, int noOfDays,String reason) {
        
        this.empID = empID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.noOfDays = noOfDays;
        
        this.reason = reason;
    }

    public int getLeaveID() {
        return leaveID;
    }

   

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    
    
}
