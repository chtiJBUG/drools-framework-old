/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author nheron
 */
public class DroolsFactObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8185674445343213645L;
	private String fullClassName;
	private int hashCode;
	protected int version;
	private List<DroolsFactObjectAttribute> listfactObjectAttributes = new ArrayList<DroolsFactObjectAttribute>();
	private final transient Object realObject;

	/**
	 * 
	 */
	public DroolsFactObject() {
		realObject = null;
	}

	public DroolsFactObject(Object realObject, int version) {
		this.realObject = realObject;
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FactObject [fullClassName=" + fullClassName + ", hashCode=" + hashCode + ", version=" + version + ", listfactObjectAttributes=" + listfactObjectAttributes
				+ ", realObject=" + realObject + "]";
	}

	public int getObjectVersion() {
		return version;
	}

	public int getNextObjectVersion() {
		return version + 1;
	}

	public List<DroolsFactObjectAttribute> getListfactObjectAttributes() {
		return listfactObjectAttributes;
	}

	public void setListfactObjectAttributes(List<DroolsFactObjectAttribute> listfactObjectAttributes) {
		this.listfactObjectAttributes = listfactObjectAttributes;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
}
