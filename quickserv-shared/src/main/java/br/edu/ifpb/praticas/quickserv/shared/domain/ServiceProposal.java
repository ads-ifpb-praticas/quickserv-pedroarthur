/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Pedro Arthur
 */

@Entity
@Table(name = "service_proposal")
@SequenceGenerator(
        initialValue = 1,
        allocationSize = 1,
        name = "service_prop_seq_gen",
        sequenceName = "service_prop_seq"
)
public class ServiceProposal implements Serializable {
    
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_prop_seq_gen"
    )
    private Long id;
    private String description;
    private BigDecimal price;
    
    @Column(name = "choosed_date")
    private LocalDateTime choosedDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professional_cpf")
    private Professional professional;

    public ServiceProposal(String description, BigDecimal price, ServiceRequest serviceRequest, Professional professional, LocalDateTime choosedDate) {
        this.description = description;
        this.price = price;
        this.serviceRequest = serviceRequest;
        this.professional = professional;
        this.choosedDate = choosedDate;
    }

    public ServiceProposal() {
        this.professional = new Professional();
        this.serviceRequest = new ServiceRequest();
        this.price = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }
    
    public LocalDateTime getChoosedDate() {
        return this.choosedDate;
    }

    public void setChoosedDate(LocalDateTime choosedDate) {
        this.choosedDate = choosedDate;
    }

    @Override
    public String toString() {
        return "ServiceProposal{" + "id=" + id + ", description=" + description + ", price=" + price + ", choosedDate=" + choosedDate + ", serviceRequest=" + serviceRequest + ", professional=" + professional + '}';
    }
}
