/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import br.edu.ifpb.praticas.quickserv.shared.enums.Avaliation;
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
    
    @Embedded
    private Period choosenDate;
    
    @Enumerated(EnumType.STRING)
    private Avaliation avaliation;

    public Service(ServiceRequest serviceRequest, ServiceProposal serviceProposal, Period choosenData) {
        this.serviceRequest = serviceRequest;
        this.serviceProposal = serviceProposal;
        this.choosenDate = choosenData;
        this.avaliation = Avaliation.NONE;
    }
    
    public Service() {
        this.serviceRequest = new ServiceRequest();
        this.serviceProposal = new ServiceProposal();
        this.choosenDate = new Period();
        this.avaliation = Avaliation.NONE;
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

    public Period getChoosenDate() {
        return choosenDate;
    }

    public void setChoosenDate(Period choosenDate) {
        this.choosenDate = choosenDate;
    }

    public Avaliation getAvaliation() {
        return avaliation;
    }

    public void setAvaliation(Avaliation avaliation) {
        this.avaliation = avaliation;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceRequest=" + serviceRequest + ", serviceProposal=" + serviceProposal + ", choosenDate=" + choosenDate + ", avaliation=" + avaliation + '}';
    }
}
