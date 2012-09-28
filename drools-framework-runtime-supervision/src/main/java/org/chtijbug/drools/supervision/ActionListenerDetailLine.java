package org.chtijbug.drools.supervision;

import org.chtijbug.drools.runtime.mbeans.ResultStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Date: 28/09/12
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class ActionListenerDetailLine implements ActionListener {
    private ListRulesJtableMouseListener listRulesJtableMouseListener;
    private Map<Integer,ResultStructure> data ;
    public ActionListenerDetailLine(ListRulesJtableMouseListener listRulesJtableMouseListener, Map<Integer,ResultStructure> data) {
        this.listRulesJtableMouseListener = listRulesJtableMouseListener;
        this.data=data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer i = Integer.valueOf(listRulesJtableMouseListener.getSelectedRow()) ;
        if (i!= null)  {
            ResultStructure toDisplay = this.data.get(i);
            if (toDisplay!= null){
                 DisplayHistoryEventDialog displayHistoryEventDialog = new DisplayHistoryEventDialog(toDisplay);
                displayHistoryEventDialog.pack();
                displayHistoryEventDialog.setVisible(true);
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
