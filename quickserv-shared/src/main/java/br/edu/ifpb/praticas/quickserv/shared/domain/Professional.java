/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author Pedro Arthur
 */

@Entity
public class Professional implements Serializable {
    
    @Id
    @CPF(message = "Insira um CPF válido")
    @NotEmpty(message = "Este campo é obrigatório")
    private String cpf;
    @NotEmpty(message = "Este campo é obrigatório")
    private String firstname;
    @NotEmpty(message = "Este campo é obrigatório")
    private String lastname;
    @NotEmpty(message = "Este campo é obrigatório")
    private String phone;
    
    @Embedded
    private Address address;
    
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "document_photo_url")
    @Lob
    private byte[] documentPhotoUrl;
    
    @Enumerated(EnumType.STRING)
    private RegisterRequestStatus status;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_username")
    private UserAccount userAccount;

    public Professional(String cpf, String firstname, String lastname, String phone, 
            Address address, byte[] documentPhotoUrl, RegisterRequestStatus status, UserAccount userAccount) {
        this.cpf = cpf;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.documentPhotoUrl = documentPhotoUrl;
        this.status = status;
        this.userAccount = userAccount;
    }

    public Professional() {
        this.address = new Address();
        this.userAccount = new UserAccount();
        this.status = RegisterRequestStatus.PENDENT;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "Professional{" + "cpf=" + cpf + ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone + ", address=" + address + ", status=" + status + ", userAccount=" + userAccount + '}';
    }
}
