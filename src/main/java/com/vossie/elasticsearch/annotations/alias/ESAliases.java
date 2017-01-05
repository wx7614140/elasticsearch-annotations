package com.vossie.elasticsearch.annotations.alias;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ESAliases {
	public ESAlias[] aliases() default {};
}
