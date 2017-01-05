package com.vossie.elasticsearch.annotations.mappingparameters;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.metafields.ESAllField;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESDefault {
	public ESAllField _all() default @ESAllField();
}
