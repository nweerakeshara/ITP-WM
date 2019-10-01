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
@Table(name = "consumers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumers.findAll", query = "SELECT c FROM Consumers c")
    , @NamedQuery(name = "Consumers.findByUsername", query = "SELECT c FROM Consumers c WHERE c.username = :username")
    , @NamedQuery(name = "Consumers.findByPassword", query = "SELECT c FROM Consumers c WHERE c.password = :password")
    , @NamedQuery(name = "Consumers.findByRoles", query = "SELECT c FROM Consumers c WHERE c.roles = :roles")
    , @NamedQuery(name = "Consumers.findBySecQ", query = "SELECT c FROM Consumers c WHERE c.secQ = :secQ")
    , @NamedQuery(name = "Consumers.findByAnswer", query = "SELECT c FROM Consumers c WHERE c.answer = :answer")})
public class Consumers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    private String roles;
    @Column(name = "sec_q")
    private String secQ;
    @Column(name = "answer")
    private String answer;

    public Consumers() {
    }

    public Consumers(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getSecQ() {
        return secQ;
    }

    public void setSecQ(String secQ) {
        this.secQ = secQ;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumers)) {
            return false;
        }
        Consumers other = (Consumers) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Consumers[ username=" + username + " ]";
    }
    
}
