package com.vossie.elasticsearch.annotations.metafields;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.enums.BooleanValue;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESSourceField {
	public BooleanValue enabled() default BooleanValue.NULL;
	public String[] includes() default {};
	public String[] excludes() default {};
}
