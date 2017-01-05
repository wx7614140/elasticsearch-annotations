package com.vossie.elasticsearch.annotations.template;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(LOCAL_VARIABLE)
public @interface ESTemplate {
//	public String template() default Empty.NULL;
//	public String settings() default Empty.NULL;
	
}
