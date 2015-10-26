/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlibrary.account.service;

import hashlibrary.account.Account;
import java.security.Principal;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author lenovo
 */
@Stateless
@Path("account")
public class AccountFacadeREST extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "com.mycompany_hashlibrary_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AccountFacadeREST() {
        super(Account.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Account entity) {
        super.create(entity);
    }

    //用户注册-原始版
    @POST
    @Path("newUser/{studentNum}/{user_name}/{passward}")
    @Consumes({"application/json"})
    public void addConfiguration(@PathParam("studentNum") Long studentNum,
            @PathParam("user_name") String user_name, @PathParam("passward") String passward, Account entity) {
        entity.setUserId(studentNum);
        entity.setUserName(user_name);
        entity.setPassword(passward);
        em.persist(entity);
    }

    //将修改的用户名存入account表
    @POST
    @Path("postAlterUserName/{studentNum}/{user_name}")
    @Consumes({"application/json"})
    public void postAlterUserName(@PathParam("studentNum") Long studentNum,
            @PathParam("user_name") String user_name) {
        Account entity = em.find(Account.class, studentNum);
        entity.setUserName(user_name);
        em.persist(entity);
    }
    
    //管理员重置用户密码
    @POST
    @Path("passwdReset/{userId}")
    @Consumes({"application/json"})
    public void passwdReset(@PathParam("userId") Long userId) {
        Account entity = em.find(Account.class, userId);
        entity.setPassword("123456");
        em.persist(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Account entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("deleteUser/{userId}")
    public void remove(@PathParam("userId") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Account find(@PathParam("id") Long id) {
        return super.find(id);
    }

    //检测用户是否已经存在-原始版
    @GET
    @Path("newUser/{studentNum}")
    @Produces({"application/xml", "application/json"})
    public List<Account> testUserExist(@PathParam("studentNum") Long studentNum) {
        List<Account> testUser = em.createQuery("SELECT a FROM Account a WHERE a.userId= :studentNum")
                .setParameter("studentNum", studentNum)
                .getResultList();
        return testUser;
    }

    //用户登录--原始版
    @GET
    @Path("UserLogin/{studentNum}/{password}")
    @Produces({"application/xml", "application/json"})
    public List<Account> UserLogin(@PathParam("studentNum") Long studentNum, @PathParam("password") String password) {
        List<Account> login = em.createQuery("SELECT a FROM Account a WHERE a.userId= :studentNum and a.password= :password")
                .setParameter("studentNum", studentNum)
                .setParameter("password", password)
                .getResultList();
        return login;
    }

    //获取旧密码，以便检测旧密码是否正确
    @GET
    @Path("getOldPasswd/{studentNum}")
    @Produces({"application/xml", "application/json"})
    public List<Account> getOldPasswd(@PathParam("studentNum") Long studentNum) {
        List<Account> oldPasswd = em.createQuery("SELECT a FROM Account a WHERE a.userId= :studentNum")
                .setParameter("studentNum", studentNum)
                .getResultList();
        return oldPasswd;
    }

    //修改密码
    @POST
    @Path("alterPasswd/{studentNum}/{newPasswd}")
    @Consumes({"application/json"})
    public void alterPasswd(@PathParam("studentNum") Long studentNum, @PathParam("newPasswd") String newPasswd) {
        Account entity = em.find(Account.class, studentNum);
        entity.setPassword(newPasswd);
        em.persist(entity);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Account> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Account> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
