package org.chtijbug.drools.supervision;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * Date: 28/09/12
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
public class ListRulesJtableMouseListener extends MouseAdapter {
    private JTable listFireAllRules;

    public ListRulesJtableMouseListener(JTable listFireAllRules) {
        this.listFireAllRules = listFireAllRules;
    }

    public int getSelectedRow(){
        return listFireAllRules.getSelectedRow();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("coucou");

    }
}
