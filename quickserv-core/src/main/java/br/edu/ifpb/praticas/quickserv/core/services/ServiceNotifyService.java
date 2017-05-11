/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.core.services;

import br.edu.ifpb.praticas.quickserv.core.dao.interfaces.ServiceDAO;
import br.edu.ifpb.praticas.quickserv.shared.domain.Service;
import br.edu.ifpb.praticas.quickserv.shared.domain.ServiceProposal;
import br.edu.ifpb.praticas.quickserv.shared.enums.Evaluation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Pedro Arthur
 */

@Startup
@Singleton
public class ServiceNotifyService {

    @EJB
    private ServiceDAO serviceDAO;

    @Resource(lookup = "dac/rhecruta/javaMailSession")
    private Session session;

    @Schedule(second = "*/5", minute = "*", hour = "*")
    private void notifyService() {
        List<Service> services = serviceDAO.getNotNotifiedServices();
        System.out.println("Services not notified: " + services.size());
        for (Service service : services) {
            ServiceProposal serviceProposal = service.getServiceProposal();
            LocalDateTime expected = serviceProposal.getChoosedDate().plusDays(1L);
            System.out.println("expected: "+expected);
            LocalDateTime now = LocalDateTime.now();
            System.out.println("now: "+now);
            if (service.getEvaluation().equals(Evaluation.NONE)
                    && (now.isAfter(expected) || now.isEqual(expected))) {
                System.out.println("Notifying service " + service);
                Message message = createMessage(service);
                try {
                    System.out.println("Transporting message...");
                    Transport.send(message);
                    service.setNotified(true);
                    serviceDAO.update(service);
                    System.out.println("Service successfully notified!");
                } catch (MessagingException ex) {
                    Logger.getLogger(ServiceNotifyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Message createMessage(Service service) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("rhecrutapp@gmail.com"));

            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(service
                            .getServiceRequest()
                            .getOwner()
                            .getUserAccount()
                            .getUsername()));
            message.setText("A avaliação do serviço para " + service.getServiceRequest().getTitle() + ""
                    + " já está disponível, por favor, avalie o profissional para que os clientes e profissionais"
                    + " tenham um feedback sobre o mesmo. :)");
            message.setSubject("Avaliação do serviço " + service.getServiceRequest().getTitle());
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(ServiceNotifyService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ServiceNotifyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
