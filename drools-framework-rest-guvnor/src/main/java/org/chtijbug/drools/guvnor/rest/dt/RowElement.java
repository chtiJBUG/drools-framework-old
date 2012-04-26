package org.chtijbug.drools.guvnor.rest.dt;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public class RowElement {
    private ColumnDefinition columnDefinition;
    private String value;

    public RowElement(ColumnDefinition columnDefinition, String value) {
        this.columnDefinition = columnDefinition;
        this.value = value;
    }

    public RowElement(ColumnDefinition columnDefinition) {
        this.columnDefinition = columnDefinition;
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

}
