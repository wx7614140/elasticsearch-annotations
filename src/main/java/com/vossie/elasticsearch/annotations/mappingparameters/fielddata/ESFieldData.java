package com.vossie.elasticsearch.annotations.mappingparameters.fielddata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.LOCAL_VARIABLE)
public @interface ESFieldData {
	public String format() default Empty.NULL;//disabled|paged_bytes
	public String loading() default Empty.NULL;//lazy|eager|eager_global_ordinals
	public ESFieldDataFilter filter() default @ESFieldDataFilter();
		
}
