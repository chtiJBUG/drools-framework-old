package org.chtijbug.drools.runtime.resource;

public class Artifact {
    private final String groupId;
    private final String artifactId;
    private final String version;

    public Artifact(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artifact)) return false;

        Artifact artifact = (Artifact) o;

        if (!artifactId.equals(artifact.artifactId)) return false;
        if (!groupId.equals(artifact.groupId)) return false;
        return version.equals(artifact.version);

    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + artifactId.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }
}
