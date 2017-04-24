/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.DAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ProfessionalDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.AdminService;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Remote(AdminService.class)
@Stateless
public class AdminServiceImpl implements AdminService {
    
    @EJB
    private DAO<UserAccount> adminDAO;
    @EJB
    private ProfessionalDAO professionalDAO;

    @Override
    public void save(UserAccount user) {
        adminDAO.persist(user);
    }

    @Override
    public void approveSolicitation(Professional professional, boolean approve) {
        professionalDAO.updateRegisterRequestStatus(professional.getCpf(),
                approve ? RegisterRequestStatus.ACCEPTED : RegisterRequestStatus.DENIED);
    }
    
}
