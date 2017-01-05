package com.vossie.elasticsearch.annotations.metafields;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
@Deprecated
public @interface ESTimestampField {
	public BooleanValue enabled() default BooleanValue.NULL;
	public String Default() default Empty.NULL;
	public String format() default Empty.NULL;
	public BooleanValue ignore_missing() default BooleanValue.NULL;
}
