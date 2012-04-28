package org.chtijbug.drools.guvnor.rest.dt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 26/04/12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class Row {
    private List<RowElement> rowElements = new ArrayList<RowElement>();

    public List<RowElement> getRowElements() {
        return rowElements;
    }
    public void addRowElement(RowElement newRowElement){
        this.rowElements.add(newRowElement);
    }
}
