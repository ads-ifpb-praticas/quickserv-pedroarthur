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
public enum Avaliation {
    
    LIKE("Gostou"), DISLIKE("Não gostou"), NONE("Sem avaliação");
    
    private final String description;
    
    Avaliation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
