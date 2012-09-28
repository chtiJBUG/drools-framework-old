package org.chtijbug.drools.supervision;

import org.chtijbug.drools.runtime.mbeans.ResultStructure;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MainJMXClient extends JDialog {
    private JPanel contentPane;
    private JButton buttonConnect;
    private JButton buttonDisconnect;
    private JTextField hostname;
    private JTextField portNumber;
    private JTextField userName;
    private JTextField password;
    private JTable listFireAllRules;
    private JToolBar toolBar;
    private JButton buttonRuleBaseOperation;
    private JButton buttonSessionOperation;
    private JButton buttonDetailLine;
    private JMXClient client = null;
    private DefaultTableModel listFiredAllRulesTableModel;
    private long selectedRow;
    private Map<Integer,ResultStructure> data = new HashMap<Integer,ResultStructure>();

    public MainJMXClient() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonConnect);

        buttonConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonDisconnect.addActionListener(new ActionListener() {
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
        userName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        buttonRuleBaseOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        buttonSessionOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        listFireAllRules.addMouseListener(new MouseAdapter() {

        });
        ListRulesJtableMouseListener mouseListener = new ListRulesJtableMouseListener(this.listFireAllRules);
        this.listFireAllRules.addMouseListener(mouseListener);
        ActionListenerDetailLine actionListenerDetailLine = new ActionListenerDetailLine(mouseListener,this.data);
        buttonDetailLine.addActionListener(actionListenerDetailLine);
    }

    private void onOK() {
        this.client = new JMXClient(this.hostname.getText(), Integer.valueOf(this.portNumber.getText()).intValue(), null, null);//, this.userName.getText(), this.password.getText());
        this.client.getMbeanSessionProxy().setGenerateXMLHistoryContainer(true);
        client.registerListener(new FireAllRulesListener() {

            @Override
            public void fireAllRules(ResultStructure container) {

               addRow(container);
            }
        });
        this.buttonConnect.setEnabled(false);
        this.buttonDisconnect.setEnabled(true);
    }

    private void onCancel() {
        try {
            this.client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.client = null;
        this.buttonConnect.setEnabled(true);
        this.buttonDisconnect.setEnabled(false);
    }

    public static void main(String[] args) {
        MainJMXClient dialog = new MainJMXClient();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void addRow(ResultStructure line){
        this.listFiredAllRulesTableModel.insertRow(this.listFiredAllRulesTableModel.getRowCount(), new Object[]{line.getNumberFireAllRulesExecuted(),line.getNumberRulesExecuted(),line.getTotalTimeExecution()});
         this.data.put(Integer.valueOf(this.listFiredAllRulesTableModel.getRowCount()),line);

    }

    private void createUIComponents() {
        this.listFiredAllRulesTableModel = new DefaultTableModel();
        this.listFireAllRules = new JTable(this.listFiredAllRulesTableModel);
        this.listFireAllRules.setFillsViewportHeight(true);
        this.listFiredAllRulesTableModel.addColumn("id");
        this.listFiredAllRulesTableModel.addColumn("numberRules");
        this.listFiredAllRulesTableModel.addColumn("Times");





        // TODO: place custom component creation code here
    }
}
