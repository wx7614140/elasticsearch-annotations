package com.vossie.elasticsearch.annotations.enums;

public enum LocationParameter {
	NAME {
        @Override
        public String toString() {
            return "location";
        }
    },
	TYPE {
    	@Override
    	public String toString() {
    		return "type";
    	}
    },
	GEOHASH {
    	@Override
    	public String toString() {
    		return "geohash";
    	}
    },
	GEOHASH_PREFIX {
    	@Override
    	public String toString() {
    		return "geohash_prefix";
    	}
    },
	GEOHASH_PRECISION {
    	@Override
    	public String toString() {
    		return "geohash_precision";
    	}
    },
	LAT_LON {
    	@Override
    	public String toString() {
    		return "lat_lon";
    	}
    };
	
}
