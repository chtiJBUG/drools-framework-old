package org.chtijbug.kie.rest.backend;

import org.uberfire.backend.vfs.Path;
import org.uberfire.workbench.type.ResourceTypeDefinition;

/**
 * Created by nheron on 23/01/15.
 */
public class RestTypeDefinition implements ResourceTypeDefinition {
    @Override
    public String getShortName() {
        return "restFrameworkTypeElement";
    }

    @Override
    public String getDescription() {
        return "restFrameworkTypeElement";
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public String getSuffix() {
        return "";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSimpleWildcardPattern() {
        return "*";
    }

    @Override
    public boolean accept(Path path) {
        boolean result = false;
        if (  path.getFileName().endsWith( ".gdst" )){
            result=true;
        }
        return result;
    }
}
