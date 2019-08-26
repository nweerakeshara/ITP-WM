/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.EmployeeLeaveModel;
import model.EmployeeModel;
import model.EmployeeType;
import net.proteanit.sql.DbUtils;
import util.DBConnect;

/**
 *
 * @author it18100280
 */
public class EmpDB {
    
    private Connection conn =DBConnect.DBConn();
    private PreparedStatement pst = null;
    
    
    //Insert employee to DB
    public void addEmployee(EmployeeModel e1 ){
        
        conn = DBConnect.DBConn();
        
        try {
            //Add employee SQL query
            String query = "INSERT INTO Employee (empName, nic, empAddress, empPhone, empEmail, empType)"
                    + " VALUES ( '"+e1.getName()+"' , '"+ e1.getNic() +"', '"+ e1.getAddress() +"',"
                    + " '"+e1.getPhone()+"', '"+e1.getEmail()+"', '"+e1.getType()+"')";
            
            pst = conn.prepareStatement(query);
            
            pst.execute();
            
        } catch (SQLException ex) {
            
            System.out.println("Addition Failed!");
            System.out.println(ex);
            //Logger.getLogger(mainUI.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    //Update employee in DB
    public void updateEmployee(EmployeeModel e1){
    
       
        try {
            
            String query = "UPDATE Employee"
                    + " SET empName = '"+e1.getName()+"' , "
                    + "nic = '"+e1.getNic()+"', "
                    + "empAddress = '"+e1.getAddress()+"', "
                    + "empPhone = '"+e1.getPhone()+"', "
                    + "empEmail = '"+e1.getEmail()+"', "
                    + "empType = '"+e1.getType()+"' "
                    + "WHERE empId = '"+e1.getEmpId()+"'";
            pst = conn.prepareStatement(query);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Update failed!");
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    //Delete employee from system
    public void deleteEmp(String empID){
    
        
        try {
            String deleteQuery = "DELETE FROM Employee WHERE empId = '"+empID+"'";
            pst = conn.prepareStatement(deleteQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Check for repeat additions
    public boolean checkRepeat(String nic){
        
        conn = DBConnect.DBConn();
        ResultSet rs ;
        
        String query = "SELECT * FROM Employee WHERE nic = '"+nic+"'";
        
        try {
            
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            return (rs.next());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    //Load employee table
    public ResultSet empTableLoad(javax.swing.JTable table){
        
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM EMPLOYEE";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
             return rs;
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return rs;
    
    }
    
    //Employee sign in
    public void empSignIn(int empID){
    
        
        try {
            
            String empSignInQuery = "INSERT INTO empattendance(empId) VALUES ('"+empID+"')";
            pst = conn.prepareStatement(empSignInQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    //Load employee attendance table
    public ResultSet empAttendanceTableLoad(javax.swing.JTable table){
        
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM empattendance";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
             return rs;
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return rs;
    
    }
    
    //Add employee type
    public void addEmpType(EmployeeType e1){
    
        String addTypeQuery = "insert into employeetype(description, basicSalary, vacationDays) VALUES ('"+e1.getEmpType()+"', '"+e1.getEmpSalary()+"', '"+e1.getEmpLeave()+"')";
        try {
            pst = conn.prepareStatement(addTypeQuery);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Update employee catergory
    public void updateEmpType(EmployeeType e1){
    
        String updateEmpTypeQuery = "UPDATE employeetype SET description = '"+e1.getEmpType()+"', basicSalary = '"+e1.getEmpSalary()+"', vacationDays = '"+e1.getEmpLeave()+"' WHERE id = '"+e1.getEmpTypeId()+"'";
        
        try {
            
            pst = conn.prepareStatement(updateEmpTypeQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    //Delete employee type 
    public void deleteEmpType(int typeID){
    
        String deleteEmpTypeQuery = "DELETE FROM employeetype WHERE id = '"+typeID+"'";
        
        try {
            
            pst = conn.prepareStatement(deleteEmpTypeQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
    //Load employee catergory table
    public ResultSet empCatergoryTableLoad(javax.swing.JTable table){
        
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM employeetype";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
             return rs;
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return rs;
    
    }
    
     //Load employee leave request table
    public ResultSet empLeaveRequestTableLoad(javax.swing.JTable table){
        
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM empleave";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
             return rs;
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return rs;
    
    }
    
    
    
    //calc remaining leave days
    public int calcRemLeave(int empID){
    
        ResultSet rs;
        int ans = 0;
        int totalInt = 0;
        int currentInt = 0;
        int rejectedInt = 0;
        
        try {
                String totalLeave = "SELECT t.vacationDays from employee e, employeetype t where e.empType = t.id and e.empId = '"+empID+"'";
                String currentLeave = "SELECT SUM(noOfDays) FROM empleave WHERE year(startDate) = 2019 AND empId = '"+empID+"'";
                String rejectedRequests = "SELECT SUM(noOfDays) FROM empleave WHERE year(startDate) = 2019 AND empId = '"+empID+"' AND rejected = '"+1+"'";
                
                //get total leave amount
                pst = conn.prepareStatement(totalLeave);
                rs = pst.executeQuery();
                rs.next();
                
                try{
                String total = rs.getString(1);
                totalInt = Integer.parseInt(total);
                System.out.println(total);
                }catch(Exception e){}
                
                //Get pending leave amount
                pst = conn.prepareStatement(currentLeave);
                rs = pst.executeQuery();
                rs.next();
                
                try{
                    String current = rs.getString(1);
                    currentInt = Integer.parseInt(current);
                    System.out.println(current);
                    
                }catch(Exception e){
                    
                }
                
                //Get rejected leave amount
                pst = conn.prepareStatement(rejectedRequests);
                rs = pst.executeQuery();
                rs.next();
                try{
                String rejected = rs.getString(1);
                rejectedInt = Integer.parseInt(rejected);
                }catch(Exception e){}
                
                ans = totalInt - currentInt + rejectedInt;
                
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ans;
        
    }
    //Add employee leave request
    public void addEmpLeaveRequest(EmployeeLeaveModel l1){
    
        String leaveRequestQuery = "INSERT INTO empleave(empId, startDate, endDate, noOfDays,reason)"
                + " VALUES ('"+l1.getEmpID()+"', '"+l1.getStartDate()+"', '"+l1.getEndDate()+"', '"+l1.getNoOfDays()+"', '"+l1.getReason()+"')";
        
        try {
            
            pst = conn.prepareStatement(leaveRequestQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    //Find employee by id
    public EmployeeModel findEmpById(int id){
    
        EmployeeModel e1 = new EmployeeModel();
        
        String findEmpQuery = "SELECT * FROM employee WHERE empId = '"+id+"'";
        
        try {
                ResultSet rs = null;
                pst = conn.prepareStatement(findEmpQuery);
                rs = pst.executeQuery();
                
                if(rs.next() == false){
                    
                    return null;
                }
                
                else{
                
                    do{
                    
                        int empId = rs.getInt(1);
                        String empName = rs.getString(2);
                        String nic = rs.getString(3);
                        String address = rs.getString(4);
                        int phone = rs.getInt(5);
                        String email = rs.getString(6);
                        int empType = rs.getInt(7);
                        
                        e1.setEmpId(empId);
                        e1.setName(empName);
                        e1.setNic(nic);
                        e1.setAddress(address);
                        e1.setPhone(phone);
                        e1.setEmail(email);
                        e1.setType(empType);
                        
                                                
                    }while(rs.next());
                
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return e1;
    }
    
    //Approve leave request
    
    public void approveLeaveRequest(int requestId){
    
        String approveRequestQuery = "UPDATE empleave SET approved = '"+1+"', rejected = '"+0+"' WHERE leaveId = '"+requestId+"'";
        
        try {
            
            pst = conn.prepareStatement(approveRequestQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    //Reject leave request
    
    public void rejectLeaveRequest(int requestId){
    
        String approveRequestQuery = "UPDATE empleave SET rejected = '"+1+"', approved = '"+0+"' WHERE leaveId = '"+requestId+"'";
        
        try {
            
            pst = conn.prepareStatement(approveRequestQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Check request overlap
   public boolean checkLeaveRequestOverlap(int empId, String startDate, String endDate){
       
       ResultSet rs = null;
       
       String dateOverlapQuery = "SELECT * FROM empleave WHERE '"+startDate+"' between startDate and endDate AND empId = '"+empId+"'";
       
        try {
            
            pst = conn.prepareStatement(dateOverlapQuery);
            rs = pst.executeQuery();
            
           return(rs.first());
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
   
   }
    
}
