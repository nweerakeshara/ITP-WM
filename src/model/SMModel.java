/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Nuwanga
 */
public class SMModel {
    private int cashierId;
    private double nettAmt;    
    private String payType;
    private int customerId;
    private int itemId;
    private int billId;
    private int custId;
    private String custp;
    private int qty;
    private double returnAmt;
    private int salesId;

    public SMModel(int cashierId, double nettAmt, String payType, int customerId, int itemId, int billId, int custId, int qty, double returnAmt, String custp, int salesId) {
        this.cashierId = cashierId;
        this.nettAmt = nettAmt;
        this.payType = payType;
        this.customerId = customerId;
        this.itemId = itemId;
        this.billId = billId;
        this.custId = custId;
        this.qty = qty;
        this.returnAmt = returnAmt;
        this.custp = custp;
        this.salesId = salesId;
    }

    public SMModel() {
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public void setNettAmt(double nettAmt) {
        this.nettAmt = nettAmt;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setcustp(String custp) {
        this.custp = custp;
    }   
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setReturnAmt(double returnAmt) {
        this.returnAmt = returnAmt;
    }
    
    public void setsalesId(int salesId) {
        this.salesId = salesId;
    }

    public int getCashierId() {
        return cashierId;
    }

    public double getNettAmt() {
        return nettAmt;
    }

    public String getPayType() {
        return payType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getBillId() {
        return billId;
    }

    public int getCustId() {
        return custId;
    }

    public int getQty() {
        return qty;
    }

    public double getReturnAmt() {
        return returnAmt;
    }
    
    public String getcustp() {
        return custp;
    }
    
    public int getsalesId() {
        return salesId;
    }
    
    
    
}
