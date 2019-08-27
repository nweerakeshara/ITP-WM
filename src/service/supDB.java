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
import model.SupplyModel;
import model.orderModel;
import net.proteanit.sql.DbUtils;
import util.DBConnect;

/**
 *
 * @author Heshan
 */
public class supDB {
    private Connection conn =DBConnect.DBConn();
    private PreparedStatement pst = null;
    
    
    public void addSupply(SupplyModel s1 ){
        
        conn = DBConnect.DBConn();
        
        try {
            
            String query = "INSERT INTO suppliers (supplierName,email,supplierPhone,supplierAddress)"
                    + " VALUES ( '"+s1.getSupName()+"' , '"+ s1.getSupEmail() +"',"
                    + " '"+s1.getSupPhone()+"', '"+s1.getSupAddress()+"')";            
           
            
            pst = conn.prepareStatement(query);
            
            pst.execute();
            
        } catch (SQLException ex) {
            
            System.out.println("Addition Failed!");
            System.out.println(ex);
            //Logger.getLogger(mainUI.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    public boolean checkRepeat(String email){
        
        conn = DBConnect.DBConn();
        ResultSet rs ;
        
        String query = "SELECT * FROM suppliers WHERE email = '"+email+"'";
        
        try {
            
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            return (rs.next());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(supDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void addOrder(orderModel o1, int supID, int Iid ){
        
        conn = DBConnect.DBConn();
        
        try {
            
            
            
            String query = "INSERT INTO orders (itemId,orderQty,nettAmt,supplierId)"
                    + " VALUES ( '"+Iid+"','"+o1.getQty()+"' , '"+ o1.getAmount() +"',"
                    + " '"+supID+"')";            
           
            
            pst = conn.prepareStatement(query);
            
            pst.execute();
            
        } catch (SQLException ex) {
            
            System.out.println("Addition Failed!");
            System.out.println(ex);
            //Logger.getLogger(mainUI.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public ResultSet supTableLoad(javax.swing.JTable table){
        
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM SUPPLIERS";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
             return rs;
        } catch (SQLException ex) {
            Logger.getLogger(supDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return rs;
    
    }
    public ResultSet orderTableLoad(javax.swing.JTable table){
        
        ResultSet rs = null;
        
        try {
            String query = "SELECT * FROM orders";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
             return rs;
        } catch (SQLException ex) {
            Logger.getLogger(supDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return rs;
    
    }
    
    public ResultSet getSupCategory(){
    
        ResultSet rs = null;
        
        String query = "SELECT * from suppliers";
        
        try{
        
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
        
        
        }catch(SQLException ex){
        
            System.out.println(ex);
        
        }
    
        return rs;
    }
    
     public void updateSupplier(SupplyModel s1){
    
       
        try {
            

            System.out.println(s1.getSupId()+" "+s1.getSupName()+" "+s1.getSupEmail()+" "+s1.getSupPhone()+" " + s1.getSupAddress());
           pst = conn.prepareStatement("UPDATE suppliers SET supplierName = ?, email = ?, supplierPhone = ?, supplierAddress = ? WHERE supplierId = ? ");
            pst.setString(1, s1.getSupName());
            pst.setString(2, s1.getSupEmail());
            pst.setString(3, Integer.toString(s1.getSupPhone()));
            pst.setString(4, s1.getSupAddress());
            pst.setString(5, Integer.toString(s1.getSupId()));
            pst.execute();
            
            System.out.println("ihhh");
            
        } catch (SQLException ex) {
            System.out.println("Update failed!");
            System.out.println(ex);
            
        }
    
    }
     
     public void deletesup(String supID){
    
        
        try {
            String deleteQuery = "DELETE FROM suppliers WHERE supplierId = '"+supID+"'";
            pst = conn.prepareStatement(deleteQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(supDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getItemCategory() {
        
        ResultSet rs = null;
        
        String query = "SELECT * from items";
        
        try{
        
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
        
        
        }catch(SQLException ex){
        
            System.out.println(ex);
        
        }
    
        return rs;
    }

    public void deleteorder(String id) {
         try {
            String deleteQuery = "DELETE FROM orders WHERE orderId = '"+id+"'";
            pst = conn.prepareStatement(deleteQuery);
            pst.execute();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(supDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       
    }
    
    

