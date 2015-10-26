/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlibrary.book.service;

import hashlibrary.book.Book;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 *
 * @author lenovo
 */
@Stateless
@Path("book")
public class BookFacadeREST extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "com.mycompany_hashlibrary_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public BookFacadeREST() {
        super(Book.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Book entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Book entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    //查找用户搜索的书籍
    @GET
    @Path("searchBook")
    @Produces({"application/xml", "application/json"})
    public List<Book> SearchBook(@Context HttpServletRequest request, @Context HttpServletResponse Response) {
        String search = request.getParameter("searchContent");
        List<Book> list = em.createQuery("select b from Book b where upper(b.bookName) like upper(:search)")
                .setParameter("search", "%" + search + "%").getResultList();
        return list;
    }

    //查询用户所借书籍
    @GET
    @Path("borrowBookInfos/{userNum}")
    @Produces({"application/xml", "application/json"})
    public JsonArray borrowBookInfos(@PathParam("userNum") String userNum) {
        Query query = em.createNativeQuery("select user_id, b1.book_id, book_name, book_author, book_press,book_description,book_score,borrow_time\n"
                + "from book.book b1, borrow.borrow1 b2\n"
                + "where b1.book_id = b2.book_id and b2.user_id=?")
                .setParameter(1, userNum);
        List result = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object r : result) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            Object[] row = (Object[]) r;
            objectBuilder.add("user_id", Objects.toString(row[0]));
            objectBuilder.add("book_id", Objects.toString(row[1]));
            objectBuilder.add("book_name", Objects.toString(row[2]));
            objectBuilder.add("book_author", Objects.toString(row[3]));
            objectBuilder.add("book_press", Objects.toString(row[4]));
            objectBuilder.add("book_description", Objects.toString(row[5]));
            objectBuilder.add("book_score", Objects.toString(row[6]));
            objectBuilder.add("borrow_time", Objects.toString(row[7]));
            arrayBuilder.add(objectBuilder);
        }
        return arrayBuilder.build();
    }

    //查询用户催还书籍
    @GET
    @Path("dueBookInfos/{userNum}")
    @Produces({"application/xml", "application/json"})
    public JsonArray dueBookInfos(@PathParam("userNum") String userNum) {
        Query query = em.createNativeQuery("select user_id, b1.book_id, book_name, book_author, book_press,book_description,book_score,borrow_time\n"
                + "from book.book b1, borrow.borrow1 b2\n"
                + "where b1.book_id = b2.book_id \n"
                + "and b2.borrow_time > CURRENT_DATE - INTERVAL '60' day\n"
                + "and b2.borrow_time < CURRENT_DATE - INTERVAL '50' day\n"
                + "and b2.user_id=?")
                .setParameter(1, userNum);
        List result = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object r : result) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            Object[] row = (Object[]) r;
            objectBuilder.add("user_id", Objects.toString(row[0]));
            objectBuilder.add("book_id", Objects.toString(row[1]));
            objectBuilder.add("book_name", Objects.toString(row[2]));
            objectBuilder.add("book_author", Objects.toString(row[3]));
            objectBuilder.add("book_press", Objects.toString(row[4]));
            objectBuilder.add("book_description", Objects.toString(row[5]));
            objectBuilder.add("book_score", Objects.toString(row[6]));
            objectBuilder.add("borrow_time", Objects.toString(row[7]));
            arrayBuilder.add(objectBuilder);
        }
        return arrayBuilder.build();
    }

    //刚进入book.html时随机显示六本书
    @GET
    @Path("randomBook")
    @Produces({"application/xml", "application/json"})
    public List<Book> randomBook(@Context HttpServletRequest request, @Context HttpServletResponse Response) {
        //设定随机数
        int books[] = new int[25];
        int book[] = new int[6];
        for (int i = 1; i <= 25; i++) {
            books[i - 1] = i;
        }
        for (int i = 0; i < 6; i++) {
            book[i] = books[(int) (Math.random() * 25)];
        }

        List<Book> list = em.createQuery("select b from Book b where b.bookId = :bookid1 or b.bookId = :bookid2 or b.bookId = :bookid3 or "
                + "b.bookId = :bookid4 or b.bookId = :bookid5 or b.bookId = :bookid6")
                .setParameter("bookid1", book[0])
                .setParameter("bookid2", book[1])
                .setParameter("bookid3", book[2])
                .setParameter("bookid4", book[3])
                .setParameter("bookid5", book[4])
                .setParameter("bookid6", book[5])
                .getResultList();
        return list;
    }

    //查询书籍总数量、可借数量
    @GET
    @Path("bookNum")
    @Produces({"application/xml", "application/json"})
    public List<Book> bookNum(@Context HttpServletRequest request, @Context HttpServletResponse Response) {
        Long bookId = Long.parseLong(request.getParameter("bookId"));
        List<Book> borrowBookInfo = em.createQuery("select b from Book b where b.bookId = :bookId")
                .setParameter("bookId", bookId).getResultList();
        return borrowBookInfo;
    }

    //借阅书籍（将书籍表中可借数量减一）
    @POST
    @Path("borrowBook/{bookId}")
    @Consumes({"application/json"})
    public void boorowBook(@PathParam("bookId") Long bookId) {
        Book entity = em.find(Book.class, bookId);
        entity.setBookRemainNum(entity.getBookRemainNum() - 1);
        em.persist(entity);
    }

    //管理员新书入库
    @POST
    @Path("newBookInput/{bookName}/{bookAuthor}/{bookPress}/{bookDescribe}/{bookScore}/{bookState}/{bookShelf}/{bookWholeNum}/{bookRemainNum}")
    @Consumes({"application/json"})
    public void postAlterUserInfo(@PathParam("bookName") String bookName, @PathParam("bookAuthor") String bookAuthor,
            @PathParam("bookPress") String bookPress, @PathParam("bookDescribe") String bookDescribe,
            @PathParam("bookScore") Integer bookScore, @PathParam("bookState") String bookState,
            @PathParam("bookShelf") String bookShelf, @PathParam("bookWholeNum") Integer bookWholeNum, @PathParam("bookRemainNum") Integer bookRemainNum) {

        Query query1 = em.createQuery("select max(b.bookId) from Book b");
        List<Book> list1 = query1.getResultList();
        int newId = Integer.parseInt(list1.toArray()[0].toString());
        System.out.println("newId: " + newId);

        Query query = em.createNativeQuery("insert into book.book (book_id,book_name,book_author,book_press,book_description,book_score,book_state,"
                + "bookshelf_number,book_whole_num,book_remain_num) values (?,?,?,?,?,?,?,?,?,?)")
                .setParameter(1, newId + 1)
                .setParameter(2, bookName)
                .setParameter(3, bookAuthor)
                .setParameter(4, bookPress)
                .setParameter(5, bookDescribe)
                .setParameter(6, bookScore)
                .setParameter(7, bookState)
                .setParameter(8, bookShelf)
                .setParameter(9, bookWholeNum)
                .setParameter(10, bookRemainNum);
        int row = query.executeUpdate();
        System.out.println("row" + row);

    }

    //test
    @GET
    @Path("testBook")
    @Consumes({"application/json"})
    public void test() {
        Query query1 = em.createQuery("select max(b.bookId) from Book b");
        List<Book> list = query1.getResultList();
        System.out.println(Integer.parseInt(list.toArray()[0].toString()));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Book find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Book> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Book> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
