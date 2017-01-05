package com.vossie.elasticsearch.annotations.mappingparameters.fielddata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.LOCAL_VARIABLE)
public @interface ESFieldDataFrequency {
	public String min() default Empty.NULL;
	public String max() default Empty.NULL;
	public String min_segment_size() default Empty.NULL;
}
