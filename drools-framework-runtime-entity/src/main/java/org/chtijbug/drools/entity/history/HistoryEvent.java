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
    private String guvnor_url;
    private String guvnor_appName;
    private String guvnor_packageName;
    private String guvnor_packageVersion;
    private int eventID;
    private int ruleBaseID;
    private int sessionId;
    private DroolsChtijbugException droolsChtijbugException;
    private ArrayList<DrlRessourceFile> drlRessourceFiles = new ArrayList<DrlRessourceFile>();

    public enum TypeEvent {
        Fact, Rule, BPMN, RuleFlowGroup, KnowledgeBaseSingleton, Session
    }

	/**
	 * Mandatory for GWT Serialization
	 */
	public HistoryEvent(){
	}

    protected Date dateEvent;
    protected TypeEvent typeEvent;

    public HistoryEvent(int eventID, Date dateEvent, TypeEvent typeEvent) {

            this.eventID = eventID;
            this.dateEvent = dateEvent;
            this.typeEvent = typeEvent;
        }

    public ArrayList<DrlRessourceFile> getDrlRessourceFiles() {
        return drlRessourceFiles;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryEvent that = (HistoryEvent) o;

        if (eventID != that.eventID) return false;
        if (ruleBaseID != that.ruleBaseID) return false;
        if (sessionId != that.sessionId) return false;
        if (dateEvent != null ? !dateEvent.equals(that.dateEvent) : that.dateEvent != null) return false;
        if (guvnor_appName != null ? !guvnor_appName.equals(that.guvnor_appName) : that.guvnor_appName != null)
            return false;
        if (guvnor_packageName != null ? !guvnor_packageName.equals(that.guvnor_packageName) : that.guvnor_packageName != null)
            return false;
        if (guvnor_packageVersion != null ? !guvnor_packageVersion.equals(that.guvnor_packageVersion) : that.guvnor_packageVersion != null)
            return false;
        if (guvnor_url != null ? !guvnor_url.equals(that.guvnor_url) : that.guvnor_url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guvnor_url != null ? guvnor_url.hashCode() : 0;
        result = 31 * result + (guvnor_appName != null ? guvnor_appName.hashCode() : 0);
        result = 31 * result + (guvnor_packageName != null ? guvnor_packageName.hashCode() : 0);
        result = 31 * result + (guvnor_packageVersion != null ? guvnor_packageVersion.hashCode() : 0);
        result = 31 * result + eventID;
        result = 31 * result + ruleBaseID;
        result = 31 * result + sessionId;
        result = 31 * result + (dateEvent != null ? dateEvent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HistoryEvent{");
        sb.append("guvnor_url='").append(guvnor_url).append('\'');
        sb.append(", guvnor_appName='").append(guvnor_appName).append('\'');
        sb.append(", guvnor_packageName='").append(guvnor_packageName).append('\'');
        sb.append(", guvnor_packageVersion='").append(guvnor_packageVersion).append('\'');
        sb.append(", eventID=").append(eventID);
        sb.append(", ruleBaseID=").append(ruleBaseID);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", droolsChtijbugException=").append(droolsChtijbugException);
        sb.append(", dateEvent=").append(dateEvent);
        sb.append(", typeEvent=").append(typeEvent);
        sb.append('}');
        return sb.toString();
    }

    public String getGuvnor_url() {
        return guvnor_url;
    }

    public void setGuvnor_url(String guvnor_url) {
        this.guvnor_url = guvnor_url;
    }

    public String getGuvnor_appName() {
        return guvnor_appName;
    }

    public void setGuvnor_appName(String guvnor_appName) {
        this.guvnor_appName = guvnor_appName;
    }

    public String getGuvnor_packageName() {
        return guvnor_packageName;
    }

    public void setGuvnor_packageName(String guvnor_packageName) {
        this.guvnor_packageName = guvnor_packageName;
    }

    public String getGuvnor_packageVersion() {
        return guvnor_packageVersion;
    }

    public void setGuvnor_packageVersion(String guvnor_packageVersion) {
        this.guvnor_packageVersion = guvnor_packageVersion;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
