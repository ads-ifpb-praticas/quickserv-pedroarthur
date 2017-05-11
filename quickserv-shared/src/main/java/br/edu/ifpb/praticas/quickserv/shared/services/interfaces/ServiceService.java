/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.services.interfaces;

import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ServiceService {
    void newService(ServiceRequest request, ServiceProposal proposal);
    void evaluate(Service service, Evaluation evaluation);
    List<Service> listServicesByClient(Client client);
    List<Service> listServicesByProfessional(Professional professional);
    Long countByClient(Client client);
    Long countByProfessional(Professional professional);
}
