/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thanuja Chamika
 */
@Entity
@Table(name = "suppliers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suppliers.findAll", query = "SELECT s FROM Suppliers s")
    , @NamedQuery(name = "Suppliers.findBySupplierId", query = "SELECT s FROM Suppliers s WHERE s.supplierId = :supplierId")
    , @NamedQuery(name = "Suppliers.findBySupplierName", query = "SELECT s FROM Suppliers s WHERE s.supplierName = :supplierName")
    , @NamedQuery(name = "Suppliers.findByEmail", query = "SELECT s FROM Suppliers s WHERE s.email = :email")
    , @NamedQuery(name = "Suppliers.findBySupplierStatus", query = "SELECT s FROM Suppliers s WHERE s.supplierStatus = :supplierStatus")
    , @NamedQuery(name = "Suppliers.findBySupplierTag", query = "SELECT s FROM Suppliers s WHERE s.supplierTag = :supplierTag")
    , @NamedQuery(name = "Suppliers.findBySupplierPhone", query = "SELECT s FROM Suppliers s WHERE s.supplierPhone = :supplierPhone")
    , @NamedQuery(name = "Suppliers.findBySupplierAddress", query = "SELECT s FROM Suppliers s WHERE s.supplierAddress = :supplierAddress")})
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "supplierId")
    private Integer supplierId;
    @Basic(optional = false)
    @Column(name = "supplierName")
    private String supplierName;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "supplierStatus")
    private boolean supplierStatus;
    @Basic(optional = false)
    @Column(name = "supplierTag")
    private String supplierTag;
    @Basic(optional = false)
    @Column(name = "supplierPhone")
    private int supplierPhone;
    @Basic(optional = false)
    @Column(name = "supplierAddress")
    private String supplierAddress;

    public Suppliers() {
    }

    public Suppliers(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Suppliers(Integer supplierId, String supplierName, String email, boolean supplierStatus, String supplierTag, int supplierPhone, String supplierAddress) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.email = email;
        this.supplierStatus = supplierStatus;
        this.supplierTag = supplierTag;
        this.supplierPhone = supplierPhone;
        this.supplierAddress = supplierAddress;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(boolean supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getSupplierTag() {
        return supplierTag;
    }

    public void setSupplierTag(String supplierTag) {
        this.supplierTag = supplierTag;
    }

    public int getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(int supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierId != null ? supplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suppliers)) {
            return false;
        }
        Suppliers other = (Suppliers) object;
        if ((this.supplierId == null && other.supplierId != null) || (this.supplierId != null && !this.supplierId.equals(other.supplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Suppliers[ supplierId=" + supplierId + " ]";
    }
    
}
