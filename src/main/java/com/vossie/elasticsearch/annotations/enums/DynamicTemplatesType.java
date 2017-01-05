package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

public enum DynamicTemplatesType {
	NULL {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    },
	INTEGERS {
    	@Override
    	public String toString() {
    		return "integers";
    	}
    },
	STRINGS {
    	@Override
    	public String toString() {
    		return "strings";
    	}
    }
}
