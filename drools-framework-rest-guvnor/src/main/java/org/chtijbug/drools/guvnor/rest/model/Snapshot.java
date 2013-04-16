package org.chtijbug.drools.guvnor.rest.model;

/**
 * Created by IntelliJ IDEA.
 * Date: 16/04/13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class Snapshot {
    private String packageName;
    private String name;

    public Snapshot(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
        sb.append("Snapshot");
        sb.append("{packageName='").append(packageName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
