package com.vossie.elasticsearch.annotations.common;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

import com.vossie.elasticsearch.annotations.alias.ESAlias;
import com.vossie.elasticsearch.annotations.alias.ESAliases;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.util.AttributeNameHelper;

public class ElasticsearchAlias {
	private final ESAliases elasticsearchAlias;
	public ElasticsearchAlias(Class<?> clazz) {
		this.elasticsearchAlias=getElasticsearchAlias(clazz);
	}
	public ElasticsearchAlias( ESAliases elasticsearchAliases) {
		this.elasticsearchAlias=elasticsearchAliases;
	}
	public String[] getStringAliases(){
		if(this.elasticsearchAlias==null||this.elasticsearchAlias.aliases()==null||this.elasticsearchAlias.aliases().length==0)
			return new String[]{};
		String[] aliasName=new String[this.elasticsearchAlias.aliases().length];
		int i=0;
		for ( ESAlias alias:this.elasticsearchAlias.aliases()){
			aliasName[i]=alias._name();
			i++;
		}
    	return aliasName;
    }
	public ESAlias[] getAliases(){
		
		return this.elasticsearchAlias.aliases();
	}
	public Map<String,Map<String, Object>> getAliasesMap(){
		Map<String,Map<String, Object>> map= new LinkedHashMap<String,Map<String, Object>>();
		for(ESAlias alias:getAliases()){
			map.put(alias._name(), setAttributes(alias));
		}
		return map;
	}
	 private static ESAliases getElasticsearchAlias(Class<?> clazz) {
	    	return clazz.getAnnotation(ESAliases.class);
	    }
	 private static Map<String, Object> setAttributes(Annotation annotation) {
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
	            if(value.toString().equals(BooleanValue.NULL))
	            	continue;

	            if(value.toString().equals("0") || value.toString().equals("0.0"))
	                continue;

	            if(value.toString().equals(Empty.class.toString()))
	                continue;

	            tempAttributes.put(attributeName, value);
	        }

	       return tempAttributes;
		}
}
