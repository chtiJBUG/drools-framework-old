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

import org.chtijbug.drools.entity.DroolsFactObject;
import org.chtijbug.drools.entity.DroolsFactObjectAttribute;
import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.entity.history.fact.DeletedFactHistoryEvent;
import org.chtijbug.drools.entity.history.fact.InsertedFactHistoryEvent;
import org.chtijbug.drools.entity.history.fact.UpdatedFactHistoryEvent;
import org.chtijbug.drools.runtime.mbeans.ResultStructure;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;

public class DisplayHistoryEventDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTree treeHistoryLevel;
    private ResultStructure resultStructure;

    public DisplayHistoryEventDialog(ResultStructure resultStructure) {
        this.resultStructure = resultStructure;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        if (resultStructure.getHistoryContainer() != null) {
            DefaultTreeModel treeModel = (DefaultTreeModel) treeHistoryLevel.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

            for (HistoryEvent e : resultStructure.getHistoryContainer().getListHistoryEvent()) {
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode();
                newChild.setUserObject(e);
                treeModel.insertNodeInto(newChild, root, 0);
                if (e instanceof InsertedFactHistoryEvent) {
                    InsertedFactHistoryEvent insertedFactHistoryEvent = (InsertedFactHistoryEvent) e;
                    DefaultMutableTreeNode insertFactTreeNode = createDefaultMutableTreeNode(insertedFactHistoryEvent.getTypeEvent().toString());
                    newChild.add(insertFactTreeNode);
                    insertFactTreeNode.add(getFromDroolsFactObject(newChild, insertedFactHistoryEvent.getInsertedObject()));
                } else if (e instanceof UpdatedFactHistoryEvent) {
                    UpdatedFactHistoryEvent updatedFactHistoryEvent = (UpdatedFactHistoryEvent) e;
                    DefaultMutableTreeNode updateOldFactTreeNode = createDefaultMutableTreeNode(updatedFactHistoryEvent.getTypeEvent().toString() + "-Old Value");
                    newChild.add(updateOldFactTreeNode);
                    updateOldFactTreeNode.add(getFromDroolsFactObject(newChild, updatedFactHistoryEvent.getObjectOldValue()));
                    DefaultMutableTreeNode updateNewFactTreeNode = createDefaultMutableTreeNode(updatedFactHistoryEvent.getTypeEvent().toString() + "-new Value");
                    newChild.add(updateNewFactTreeNode);
                    updateNewFactTreeNode.add(getFromDroolsFactObject(newChild, updatedFactHistoryEvent.getObjectNewValue()));
                } else if (e instanceof DeletedFactHistoryEvent) {
                    DeletedFactHistoryEvent deletedFactHistoryEvent = (DeletedFactHistoryEvent) e;
                    DefaultMutableTreeNode deleteFactTreeNode = createDefaultMutableTreeNode(deletedFactHistoryEvent.getTypeEvent().toString());
                    newChild.add(deleteFactTreeNode);
                    deleteFactTreeNode.add(getFromDroolsFactObject(newChild, deletedFactHistoryEvent.getDeletedObject()));
                }

            }
        }
    }

    private DefaultMutableTreeNode getFromDroolsFactObject(DefaultMutableTreeNode father, DroolsFactObject droolsFactObject) {
        DefaultMutableTreeNode rootDroolsFactObject = createDefaultMutableTreeNode(droolsFactObject.getFullClassName());
        father.add(rootDroolsFactObject);
        rootDroolsFactObject.add(createDefaultMutableTreeNode("objectVersion = " + Integer.valueOf(droolsFactObject.getObjectVersion()).toString()));
        for (DroolsFactObjectAttribute attribute : droolsFactObject.getListfactObjectAttributes()) {
            rootDroolsFactObject.add(createDefaultMutableTreeNode(attribute.getAttributeName() + ":" + attribute.getAttributeValue()));
        }
        return rootDroolsFactObject;
    }

    private DefaultMutableTreeNode createDefaultMutableTreeNode(String toDisplay) {
        DefaultMutableTreeNode newDefaultMutableTreeNode = new DefaultMutableTreeNode();
        newDefaultMutableTreeNode.setUserObject(toDisplay);
        return newDefaultMutableTreeNode;
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
