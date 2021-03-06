/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.filter;

import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Pedro Arthur
 */
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest      = (HttpServletRequest) request;
        HttpServletResponse httpResponse    = (HttpServletResponse) response;
        
        UserAccount loggedUser = (UserAccount) httpRequest.getSession().getAttribute("loggedUserAccount");
        
        if(loggedUser == null) {
            System.out.println("[AccessFilter] There's no logged user!");
            httpResponse.sendRedirect("/quickserv-web/faces/index.xhtml");
        }
        else {
            System.out.println("Logged User: "+loggedUser);
            String path = httpRequest.getRequestURI();
            path = path.substring(httpRequest.getContextPath().length());
            UserRole role = loggedUser.getRole();
            switch(role) {
                
                case CLIENT:
                    if(!path.startsWith("/faces/client/")) {
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    } else 
                        chain.doFilter(request, response);
                    break;
                case PROFESSIONAL:
                    if(!path.startsWith("/faces/professional/")) {
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    } else 
                        chain.doFilter(request, response);
                    break;
                case ADMIN:
                    if(!path.startsWith("/faces/admin/")) {
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    } else 
                        chain.doFilter(request, response);
                    break;
            }
            
        }
        
        
    }

    @Override
    public void destroy() {
    }

    
    
}
