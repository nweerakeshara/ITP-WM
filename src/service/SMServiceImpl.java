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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.SMModel;

import util.SMDBconnect;
/**
 *
 * @author Nuwanga
 */
public class SMServiceImpl {
    private static Connection con;
    private PreparedStatement ps,ps1,ps2,ps3,ps4;
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
        ResultSet rs4 = null;
        int itemqty = 0;
        con = SMDBconnect.connect();
        double amt = 0;
        String smcustomerid = "0";
        try {           
            
            ps = con.prepareStatement("Select itemid,itemname,qty,amt from billongoing");
            rs = ps.executeQuery();
            
            
                while(rs.next()){
                    amt = amt + rs.getDouble(4);
                        
                }
            
            ps = con.prepareStatement("Select itemid,itemname,qty,amt from billongoing");
            rs = ps.executeQuery();
            
            
                while(rs.next()){
                    
                    ps4 = con.prepareStatement("Select itemQty from items where itemId = ?");
                    ps4.setString(1, rs.getString(1));
                    rs4 = ps4.executeQuery();
                    while(rs4.next()){
                  
                       itemqty = (rs4.getInt(1) - rs.getInt(3));  
                    }
                                       
                    ps3 = con.prepareStatement("update items set itemQty = ? where itemId = ?");
                    ps3.setString(1,Integer.toString(itemqty));
                    ps3.setString(2,Integer.toString(rs.getInt(1)));
                    ps3.executeUpdate();  
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
        ResultSet rs4 = null;
        con = SMDBconnect.connect();
        int itemqty = 0;
         try {           
            
            ps = con.prepareStatement("DELETE FROM sales WHERE salesid = ?");
            ps.setString(1, Integer.toString(sm.getsalesId()));
            ps.execute();
            
            ps4 = con.prepareStatement("Select itemQty from items where itemId = ?");
            ps4.setString(1, Integer.toString(sm.getItemId()));
            rs4 = ps4.executeQuery();
            while(rs4.next()){
                  
                itemqty = (rs4.getInt(1) + sm.getQty());  
            }
                                       
            ps3 = con.prepareStatement("update items set itemQty = ? where itemId = ?");
            ps3.setString(1,Integer.toString(itemqty));
            ps3.setString(2,Integer.toString(sm.getItemId()));
            ps3.executeUpdate();  
            
            
            
            
            
            
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
    
    public ResultSet getbills(){
        ResultSet rs = null;
        con = SMDBconnect.connect();
        
        try {           
            
            ps = con.prepareStatement("Select billId, billDateTime , nettAmt, payType, customerId, refundamt  from bill");
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return rs;
    }
    
    public ResultSet getsales(){
        ResultSet rs = null;
        con = SMDBconnect.connect();
        
        try {           
            
            ps = con.prepareStatement("Select salesId, billId , custId, qty, salesDate, itemId from sales");
            
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(SMServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return rs;
    }
    
    public void viewreportbill(String path){
        
        con = SMDBconnect.connect();
        ResultSet rs = null;
                    
        //deleted from here

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\ReportSMBillRec.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            //document.add(new Paragraph("image"));
            document.add(image);

            
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            PdfPTable table = new PdfPTable(6);

            PdfPCell cell = new PdfPCell(new Paragraph("Report - Bills"));
            cell.setColspan(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell);
            
            PdfPTable table1 = new PdfPTable(1);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Cus ID"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell1);
            
            PdfPTable table2 = new PdfPTable(1);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Cus Name"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell2);
            
            PdfPTable table3 = new PdfPTable(1);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Cus TP"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell3);
            
            PdfPTable table4 = new PdfPTable(1);

            PdfPCell cell4 = new PdfPCell(new Paragraph("Date & Time"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell4);
            
            PdfPTable table5 = new PdfPTable(1);

            PdfPCell cell5 = new PdfPCell(new Paragraph("Nett Amt"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell5);
            
            
            PdfPTable table6 = new PdfPTable(1);

            PdfPCell cell6 = new PdfPCell(new Paragraph("Refund Amt"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell6);
            
            
            
            
            String query = "SELECT custId, custName, custPhone, billDateTime, nettAmt, refundamt\n" +
                                "from customers c, bill b\n" +
                                    "where c.custId = b.customerId";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                table.addCell(Integer.toString(rs.getInt("custId")));
                table.addCell((rs.getString("custName")));
                table.addCell(Integer.toString(rs.getInt("custPhone")));
                table.addCell((rs.getString("billDateTime")));
                table.addCell(Double.toString(rs.getDouble("nettAmt")));
                table.addCell(Double.toString(rs.getDouble("refundamt")));
            }
            //table.addCell("item7");
            document.add(table);

            document.close();
            //deleted from here
            
        } catch (Exception e) {

        }

        
    }
    
    public void viewreportsales(String path){
        
        con = SMDBconnect.connect();
        ResultSet rs = null;
                    
        //deleted from here

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\ReportSMTotSales.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            //document.add(new Paragraph("image"));
            document.add(image);

            
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            PdfPTable table = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Paragraph("Report - Sales"));
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell);
            
            PdfPTable table1 = new PdfPTable(1);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Item ID"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell1);
            
            PdfPTable table2 = new PdfPTable(1);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Item Name"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell2);
            
            PdfPTable table3 = new PdfPTable(1);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Total Qty"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell3);
            
            
            
            String query = "select s.itemId,itemName,sum(s.qty)\n" +
                                    "from items i, sales s\n" +
                                    "where s.itemId = i.itemId\n" +
                                    "group by s.itemId";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
           
            
            while (rs.next()) {
                table.addCell(Integer.toString(rs.getInt("s.itemId")));
                table.addCell((rs.getString("itemName")));
                table.addCell(Integer.toString(rs.getInt("sum(s.qty)")));
                
                
            }
            //table.addCell("item7");
            document.add(table);

            document.close();
            //deleted from here
            
        } catch (Exception e) {

        }

        
    }
    
    
    public void viewreportsalesrec(String path){
        
        con = SMDBconnect.connect();
        ResultSet rs = null;
                    
        //deleted from here

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\ReportSMSalesRec.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            //document.add(new Paragraph("image"));
            document.add(image);

            
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            PdfPTable table = new PdfPTable(4);

            PdfPCell cell = new PdfPCell(new Paragraph("Report - Sales Records"));
            cell.setColspan(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell);
            
            PdfPTable table1 = new PdfPTable(1);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Cust Name"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell1);
            
            PdfPTable table2 = new PdfPTable(1);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Item Name"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell2);
            
            PdfPTable table3 = new PdfPTable(1);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Qty"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell3);
            
            PdfPCell cell4 = new PdfPCell(new Paragraph("Sales Date"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell4);
            
            
            
            String query = "select custName, ItemName, qty, SalesDate\n" +
                                "from customers c, items i, sales s\n" +
                                "where c.custId = s.custId and s.itemId = i.itemId";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
           
            
            while (rs.next()) {
                table.addCell((rs.getString("custName")));
                table.addCell((rs.getString("itemName")));
                table.addCell(Integer.toString(rs.getInt("qty")));
                table.addCell((rs.getString("SalesDate")));
               
                
                
                
            }
            //table.addCell("item7");
            document.add(table);

            document.close();
            //deleted from here
            
        } catch (Exception e) {

        }

        
    }
}
