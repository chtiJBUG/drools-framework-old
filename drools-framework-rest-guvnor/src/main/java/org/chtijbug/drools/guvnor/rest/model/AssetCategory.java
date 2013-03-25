package org.chtijbug.drools.guvnor.rest.model;

/**
 * Created by IntelliJ IDEA.
 * Date: 22/03/13
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class AssetCategory {
    String name;

    public AssetCategory() {
    }
    public AssetCategory(String name) {
         this.name = name;
     }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("AssetCategory");
        sb.append("{name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
