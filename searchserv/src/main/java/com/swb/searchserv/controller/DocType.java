package com.swb.searchserv.controller;

public enum DocType {
	PARTICULATE("particulate"), TEMPERATURE("temperature"),FLURIDE("fluride");
	private final String type;

	private DocType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

	public String getText() {
		return this.type;
	}

	public static DocType fromString(String type) {
		if (type != null) {
			for (DocType b : DocType.values()) {
				if (type.equalsIgnoreCase(b.type)) {
					return b;
				}
			}
		}
		return null;
	}
}
