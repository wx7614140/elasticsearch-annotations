package com.vossie.elasticsearch.annotations.mappingparameters;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESLocation {
	public String type() default Empty.NULL; 
	public BooleanValue geohash() default BooleanValue.NULL;
	public BooleanValue geohash_prefix() default BooleanValue.NULL;
	public String geohash_precision() default Empty.NULL;
	public BooleanValue lat_lon() default BooleanValue.NULL;
}
