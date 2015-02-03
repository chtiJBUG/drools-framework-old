package org.chtijbug.drools.runtime.resource;

import org.apache.commons.io.IOUtils;
import org.chtijbug.drools.entity.history.EventCounter;
import org.chtijbug.drools.entity.history.knowledge.KnowledgeBaseAddResourceEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Throwables.propagate;

public class KnowledgeModule {

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final KieServices kieServices;

    private final KieResources kieResources;
    private final KieFileSystem kieFileSystem;
    private final KieRepository kieRepository;
    private final HistoryListener historyListener;
    private final EventCounter sharedCounter;

    private final Long ruleBaseId;
    private ReleaseId releaseId;
    private boolean fileBaseModule = false;

    public KnowledgeModule(Long ruleBaseId, HistoryListener historyListener, String groupId, String artifactId,String version, EventCounter sharedCounter) {
        this.ruleBaseId = ruleBaseId;
        this.historyListener = historyListener;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.kieServices = KieServices.Factory.get();
        this.kieRepository = kieServices.getRepository();
        this.kieResources = kieServices.getResources();
        this.kieFileSystem = kieServices.newKieFileSystem();
        this.sharedCounter = sharedCounter;
    }

    public void addAllFiles(List<String> filenames) {
        for (String filename : filenames) {
            addRuleFile(groupId + ".rules", filename);
        }
    }

    public void addRuleFile(String packageName, String ruleFile) {
        this.fileBaseModule = true;
        Resource resource = kieResources.newClassPathResource(ruleFile);
        packageName = packageName.replace(".", "/");
        String resourcePath = "src/main/resources/" + packageName + "/" + ruleFile;
        kieFileSystem.write(resourcePath, resource);
        if (historyListener != null)
            try {
                historyListener.fireEvent(
                        new KnowledgeBaseAddResourceEvent(
                                sharedCounter.next(), new Date(), this.ruleBaseId,
                                ruleFile, IOUtils.toString(resource.getInputStream())));
            } catch (IOException | DroolsChtijbugException e) {
                throw propagate(e);
            }
    }

    public KieContainer build() {

        this.releaseId = kieServices.newReleaseId(groupId, artifactId, version);
        if (fileBaseModule) {
            this.kieFileSystem.generateAndWritePomXML(releaseId);
            KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
            kb.buildAll();
            if (kb.getResults().hasMessages(Message.Level.ERROR)) {
                throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
            }
        }
        return this.kieServices.newKieContainer(releaseId);
    }

    public void addWorkbenchResource(String workbenchUrl, String username, String password) {
        try (WorkbenchClient client = new WorkbenchClient(workbenchUrl, username, password)) {
            Resource resource = kieServices.getResources().newInputStreamResource(client.getWorkbenchResource(this));
            this.kieRepository.addKieModule(resource);
            if (historyListener != null)
                try {
                    historyListener.fireEvent(
                            new KnowledgeBaseAddResourceEvent(
                                    sharedCounter.next(), new Date(), this.ruleBaseId,
                                    workbenchUrl,this.groupId,this.artifactId,this.version));
                } catch ( DroolsChtijbugException e) {
                    throw propagate(e);
                }
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

}
