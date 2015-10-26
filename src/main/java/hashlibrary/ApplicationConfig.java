/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashlibrary.account.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author lenovo
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(hashlibrary.account.service.AccountFacadeREST.class);
        resources.add(hashlibrary.adminaccount.service.AdminaccountFacadeREST.class);
        resources.add(hashlibrary.book.service.BookFacadeREST.class);
        resources.add(hashlibrary.borrow.service.BorrowFacadeREST.class);
        resources.add(hashlibrary.borrow1.Borrow1FacadeREST.class);
        resources.add(hashlibrary.users.service.UserFacadeREST.class);
    }
    
}
