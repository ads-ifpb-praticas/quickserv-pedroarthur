/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.enums;

/**
 *
 * @author Pedro Arthur
 */
public enum UserRole {
    
    PROFESSIONAL("Profissional"),
    CLIENT("Cliente"),
    ADMIN("Administrador");
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
