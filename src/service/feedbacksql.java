/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import util.FBDBConnect;
import model.feedback;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class feedbacksql {
   
    public ArrayList<feedback> feedbackList(int phone){ 
    
        
        ArrayList<feedback> fbList = new ArrayList<feedback>();
         
        Connection connection;
        PreparedStatement preparedStatement;

        try {

                connection = FBDBConnect.getDBConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM feedback f,customers c where c.custId=f.custId and c.custPhone=?");
                preparedStatement.setInt(1, phone);
                
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                        feedback feedback = new feedback();

                        feedback.setId(Integer.parseInt(resultSet.getString(1)));
                        feedback.setCategory(resultSet.getString(3));
                        feedback.setRating(Integer.parseInt(resultSet.getString(4)));
                        feedback.setOption(resultSet.getString(5));
                        
                        fbList.add(feedback);

                }

                preparedStatement.close();
                connection.close();

        }catch (ClassNotFoundException | SQLException  e) {

                System.out.println(e.getMessage());
        }

        return fbList;
    }
    
    public ArrayList<feedback> feedbackList(){ 
    
        ArrayList<feedback> fbList = new ArrayList<feedback>();
         
        Connection connection;
        PreparedStatement preparedStatement;

        try {

                connection = FBDBConnect.getDBConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM feedback f,customers c where c.custId=f.custId");
                
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                        feedback feedback = new feedback();

                        feedback.setId(Integer.parseInt(resultSet.getString(1)));
                        feedback.setCustomerId(Integer.parseInt(resultSet.getString(2)));
                        feedback.setCategory(resultSet.getString(3));
                        feedback.setRating(Integer.parseInt(resultSet.getString(4)));
                        feedback.setOption(resultSet.getString(5));
                        feedback.setPhone(Integer.parseInt(resultSet.getString(8)));
                        
                        fbList.add(feedback);

                }

                preparedStatement.close();
                connection.close();

        }catch (ClassNotFoundException | SQLException  e) {

                System.out.println(e.getMessage());
        }

        return fbList;
    }

    public ArrayList<feedback> feedbackListCustomer(int id) {
        
        ArrayList<feedback> fbList = new ArrayList<feedback>();
         
        Connection connection;
        PreparedStatement preparedStatement;

        try {

                connection = FBDBConnect.getDBConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM feedback f,customers c where c.custId=f.custId and c.custId=?");
                preparedStatement.setInt(1, id);
                
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                        feedback feedback = new feedback();

                        feedback.setId(Integer.parseInt(resultSet.getString(1)));
                        feedback.setCustomerId(Integer.parseInt(resultSet.getString(2)));
                        feedback.setCategory(resultSet.getString(3));
                        feedback.setRating(Integer.parseInt(resultSet.getString(4)));
                        feedback.setOption(resultSet.getString(5));
                        feedback.setPhone(Integer.parseInt(resultSet.getString(8)));
                        
                        fbList.add(feedback);

                }

                preparedStatement.close();
                connection.close();

        }catch (ClassNotFoundException | SQLException  e) {

                System.out.println(e.getMessage());
        }

        return fbList;
        
    }
    
}
