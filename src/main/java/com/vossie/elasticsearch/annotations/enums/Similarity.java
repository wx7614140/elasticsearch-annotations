package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

public enum Similarity {
	 NULL {
	        @Override
	        public String toString() {
	            return Empty.NULL;
	        }
	    },
	 BM25 {
	    	@Override
	    	public String toString() {
	    		return "BM25";
	    	}
	    },
	 TF_IDF {
	    	@Override
	    	public String toString() {
	    		return " TF/IDF";
	    	}
	    }
	 
}
