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
import java.util.Date;
import java.util.List;

import static com.google.common.base.Throwables.propagate;

public class KnowledgeModule {

    private final String packageName;
    private final String name;
    private final KieServices kieServices;

    private final KieResources kieResources;
    private final KieFileSystem kieFileSystem;
    private final KieRepository kieRepository;
    private final HistoryListener historyListener;
    private final EventCounter sharedCounter;
    private String version;
    private final Long ruleBaseId;
    private ReleaseId releaseId;
    private boolean fileBaseModule = false;

    public KnowledgeModule(Long ruleBaseId, HistoryListener historyListener, String packageName, String name, EventCounter sharedCounter) {
        this.ruleBaseId = ruleBaseId;
        this.historyListener = historyListener;
        this.packageName = packageName;
        this.name = name;
        this.kieServices = KieServices.Factory.get();
        this.kieRepository = kieServices.getRepository();
        this.kieResources = kieServices.getResources();
        this.kieFileSystem = kieServices.newKieFileSystem();
        this.sharedCounter = sharedCounter;
    }

    public void addAllFiles(List<String> filenames) {
        for (String filename : filenames) {
            addRuleFile(packageName + ".rules", filename);
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
        if (version == null) {
            version = "1.0.0-SNAPSHOT";
        }
        this.releaseId = kieServices.newReleaseId(packageName, name, version);
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

    public void addWorkbenchResource(String version, String workbenchUrl, String username, String password) {
        try (WorkbenchClient client = new WorkbenchClient(workbenchUrl, username, password)) {
            this.version = version;
            Resource resource = kieServices.getResources().newInputStreamResource(client.getWorkbenchResource(this));
            this.kieRepository.addKieModule(resource);
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

}
