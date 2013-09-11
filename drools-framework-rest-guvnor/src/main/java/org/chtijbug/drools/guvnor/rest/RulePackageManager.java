package org.chtijbug.drools.guvnor.rest;

import org.apache.cxf.jaxrs.client.WebClient;
import org.chtijbug.drools.common.jaxb.JAXBContextUtils;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.model.Snapshot;
import org.drools.guvnor.server.jaxrs.jaxb.SnapshotCreationData;
import org.drools.guvnor.server.jaxrs.jaxb.Snapshots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(RulePackageManager.class);

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
            String path = format("%s/rest/packages/%s/snapshot/%s/param", this.configuration.getWebappName(), this.configuration.getPackageName(), snapshotName);

            String xmlObject = JAXBContextUtils.marshallObjectAsString(SnapshotCreationData.class, snapshotCreationData);
            WebClient webClient = this.configuration.webClient();
            this.configuration.noTimeout(webClient);
            webClient.path(path)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .post(xmlObject);
        } catch (JAXBException e) {
            throw new ChtijbugDroolsRestException(e);
        }
    }

    public List<Snapshot> getListSnaphots() throws ChtijbugDroolsRestException {
        List<Snapshot> result = new ArrayList<Snapshot>();
        try {
            String path = format("%s/rest/packages/%s/snapshots", this.configuration.getWebappName(), this.configuration.getPackageName());
            WebClient webClient = this.configuration.webClient();
            this.configuration.noTimeout(webClient);
            Snapshots list = webClient.path(path)
                    .type(MediaType.APPLICATION_ATOM_XML)
                    .get(Snapshots.class);
            for (int i=0;i< list.getListNames().length;i++){
                Snapshot snapshot = new Snapshot(this.configuration.getPackageName(),list.getListNames()[i]);
                result.add(snapshot);
            }
        } catch (Exception e) {
            throw new ChtijbugDroolsRestException(e);
        }
        return result;
    }

}
