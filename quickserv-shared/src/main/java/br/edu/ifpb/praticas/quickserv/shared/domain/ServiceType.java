/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.domain;

/**
 *
 * @author Pedro Arthur
 */
public enum ServiceType {
    
    ELECTRIC("Elétrico"),
    HIDRAULIC("Hidráulico"),
    PAINT("Pintura"),
    MASONRY("Alvenaria"),
    REPAIR("Reparo");
    
    private final String description;
    
    ServiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
