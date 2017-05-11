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
public enum ServiceType {
    
    ELECTRIC("Elétrico", "electric"),
    HIDRAULIC("Hidráulico", "hydraulic"),
    PAINT("Pintura", "paint"),
    MASONRY("Alvenaria", "masonry"),
    REPAIR("Reparo", "repair");
    
    private final String description;
    private final String iconName;
    
    ServiceType(String description, String iconName) {
        this.description = description;
        this.iconName = iconName;
    }

    public String getDescription() {
        return description;
    }
    
    public String getIconName() {
        return this.iconName;
    }
}
