/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashlibrary.adminaccount;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "adminaccount", catalog = "hashlib", schema = "adminaccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adminaccount.findAll", query = "SELECT a FROM Adminaccount a"),
    @NamedQuery(name = "Adminaccount.findById", query = "SELECT a FROM Adminaccount a WHERE a.id = :id"),
    @NamedQuery(name = "Adminaccount.findByPassword", query = "SELECT a FROM Adminaccount a WHERE a.password = :password")})
public class Adminaccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 32)
    @Column(name = "password")
    private String password;

    public Adminaccount() {
    }

    public Adminaccount(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adminaccount)) {
            return false;
        }
        Adminaccount other = (Adminaccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hashlibrary.adminaccount.Adminaccount[ id=" + id + " ]";
    }
    
}
