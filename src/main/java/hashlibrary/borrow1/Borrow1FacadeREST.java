/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlibrary.borrow1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.TemporalType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author lenovo
 */
@Stateless
@Path("borrow1")
public class Borrow1FacadeREST extends AbstractFacade<Borrow1> {

    @PersistenceContext(unitName = "com.mycompany_hashlibrary_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Borrow1FacadeREST() {
        super(Borrow1.class);
    }

    @GET
    @Path("addRecord")
    public void addRecord(@QueryParam("userNum") String userNum,
            @QueryParam("bookId") int bookId) {
        Borrow1 b = new Borrow1();  //borrow1是一个entity，实例化成为b
        b.setBookId(bookId);
        b.setUserId(userNum);
        b.setBorrowTime(new Date());
        super.create(b);
    }

    //查询某用户借书总数
    @GET
    @Path("borrowBookNum/{userNum}")
    @Produces({"application/xml", "application/json"})
    public List<Borrow1> borrowBookNum(@PathParam("userNum") String userNum) {
        List<Borrow1> userBorrowBookNum = em.createQuery("select b from Borrow1 b where b.userId = :userNum")
                .setParameter("userNum", userNum)
                .getResultList();
        return userBorrowBookNum;
    }

    //查询某用户催还数量
    @GET
    @Path("dueBookNum/{userNum}")
    @Produces({"application/xml", "application/json"})
    public List<Borrow1> dueBookNum(@PathParam("userNum") String userNum) {

        Date d = new Date();
        Calendar now = Calendar.getInstance();

        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 60);
        Date startDate = now.getTime();

        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 50);
        Date endDate = now.getTime();
        System.out.println("startTime：" + startDate);
        System.out.println("endTime：" + endDate);

        List<Borrow1> userDueBookNum = em.createQuery("select b from Borrow1 b where b.userId = :userNum and b.borrowTime BETWEEN :startDate AND :endDate")
                .setParameter("userNum", userNum)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return userDueBookNum;
    }

    //管理员删除用户前检测该用户是否有书籍尚未归还
    @GET
    @Path("judgeUserBook/{userId}")
    @Produces({"application/xml", "application/json"})
    public List<Borrow1> judgeUserBook(@PathParam("userId") String userId) {
        List<Borrow1> judgeIfUserBook = em.createQuery("select b from Borrow1 b where b.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
        return judgeIfUserBook;
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Borrow1 entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, Borrow1 entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Borrow1 find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Borrow1> findAll() {
        return super.findAll();
    }

    //管理员查询用户借书情况
    @GET
    @Path("userBorrowBookInfos")
    @Produces({"application/xml", "application/json"})
    public JsonArray userBorrowBookInfos() {
        Query query = em.createNativeQuery("select borrow_num, user_id, borrow_time, b1.book_id, book_name\n"
                + "from book.book b1, borrow.borrow1 b2\n"
                + "where b1.book_id = b2.book_id");

        List result = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object r : result) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            Object[] row = (Object[]) r;
            objectBuilder.add("borrow_num", Objects.toString(row[0]));
            objectBuilder.add("user_id", Objects.toString(row[1]));
            objectBuilder.add("borrow_time", Objects.toString(row[2]));
            objectBuilder.add("book_id", Objects.toString(row[3]));
            objectBuilder.add("book_name", Objects.toString(row[4]));
            
            arrayBuilder.add(objectBuilder);
        }
        return arrayBuilder.build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Borrow1> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
