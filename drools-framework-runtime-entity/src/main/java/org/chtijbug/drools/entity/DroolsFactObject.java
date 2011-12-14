/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chtijbug.drools.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author nheron
 */
public class DroolsFactObject {

	static final Logger LOGGER = LoggerFactory.getLogger(DroolsFactObject.class);
	private String fullClassName;
	private int hashCode;
	private final int version;
	private List<DroolsFactObjectAttribute> listfactObjectAttributes = new ArrayList<DroolsFactObjectAttribute>();
	private final Object realObject;

	public DroolsFactObject(Object realObject, int version) {
		this.realObject = realObject;
		this.version = version;
		this.fullClassName = realObject.getClass().getCanonicalName();
		this.hashCode = realObject.hashCode();
		try {
			this.introspect();
		} catch (Exception e) {
			LOGGER.error("Not possible to introspect {}", realObject);
		}
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

	private void introspect() throws Exception {
		BeanMap m = new BeanMap(this.realObject);
		for (Object para : m.keySet()) {
			if (!para.toString().equals("class")) {
				DroolsFactObjectAttribute attribute = new DroolsFactObjectAttribute(para.toString(), m.get(para).toString(), m.get(para).getClass().getSimpleName());
				this.listfactObjectAttributes.add(attribute);
			}

		}
	}

	public static DroolsFactObject createFactObject(Object o) {
		return createFactObject(o, 0);
	}

	public static DroolsFactObject createFactObject(Object o, int version) {
		DroolsFactObject createFactObject = null;
		if (o != null) {
			createFactObject = new DroolsFactObject(o, version);
		}
		return createFactObject;
	}
}
