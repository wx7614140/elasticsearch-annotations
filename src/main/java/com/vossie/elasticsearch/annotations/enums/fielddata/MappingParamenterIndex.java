package com.vossie.elasticsearch.annotations.enums.fielddata;

public enum MappingParamenterIndex {
	NO {
	        @Override
	        public String toString() {
	            return "no";
	        }
	    },
	NOT_ANALYZED {
	    	@Override
	    	public String toString() {
	    		return "not_analyzed";
	    	}
	    },
	ANALYZED {
	    	@Override
	    	public String toString() {
	    		return "analyzed";
	    	}
	    };
}
