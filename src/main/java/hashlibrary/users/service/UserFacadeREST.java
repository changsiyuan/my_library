/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlibrary.users.service;

import hashlibrary.users.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("users")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "com.mycompany_hashlibrary_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, User entity) {
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
    public User find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    //获取用户基本信息
    @GET
    @Path("userInfo/{studentNum}")
    @Produces({"application/xml", "application/json"})
    public List<User> userInfo(@PathParam("studentNum") Long studentNum) {
        List<User> userInfomation = em.createQuery("SELECT u FROM User u WHERE u.userId= :studentNum")
                .setParameter("studentNum", studentNum)
                .getResultList();
        return userInfomation;
    }

    //将新注册用户的用户名、学号存入users表中
    @POST
    @Path("newUserInfo/{studentNum}/{user_name}")
    @Consumes({"application/json"})
    public void postNewUserInfo(@PathParam("studentNum") Long studentNum,
            @PathParam("user_name") String user_name, User entity) {
        entity.setUserId(studentNum);
        entity.setUserName(user_name);
        em.persist(entity);
    }

    //将用户修改的用户基本信息存入users表中
    @POST
    @Path("alterUserInfo/{studentNum}/{user_name}/{user_sex}/{user_school}/{user_description}/{user_phoneNum}")
    @Consumes({"application/json"})
    public void postAlterUserInfo(@PathParam("studentNum") Long studentNum, @PathParam("user_name") String user_name,
            @PathParam("user_sex") String user_sex, @PathParam("user_school") String user_school,
            @PathParam("user_description") String user_description, @PathParam("user_phoneNum") String user_phoneNum) {

        User entity = em.find(User.class, studentNum);
        entity.setUserName(user_name);
        entity.setUserSex(user_sex);
        entity.setUserSchool(user_school);
        entity.setUserDescription(user_description);
        entity.setPhoneNumber(user_phoneNum);
        em.persist(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
