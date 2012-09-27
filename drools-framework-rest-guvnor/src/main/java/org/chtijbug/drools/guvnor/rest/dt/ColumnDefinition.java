package org.chtijbug.drools.guvnor.rest.dt;

import org.drools.ide.common.client.modeldriven.dt52.*;

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
    private String fieldType;
    private String hearder;
    private RowNumberCol52 rowNumberCol52;
    private DescriptionCol52 descriptionCol52;
    private AttributeCol52 attributeCol52;
    private ConditionCol52 conditionCol52;
    private ActionInsertFactCol52 actionInsertFact52;
    private DTCellValue52 defaultValueCell;

    public ColumnDefinition(int columnNumber, RowNumberCol52 rowNumberCol52) {
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.rowNumber;
        this.fieldType   =DTDataTypes52.NUMERIC_INTEGER.toString();
        this.rowNumberCol52 = rowNumberCol52;
        this.hearder = rowNumberCol52.getHeader();
    }

    public ColumnDefinition(int columnNumber, DescriptionCol52 descriptionCol52) {
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.description;
        this.hearder = descriptionCol52.getHeader();
        this.fieldType=DTDataTypes52.STRING.toString();
        this.descriptionCol52 = descriptionCol52;
    }
    public ColumnDefinition(int columnNumber, AttributeCol52 attributeCol52) {
        this.attributeCol52 = attributeCol52;
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.attribute;
        this.fieldType=DTDataTypes52.STRING.toString();
        if (attributeCol52.getDefaultValue() != null) {
            this.hasDefaultValue = true;
            this.defaultValueCell = attributeCol52.getDefaultValue();
            this.defaultValue = getValue(attributeCol52.getDefaultValue());
        }
        this.hearder = attributeCol52.getHeader();
        this.hideColumn = attributeCol52.isHideColumn();
    }

    public ColumnDefinition(int columnNumber, ConditionCol52 conditionCol52) {
        this.conditionCol52 = conditionCol52;
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.condition;
        this.fieldType = conditionCol52.getFieldType();
        if (conditionCol52.getDefaultValue() != null) {
            this.hasDefaultValue = true;
            this.defaultValue = getValue(conditionCol52.getDefaultValue());
        }
        this.hideColumn = conditionCol52.isHideColumn();
    }

    public ColumnDefinition(int columnNumber, ActionInsertFactCol52 actionInsertFact52) {
        this.actionInsertFact52 = actionInsertFact52;
        this.columnNumber = columnNumber;
        this.columnType = ColumnType.action;
        this.fieldType = actionInsertFact52.getType();
        this.hearder = actionInsertFact52.getHeader();
        if (actionInsertFact52.getDefaultValue() != null ) {
            DTCellValue52 defaultValue = actionInsertFact52.getDefaultValue();
            if (!(DTDataTypes52.STRING.equals(defaultValue.getDataType()) && defaultValue.getStringValue().isEmpty())){
                this.hasDefaultValue = true;
                this.defaultValue = getValue(actionInsertFact52.getDefaultValue());
            }
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

    public ConditionCol52 getPattern52() {
        return conditionCol52;
    }

    public ActionInsertFactCol52 getActionInsertFact52() {
        return actionInsertFact52;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getHearder() {
        return hearder;
    }

    public static String getValue(DTCellValue52 cell) {
        String value = null;
        switch (cell.getDataType()) {
            case BOOLEAN:
                value = Boolean.toString(cell.getBooleanValue());
                break;
            case NUMERIC:
                value = cell.getNumericValue().toString();
                break;
            case NUMERIC_INTEGER:
                value = cell.getNumericValue().toString();
                break;
            case NUMERIC_DOUBLE:
                value = cell.getNumericValue().toString();
                break;
            case STRING:
                value = cell.getStringValue();
                break;
            case DATE:
                value = cell.getDateValue().toString();
                break;
        }
        /**
         *  STRING,
             NUMERIC,
             NUMERIC_BIGDECIMAL,
             NUMERIC_BIGINTEGER,
             NUMERIC_BYTE,
             NUMERIC_DOUBLE,
             NUMERIC_FLOAT,
             NUMERIC_INTEGER,
             NUMERIC_LONG,
             NUMERIC_SHORT,
             DATE,
             BOOLEAN
         */
        return value;
    }

}
