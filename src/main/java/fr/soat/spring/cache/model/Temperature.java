package fr.soat.spring.cache.model;

import java.io.Serializable;

import com.google.common.base.Objects;

public class Temperature implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer value;
	private long validAt;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public long getValidAt() {
		return validAt;
	}

	public void setValidAt(long validAt) {
		this.validAt = validAt;
	}

	public int hashCode() {
		return Objects.hashCode(value, validAt);
	}

	public boolean equals(Object o) {
		return o instanceof Temperature && o.hashCode() == this.hashCode();
	}
}
