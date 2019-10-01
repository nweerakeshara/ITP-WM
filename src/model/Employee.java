/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thanuja Chamika
 */
@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
    , @NamedQuery(name = "Employee.findByEmpId", query = "SELECT e FROM Employee e WHERE e.empId = :empId")
    , @NamedQuery(name = "Employee.findByEmpName", query = "SELECT e FROM Employee e WHERE e.empName = :empName")
    , @NamedQuery(name = "Employee.findByNic", query = "SELECT e FROM Employee e WHERE e.nic = :nic")
    , @NamedQuery(name = "Employee.findByEmpAddress", query = "SELECT e FROM Employee e WHERE e.empAddress = :empAddress")
    , @NamedQuery(name = "Employee.findByEmpPhone", query = "SELECT e FROM Employee e WHERE e.empPhone = :empPhone")
    , @NamedQuery(name = "Employee.findByEmpEmail", query = "SELECT e FROM Employee e WHERE e.empEmail = :empEmail")
    , @NamedQuery(name = "Employee.findByEmpType", query = "SELECT e FROM Employee e WHERE e.empType = :empType")})
public class Employee implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "empId")
    private Integer empId;
    @Basic(optional = false)
    @Column(name = "empName")
    private String empName;
    @Basic(optional = false)
    @Column(name = "nic")
    private String nic;
    @Basic(optional = false)
    @Column(name = "empAddress")
    private String empAddress;
    @Basic(optional = false)
    @Column(name = "empPhone")
    private int empPhone;
    @Basic(optional = false)
    @Column(name = "empEmail")
    private String empEmail;
    @Basic(optional = false)
    @Column(name = "empType")
    private int empType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eid")
    private Collection<Billpayment> billpaymentCollection;

    public Employee() {
    }

    public Employee(Integer empId) {
        this.empId = empId;
    }

    public Employee(Integer empId, String empName, String nic, String empAddress, int empPhone, String empEmail, int empType) {
        this.empId = empId;
        this.empName = empName;
        this.nic = nic;
        this.empAddress = empAddress;
        this.empPhone = empPhone;
        this.empEmail = empEmail;
        this.empType = empType;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        Integer oldEmpId = this.empId;
        this.empId = empId;
        changeSupport.firePropertyChange("empId", oldEmpId, empId);
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        String oldEmpName = this.empName;
        this.empName = empName;
        changeSupport.firePropertyChange("empName", oldEmpName, empName);
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        String oldNic = this.nic;
        this.nic = nic;
        changeSupport.firePropertyChange("nic", oldNic, nic);
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        String oldEmpAddress = this.empAddress;
        this.empAddress = empAddress;
        changeSupport.firePropertyChange("empAddress", oldEmpAddress, empAddress);
    }

    public int getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(int empPhone) {
        int oldEmpPhone = this.empPhone;
        this.empPhone = empPhone;
        changeSupport.firePropertyChange("empPhone", oldEmpPhone, empPhone);
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        String oldEmpEmail = this.empEmail;
        this.empEmail = empEmail;
        changeSupport.firePropertyChange("empEmail", oldEmpEmail, empEmail);
    }

    public int getEmpType() {
        return empType;
    }

    public void setEmpType(int empType) {
        int oldEmpType = this.empType;
        this.empType = empType;
        changeSupport.firePropertyChange("empType", oldEmpType, empType);
    }

    @XmlTransient
    public Collection<Billpayment> getBillpaymentCollection() {
        return billpaymentCollection;
    }

    public void setBillpaymentCollection(Collection<Billpayment> billpaymentCollection) {
        this.billpaymentCollection = billpaymentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "model.Employee[ empId=" + empId + " ]";
        return empId.toString();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
