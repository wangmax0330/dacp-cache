package com.pikai.cache.model;

import org.springframework.util.ObjectUtils;

public class ModelKey {
	private final Class<?> modelClass;
	private final Object identifier;

	public ModelKey(Class<?> modelClass, Object identifier) {
		this.modelClass = modelClass;
		this.identifier = identifier;
	}

	public Class<?> getModelClass() {
		return this.modelClass;
	}

	public Object getIdentifier() {
		return this.identifier;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ModelKey)) {
			return false;
		}
		ModelKey otherKey = (ModelKey) other;
		return (ObjectUtils.nullSafeEquals(getModelClass(), otherKey.getModelClass()))
				&& (ObjectUtils.nullSafeEquals(getIdentifier(), otherKey.getIdentifier()));
	}

	public int hashCode() {
		int hashCode = getModelClass() == null ? 0 : getModelClass().hashCode();
		hashCode = 29 * hashCode + (getIdentifier() == null ? 0 : getIdentifier().hashCode());

		return hashCode;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Model");
		if (this.modelClass != null) {
			buf.append(this.modelClass.getName());
		}
		if (this.identifier != null) {
			buf.append(this.identifier.toString());
		}
		return buf.toString();
	}
}
