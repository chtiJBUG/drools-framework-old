package org.chtijbug.drools.runtime.listener;

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 03/06/13
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
public interface HistoryListener extends Serializable {

    public void fireEvent(HistoryEvent newHistoryEvent) throws DroolsChtijbugException;


}
