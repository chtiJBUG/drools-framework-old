package org.chtijbug.drools.guvnor.rest;

import org.chtijbug.drools.common.jaxb.JAXBContextUtils;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.model.Snapshot;
import org.drools.guvnor.server.jaxrs.jaxb.SnapshotCreationData;
import org.drools.guvnor.server.jaxrs.jaxb.Snapshots;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Date: 16/04/13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class RulePackageManager {

    private GuvnorConnexionConfiguration configuration = null;

    public RulePackageManager(GuvnorConnexionConfiguration configuration) {
        this.configuration = configuration;
    }

    public void buildRulePackageByStatus(String snapshotName, String filter) throws ChtijbugDroolsRestException {
        SnapshotCreationData snapshotCreationData = new SnapshotCreationData();
        snapshotCreationData.setBuildMode("BuiltInSelector");
        snapshotCreationData.setEnableStatusSelector(true);
        snapshotCreationData.setStatusOperator("=");
        snapshotCreationData.setStatusDescriptionValue(filter);
        try {
            String path = format("%s/rest/packages/%s/snapshot/%s", this.configuration.getWebappName(), this.configuration.getPackageName(), snapshotName);

            String xmlObject = JAXBContextUtils.marshallObjectAsString(SnapshotCreationData.class, snapshotCreationData);
            GuvnorRestClient webClient = this.configuration.webClient();
            webClient.post(path, MediaType.APPLICATION_XML,xmlObject);
        } catch (JAXBException e) {
            throw new ChtijbugDroolsRestException(e);
        }
    }

    public List<Snapshot> getListSnaphots() throws ChtijbugDroolsRestException {
        List<Snapshot> result = new ArrayList<Snapshot>();
        try {
            String path = format("%s/rest/packages/%s/snapshots", this.configuration.getWebappName(), this.configuration.getPackageName());
            GuvnorRestClient webClient = this.configuration.webClient();
            Snapshots list = webClient.get(path, MediaType.APPLICATION_ATOM_XML, Snapshots.class);
            for (int i=0;i< list.getListNames().length;i++){
                Snapshot snapshot = new Snapshot(this.configuration.getPackageName(),list.getListNames()[i]);
                result.add(snapshot);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChtijbugDroolsRestException(e);
        }
        return result;
    }

}
