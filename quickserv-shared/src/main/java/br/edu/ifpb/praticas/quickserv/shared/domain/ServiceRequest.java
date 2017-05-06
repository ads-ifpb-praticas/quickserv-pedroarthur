/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.converter.LocalDateTimeConverter;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceRequestStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Pedro Arthur
 */

@Entity
@Table(name = "service_request")
@SequenceGenerator(
        initialValue = 1,
        allocationSize = 1,
        name = "service_req_seq_gen",
        sequenceName = "service_req_seq"
)
public class ServiceRequest implements Serializable {
    
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_req_seq_gen"
    )
    private Long id;
    @NotEmpty(message = "Este campo é obrigatório")
    private String title;
    @NotEmpty(message = "Este campo é obrigatório")
    private String description;
    
    @Embedded
    private Address locate;
    
    @Enumerated(EnumType.STRING)
    private ServiceType type;
    
    @Enumerated(EnumType.STRING)
    private ServiceRequestStatus status;
    
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    
    @ElementCollection(fetch = FetchType.EAGER, targetClass = LocalDateTime.class)
    @Convert(converter = LocalDateTimeConverter.class)
    @CollectionTable(
            name = "service_request_dates",
            joinColumns = @JoinColumn(name = "service_request_id")
    )
    private List<LocalDateTime> suggestedDates;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_cpf")
    private Client owner;
    

    public ServiceRequest(String title, String description, Address locate, ServiceType type, Client owner, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.locate = locate;
        this.type = type;
        this.owner = owner;
        this.dateTime = dateTime;
        this.status = ServiceRequestStatus.PENDENT;
        this.suggestedDates = new ArrayList<>();
    }

    public ServiceRequest() {
        this.status = ServiceRequestStatus.PENDENT;
        this.suggestedDates = new ArrayList<>();
        this.type = ServiceType.ELECTRIC;
        this.owner = new Client();
        this.locate = new Address();
        this.dateTime = LocalDateTime.MIN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getLocate() {
        return locate;
    }

    public void setLocate(Address locate) {
        this.locate = locate;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<LocalDateTime> getSuggestedDates() {
        return Collections.unmodifiableList(this.suggestedDates);
    }

    public void setSuggestedDates(List<LocalDateTime> suggestedDates) {
        this.suggestedDates = suggestedDates;
    }
    
    public void addSuggestedDates(List<LocalDateTime> suggestedDates) {
        this.suggestedDates.addAll(suggestedDates);
    }
    
    public void addSuggestedDate(LocalDateTime period) {
        this.suggestedDates.add(period);
    }
    
    public void removeSuggestedDate(LocalDateTime period) {
        this.suggestedDates.remove(period);
    }

    @Override
    public String toString() {
        return "ServiceRequest{" + "id=" + id + ", title=" + title + ", description=" + description + ", locate=" + locate + ", type=" + type + ", status=" + status + ", suggestedDates=" + suggestedDates + ", owner=" + owner + '}';
    }
}
