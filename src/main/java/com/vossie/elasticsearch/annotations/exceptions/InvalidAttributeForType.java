package com.vossie.elasticsearch.annotations.exceptions;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 11:58
 */
public class InvalidAttributeForType extends AnnotationException {

    public InvalidAttributeForType(String typeName, String attributeName, String index) {
        super(String.format("The attribute %s is not valid for field type %s in index %s.", attributeName, typeName, index));
    }
}