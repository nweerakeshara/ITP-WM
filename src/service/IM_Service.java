
package service;

import util.IM_DBconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class IM_Service {
    
    Connection IM_con = null;
    PreparedStatement IM_st = null;
    ResultSet IM_rs = null;
    
    public IM_Service(){
        DBcon();
        
    }
    
     final void DBcon(){
        IM_con = IM_DBconnect.IM_connect();
    }

    public void add_item(String Iname, String Itype, int Iqty, double IBprice, double ISprice) {
     
        try {

            String que = "INSERT INTO items(itemName,itemType,itemQty,buyingPrice,sellingPrice) VALUES ('"+ Iname +"','"+ Itype +"','"+ Iqty +"','"+ IBprice +"','"+ ISprice +"')";

            IM_st = IM_con.prepareStatement(que);
            IM_st.execute();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public void update_items(int ans, String Iid, String Iname, String Itype, int Iqty, String IBprice, String ISprice) {
        
          if(ans==0)
        {
          
           String que = "UPDATE items SET itemName = '"+ Iname +"' , itemType = '"+ Itype +"' , itemQty = '"+ Iqty +"' , buyingPrice = '"+ IBprice +"' , sellingPrice = '"+ ISprice +"'  WHERE itemId = '"+ Iid +"' " ;
          
            try {
                 
                IM_st = IM_con.prepareStatement(que);
                IM_st.execute();
               
                
            } catch (Exception e) {
                
                System.out.println(e);
            }
           
        }
        
    }

    public void update_items(String Iid, int ans) {
        
               if(ans==0){
            
            String sql = "DELETE FROM items WHERE itemId = '"+ Iid +"' ";
            
            try {
                IM_st = IM_con.prepareStatement(sql);
                IM_st.execute();
                
                
            } catch (Exception e) {
                
                System.out.println(e);
                
            }
        }
        
    }

    public ResultSet search_item(String by, String key) {
        
        try {
            switch (by) {
                case "id":
{
                      String sql  = "SELECT itemId AS ID,itemName AS NAME,itemType AS BRAND,itemQty AS QTY,buyingPrice AS BOUGTH_PRICE,sellingPrice AS SELLING_PRICE FROM items WHERE itemId LIKE '%" + key + "%' ";
                      IM_st = IM_con.prepareStatement(sql);
                      IM_rs = IM_st.executeQuery(); 
                      return IM_rs;
                    }
                case "name":
{
                       String  sql  = "SELECT itemId AS ID,itemName AS NAME,itemType AS BRAND,itemQty AS QTY,buyingPrice AS BOUGTH_PRICE,sellingPrice AS SELLING_PRICE FROM items WHERE itemName LIKE '%" + key + "%' ";
                       IM_st = IM_con.prepareStatement(sql);
                       IM_rs = IM_st.executeQuery();
                       return IM_rs;
                    }
                case "brand":
{
                        String  sql  = "SELECT itemId AS ID,itemName AS NAME,itemType AS BRAND,itemQty AS QTY,buyingPrice AS BOUGTH_PRICE,sellingPrice AS SELLING_PRICE FROM items WHERE itemType LIKE '%" + key + "%' ";
                       IM_st = IM_con.prepareStatement(sql);
                       IM_rs = IM_st.executeQuery();
                       return IM_rs;
                    }
                case "less than qty":
{
                        String  sql  = "SELECT itemId AS ID,itemName AS NAME,itemType AS BRAND,itemQty AS QTY,buyingPrice AS BOUGTH_PRICE,sellingPrice AS SELLING_PRICE FROM items WHERE itemQty <= '" + key + "' ";
                       IM_st = IM_con.prepareStatement(sql);
                       IM_rs = IM_st.executeQuery();
                       return IM_rs;
                    }
                case "greater than qty":
{
                       String  sql  = "SELECT itemId AS ID,itemName AS NAME,itemType AS BRAND,itemQty AS QTY,buyingPrice AS BOUGTH_PRICE,sellingPrice AS SELLING_PRICE FROM items WHERE itemQty >= '" + key + "' ";
                       IM_st = IM_con.prepareStatement(sql);
                       IM_rs = IM_st.executeQuery();
                       return IM_rs;
                    }
            }
           
        } catch (Exception e) {
            
            System.out.println(e);
        }
        return null;
    }
      
    public ResultSet IM_tableload(){
        try {
            String sql = "SELECT itemId AS ID,itemName AS NAME,itemType AS BRAND,itemQty AS QTY,buyingPrice AS BOUGTH_PRICE,sellingPrice AS SELLING_PRICE FROM items";
            IM_st = IM_con.prepareStatement(sql);
            IM_rs = IM_st.executeQuery();
                        
        } catch (Exception e) {
            System.out.println(e);
        }
        return IM_rs;
    }
    
   
}
