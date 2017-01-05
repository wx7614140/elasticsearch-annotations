package com.vossie.elasticsearch.annotations.enums.fielddata;

public enum FieldataFilter {
	FREQUENCY {
    	@Override
    	public String toString() {
    		return "frequency";
    	}
    },
	REGEX {
    	@Override
    	public String toString() {
    		return "regex";
    	}
    }
}
