/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.shared.main;

import br.edu.ifpb.praticas.quickserv.shared.domain.Client;
import br.edu.ifpb.praticas.quickserv.shared.domain.Professional;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceRequest;
import br.edu.ifpb.praticas.quickserv.shared.enums.ServiceType;
import br.edu.ifpb.praticas.quickserv.shared.domain.UserAccount;
import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import br.edu.ifpb.praticas.quickserv.shared.enums.RegisterRequestStatus;
import br.edu.ifpb.praticas.quickserv.shared.enums.UserRole;
import br.edu.ifpb.praticas.quickserv.shared.exceptions.LoginException;
import br.edu.ifpb.praticas.quickserv.shared.vo.ServicePK;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */
public class App {
    
    public static void main(String[] args) {
        
        EntityManager em = Persistence
                .createEntityManagerFactory("quickserv-pu")
                .createEntityManager();
        
//        persistUser("pedroviniv@gmail.com", "123456", true, em);
        persistClient("pedroviniv@gmail.com", "123456", true, "111.222.333-44", "Pedro Arthur", "Fernandes de Vasconcelos", em);
        
//        List<Period> periods = new ArrayList<>();
//        periods.add(new Period(LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
//        periods.add(new Period(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(3).plusHours(2)));
        persistServiceRequest(em, "Preciso de X", "Preciso de X amanhã", ServiceType.PAINT, "111.222.333-44", null);
        
        ServiceRequest request = getServiceRequestById(1L, em);

//        request.addSuggestedDate(new Period(LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(10).plusHours(2)));
        updateServiceRequest(request, em);
        
//        for(Period period : request.getSuggestedDates()) {
//            System.out.println("Entre "+format(period.getStart())+" e "+format(period.getEnd()));
//        }
        persistProfessional("luanda@gmail.com", "123456",
                true, "222.333.444-55", "Luanda Maria", "Sousa da Silva",
                "imgs/22233344455/perfil.jpg", RegisterRequestStatus.ACCEPTED, 
                em);
//        persistServiceProposal("Resolverei o problema X", 100D, 
//                "222.333.444-55", 1L, periods, em);
        ServiceProposal proposal = getServiceProposalById(1L, em);
//        proposal.addSuggestedDate(new Period(LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(10).plusHours(2)));

        updateServiceProposal(proposal, em);
        
//        persistService(1L, 1L, new Period(LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(10).plusHours(2)), em);
        Service service = getServiceById(new ServicePK(1L, 1L), em);
        service.setEvaluation(Evaluation.LIKE);
        updateService(service, em);
    }
    
    private static String format(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh'h'mm");
        return dateTime.format(dtf);
    }
    
    private static Service getServiceById(ServicePK servicePK, EntityManager em) {
        return em.find(Service.class, servicePK);
    }
    
    private static void updateService(Service service, EntityManager em) {
        mergeObject(service, em);
    }
    
    private static void persistService(Long serviceReqId, Long servicePropId, 
            LocalDateTime period, EntityManager em) {
        
        ServiceProposal proposal = getServiceProposalById(servicePropId, em);
        ServiceRequest request = getServiceRequestById(serviceReqId, em);
        
        Service service = new Service(request, proposal);
        
        persistObject(service, em);
    }
    
    private static void persistServiceProposal(String desc, Double price, 
            String professionalCpf, Long serviceReqId, List<LocalDateTime> periods, EntityManager em) {
        
        Professional prof = getProfessionalByCpf(professionalCpf, em);
        ServiceRequest req = getServiceRequestById(serviceReqId, em);
        
        ServiceProposal prop = new ServiceProposal();
        prop.setDescription(desc);
        prop.setPrice(new BigDecimal(price));
        prop.setProfessional(prof);
        prop.setServiceRequest(req);
//        prop.addSuggestedDates(periods);
        
        persistObject(prop, em);
    }
    
    private static void persistServiceRequest(EntityManager em, String title, String description, 
            ServiceType type, String clientCpf, List<LocalDateTime> periods) {
        
        Client client = getClientByCpf(clientCpf, em);
        
        ServiceRequest request = new ServiceRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setType(type);
        request.setOwner(client);
        request.addSuggestedDates(periods);
        
        persistObject(request, em);
    }
    
    private static ServiceRequest getServiceRequestById(Long id, EntityManager em) {
        return em.find(ServiceRequest.class, id);
    }
    
    private static void updateServiceRequest(ServiceRequest serviceRequest, EntityManager em) {
        mergeObject(serviceRequest, em);
    }
    
    private static ServiceProposal getServiceProposalById(Long id, EntityManager em) {
        return em.find(ServiceProposal.class, id);
    }
    
    private static void updateServiceProposal(ServiceProposal serviceProposal, EntityManager em) {
        mergeObject(serviceProposal, em);
    }
    
    private static void logar(String username, String password, EntityManager em) {
        try {
            UserAccount user = signIn("pedroviniv@gmail.com", "123456", em);
            System.out.println("Logged User: "+user);
        } catch (LoginException ex) {
            System.out.println(ex);
        }
    }
    
    private static void persistUser(String username, String password, UserRole role, boolean active, EntityManager em) {
        persistObject(new UserAccount(username, password, role, active), em);
    }
    
    private static void persistClient(String username, String password, 
            boolean active, String cpf, String firstname, String lastname, EntityManager em) {
        
        UserAccount user = new UserAccount(username, password, UserRole.CLIENT, active);
        Client client = new Client();
        client.setUserAccount(user);
        client.setCpf(cpf);
        client.setFirstname(firstname);
        client.setLastname(lastname);
        
        persistObject(client, em);
    }
    
    private static void persistProfessional(String username, String password, 
            boolean active, String cpf, String firstname, String lastname, 
            String documentPhotoUrl, RegisterRequestStatus status, EntityManager em) {
        
        UserAccount user = new UserAccount(username, password, UserRole.PROFESSIONAL, active);
        Professional professional = new Professional();
        professional.setUserAccount(user);
        professional.setCpf(cpf);
        professional.setFirstname(firstname);
        professional.setLastname(lastname);
        professional.setDocumentPhotoUrl(new byte[10]);
        professional.setStatus(status);
        
        persistObject(professional, em);
    }
    
    private static Client getClientByCpf(String cpf, EntityManager em) {
        return em.find(Client.class, cpf);
    }
    
    private static Professional getProfessionalByCpf(String cpf, EntityManager em) {
        return em.find(Professional.class, cpf);
    }
    
    private static void persistObject(Object object, EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(object);
        tx.commit();
    }
    
    private static void mergeObject(Object object, EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(object);
        tx.commit();
    }
    
    private static UserAccount signIn(String username, String password, EntityManager em) {
        //
        TypedQuery<UserAccount> query = em
                .createQuery("SELECT u FROM UserAccount u "
                + "WHERE u.username = :username AND u.password = :password",
                UserAccount.class)
                .setParameter("username", username)
                .setParameter("password", password);
        
        try {
            UserAccount userAccount = query.getSingleResult();
            if(!userAccount.isActived())
                throw new LoginException("O registro do usuário "
                        + userAccount.getUsername() +
                        " ainda não foi aprovado.");
            return userAccount;
        } catch (NoResultException ex) {
            throw new LoginException("Usuário ou Senha incorreto(s)!");
        }
    }
}
