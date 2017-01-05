package com.vossie.elasticsearch.annotations.common;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

import com.vossie.elasticsearch.annotations.template.dynamictemplates.ESDynamicTemplates;
import com.vossie.elasticsearch.annotations.util.AttributeNameHelper;

public class ElasticsearchDynamicTemplate {
	private boolean isArray;
	private String templateName;
	private Map<String, Object> attributes;
	private ESDynamicTemplates dynamicTemplates;
	public ElasticsearchDynamicTemplate(String templateName, ESDynamicTemplates dynamicTemplates,boolean isArray) {
		this.templateName = templateName;
		this.dynamicTemplates = dynamicTemplates;
		this.isArray=isArray;
		setAttributes(this.dynamicTemplates);
	}
	public ElasticsearchDynamicTemplate(String templateName, ESDynamicTemplates dynamicTemplates) {
		this.templateName = templateName;
		this.dynamicTemplates = dynamicTemplates;
		setAttributes(this.dynamicTemplates);
	}
	private void setAttributes(Annotation annotation) {
		// TODO Auto-generated method stub

        Map<String, Object> allAttributes = AnnotationUtils.getAnnotationAttributes(annotation);

        Map<String, Object> tempAttributes = new HashMap<>();

        for(String key : allAttributes.keySet()) {

            String attributeName = AttributeNameHelper.getAttributeName(annotation, key);
            Object value = allAttributes.get(key);

            if(key.toString() == "_name")
                continue;

            if (value.getClass().isArray()){
                if (((Object[]) value).length ==0)
                    continue;
            }

            if(value.toString().equals(Empty.NULL))
                continue;

            if(value.toString().equals("0") || value.toString().equals("0.0"))
                continue;

            if(value.toString().equals(Empty.class.toString()))
                continue;

            tempAttributes.put(attributeName, value);
        }

        this.attributes = Collections.unmodifiableMap(tempAttributes);
	}
	public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public String getTemplateName() {
        return templateName;
    }


    public boolean isArray() {
        return this.isArray;
    }
}
