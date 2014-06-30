package org.chtijbug.drools.guvnor.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * _____________________________________________
 * This class is located in the following :<br/>
 * <ul>
 * <li>Project : rules-management-support</li>
 * <li>Package : com.axonactive.decision</li>
 * </ul>
 * Created by : Samuel
 * Creation date : 04/03/13
 * _____________________________________________
 * TODO Add specific JAVADOC for
 */
public class Asset {
    /** Busines Package name where this asset is located */
    private String packageName;
    /** Business Asset Name */
    private String name;
    /** Business Asset Managed Version */
    private String status;
    /** Business Asset Type */
    private String type;

    private String summary;

    private String versionNumber;

    private List<AssetCategory> categories = new ArrayList<AssetCategory>();

    public Asset() {
        // nop
    }

    public Asset(String packageName, String name, String status, String type) {
        this.packageName = packageName;
        this.name = name;
        this.status = status;
        this.type = type;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<AssetCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<AssetCategory> categories) {
        this.categories = categories;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Asset{");
        sb.append("packageName='").append(packageName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", versionNumber='").append(versionNumber).append('\'');
        sb.append(", categories=").append(categories);
        sb.append('}');
        return sb.toString();
    }
}
