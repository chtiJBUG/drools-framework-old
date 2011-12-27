/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;

/**
 * 
 * @author nheron
 */
public class DroolsFactObjectAttribute implements Serializable {

	private static final long serialVersionUID = 2251337648100424168L;
	private String attributeName;
	private String attributeValue;
	private String attributeType;

	/**
	 * 
	 */
	public DroolsFactObjectAttribute() {
	}

	public DroolsFactObjectAttribute(String attributeName, String attributeValue, String attributeType) {
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		this.attributeType = attributeType;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
}
