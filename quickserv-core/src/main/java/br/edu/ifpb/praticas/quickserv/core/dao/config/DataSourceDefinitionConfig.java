/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.dao.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@DataSourceDefinition(
        name = "java:app/quickserv-datasource",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://localhost:5432/quickserv",
        user = "postgres",
        password = "123456"
)

@Stateless
public class DataSourceDefinitionConfig {
    
}
