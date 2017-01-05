package com.vossie.elasticsearch.annotations.enums.fielddata;

public enum FielddataFilterFrequency {
	MIN {
    	@Override
    	public String toString() {
    		return "min";
    	}
    },
	MAX {
    	@Override
    	public String toString() {
    		return "max";
    	}
    },
	MIN_SEGMENT_SIZE {
    	@Override
    	public String toString() {
    		return "min_segment_size";
    	}
    },
}
