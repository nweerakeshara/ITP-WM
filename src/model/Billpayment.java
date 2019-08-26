/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thanuja Chamika
 */
@Entity
@Table(name = "billpayment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Billpayment.findAll", query = "SELECT b FROM Billpayment b")
    , @NamedQuery(name = "Billpayment.findByPid", query = "SELECT b FROM Billpayment b WHERE b.pid = :pid")
    , @NamedQuery(name = "Billpayment.findByMonth", query = "SELECT b FROM Billpayment b WHERE b.month = :month")
    , @NamedQuery(name = "Billpayment.findByAmount", query = "SELECT b FROM Billpayment b WHERE b.amount = :amount")})
public class Billpayment implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pid")
    private Integer pid;
    @Basic(optional = false)
    @Column(name = "month")
    private int month;
    @Basic(optional = false)
    @Column(name = "amount")
    private int amount;
    @JoinColumn(name = "eid", referencedColumnName = "empId")
    @ManyToOne(optional = false)
    private Employee eid;
    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Billtype type;

    public Billpayment() {
    }

    public Billpayment(Integer pid) {
        this.pid = pid;
    }

    public Billpayment(Integer pid, int month, int amount) {
        this.pid = pid;
        this.month = month;
        this.amount = amount;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        Integer oldPid = this.pid;
        this.pid = pid;
        changeSupport.firePropertyChange("pid", oldPid, pid);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        int oldMonth = this.month;
        this.month = month;
        changeSupport.firePropertyChange("month", oldMonth, month);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        int oldAmount = this.amount;
        this.amount = amount;
        changeSupport.firePropertyChange("amount", oldAmount, amount);
    }

    public Employee getEid() {
        return eid;
    }

    public void setEid(Employee eid) {
        Employee oldEid = this.eid;
        this.eid = eid;
        changeSupport.firePropertyChange("eid", oldEid, eid);
    }

    public Billtype getType() {
        return type;
    }

    public void setType(Billtype type) {
        Billtype oldType = this.type;
        this.type = type;
        changeSupport.firePropertyChange("type", oldType, type);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Billpayment)) {
            return false;
        }
        Billpayment other = (Billpayment) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Billpayment[ pid=" + pid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
