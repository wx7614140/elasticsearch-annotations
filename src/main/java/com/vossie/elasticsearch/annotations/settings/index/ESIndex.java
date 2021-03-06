package com.vossie.elasticsearch.annotations.settings.index;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESIndex {
	public String number_of_shards() default Empty.NULL;
	public String number_of_replicas() default Empty.NULL;
}
