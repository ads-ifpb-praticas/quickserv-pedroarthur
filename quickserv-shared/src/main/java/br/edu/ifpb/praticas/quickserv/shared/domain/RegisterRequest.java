/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Pedro Arthur
 */

@Entity
@Table(name = "register_request")
@Cacheable(false)
@SequenceGenerator(
        name = "register_req_seq_gen",
        sequenceName = "register_req_sequence",
        initialValue = 1,
        allocationSize = 1
)
public class RegisterRequest implements Serializable {
    
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "register_req_seq_gen"
    )
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_username")
    private UserAccount account;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "document_photo_url")
    @Lob
    private byte[] documentPhotoUrl;
    
    @Enumerated(EnumType.STRING)
    private RegisterRequestStatus status;
    
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public RegisterRequest(UserAccount account, byte[] documentPhotoUrl, RegisterRequestStatus status, LocalDateTime dateTime) {
        this.account = account;
        this.documentPhotoUrl = documentPhotoUrl;
        this.status = status;
        this.dateTime = dateTime;
    }

    public RegisterRequest() {
        this.account = new UserAccount();
        this.status = RegisterRequestStatus.PENDENT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public byte[] getDocumentPhotoUrl() {
        return documentPhotoUrl;
    }

    public void setDocumentPhotoUrl(byte[] documentPhotoUrl) {
        this.documentPhotoUrl = documentPhotoUrl;
    }

    public RegisterRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RegisterRequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" + "id=" + id + ", account=" + account + ", documentPhotoUrl=" + documentPhotoUrl + ", status=" + status + '}';
    }
}
