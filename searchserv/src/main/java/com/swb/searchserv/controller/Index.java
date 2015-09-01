package com.swb.searchserv.controller;

public enum Index {
	SENSOR_DATA("sensor_data"), META_DATA("swb_meta_data");
	private final String type;

	private Index(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

	public String getText() {
		return this.type;
	}

	public static Index fromString(String type) {
		if (type != null) {
			for (Index b : Index.values()) {
				if (type.equalsIgnoreCase(b.type)) {
					return b;
				}
			}
		}
		return null;
	}
}
