/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named("suggestHoursCtrl")
@ConversationScoped
public class SuggestedHoursController implements Serializable {
    
    @Inject
    private Conversation conversation;
    
    private List<LocalDateTime> dateTimes;
    private LocalDateTime dateTime;
    
    @PostConstruct
    private void postConstruct() {
        System.out.println("[SuggestedHoursController] I'm Constructed!");
        initConversation();
        initPeriods();
        initPeriod();
    }
    
    @PreDestroy
    private void preDestroy() {
        endConversation();
    }
    
    private void initPeriod() {
        this.dateTime = LocalDateTime.now();
    }
    
    private void initPeriods() {
        this.dateTimes = new ArrayList<>();
    }
    
    public List<LocalDateTime> getDateTimes() {
        return this.dateTimes;
    }
    
    public String addDateTime() {
        this.dateTimes.add(this.dateTime);
        initPeriod();
        return null;
    }
    
    public String removeDateTime(LocalDateTime dateTime) {
        System.out.println("[SuggeestedHoursController] removing "+dateTime);
        this.dateTimes.remove(dateTime);
        return null;
    }
    
    public String beautify(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(dtf);
    }
    
    public void initConversation() {
        if(conversation.isTransient()) {
            this.conversation.begin();
        }
    }
    
    public void endConversation() {
        if(!conversation.isTransient()) {
            this.conversation.end();
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    
}
