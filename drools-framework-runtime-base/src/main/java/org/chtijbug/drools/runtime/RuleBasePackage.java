/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime;

import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.chtijbug.drools.runtime.resource.DroolsResource;

import java.util.List;

/**
 * @author nheron
 */
public interface RuleBasePackage {


    RuleBaseSession createRuleBaseSession() throws DroolsChtijbugException;

    RuleBaseSession createRuleBaseSession(int maxNumberRulesToExecute) throws DroolsChtijbugException;


    public void createKBase(DroolsResource... res) throws DroolsChtijbugException;

    public void createKBase(List<DroolsResource> res) throws DroolsChtijbugException;

    public void RecreateKBaseWithNewRessources(DroolsResource... res) throws DroolsChtijbugException;

    public void RecreateKBaseWithNewRessources(List<DroolsResource> res) throws DroolsChtijbugException;

    public void ReloadWithSameRessources() throws DroolsChtijbugException;

    public HistoryListener getHistoryListener();

    public int getRuleBaseID();

    public void dispose();

    void cleanup();
}
