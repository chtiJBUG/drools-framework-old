/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author nheron
 */
public class HistoryContainer {

    private List<HistoryEvent> listHistoryEvent = new LinkedList<HistoryEvent>();

    public List<HistoryEvent> getListHistoryEvent() {
        return listHistoryEvent;
    }

    public void addHistoryElement(HistoryEvent newHistoryElement){
        this.listHistoryEvent.add(newHistoryElement);
        
    }
}
