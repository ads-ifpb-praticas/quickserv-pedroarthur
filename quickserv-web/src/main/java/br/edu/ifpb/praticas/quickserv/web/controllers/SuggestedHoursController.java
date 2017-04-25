/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.praticas.quickserv.web.controllers;

import br.edu.ifpb.praticas.quickserv.shared.domain.Period;
import java.io.Serializable;
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
    
    private List<Period> periods;
    private Period period;
    
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
        this.period = new Period();
    }
    
    private void initPeriods() {
        this.periods = new ArrayList<>();
    }
    
    public List<Period> getPeriods() {
        return this.periods;
    }
    
    public String addPeriod() {
        this.periods.add(this.period);
        initPeriod();
        return null;
    }
    
    public String removePeriod(Period period) {
        System.out.println("[SuggeestedHoursController] removing "+period);
        this.periods.remove(period);
        return null;
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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
