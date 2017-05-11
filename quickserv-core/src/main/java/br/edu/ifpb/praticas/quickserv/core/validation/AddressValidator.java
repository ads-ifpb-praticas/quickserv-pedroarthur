/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.validation;

import br.edu.ifpb.praticas.quickserv.shared.domain.Address;

/**
 *
 * @author Pedro Arthur
 */
public class AddressValidator {
    
    public static void validate(Address address) {
        if(address == null) throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getCountry() == null || address.getCountry().isEmpty())
            throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getState() == null || address.getState().isEmpty())
            throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getCity() == null || address.getCity().isEmpty())
            throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getNeighborhood() == null || address.getNeighborhood().isEmpty())
            throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getStreet() == null || address.getStreet().isEmpty())
            throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getNumber() == null)
            throw new IllegalArgumentException("You must fill all the address fields!");
        if(address.getComplemento() == null || address.getComplemento().isEmpty())
            throw new IllegalArgumentException("You must fill all the address fields!");
    }
}
