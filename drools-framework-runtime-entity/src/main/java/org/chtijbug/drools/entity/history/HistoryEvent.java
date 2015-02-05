/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    private Long eventID;
    private Long ruleBaseID;
    private Long sessionId;
    private DroolsChtijbugException droolsChtijbugException;
    private ArrayList<RuleResource> ruleResources = new ArrayList<>();

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

    public HistoryEvent(Long eventID, Date dateEvent, TypeEvent typeEvent) {

        this.eventID = eventID;
        this.dateEvent = dateEvent;
        this.typeEvent = typeEvent;
    }

    public ArrayList<RuleResource> getRuleResources() {
        return ruleResources;
    }

    public void setRuleResources(ArrayList<RuleResource> ruleResources) {
        this.ruleResources = ruleResources;
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

    public Long getEventID() {
        return eventID;
    }

    public Long getRuleBaseID() {
        return ruleBaseID;
    }

    public void setRuleBaseID(Long ruleBaseID) {
        this.ruleBaseID = ruleBaseID;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }


    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }
}
