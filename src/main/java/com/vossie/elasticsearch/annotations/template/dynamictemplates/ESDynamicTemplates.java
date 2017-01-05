package com.vossie.elasticsearch.annotations.template.dynamictemplates;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.FieldDatatype;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESDynamicTemplates {
	public String _name() default Empty.NULL;
	public FieldDatatype match_mapping_type() default FieldDatatype.NULL;
	public String match() default Empty.NULL;
	public String match_pattern() default Empty.NULL;
	public String unmatch() default Empty.NULL;
	public String path_match() default Empty.NULL;
	public String path_unmatch() default Empty.NULL;
	public ESMapping mapping() default @ESMapping();
}
