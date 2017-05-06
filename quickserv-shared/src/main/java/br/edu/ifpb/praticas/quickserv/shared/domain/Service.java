/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import br.edu.ifpb.praticas.quickserv.shared.vo.ServicePK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Pedro Arthur
 * 
 */

@Entity
@IdClass(ServicePK.class)
public class Service implements Serializable {
    
    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;
    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_proposal_id")
    private ServiceProposal serviceProposal;
    
    @Enumerated(EnumType.STRING)
    private Evaluation evaluation;

    public Service(ServiceRequest serviceRequest, ServiceProposal serviceProposal) {
        this.serviceRequest = serviceRequest;
        this.serviceProposal = serviceProposal;
        this.evaluation = Evaluation.NONE;
    }
    
    public Service() {
        this.serviceRequest = new ServiceRequest();
        this.serviceProposal = new ServiceProposal();
        this.evaluation = Evaluation.NONE;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public ServiceProposal getServiceProposal() {
        return serviceProposal;
    }

    public void setServiceProposal(ServiceProposal serviceProposal) {
        this.serviceProposal = serviceProposal;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceRequest=" + serviceRequest + ", serviceProposal=" + serviceProposal + ", evaluation=" + evaluation + '}';
    }
}
