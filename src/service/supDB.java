/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    private PreparedStatement pst1 = null;
    
    
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
        
        String query = "select * from suppliers";
        
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
    
    public ResultSet search_item(String by, String key) {
        
        try {
            switch (by) {
                case "id":
                {
                     ResultSet rs = null;
                     String query = "SELECT * from suppliers where supplierId  LIKE '%" + key + "%' ";
                     try{
                     
                            pst = conn.prepareStatement(query);
                            rs = pst.executeQuery();
                     
                     }catch(Exception e){}
                     
                      return rs;
                      
                     
                    }
                case "name":
                    {
                        ResultSet rs = null;
                        String query = "SELECT * from suppliers where supplierName  LIKE '%" + key + "%' ";
                    
                     
                            pst = conn.prepareStatement(query);
                            rs = pst.executeQuery();
                     
                    
                     
                      return rs;
                    }
           
            }
           
        } catch (Exception e) {
            
            System.out.println(e);
        }
        return null;
    }
    public ResultSet search_item_order(String by, String key) {
        
        try {
            switch (by) {
                case "orderid":
                {
                     ResultSet rs = null;
                     String query = "SELECT * from orders where orderId  LIKE '%" + key + "%' ";
                     try{
                     
                            pst = conn.prepareStatement(query);
                            rs = pst.executeQuery();
                     
                     }catch(Exception e){}
                     
                      return rs;
                      
                     
                    }
                case "itemId":
                    {
                        ResultSet rs = null;
                        String query = "SELECT * from orders where itemId  LIKE '%" + key + "%' ";
                    
                     
                            pst = conn.prepareStatement(query);
                            rs = pst.executeQuery();
                     
                    
                     
                      return rs;
                    }
                case "supId":
                    {
                        ResultSet rs = null;
                        String query = "SELECT * from orders where supplierId  LIKE '%" + key + "%' ";
                    
                     
                            pst = conn.prepareStatement(query);
                            rs = pst.executeQuery();
                     
                    
                     
                      return rs;
                    }
           
            }
           
        } catch (Exception e) {
            
            System.out.println(e);
        }
        return null;
    }
    public void generateReport(String path){
        
        ResultSet rs,rs1;
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\ReportSup.pdf"));
            document.open();

           com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            
           document.add(image);

            
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("---------------------------------------------------------------------------------------"));

            PdfPTable table = new PdfPTable(5);

            PdfPCell cell = new PdfPCell(new Paragraph("Report - Suppliers"));
            cell.setColspan(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.GREEN);
            table.addCell(cell);
            
            table.addCell("SupplierId");
            table.addCell("SupplierName");
            table.addCell("SupplierId");
            table.addCell("SupplierIEmail");
            table.addCell("PhoneNumber");
            String query = "SELECT * FROM suppliers";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            
            while (rs.next()) {
                table.addCell(Integer.toString(rs.getInt("supplierId")));
                table.addCell(rs.getString("supplierName"));
                table.addCell(rs.getString("email"));
                table.addCell(Integer.toString(rs.getInt("supplierPhone")));
                table.addCell(rs.getString("supplierAddress"));
            }
            
            
            
            document.add(table);
            
//            document.add(new Paragraph("---------------------------------------------------------------------------------------"));
//
//            PdfPTable table1 = new PdfPTable(5);
//
//            PdfPCell cell1 = new PdfPCell(new Paragraph("Report - Orders"));
//            cell1.setColspan(5);
//            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell1.setBackgroundColor(BaseColor.GREEN);
//            table1.addCell(cell1);
//            
//            table1.addCell("orderId");
//            table1.addCell("itemId");
//            table1.addCell("orderQty");
//            table1.addCell("nettAmt");
//            table1.addCell("supplierId");
//            String query1 = "SELECT orderId,itemId,orderQty,nettAmt,supplierId FROM orders";
//            pst1 = conn.prepareStatement(query1);
//            rs1 = pst1.executeQuery();
//            
//            
//            while (rs1.next()) {
//                table1.addCell(Integer.toString(rs.getInt("orderId")));
//                table1.addCell(Integer.toString(rs.getInt("itemId")));
//                table1.addCell(Integer.toString(rs.getInt("orderQty")));
//                table1.addCell(Double.toString(rs.getInt("nettAmt")));
//                table1.addCell(Integer.toString(rs.getInt("supplierId")));
//            }
//            
//            document.add(table1);
//            
//
            document.close();
            //deleted from here
            
        } catch (Exception e) {

        }
            
        
        
        
    }
    
       
}    
    
    

