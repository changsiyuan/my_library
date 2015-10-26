/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlibrary.adminaccount.service;

import hashlibrary.adminaccount.Adminaccount;
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
@Path("adminaccount")
public class AdminaccountFacadeREST extends AbstractFacade<Adminaccount> {

    @PersistenceContext(unitName = "com.mycompany_hashlibrary_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AdminaccountFacadeREST() {
        super(Adminaccount.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Adminaccount entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Adminaccount entity) {
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
    public Adminaccount find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("adminLogin/{adminNum}/{adminPasswd}")
    @Produces({"application/xml", "application/json"})
    public List<Adminaccount> adminLogin(@PathParam("adminNum") Long adminNum, @PathParam("adminPasswd") String adminPasswd) {
        List<Adminaccount> list = em.createQuery("select a from Adminaccount a where a.id=:adminNum and a.password = :adminPasswd")
                .setParameter("adminNum", adminNum)
                .setParameter("adminPasswd", adminPasswd)
                .getResultList();
        return list;
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Adminaccount> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Adminaccount> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
