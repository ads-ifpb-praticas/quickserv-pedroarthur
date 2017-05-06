/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.AdminDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.DAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ProfessionalDAO;
import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.RegisterRequestDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.dto.ProfessionalRegisterRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.services.interfaces.AdminService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    private AdminDAO adminDAO;
    @EJB
    private RegisterRequestDAO registerRequestDAO;

    @Override
    public void save(UserAccount user) {
        try {
            validate(user);
            adminDAO.persist(user);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }

    private void validate(UserAccount user) {
        if (user == null) {
            throw new IllegalArgumentException("Você precisa passar um usuário admin válido");
        }
    }

    @Override
    public void approveSolicitation(RegisterRequest registerRequest, boolean approve) {
        try {
            validate(registerRequest);
            registerRequest.setStatus(approve
                    ? RegisterRequestStatus.ACCEPTED : RegisterRequestStatus.DENIED);
            registerRequestDAO.update(registerRequest);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }

    private void validate(RegisterRequest registerRequest) {
        if (!registerRequestDAO.isPendent(registerRequest.getId())) {
            throw new IllegalArgumentException("The request was already responded!");
        }
    }
    
    @Override
    public List<ProfessionalRegisterRequest> listProfessionalsRegisterRequestsByStatus(RegisterRequestStatus status) {
        return registerRequestDAO.listProfessionalRequestsByStatus(status);
    }

}
