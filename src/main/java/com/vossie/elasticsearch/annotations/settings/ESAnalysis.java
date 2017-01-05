package com.vossie.elasticsearch.annotations.settings;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.settings.analyzer.ESAnalyzer;
import com.vossie.elasticsearch.annotations.settings.analyzer.ESFilter;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
@Inherited
public @interface ESAnalysis {
	public ESAnalyzer[] analyzer() default {};
	public ESFilter[] filter() default {};
}
