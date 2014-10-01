/*
 * Copyright 2014 Pymma Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chtijbug.drools.runtime.mbeans;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.DroolsResource;

import javax.management.NotificationBroadcasterSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nheron
 */

public class RuleBaseSupervision extends NotificationBroadcasterSupport implements RuleBaseSupervisionMBean {

    private RuleBaseSingleton ruleBaseSingleton;

    public RuleBaseSupervision(RuleBaseSingleton ruleBaseSingleton) {
        super();
        this.ruleBaseSingleton = ruleBaseSingleton;

    }


    @Override
    public List<String> getDroolsRessource() {
        List<String> ll = new ArrayList<String>();
        for (DroolsResource d : ruleBaseSingleton.getListDroolsRessources()) {
            ll.add(d.toString());
        }
        return ll;
    }

    @Override
    public boolean isKbaseLoaded() {
        return ruleBaseSingleton.isKbaseLoaded();
    }

    @Override
    public void reLoadRuleBase() throws DroolsChtijbugException {
        ruleBaseSingleton.createKBase();
    }

    @Override
    public int getRuleBaseID() {
        return this.ruleBaseSingleton.getRuleBaseID();
    }


}
