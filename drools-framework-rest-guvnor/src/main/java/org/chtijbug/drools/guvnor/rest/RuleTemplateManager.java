package org.chtijbug.drools.guvnor.rest;

import com.google.common.collect.Iterables;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.drools.ide.common.client.modeldriven.brl.RuleModel;
import org.drools.ide.common.client.modeldriven.brl.templates.InterpolationVariable;
import org.drools.ide.common.client.modeldriven.brl.templates.TemplateModel;
import org.drools.ide.common.server.util.BRXMLPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 25/03/13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
class RuleTemplateManager {
    private static Logger logger = LoggerFactory.getLogger(RuleTemplateManager.class);

    private GuvnorConnexionConfiguration configuration = null;

    private AssetManager assetManager=null;

    public RuleTemplateManager(GuvnorConnexionConfiguration configuration,AssetManager assetManager) {
        this.configuration = configuration;
        this.assetManager = assetManager;
    }

    public Map<String, List<String>> getTemplateTable(String templateRuleName) throws ChtijbugDroolsRestException{
        TemplateModel templateModel = getTemplateModel(templateRuleName);
        Map<String, List<String>> table = templateModel.getTable();
        table.remove(TemplateModel.ID_COLUMN_NAME);
        return table;
    }

    public void putTemplateTable(String templateRuleName, Map<String, List<String>> newTable) throws ChtijbugDroolsRestException {
        TemplateModel templateModel = getTemplateModel(templateRuleName);
        updateTableContent(newTable, templateModel);
        String xmlContent = BRXMLPersistence.getInstance().marshal(templateModel);
        this.assetManager.updateAssetCodeFromXML(templateRuleName,xmlContent);
     }

    private TemplateModel getTemplateModel(String templateRuleName) throws ChtijbugDroolsRestException {
        String content =this.assetManager.getAssetCodeInXML(templateRuleName);
        RuleModel ruleModel = BRXMLPersistence.getInstance().unmarshal(content);
        return (TemplateModel) ruleModel;
    }

    private void updateTableContent(Map<String, List<String>> newTable, TemplateModel templateModel) throws ChtijbugDroolsRestException {
        templateModel.clearRows();
        int rowCount = Iterables.get(newTable.values(), 1).size();
        List<String> columnNames = orderedColumnNames(templateModel);
        checkColumnNames(columnNames, newTable);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            List<String> row = newArrayList();
            for (String columnName : columnNames) {
                row.add(newTable.get(columnName).get(rowIndex));
            }
            templateModel.addRow(row.toArray(new String[row.size()]));
        }
    }

    private void checkColumnNames(List<String> columnNames, Map<String, List<String>> newTable) throws ChtijbugDroolsRestException {
        Set<String> newColumnNames = newTable.keySet();
        Set<String> columnNamesSet = new HashSet<String>(columnNames);
        if (!newColumnNames.equals(columnNamesSet)) {
            throw new ChtijbugDroolsRestException("Expected columns names : " + columnNames);
        }
    }

    private List<String> orderedColumnNames(TemplateModel templateModel) {
        List<String> columnNames = new ArrayList<String>();
        InterpolationVariable[] variablesList = templateModel.getInterpolationVariablesList();
        for (int i = 0; i < variablesList.length; i++) {
            columnNames.add(variablesList[i].getVarName());
        }
        return columnNames;
    }

}
