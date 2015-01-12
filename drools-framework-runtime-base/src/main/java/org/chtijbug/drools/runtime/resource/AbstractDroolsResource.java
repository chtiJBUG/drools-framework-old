package org.chtijbug.drools.runtime.resource;

import java.io.InputStream;

public abstract class AbstractDroolsResource implements DroolsResource {


    private final Artifact artifact;

    protected AbstractDroolsResource(Artifact artifact) {
        this.artifact = artifact;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }
}
