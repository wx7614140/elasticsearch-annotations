package com.vossie.elasticsearch.annotations.enums;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 12/12/2013
 * Time: 14:14
 */
public enum MetaFieldName {
	//Identity meta-fields
	_INDEX {
		@Override
		public String toString() {
			return "_index";
		}
	},
    _UID {
        @Override
        public String toString() {
            return "_uid";
        }
    },
    _ID {
        @Override
        public String toString() {
            return "_id";
        }
    },
    _TYPE {
        @Override
        public String toString() {
            return "_type";
        }
    },
//    Document source meta-fields
    _SOURCE {
        @Override
        public String toString() {
            return "_source";
        }
    },
    _SIZE {//插件提供的
    	@Override
    	public String toString() {
    		return "_size";
    	}
    },
//Indexing meta-fields
    _ALL {
        @Override
        public String toString() {
            return "_all";
        }
    },
    _FIELD_NAMES {
    	@Override
    	public String toString() {
    		return "_field_names";
    	}
    },
    @Deprecated
    _TIMESTAMP {//enabled: true或者false,default,不支持path ,format: epoch_millis||strictDateOptionalTime, ignore_missing true或者false
        @Override
        public String toString() {
            return "_timestamp";
        }
    },
    @Deprecated
    _TTL {//enabled default
        @Override
        public String toString() {
            return "_ttl";
        }
    },
//    Routing meta-fields

    _PARENT {
        @Override
        public String toString() {
            return "_parent";
        }
    },

    _ROUTING {
        @Override
        public String toString() {
            return "_routing";
        }
    } ,
//    Other meta-field
    _META {
        @Override
        public String toString() {
            return "_meta";
        }
    },
// Other field
//    _ANALYZER {
//    	@Override
//    	public String toString() {
//    		return "_analyzer";
//    	}
//    },
//    
//    _BOOST {
//        @Override
//        public String toString() {
//            return "_boost";
//        }
//    }
}
