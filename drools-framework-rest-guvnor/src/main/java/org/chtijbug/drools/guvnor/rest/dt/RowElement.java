package org.chtijbug.drools.guvnor.rest.dt;

import org.drools.ide.common.client.modeldriven.dt52.DTCellValue52;
import org.drools.ide.common.client.modeldriven.dt52.DTDataTypes52;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public class RowElement {
    private ColumnDefinition columnDefinition;
    private String value;
    private DTCellValue52 dtCellValue52;

    public RowElement(ColumnDefinition columnDefinition, DTCellValue52 dtCellValue52) {
        this.columnDefinition = columnDefinition;
        this.dtCellValue52 = dtCellValue52;
        this.value = ColumnDefinition.getValue(this.dtCellValue52);
    }

    public RowElement(ColumnDefinition columnDefinition) {
        this.columnDefinition = columnDefinition;
        this.dtCellValue52 = new DTCellValue52();
        if (this.columnDefinition.getColumnDefinition() == ColumnType.rowNumber) {
            int rowNumber = new Integer(value).intValue();
            this.dtCellValue52.setNumericValue(rowNumber);

        } else if (this.columnDefinition.getColumnDefinition() == ColumnType.description) {
            this.dtCellValue52.setStringValue(value);

        } else if (this.columnDefinition.getColumnDefinition() == ColumnType.attribute) {
            this.dtCellValue52.setStringValue(value);

        } else if (this.columnDefinition.getColumnDefinition() == ColumnType.condition) {
            setValuedtCell(this.value);
        } else if (this.columnDefinition.getColumnDefinition() == ColumnType.action) {

        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ColumnDefinition getColumnDefinition() {
        return columnDefinition;
    }

    private void setValuedtCell(String aValue) {
        if (this.columnDefinition.getFieldType().equals(DTDataTypes52.STRING.toString())) {
            this.dtCellValue52.setStringValue(aValue);

        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_BIGDECIMAL.toString())) {
            this.dtCellValue52.setNumericValue(new BigDecimal(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_BIGINTEGER.toString())) {
            this.dtCellValue52.setNumericValue(new BigInteger(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_BYTE.toString())) {
            this.dtCellValue52.setNumericValue(new Byte(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_DOUBLE.toString())) {
            this.dtCellValue52.setNumericValue(new Double(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_FLOAT.toString())) {
            this.dtCellValue52.setNumericValue(new Float(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_INTEGER.toString())) {
            this.dtCellValue52.setNumericValue(new Integer(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_LONG.toString())) {
            this.dtCellValue52.setNumericValue(new Long(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.NUMERIC_SHORT.toString())) {
            this.dtCellValue52.setNumericValue(new Short(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.DATE.toString())) {
            this.dtCellValue52.setDateValue(new Date(aValue));
        } else if (this.columnDefinition.getFieldType().equals(DTDataTypes52.BOOLEAN.toString())) {
            this.dtCellValue52.setBooleanValue(new Boolean(aValue));
        }

    }

}
