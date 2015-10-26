/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlibrary.borrow.service;

import hashlibrary.borrow.Borrow;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author lenovo
 */
@Stateless
@Path("borrow")
public class BorrowFacadeREST extends AbstractFacade<Borrow> {

    @PersistenceContext(unitName = "com.mycompany_hashlibrary_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public BorrowFacadeREST() {
        super(Borrow.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Borrow entity) {
        super.create(entity);
    }

    //添加用户借书信息
    @POST
    @Path("borrowBooks/{userNum}/{bookId}")
    @Consumes({"application/json"})
    public void borrowBooks(@PathParam("userNum") String userNum, @PathParam("bookId") Integer bookId, Borrow entity) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        System.out.println("现在时间： " + df.format(new Date()));// new Date()为获取当前系统时间作为借书时间

        Query query = em.createQuery("SELECT MAX(b.borrowNum) FROM Borrow b");
        Object o = query.getSingleResult();
        Long maxBorrowId = Long.valueOf(String.valueOf(o));
        System.out.println("maxBorrowId: " + maxBorrowId);
        Object insertBorrowInfo = em.createNativeQuery("insert into borrow.borrow (borrow_num, user_id, borrow_time, book_id) values (5,20140001,'2015-5-5',2)")
                .setParameter("borrowNum", maxBorrowId + 1)
                .setParameter("userId", userNum)
                .setParameter("borrowTime", new Date())
                .setParameter("bookId", bookId)
                .getSingleResult();

        
//        em.persist(o);
//        em.getTransaction().begin();
//        entity.setBorrowNum(maxBorrowId);
//        entity.setBookId(bookId);
//        entity.setUserId(userNum);
//        entity.setBorrowTime(new Date());
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Borrow entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Borrow find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Borrow> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Borrow> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
