package com.vossie.elasticsearch.annotations.mappingparameters.fielddata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vossie.elasticsearch.annotations.mappingparameters.ESRegexPattern;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:28
 *
 * It is possible to control which field values are loaded into memory, which is particularly useful for faceting
 * on string fields, using fielddata filters, which are explained in detail in the Fielddata section.
 * Fielddata filters can exclude terms which do not match a regex, or which don’t
 * fall between a min and max frequency range:
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html#fielddata-filters
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.LOCAL_VARIABLE)
public @interface ESFieldDataFilter {

    // Todo: implement the data filter.

    public ESRegexPattern regexPattern() default @ESRegexPattern();

//    public double frequencyMin();
//
//    public double frequencyMax();
//
//    public double frequencyMinSegmentSize();
    public ESFieldDataFrequency frequency() default @ESFieldDataFrequency();
}
