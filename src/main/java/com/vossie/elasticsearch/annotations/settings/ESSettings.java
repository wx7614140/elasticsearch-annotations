package com.vossie.elasticsearch.annotations.settings;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.settings.index.ESIndex;

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
@Inherited
public @interface ESSettings {
	public ESIndex index() default @ESIndex();
}
