/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author it18100280
 */
public class employeeValidator {
    
   //Validate email address 
   public static boolean isValidEmailAddress(String email) {
       
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
        return result;
    }
   
   //Validate date formate to dddd/MM/yy
   
   public static boolean isValidDate(String dateString){
   
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        formatter.setLenient(false);
        
        try {
            
            java.util.Date date = formatter.parse(dateString);
            
            return true;
            
        } catch (Exception e) {
            
           return false;
           
        }
   
   }
   //Compare dates
   public static boolean isDateGreater(String d1, String d2){
   
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
       try {
           
           java.util.Date date1 = sdf.parse(d1);
           java.util.Date date2 = sdf.parse(d2);
           
           if(date1.before(date2) || date1.equals(date2)){
           
               return true;
               
           }
           else{
               return false;
           }
           
       } catch (ParseException ex) {
           
           Logger.getLogger(employeeValidator.class.getName()).log(Level.SEVERE, null, ex);
           
           return false;
       }
            
       
   }
   
   
   
}
