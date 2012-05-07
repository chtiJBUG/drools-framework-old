package org.chtijbug.drools.guvnor.rest.dt;

import org.chtijbug.drools.guvnor.rest.ChtijbugDroolsRestException;
import org.drools.ide.common.client.modeldriven.dt52.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 14:17
 * To change this template use File | Settings | File Templates.
 */
public class DecisionTable {
    private String name;
    private GuidedDecisionTable52 guidedDecisionTable52;
    private List<ColumnDefinition> columnDefinitionList = new ArrayList<ColumnDefinition>();
    private List<Row> rows = new ArrayList<Row>();

    public DecisionTable(GuidedDecisionTable52 guidedDecisionTable52) throws ChtijbugDroolsRestException{
        this.guidedDecisionTable52 = guidedDecisionTable52;
        this.name = this.guidedDecisionTable52.getTableName();
        ColumnDefinition rowNumberColumn = new ColumnDefinition(0, guidedDecisionTable52.getRowNumberCol());
        columnDefinitionList.add(rowNumberColumn);
        ColumnDefinition descriptionColumn = new ColumnDefinition(1, guidedDecisionTable52.getDescriptionCol());
        columnDefinitionList.add(descriptionColumn);
        int columnNumber = 2;
        for (AttributeCol52 attributeCol52 : this.guidedDecisionTable52.getAttributeCols()) {
            ColumnDefinition columnDefinition = new ColumnDefinition(columnNumber, attributeCol52);
            columnDefinitionList.add(columnDefinition);
            columnNumber++;

        }
        for (Pattern52 pattern52 : this.guidedDecisionTable52.getPatterns()) {
            for (ConditionCol52 conditionCol52 : pattern52.getChildColumns()) {
                ColumnDefinition columnDefinition = new ColumnDefinition(columnNumber, conditionCol52);
                columnDefinitionList.add(columnDefinition);
                columnNumber++;
            }
        }
        for (ActionCol52 actionCol52 : this.guidedDecisionTable52.getActionCols()) {
            if (actionCol52 instanceof ActionInsertFactCol52) {
                ActionInsertFactCol52 actionInsertFactCol52 = (ActionInsertFactCol52) actionCol52;
                ColumnDefinition columnDefinition = new ColumnDefinition(columnNumber, actionInsertFactCol52);
                columnDefinitionList.add(columnDefinition);
                columnNumber++;
            }
        }
        for (List<DTCellValue52> line : this.guidedDecisionTable52.getData()) {
            try {
                Row newRow = new Row(line,this);
                //Row newRow = fillRow(line);
                rows.add(newRow);
            } catch (ChtijbugDroolsRestException e) {
                ChtijbugDroolsRestException chtijbugDroolsRestException = new ChtijbugDroolsRestException();
                chtijbugDroolsRestException.setOriginalException(e);
                e.setClassName("DecisionTable.Constructor");
                e.setAttribute("Data");
                e.setValue(line.toString());
                throw chtijbugDroolsRestException;

            }

        }
    }

    public Row createEmptyRow(int rowNumber) throws ChtijbugDroolsRestException {
        for (int j=rowNumber;j < this.rows.size();j++){
            this.rows.get(j).updateRowNumber(j+1);
        }
        Row newRow = new Row(this,rowNumber);

        return newRow;
    }

    public void clearAllData(){

    }

    public String getName() {
        return name;
    }

    public GuidedDecisionTable52 getGuidedDecisionTable52() {
        return guidedDecisionTable52;
    }

    public List<ColumnDefinition> getColumnDefinitionList() {
        return columnDefinitionList;
    }

    public List<Row> getRows() {
        return rows;
    }
}
