/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.config.DataSourceDefinitionConfig;
import br.edu.ifpb.praticas.quickserv.core.dao.impl.AdminDAOJpaImpl;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.AdminDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.AdminService;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Pedro Arthur
 */

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
public class StartupBeanIT {
    
    @EJB
    private StartupBean startupBean;
    @EJB
    private AdminDAO dao;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "quickserv-test.war")
                .addClass(DataSourceDefinitionConfig.class)
                .addAsResource("META-INF/persistence.xml")
                .addClass(UserAccount.class)
                .addClass(AdminDAO.class)
                .addClass(AdminDAOJpaImpl.class)
                .addClass(StartupBean.class);
    }
    
    @Test
    @InSequence(1)
    public void testSavedAdmin() {
        assertEquals(true, dao.hasAnAdminRegistered());
    }
    
}
