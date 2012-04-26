package org.chtijbug.drools.guvnor.rest.dt;

import org.drools.ide.common.client.modeldriven.dt52.ActionInsertFactCol52;
import org.drools.ide.common.client.modeldriven.dt52.AttributeCol52;
import org.drools.ide.common.client.modeldriven.dt52.DTCellValue52;
import org.drools.ide.common.client.modeldriven.dt52.Pattern52;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public class ColumnDefinition {
    private int columnNumber;
    private ColumnType columnType;
    private boolean hideColumn;
    private boolean hasDefaultValue;
    private String defaultValue;
    private AttributeCol52 attributeCol52;
    private Pattern52 pattern52;
    private ActionInsertFactCol52 actionInsertFact52;
    private  DTCellValue52 defaultValueCell;

    public ColumnDefinition(int columnNumber, AttributeCol52 attributeCol52) {
        this.attributeCol52 = attributeCol52;
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.attribute;
        if (attributeCol52.getDefaultValue() != null) {
            this.hasDefaultValue = true;
            this.defaultValueCell =  attributeCol52.getDefaultValue();
            this.defaultValue = getDefaultValue(attributeCol52.getDefaultValue());
        }
        this.hideColumn = attributeCol52.isHideColumn();
    }

    public ColumnDefinition(int columnNumber, Pattern52 pattern52) {
        this.pattern52 = pattern52;
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.condition;
        if (pattern52.getDefaultValue() != null) {
            this.hasDefaultValue = true;
            this.defaultValue = getDefaultValue(pattern52.getDefaultValue());
        }
        this.hideColumn = pattern52.isHideColumn();
    }

    public ColumnDefinition(int columnNumber, ActionInsertFactCol52 actionInsertFact52) {
        this.actionInsertFact52 = actionInsertFact52;
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.action;
        if (actionInsertFact52.getDefaultValue() != null) {
            this.hasDefaultValue = true;
            this.defaultValue = getDefaultValue(actionInsertFact52.getDefaultValue());
        }
        this.hideColumn = actionInsertFact52.isHideColumn();
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public ColumnType getColumnDefinition() {
        return columnType;
    }

    public boolean isHideColumn() {
        return hideColumn;
    }

    public boolean isHasDefaultValue() {
        return hasDefaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public AttributeCol52 getAttributeCol52() {
        return attributeCol52;
    }

    public Pattern52 getPattern52() {
        return pattern52;
    }

    public ActionInsertFactCol52 getActionInsertFact52() {
        return actionInsertFact52;
    }

    private String getDefaultValue(DTCellValue52 cell) {
        String value = null;
        switch (cell.getDataType()) {
            case BOOLEAN:
                value = Boolean.toString(cell.getBooleanValue());
                break;
            case NUMERIC:
                value = cell.getNumericValue().toString();
                break;
            case STRING:
                value = cell.getStringValue();
                break;
            case DATE:
                value = cell.getDateValue().toString();
                break;
        }
        return value;
    }
    /**
    private boolean isDataValid(String newValue,DTCellValue52 cell) throws  Exception{
          boolean result = true
         DTCellValue52 cell = this.columnDefinition.
         switch (cell.getDataType()) {
             case BOOLEAN:
                 new Boolean(newValuue);
                 value = Boolean.toString(cell.getBooleanValue());
                 break;
             case NUMERIC:
                 value = cell.getNumericValue().toString();
                 break;
             case STRING:
                 value = cell.getStringValue();
                 break;
             case DATE:
                 value = cell.getDateValue().toString();
                 break;
         }
         return value;
     }
     **/
}
