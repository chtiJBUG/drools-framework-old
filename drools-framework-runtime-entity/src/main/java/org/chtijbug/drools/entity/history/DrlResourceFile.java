package org.chtijbug.drools.entity.history;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 23/01/14
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class DrlResourceFile implements Serializable ,ResourceFile{
    private String fileName;
    private String content;

    public DrlResourceFile() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DrlRessourceFile{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
