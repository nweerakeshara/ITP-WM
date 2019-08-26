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
import model.SMModel;
import util.SMDBconnect;
/**
 *
 * @author Nuwanga
 */
public class SMServiceImpl {
    private static Connection con;
    private PreparedStatement ps,ps1,ps2;
    public boolean customerTableLoad(SMModel sm){
        con = SMDBconnect.connect();
        boolean status = false;
        try {           
            
            ps = con.prepareStatement("Select * from customers where custPhone = ?");
            ps.setString(1, sm.getcustp());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                status = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public boolean addtobill(SMModel sm){
        con = SMDBconnect.connect();
        boolean status = false;
        try {           
            
            ps = con.prepareStatement("Select * from items where itemId = ?");
            ps.setString(1, Integer.toString(sm.getItemId()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                status = true;
                
                ps1 = con.prepareStatement("Insert into billongoing(itemid,itemname,qty,amt)"+
                                            "values(?,?,?,?)");
                ps1.setString(1, Integer.toString(sm.getItemId()));
                ps1.setString(2, rs.getString(2));
                ps1.setString(3, Integer.toString(sm.getQty()));
                ps1.setString(4, Double.toString(sm.getQty() * Double.parseDouble(rs.getString(6) )));
                ps1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    
    }
    
    public ResultSet getbillitems(){
        ResultSet rs = null;
        con = SMDBconnect.connect();
        
        try {           
            
            ps = con.prepareStatement("Select itemid,itemname,qty,amt from billongoing");
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return rs;
    }
    
    public void deleteitemfrmbill(SMModel sm){
        con = SMDBconnect.connect();
        
         try {           
            
            ps = con.prepareStatement("DELETE FROM billongoing WHERE itemid = ?");
            ps.setString(1, Integer.toString(sm.getItemId()));
            ps.execute();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteallfrmbill(){
        con = SMDBconnect.connect();
        
         try {           
            
            ps = con.prepareStatement("DELETE  from billongoing ");
            ps.execute();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generatebill(SMModel sm){
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        con = SMDBconnect.connect();
        double amt = 0;
        String smcustomerid = "0";
        try {           
            
            ps = con.prepareStatement("Select itemid,itemname,qty,amt from billongoing");
            rs = ps.executeQuery();
            
            
                while(rs.next()){
                    amt = amt + rs.getDouble(4);
                }
            
            
            
            ps1 = con.prepareStatement("Select custId from customers where custPhone = ?");
            ps1.setString(1, sm.getcustp());
            rs = ps1.executeQuery();
            
            if(rs.next()){
               smcustomerid = rs.getString(1);
            }
            
            ps2 = con.prepareStatement("Insert into bill(cashierId,nettAmt,payType,customerId,refundamt)"+
                                        "values(?,?,?,?,?)");
            ps2.setString(1,Integer.toString(5));
            ps2.setString(2,Double.toString(amt - sm.getReturnAmt()));
            ps2.setString(3,sm.getPayType());
            ps2.setString(4,smcustomerid);
            ps2.setString(5,Double.toString(sm.getReturnAmt()));
            ps2.executeUpdate();
                     
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       
      try { 
           
            ps = con.prepareStatement("select * from bill order by billId DESC LIMIT 1  ");
            rs2 = ps.executeQuery();
            int maxid = 0;
            while(rs2.next()){
                    maxid = rs2.getInt(1);
                }
            
            
            ps1 = con.prepareStatement("Select itemid,itemname,qty,amt from billongoing");
            rs1 = ps1.executeQuery();
           
                while(rs1.next()){
                    ps2 = con.prepareStatement("Insert into sales(billId,custId,qty,itemId)"+
                                            " values(?,?,?,?)");
                    ps2.setString(1, Integer.toString(maxid));
                    ps2.setString(2, smcustomerid);
                    ps2.setString(3, rs1.getString(3));
                    ps2.setString(4, rs1.getString(1));
                    ps2.executeUpdate();
                }
             
        }catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }  
       
        
        
    }
    
    public double returnbillamt(){
      
        double r = 0;
        ResultSet rs = null;
        try {    ps = con.prepareStatement("select * from bill order by billId DESC LIMIT 1  ");
                 rs = ps.executeQuery();
                 int maxid = 0;
                 while(rs.next()){
                 r =  rs.getDouble(4);
                 }
                
           
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return r;
    }
    
    public ResultSet getbillitemsforreturns(SMModel sm){
        ResultSet rs = null;
        con = SMDBconnect.connect();
        
        try {           
            
            ps = con.prepareStatement("Select salesId, itemId , qty from sales where billId =  ?");
            ps.setString(1, Integer.toString(sm.getBillId()));
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return rs;
    }
    
    public void deleteitemfrmsales(SMModel sm){
        con = SMDBconnect.connect();
        
         try {           
            
            ps = con.prepareStatement("DELETE FROM sales WHERE salesid = ?");
            ps.setString(1, Integer.toString(sm.getsalesId()));
            ps.execute();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public ResultSet deleteallfrmsalestable(){
        con = SMDBconnect.connect();
        ResultSet rs = null;
         try {           
            
            ps = con.prepareStatement("Select * from sales where salesId= 0 ");
            rs = ps.executeQuery();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return rs;
    }
}
