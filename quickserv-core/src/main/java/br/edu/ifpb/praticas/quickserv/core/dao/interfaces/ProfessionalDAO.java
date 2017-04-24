/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;

/**
 *
 * @author Pedro Arthur
 */
public interface ProfessionalDAO extends DAO<Professional> {
    
    void updateRegisterRequestStatus(String professionalCpf, RegisterRequestStatus status);
    boolean isCpfInUse(String cpf);
    Professional getByUsername(String username);
}
