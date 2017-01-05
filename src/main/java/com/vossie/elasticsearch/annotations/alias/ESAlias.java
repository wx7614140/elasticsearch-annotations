package com.vossie.elasticsearch.annotations.alias;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESAlias {
	public String _name() ;
	public String filter() default Empty.NULL;
	public String indexRouting() default Empty.NULL;
	public String searchRouting() default Empty.NULL;
}
