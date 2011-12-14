/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity.history.process;

import java.util.Date;
import org.chtijbug.drools.entity.history.HistoryEvent;

/**
 *
 * @author nheron
 */
public class ProcessHistoryEvent extends HistoryEvent{

    public ProcessHistoryEvent() {
        super(new Date(), TypeEvent.BPMN);
    }
    
}
