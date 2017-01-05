package com.vossie.elasticsearch.annotations.enums;

public enum AllParameter {
	ENABLED {
		@Override
		public String toString() {
			return "enabled";
		}
	},
	STORE {
		@Override
		public String toString() {
			return "store";
		}
	},
	NAME {
		@Override
		public String toString() {
			return "_all";
		}
	}
}
