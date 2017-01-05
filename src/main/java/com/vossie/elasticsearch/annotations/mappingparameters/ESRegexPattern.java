package com.vossie.elasticsearch.annotations.mappingparameters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.LOCAL_VARIABLE)
public @interface ESRegexPattern {
	public String pattern() default Empty.NULL;
}
