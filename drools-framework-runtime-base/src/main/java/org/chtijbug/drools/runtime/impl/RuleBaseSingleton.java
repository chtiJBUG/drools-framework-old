/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Bertrand Gressier
 */
public class RuleBaseSingleton implements RuleBasePackage {

	static final Logger LOGGER = LoggerFactory
			.getLogger(RuleBaseSingleton.class);

	private KnowledgeBase kbase = null;

	private final List<DroolsResource> listResouces;

	public RuleBaseSingleton() {
		listResouces = new ArrayList<DroolsResource>();
	}

	@Override
	public RuleBaseSession createRuleBaseSession() {
		RuleBaseSession newRuleBaseSession = null;
		if (kbase != null) {
			StatefulKnowledgeSession newDroolsSession = kbase
					.newStatefulKnowledgeSession();
			newRuleBaseSession = new RuleBaseStatefullSession(newDroolsSession);
		} else {
			throw new UnsupportedOperationException("Kbase not initialized");
		}
		return newRuleBaseSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.chtijbug.drools.runtime.RuleBasePackage#addDroolsResouce(org.chtijbug
	 * .drools.runtime.resource.DroolsResource)
	 */
	@Override
	public void addDroolsResouce(DroolsResource res) {
		listResouces.add(res);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.chtijbug.drools.runtime.RuleBasePackage#createKBase()
	 */
	@Override
	public void createKBase() {

		if (kbase != null) {
			// TODO dispose all elements
		}

		try {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();

			for (DroolsResource res : listResouces) {
				kbuilder.add(res.getResource(), res.getResourceType());
			}

			kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		} catch (Exception e) {
			LOGGER.error("error to load Agent", e);
		}
	}
}
