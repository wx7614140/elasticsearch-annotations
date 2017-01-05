package com.vossie.elasticsearch.annotations.settings.analyzer;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESFilter {
	public String _name();
	public String type() default Empty.NULL;
	public String stopwords() default Empty.NULL;
}
