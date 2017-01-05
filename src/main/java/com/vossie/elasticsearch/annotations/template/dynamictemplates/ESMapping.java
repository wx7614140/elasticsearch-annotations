package com.vossie.elasticsearch.annotations.template.dynamictemplates;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.enums.FieldDatatype;
import com.vossie.elasticsearch.annotations.mappingparameters.ESMultiFieldType;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESMapping {
	 public FieldDatatype type() default FieldDatatype.NULL;
	 public ESMultiFieldType[] fields() default {};
}
