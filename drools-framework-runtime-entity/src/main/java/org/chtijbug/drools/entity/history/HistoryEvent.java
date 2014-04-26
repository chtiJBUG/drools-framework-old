/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import org.chtijbug.drools.runtime.DroolsChtijbugException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author nheron
 */
public class HistoryEvent implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6640538290066213804L;

    private int eventID;
    private int ruleBaseID;
    private int sessionId;
    private DroolsChtijbugException droolsChtijbugException;
    private ArrayList<ResourceFile> resourceFiles = new ArrayList<ResourceFile>();

    public enum TypeEvent {
        Fact, Rule, BPMN, RuleFlowGroup, KnowledgeBaseSingleton, Session
    }

    /**
     * Mandatory for GWT Serialization
     */
    public HistoryEvent() {
    }

    protected Date dateEvent;
    protected TypeEvent typeEvent;

    public HistoryEvent(int eventID, Date dateEvent, TypeEvent typeEvent) {

        this.eventID = eventID;
        this.dateEvent = dateEvent;
        this.typeEvent = typeEvent;
    }

    public ArrayList<ResourceFile> getResourceFiles() {
        return resourceFiles;
    }

    public void setResourceFiles(ArrayList<ResourceFile> resourceFiles) {
        this.resourceFiles = resourceFiles;
    }

    public DroolsChtijbugException getDroolsChtijbugException() {
        return droolsChtijbugException;
    }

    public void setDroolsChtijbugException(DroolsChtijbugException droolsChtijbugException) {
        this.droolsChtijbugException = droolsChtijbugException;
    }


    public Date getDateEvent() {
        return dateEvent;
    }

    public TypeEvent getTypeEvent() {
        return typeEvent;
    }

    public int getEventID() {
        return eventID;
    }

    public int getRuleBaseID() {
        return ruleBaseID;
    }

    public void setRuleBaseID(int ruleBaseID) {
        this.ruleBaseID = ruleBaseID;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }


    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
