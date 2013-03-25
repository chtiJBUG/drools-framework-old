package org.chtijbug.drools.guvnor.rest.model;

/**
 * Created by IntelliJ IDEA.
 * Date: 22/03/13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public enum AssetType {
    GuidedRule("brl"), RuleTemplate("template"), DecisionTable("gdst") ,Scenario("scenario");
    private String id;

    private AssetType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
