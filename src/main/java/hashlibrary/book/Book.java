/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashlibrary.book;

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
@Table(name = "book", catalog = "hashlib", schema = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByBookId", query = "SELECT b FROM Book b WHERE b.bookId = :bookId"),
    @NamedQuery(name = "Book.findByBookName", query = "SELECT b FROM Book b WHERE b.bookName = :bookName"),
    @NamedQuery(name = "Book.findByBookAuthor", query = "SELECT b FROM Book b WHERE b.bookAuthor = :bookAuthor"),
    @NamedQuery(name = "Book.findByBookPress", query = "SELECT b FROM Book b WHERE b.bookPress = :bookPress"),
    @NamedQuery(name = "Book.findByBookDescription", query = "SELECT b FROM Book b WHERE b.bookDescription = :bookDescription"),
    @NamedQuery(name = "Book.findByBookScore", query = "SELECT b FROM Book b WHERE b.bookScore = :bookScore"),
    @NamedQuery(name = "Book.findByBookState", query = "SELECT b FROM Book b WHERE b.bookState = :bookState"),
    @NamedQuery(name = "Book.findByBookshelfNumber", query = "SELECT b FROM Book b WHERE b.bookshelfNumber = :bookshelfNumber"),
    @NamedQuery(name = "Book.findByBookWholeNum", query = "SELECT b FROM Book b WHERE b.bookWholeNum = :bookWholeNum"),
    @NamedQuery(name = "Book.findByBookRemainNum", query = "SELECT b FROM Book b WHERE b.bookRemainNum = :bookRemainNum")})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_id")
    private Long bookId;
    @Size(max = 255)
    @Column(name = "book_name")
    private String bookName;
    @Size(max = 64)
    @Column(name = "book_author")
    private String bookAuthor;
    @Size(max = 64)
    @Column(name = "book_press")
    private String bookPress;
    @Size(max = 255)
    @Column(name = "book_description")
    private String bookDescription;
    @Column(name = "book_score")
    private Integer bookScore;
    @Size(max = 64)
    @Column(name = "book_state")
    private String bookState;
    @Size(max = 128)
    @Column(name = "bookshelf_number")
    private String bookshelfNumber;
    @Column(name = "book_whole_num")
    private Integer bookWholeNum;
    @Column(name = "book_remain_num")
    private Integer bookRemainNum;

    public Book() {
    }

    public Book(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Integer getBookScore() {
        return bookScore;
    }

    public void setBookScore(Integer bookScore) {
        this.bookScore = bookScore;
    }

    public String getBookState() {
        return bookState;
    }

    public void setBookState(String bookState) {
        this.bookState = bookState;
    }

    public String getBookshelfNumber() {
        return bookshelfNumber;
    }

    public void setBookshelfNumber(String bookshelfNumber) {
        this.bookshelfNumber = bookshelfNumber;
    }

    public Integer getBookWholeNum() {
        return bookWholeNum;
    }

    public void setBookWholeNum(Integer bookWholeNum) {
        this.bookWholeNum = bookWholeNum;
    }

    public Integer getBookRemainNum() {
        return bookRemainNum;
    }

    public void setBookRemainNum(Integer bookRemainNum) {
        this.bookRemainNum = bookRemainNum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hashlibrary.book.Book[ bookId=" + bookId + " ]";
    }
    
}
