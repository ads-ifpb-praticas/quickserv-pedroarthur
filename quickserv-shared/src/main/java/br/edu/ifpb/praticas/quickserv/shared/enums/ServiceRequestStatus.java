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
public enum ServiceRequestStatus {
    
    SOLVED("Resolvido"), PENDENT("Pendente");
    
    private final String description;

    private ServiceRequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
