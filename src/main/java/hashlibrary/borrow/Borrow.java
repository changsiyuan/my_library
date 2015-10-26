/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashlibrary.borrow;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "borrow", catalog = "hashlib", schema = "borrow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrow.findAll", query = "SELECT b FROM Borrow b"),
    @NamedQuery(name = "Borrow.findByBorrowNum", query = "SELECT b FROM Borrow b WHERE b.borrowNum = :borrowNum"),
    @NamedQuery(name = "Borrow.findByUserId", query = "SELECT b FROM Borrow b WHERE b.userId = :userId"),
    @NamedQuery(name = "Borrow.findByBorrowTime", query = "SELECT b FROM Borrow b WHERE b.borrowTime = :borrowTime"),
    @NamedQuery(name = "Borrow.findByBookId", query = "SELECT b FROM Borrow b WHERE b.bookId = :bookId")})
public class Borrow implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "borrow_num")
    private Long borrowNum;
    @Size(max = 128)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "borrow_time")
    @Temporal(TemporalType.DATE)
    private Date borrowTime;
    @Column(name = "book_id")
    private Integer bookId;

    public Borrow() {
    }

    public Borrow(Long borrowNum) {
        this.borrowNum = borrowNum;
    }

    public Long getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(Long borrowNum) {
        this.borrowNum = borrowNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (borrowNum != null ? borrowNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrow)) {
            return false;
        }
        Borrow other = (Borrow) object;
        if ((this.borrowNum == null && other.borrowNum != null) || (this.borrowNum != null && !this.borrowNum.equals(other.borrowNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hashlibrary.borrow.Borrow[ borrowNum=" + borrowNum + " ]";
    }
    
}
