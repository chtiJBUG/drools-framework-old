/**
 * 
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;

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

	protected String ruleName;
	protected String rulePackageName;

	/**
	 * 
	 */
	public DroolsRuleObject() {
	}

	/**
	 * @param ruleName
	 * @param rulePackageName
	 */
	protected DroolsRuleObject(String ruleName, String rulePackageName) {
		this.ruleName = ruleName;
		this.rulePackageName = rulePackageName;
	}

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @return the rulePackageName
	 */
	public String getRulePackageName() {
		return rulePackageName;
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

	public static DroolsRuleObject createDroolRuleObject(String ruleName, String rulePackageName) {
		return new DroolsRuleObject(ruleName, rulePackageName);
	}
}
