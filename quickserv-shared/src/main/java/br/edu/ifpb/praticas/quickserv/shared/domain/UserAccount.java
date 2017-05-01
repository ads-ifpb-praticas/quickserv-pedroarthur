/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Pedro Arthur
 */

@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {
    
    @Id
    @Email(message = "Insira um E-mail válido")
    @NotEmpty(message = "Este campo é obrigatório")
    private String username;
    @NotEmpty(message = "Este campo é obrigatório")
    private String password;
    @Column(nullable = false)
    private boolean actived;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] photo;

    public UserAccount(String username, String password, UserRole role, boolean actived) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.actived = actived;
    }

    public UserAccount() {
        this.role = UserRole.CLIENT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    @Override
    public String toString() {
        return "UserAccount{" + "username=" + username + ", password=" + password + ", actived=" + actived + ", role=" + role + '}';
    }
}
