/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Heshan
 */
public class SupplyModel {
    
    private int supId;
    private String supName;
    private String supEmail;
    private int supPhone;
    private String supAddress;

    public SupplyModel(int supId, String supName) {
        this.supId = supId;
        this.supName = supName;
    }

    @Override
    public String toString() {
        return supName; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    public SupplyModel(String supName, String supEmail, int supPhone, String supAddress) {
        
        this.supName = supName;
        this.supEmail = supEmail;
        this.supPhone = supPhone;
        this.supAddress = supAddress;
    }
    
    
    

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }

    public int getSupPhone() {
        return supPhone;
    }

    public void setSupPhone(int supPhone) {
        this.supPhone = supPhone;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }
    
    
    
    
    
}
