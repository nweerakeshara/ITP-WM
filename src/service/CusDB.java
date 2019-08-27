/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import model.CustomersModel;
import net.proteanit.sql.DbUtils;
import util.DBConnect;

/**
 *
 * @author Chathura Harshanga
 */
public class CusDB {
    
    private static Connection con = null;
    private static PreparedStatement pst = null;
    
    public void addCustomers(CustomersModel c){
        
    con = DBConnect.DBConn();
    
        
        try {
            String q = "INSERT INTO customers (custId,custName,custPhone,custEmail,custAddress) values('"+ c.getId() +"','"+ c.getName() +"','"+ c.getPhone() +"','"+ c.getEmail() +"','"+ c.getAddress() +"')";
            pst = con.prepareStatement(q);
            pst.execute();
            
            
        } catch (Exception e) {
        }
    
    }
    public void updateCustomers(CustomersModel c){
        con = DBConnect.DBConn();
        
        String sql = "UPDATE customers SET custName = '"+ c.getName() +"' ,custPhone = '"+ c.getPhone() +"',custEmail = '"+ c.getEmail() +"',custAddress = '"+ c.getAddress()+"' WHERE custId = '"+ c.getId() +"'";
           
            try {
                pst = con.prepareStatement(sql);
            pst.execute();
            
            
            } catch (Exception e) {
            }
        
    }
    public void deleteCustomers(CustomersModel c){
        con = DBConnect.DBConn();
        
        String sql = "DELETE FROM customers WHERE custId = '"+ c.getId() +"' ";
            
            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                
            } catch (Exception e) {
            }
        
    }
    
        public static void CustableLoad(JTable jt){
             con = DBConnect.DBConn();
            ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM customers";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            jt.setModel(DbUtils.resultSetToTableModel(rs));
            System.out.println("No problem");
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
