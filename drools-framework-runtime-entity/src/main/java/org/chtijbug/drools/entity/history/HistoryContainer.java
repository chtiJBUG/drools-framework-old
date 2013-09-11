/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.listener.HistoryListener;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nheron
 */
public class HistoryContainer implements Serializable {
    public static String nameRuleBaseObjectName = "org.chtijbug.drools.runtime:type=RuleBaseSupervision";
    public static String nameSessionObjectName = "org.chtijbug.drools.runtime:type=StateFullSessionSupervision";
    private static final long serialVersionUID = 5645452451089006572L;
    private int sessionID;
    protected List<HistoryEvent> listHistoryEvent = new LinkedList<HistoryEvent>();
    private HistoryListener historylistener = null;

    public HistoryContainer() {
    }

    /**
     *
     */

    public HistoryContainer(int sessionID,HistoryListener historylistener) {
        this.sessionID = sessionID;
        this.historylistener = historylistener;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public List<HistoryEvent> getListHistoryEvent() {
        return listHistoryEvent;
    }

    public void addHistoryElement(HistoryEvent newHistoryElement) {
        DroolsChtijbugException error = null;
        try {
            if (historylistener != null) {
                historylistener.fireEvent(newHistoryElement);
            }
        }catch (DroolsChtijbugException e){
            error=e;
        }
        finally {
            if (error!= null){
                newHistoryElement.setDroolsChtijbugException(error);
            }
            this.listHistoryEvent.add(newHistoryElement);
        }


    }
}
