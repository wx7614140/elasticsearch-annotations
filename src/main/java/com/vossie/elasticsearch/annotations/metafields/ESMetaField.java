package com.vossie.elasticsearch.annotations.metafields;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESMetaField {
	public String Class() default Empty.NULL;
	public ESVersion version() default @ESVersion();
}
