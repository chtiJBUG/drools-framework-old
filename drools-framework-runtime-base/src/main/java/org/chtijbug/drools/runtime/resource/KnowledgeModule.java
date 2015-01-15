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
    private final HistoryListener historyListener;
    private String version;
    private final int ruleBaseId;

    public KnowledgeModule(int ruleBaseId, HistoryListener historyListener, String packageName, String name) {
        this.ruleBaseId = ruleBaseId;
        this.historyListener = historyListener;
        this.packageName = packageName;
        this.name = name;
        this.kieServices = KieServices.Factory.get();
        this.kieResources = kieServices.getResources();
        this.kieFileSystem = kieServices.newKieFileSystem();
    }

    public void addAllFiles(List<String> filenames) {
        for (String filename : filenames) {
            addRuleFile(packageName + ".rules", filename);
        }
    }

    public void addRuleFile(String packageName, String ruleFile) {
        Resource resource = kieResources.newClassPathResource(ruleFile);
        packageName = packageName.replace(".", "/");
        String resourcePath = "src/main/resources/" + packageName + "/" + ruleFile;
        kieFileSystem.write(resourcePath, resource);
        if (historyListener != null)
            try {
                historyListener.fireEvent(
                        new KnowledgeBaseAddResourceEvent(
                                EventCounter.Next(), new Date(), this.ruleBaseId,
                                ruleFile, IOUtils.toString(resource.getInputStream())));
            } catch (IOException | DroolsChtijbugException e) {
                throw propagate(e);
            }
    }

    public KieContainer build() {
        if (version == null) {
            version = "0.0.1-SNAPSHOT";
        }
        ReleaseId releaseId = kieServices.newReleaseId(packageName, name, version);
        this.kieFileSystem.generateAndWritePomXML(releaseId);
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        return this.kieServices.newKieContainer(releaseId);
    }
}
