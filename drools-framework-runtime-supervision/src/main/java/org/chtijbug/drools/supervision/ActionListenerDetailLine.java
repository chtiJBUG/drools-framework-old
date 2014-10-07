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
package org.chtijbug.drools.supervision;

import org.chtijbug.drools.runtime.mbeans.ResultStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class ActionListenerDetailLine implements ActionListener {
    private ListRulesJtableMouseListener listRulesJtableMouseListener;
    private Map<Integer, ResultStructure> data;

    public ActionListenerDetailLine(ListRulesJtableMouseListener listRulesJtableMouseListener, Map<Integer, ResultStructure> data) {
        this.listRulesJtableMouseListener = listRulesJtableMouseListener;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer i = Integer.valueOf(listRulesJtableMouseListener.getSelectedRow());
        if (i != null) {
            ResultStructure toDisplay = this.data.get(i + 1);
            if (toDisplay != null) {
                DisplayHistoryEventDialog displayHistoryEventDialog = new DisplayHistoryEventDialog(toDisplay);
                displayHistoryEventDialog.pack();
                displayHistoryEventDialog.setVisible(true);
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
