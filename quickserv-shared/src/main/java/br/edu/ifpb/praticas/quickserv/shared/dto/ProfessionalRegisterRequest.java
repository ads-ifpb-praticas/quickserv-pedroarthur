/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.dto;

import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.RegisterRequest;
import java.io.Serializable;

/**
 *
 * @author Pedro Arthur
 */
public class ProfessionalRegisterRequest implements Serializable {
    
    private RegisterRequest registerRequest;
    private Professional profissional;

    public ProfessionalRegisterRequest(RegisterRequest registerRequest, Professional profissional) {
        this.registerRequest = registerRequest;
        this.profissional = profissional;
        }
    
    public ProfessionalRegisterRequest() {
        
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public Professional getProfissional() {
        return profissional;
    }

    public void setProfissional(Professional profissional) {
        this.profissional = profissional;
    }

    @Override
    public String toString() {
        return "ProfessionalRegisterRequest{" + "registerRequest=" + registerRequest + ", profissional=" + profissional + '}';
    }
}
