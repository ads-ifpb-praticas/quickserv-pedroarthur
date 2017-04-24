/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Pedro Arthur
 */

public class ServicePK implements Serializable {
    
    private Long serviceRequest;
    private Long serviceProposal;

    public ServicePK(Long serviceRequest, Long serviceProposal) {
        this.serviceRequest = serviceRequest;
        this.serviceProposal = serviceProposal;
    }

    public Long getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(Long serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Long getServiceProposal() {
        return serviceProposal;
    }

    public void setServiceProposal(Long serviceProposal) {
        this.serviceProposal = serviceProposal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.serviceRequest);
        hash = 89 * hash + Objects.hashCode(this.serviceProposal);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServicePK other = (ServicePK) obj;
        if (!Objects.equals(this.serviceRequest, other.serviceRequest)) {
            return false;
        }
        if (!Objects.equals(this.serviceProposal, other.serviceProposal)) {
            return false;
        }
        return true;
    }
    
    
}
