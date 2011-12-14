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
public class DroolsNodeObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2149698078767524188L;
	private final String id;

	public DroolsNodeObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DroolsNodeObject other = (DroolsNodeObject) obj;
		if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

}
