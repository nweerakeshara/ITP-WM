/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Chathura Harshanga
 */
public class CustomersModel {
    
        private String Id ;
        private String name ;
        private String phone;
        private String address ;
        private String email;

    public CustomersModel(String Id, String name, String phone, String address, String email) {
        this.Id = Id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        
    }

    public CustomersModel(String Id) {
        this.Id = Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
        
            
}
