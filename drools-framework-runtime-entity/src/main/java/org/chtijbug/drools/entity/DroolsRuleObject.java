/**
 * 
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;

import org.drools.definition.rule.Rule;

/**
 * @author Bertrand Gressier
 * @date 14 déc. 2011
 * 
 * 
 */
public class DroolsRuleObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -716077698281963299L;

	private final Rule rule;

	/**
	 * Define a Rule Object with this name
	 * 
	 */
	protected DroolsRuleObject(Rule rule) {
		this.rule = rule;
	}

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return rule.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rule object with name :" + getRuleName();
	}

	/**
	 * @return
	 */
	public Rule getRule() {
		return rule;
	}

	public static DroolsRuleObject createDroolRuleObject(Rule rule) {
		return new DroolsRuleObject(rule);
	}
}
