/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashlibrary.borrow1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "borrow1", catalog = "hashlib", schema = "borrow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrow1.findAll", query = "SELECT b FROM Borrow1 b"),
    @NamedQuery(name = "Borrow1.findByBorrowNum", query = "SELECT b FROM Borrow1 b WHERE b.borrowNum = :borrowNum"),
    @NamedQuery(name = "Borrow1.findByUserId", query = "SELECT b FROM Borrow1 b WHERE b.userId = :userId"),
    @NamedQuery(name = "Borrow1.findByBorrowTime", query = "SELECT b FROM Borrow1 b WHERE b.borrowTime = :borrowTime"),
    @NamedQuery(name = "Borrow1.findByBookId", query = "SELECT b FROM Borrow1 b WHERE b.bookId = :bookId")})
public class Borrow1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "borrow_num")
    private Integer borrowNum;
    @Size(max = 128)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "borrow_time")
    @Temporal(TemporalType.DATE)
    private Date borrowTime;
    @Column(name = "book_id")
    private Integer bookId;

    public Borrow1() {
    }

    public Borrow1(Integer borrowNum) {
        this.borrowNum = borrowNum;
    }

    public Integer getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(Integer borrowNum) {
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
        if (!(object instanceof Borrow1)) {
            return false;
        }
        Borrow1 other = (Borrow1) object;
        if ((this.borrowNum == null && other.borrowNum != null) || (this.borrowNum != null && !this.borrowNum.equals(other.borrowNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hashlibrary.borrow1.Borrow1[ borrowNum=" + borrowNum + " ]";
    }
    
}
