package com.vossie.elasticsearch.annotations.enums;

public enum DefaultParameter {
	ALL {
    	@Override
    	public String toString() {
    		return "_all";
    	}
    }
}
