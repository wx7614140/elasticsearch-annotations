package com.vossie.elasticsearch.annotations.enums.fielddata;
public enum FielddataParams {
	FORMAT {
        @Override
        public String toString() {
            return "format";
        }
//        private  String DISABLED="disabled";
//        private  String PAGED_BYTES ="paged_bytes";
    },
	LOADING {
    	@Override
    	public String toString() {
    		return "loading";
    	}
//    	private  String LAZY="lazy";
//    	private  String EAGER="eager";
//    	private  String EAGER_GLOBAL_ORDINALS="eager_global_ordinals";
    },
	FILTER {
    	@Override
    	public String toString() {
    		return "filter";
    	}
    };
}
